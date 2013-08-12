package com.wtf.gui;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.wtf.services.Agent;

public class GreenHouse {
	
    static {
        System.setProperty("log.name", System.getProperty("user.dir") + "/logs/log.log");
    }	
	
	private static Logger log = Logger.getLogger(GreenHouse.class.getName());
	public static ResourceBundle props = ResourceBundle.getBundle("greenHouse");
	
	private String name;
	private String address;
	private String port;
	private Agent agent;
	
	public GreenHouse() {
		name = props.getString("name");
		address = props.getString("IP");
		port = props.getString("port");
		agent = new Agent(name, address, port);
	}
	
	
	public static void main(String[] args) {
		GreenHouse gh = new GreenHouse();
		log.info("name: " + gh.getName());
		log.info("Ip: " + gh.getAddress());
		log.info("port: " + gh.getPort());
		
	}
	
	public void askTemperature(String name) {
		agent.askTemperature(name);
	}
	
	public void askAllTemperature() {
		agent.askAllTemperature();
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

	
	
}
