package com.wtf.listener;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Observable;

import com.wtf.commons.Configuration;
import com.wtf.commons.Entry;
import com.wtf.commons.ReceiverFactory;
import com.wtf.commons.RegistrySingleton;
import com.wtf.comunications.Receiver;
import com.wtf.comunications.messages.Message;
import com.wtf.comunications.messages.ReqDispatcherAskTempMessage;
import com.wtf.comunications.messages.ReqDispatcherUpFrecuencyMessage;
import com.wtf.comunications.messages.RespDispatcherAskTempMessage;
import com.wtf.comunications.messages.RespDispatcherRegisterMessage;
import com.wtf.comunications.messages.RespDispatcherUnRegisterMessage;
import com.wtf.services.Agent;

public class AppListener  implements Runnable  {

	private Receiver receiver;
	private Agent agent; 

	public AppListener(Agent agent){
		this.agent = agent;
		receiver = ReceiverFactory.get(Integer.parseInt(Configuration.PORT), Configuration.PROTOCOL);
	}

	@Override
	public void run() {
		while (true) {
			Message message;
			try {
				message = receiver.receiveMessage();
				manejarMessage(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}


	@SuppressWarnings("unchecked")
	private void manejarMessage(Message message) throws IOException {
		if (message instanceof RespDispatcherRegisterMessage) {
			System.out.println("Recibiendo mensaje RespDispatcherRegisterMessage");
			RespDispatcherRegisterMessage response = ((RespDispatcherRegisterMessage) message);			
			agent.setFrecuency(response.getFrecuency());
			RegistrySingleton.getInstance().putAll(( Hashtable<String,Entry>)message.getData());
			 // trigger notification
	         //notifyObservers(agent.getFrecuency());
		} else if (message instanceof RespDispatcherUnRegisterMessage) {		
			System.out.println("Recibiendo mensaje RespDispatcherUnRegisterMessage");
			RegistrySingleton.getInstance().putAll(( Hashtable<String,Entry>)message.getData());
		}else if (message instanceof ReqDispatcherUpFrecuencyMessage) {
			System.out.println("Recibiendo mensaje ReqDispatcherUpFrecuencyMessage");
			agent.setFrecuency(Integer.valueOf(message.getData().toString()));
		} else if (message instanceof ReqDispatcherAskTempMessage) {
			System.out.println("Recibiendo mensaje ReqDispatcherAskTempMessage");
			agent.replyTemperature(); 
		} else if (message instanceof RespDispatcherAskTempMessage) {
			//TODO: como se le pasa la informacion al agente 
		}
		
	}
}
