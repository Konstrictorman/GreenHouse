package com.wtf.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.wtf.comunications.Forwarder;
import com.wtf.comunications.Receiver;

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
	private Receiver receiver;
	private Map<Calendar, Float> tempRegistry;
	private TimerTask timerTask;
	private Timer timer;
	
	
	public Agent(String name, String address, String port) {
		super();
		this.name = name;
		this.address = address;
		this.port = port;
		forwarder = new Forwarder();
		receiver = new Receiver();
		tempRegistry = new LinkedHashMap<Calendar, Float>() {
			private static final long serialVersionUID = -5906100478003476286L;
			private static final int STACK_SIZE=5;
			
			public boolean removeEldestEntry(Map.Entry<Calendar, Float> eldest) {
				return size() > STACK_SIZE;
			}
		};
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
	
	public void updateFrecuency(int newFrecuency) {
		log.info("Cambiando frecuencia de "+getFrecuency()+" a "+newFrecuency);
		setFrecuency(newFrecuency);
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
			log.info(formatter.format(c.getTime()) +" -- " + temp+" �C");
		}
	}
	
	public void askTemperature(String name) {
		/*
		 * Preguntar al dispatcher d�nde se ubica el invernadero de nombre name
		 * y establecer conexi�n con este y enviar petici�n
		 */
		log.info("Preguntando temperatura del invernadero "+name);
	}
	
	public void askAllTemperature() {
		/*
		 * Preguntar al dispatcher d�nde se ubican los dem�s invernaderos
		 * y establecer conexi�n con estos y enviar petici�n a c/u
		 */	
		log.info("Preguntando temperatura de todos los invernaderos ");
	}
	
	public void register() {
		//Debe ir a registrarse con el dispatcher y establecer su frecuencia
		
	}
	
	public void unregister() {
		
	}
	
	
	public static void main(String[] args) {
		Agent a = new Agent("Medell�n","localhost","369");
		a.setFrecuency(10);
		//float f = a.calculateTemperature();
		//log.info("Temp: "+f+" C");
		a.measureTemperature();	
		
		String input = "";
		Scanner scanner = new Scanner(System.in);
		byte cont = 0;
		//System.out.println("Type your command :");
		do {
			String[] params = input.split(" ");
			if (params.length > 0) {
				if (params[0].equalsIgnoreCase("getTemp")) {
					if (params.length == 1) {
						a.printRegistry();	
					} else {
						for (byte b=1; b<params.length; b++) {
							String s = params[b];
							if (s.equalsIgnoreCase("all")) {
								a.askAllTemperature();
							} else {
								a.askTemperature(s);
							}
						}
					}
				} else if (params[0].equalsIgnoreCase("newFreq")) {
					if (params.length != 2) {
						log.error("ERROR Bad command or missing arguments !");
					} else {
						String freq = params[1];
						try {
							int f = Integer.parseInt(freq);
							a.updateFrecuency(f);
						} catch (NumberFormatException nfe) {
							log.error("ERROR argument is not number type !");
						}
					}
					
				} else {
					if (cont++ > 0) {
						log.error("ERROR Bad command !");
					}
				}
			}
			
			log.info("Type your command :");	
			input = scanner.nextLine();
		} while (!input.equals("exit"));
		log.warn("Shutting down greenhouse "+ a.name);
		log.warn("Bye !");
		scanner.close();
		a.timer.cancel();
		a.timer.purge();
		a.timerTask.cancel();
		
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

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public Map<Calendar, Float> getTempRegistry() {
		return tempRegistry;
	}

	public void setTempRegistry(Map<Calendar, Float> tempRegistry) {
		this.tempRegistry = tempRegistry;
	}


	
	
	

}