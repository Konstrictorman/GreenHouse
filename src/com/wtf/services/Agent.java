package com.wtf.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.wtf.commons.Configuration;
import com.wtf.commons.Entry;
import com.wtf.commons.ForwarderFactory;
import com.wtf.commons.RegistrySingleton;
import com.wtf.comunications.Forwarder;
import com.wtf.comunications.messages.Message;
import com.wtf.comunications.messages.ReqDispatcherAskTempMessage;
import com.wtf.comunications.messages.ReqDispatcherRegisterMessage;
import com.wtf.comunications.messages.ReqDispatcherUnRegisterMessage;
import com.wtf.comunications.messages.ReqDispatcherUpFrecuencyMessage;
import com.wtf.comunications.messages.RespDispatcherAskTempMessage;
import com.wtf.listener.AppListener;

public class Agent {
	
    static {
        System.setProperty("log.name", System.getProperty("user.dir") + "/logs/log.log");
    }	
	
	private static Logger log = Logger.getLogger(Agent.class.getName());
	
    public static DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private int frecuency;
	private String name;
	private String address;
	private String port;
	private Forwarder forwarder;	
	private Map<Calendar, Float> tempRegistry;
	private TimerTask timerTask;
	private Timer timer;
	
	
	public Agent(String name, String address, String port) {
		super();
		this.startUp();
		this.name = name;
		this.address = address;
		this.port = port;
		forwarder = ForwarderFactory.get();		
		ExecutorService service = Executors.newFixedThreadPool(10);
		service.submit(new AppListener(this));
		tempRegistry = new LinkedHashMap<Calendar, Float>() {
			private static final long serialVersionUID = -5906100478003476286L;
			private static final int STACK_SIZE=5;
			
			public boolean removeEldestEntry(Map.Entry<Calendar, Float> eldest) {
				return size() > STACK_SIZE;
			}
		};		
	}
	
	private void startUp(){
		Entry theEntry = new Entry(Configuration.IP , Integer.valueOf(Configuration.PORT), Configuration.PROTOCOL);
		RegistrySingleton.getInstance().put(Configuration.lOCALHOST, theEntry);
		Entry entry = new Entry(Configuration.IP_DISPATCHER , 
				Integer.valueOf(Configuration.PORT_DISPATCHER), Configuration.PROTOCOL);
		RegistrySingleton.getInstance().put(Configuration.DISPATCHER, entry);
	}
	
	private float calculateTemperature() {
		float temp = 0f;
		int max = 35;
		int min = -10;
		temp = (float) (min + (max - min) * Math.random());
		return temp;
	}
	
	public void measureTemperature() {
		if (timer == null) {
			log.info("Frec: "+getFrecuency());
			timer = new Timer(); 
			timerTask = new TimerTask() {
				
				@Override
				public void run() {
					float temp = calculateTemperature();
					Calendar now = Calendar.getInstance();
					synchronized  (this) {
						tempRegistry.put(now, temp);	
					}
					
					//log.info("Temperatura para "+name+" :"+temp);
				}
			};
			
			timer.schedule(timerTask, 0, getFrecuency()*1000);
		}		
	}
	
	public void updateFrecuency(int newFrecuency) throws IOException {
		log.info("Cambiando frecuencia de "+getFrecuency()+" a "+newFrecuency);
		setFrecuency(newFrecuency);
		//Envia mensaje de cambio de frecuencia a todas las estaciones inclusive al Dispatcher 
		for (java.util.Map.Entry<String, Entry> station :  RegistrySingleton.getInstance().getAll().entrySet()) {
			if (!station.getKey().equals(Configuration.lOCALHOST)) {		
				ReqDispatcherUpFrecuencyMessage msg = new ReqDispatcherUpFrecuencyMessage(Configuration.lOCALHOST,newFrecuency) ;		
				forwarder.sendMessage(station.getKey(), msg);
			}
		}		
		
		timerTask.cancel();
		//timer.schedule(timerTask, 0, getFrecuency()*1000);
		timer.cancel();
		timer.purge();
		timer = null;
		measureTemperature();
		
	}
	
	public synchronized void printRegistry() {
		Set<Calendar> cals = tempRegistry.keySet();
		Iterator<Calendar> it = cals.iterator();
		log.info("Registro de temperaturas en " + name);
		while (it.hasNext()) {
			Calendar c = it.next();
			Float temp = tempRegistry.get(c);
			log.info(formatter.format(c.getTime()) +" -- " + temp+" ºC");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void replyTemperature() throws IOException{
		//TODO: como se v a ha enviar la temperatura
		RespDispatcherAskTempMessage msg = new RespDispatcherAskTempMessage(Configuration.lOCALHOST, new ArrayList());
		forwarder.sendMessage(name , msg);		
	}
	
	public void askTemperature(String name) throws IOException {
		/*
		 * Preguntar al dispatcher dónde se ubica el invernadero de nombre name
		 * y establecer conexión con este y enviar petición
		 */
		log.info("Preguntando temperatura del invernadero "+name);
		ReqDispatcherAskTempMessage msg = new ReqDispatcherAskTempMessage(Configuration.lOCALHOST, "");
		forwarder.sendMessage(name , msg);		
	}
	
	public void askAllTemperature() throws IOException {
		/*
		 * Preguntar al dispatcher dónde se ubican los demás invernaderos
		 * y establecer conexión con estos y enviar petición a c/u
		 */	
		log.info("Preguntando temperatura de todos los invernaderos ");
		for (java.util.Map.Entry<String, Entry> station :  RegistrySingleton.getInstance().getAll().entrySet()) {
			if (!station.getKey().equals(Configuration.lOCALHOST)) {		
				ReqDispatcherAskTempMessage msg = new ReqDispatcherAskTempMessage(Configuration.lOCALHOST, "");
				forwarder.sendMessage(name , msg);
			}
		}
	}
	
	public void shutDown() throws IOException {
		timer.cancel();
		timer.purge();
		timerTask.cancel();
		unregister();
	}
	
	public void register() throws IOException  {		
		//Debe ir a registrarse con el dispatcher y establecer su frecuencia
		ReqDispatcherRegisterMessage msg = new ReqDispatcherRegisterMessage(Configuration.lOCALHOST, "") ; 
		msg.setIpAddress(Configuration.IP);
		msg.setPort(Integer.valueOf(Configuration.PORT));		
		forwarder.sendMessage(Configuration.DISPATCHER, msg);
	}

	public void unregister() throws IOException {
		Message msg = new ReqDispatcherUnRegisterMessage(Configuration.lOCALHOST, "") ;				
		forwarder.sendMessage(Configuration.DISPATCHER, msg);
	}
	
	
	public static void main(String[] args) {
		Agent a = new Agent("Medellín","localhost","369");
		a.setFrecuency(10);
		//float f = a.calculateTemperature();
		//log.info("Temp: "+f+" C");
		a.measureTemperature();	
		

		
		/*
		try {
			Thread.sleep(30000);
		} catch (InterruptedException ie) {
			log.error(ie.getMessage());
		}
		a.printRegistry();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException ie) {
			log.error(ie.getMessage());
		}		
		a.updateFrecuency(5);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ie) {
			log.error(ie.getMessage());
		}		
		a.printRegistry();
		try {
			Thread.sleep(15000);
		} catch (InterruptedException ie) {
			log.error(ie.getMessage());
		}		
		a.printRegistry();
		*/		
	}

	public int getFrecuency() {
		return frecuency;
	}

	public void setFrecuency(int frecuency) {
		this.frecuency = frecuency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public Forwarder getForwarder() {
		return forwarder;
	}

	public void setForwarder(Forwarder forwarder) {
		this.forwarder = forwarder;
	}

	public Map<Calendar, Float> getTempRegistry() {
		return tempRegistry;
	}

	public void setTempRegistry(Map<Calendar, Float> tempRegistry) {
		this.tempRegistry = tempRegistry;
	}


	
	
	

}
