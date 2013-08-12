package com.wtf.services;

import java.util.Calendar;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.wtf.comunications.Forwarder;
import com.wtf.comunications.Receiver;

public class Agent {
	
	private static Logger log = Logger.getLogger(Agent.class.getName());
	
	private int frecuency;
	private String name;
	private String address;
	private String port;
	private Forwarder forwarder;
	private Receiver receiver;
	private HashMap<Calendar, Float> tempRegistry;
	
	
	public Agent(String name, String address, String port) {
		super();
		this.name = name;
		this.address = address;
		this.port = port;
		forwarder = new Forwarder();
		receiver = new Receiver();
	}
	
	public void askTemperature(String name) {
		/*
		 * Preguntar al dispatcher d�nde se ubica el invernadero de nombre name
		 * y establecer conexi�n con este y enviar petici�n
		 */
		
	}
	
	public void askAllTemperature() {
		/*
		 * Preguntar al dispatcher d�nde se ubican los dem�s invernaderos
		 * y establecer conexi�n con estos y enviar petici�n a c/u
		 */		
	}
	
	public void register() {
		//Debe ir a registrarse con el dispatcher y establecer su frecuencia
		
	}
	
	public void unregister() {
		
	}
	
	

}
