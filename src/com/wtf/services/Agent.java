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
		 * Preguntar al dispatcher dónde se ubica el invernadero de nombre name
		 * y establecer conexión con este y enviar petición
		 */
		
	}
	
	public void askAllTemperature() {
		/*
		 * Preguntar al dispatcher dónde se ubican los demás invernaderos
		 * y establecer conexión con estos y enviar petición a c/u
		 */		
	}
	
	public void register() {
		//Debe ir a registrarse con el dispatcher y establecer su frecuencia
		
	}
	
	public void unregister() {
		
	}
	
	

}
