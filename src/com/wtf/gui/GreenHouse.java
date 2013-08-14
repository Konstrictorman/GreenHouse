package com.wtf.gui;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;

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
	
	public GreenHouse() throws IOException {
		name = props.getString("name");
		address = props.getString("IP");
		port = props.getString("port");
		agent = new Agent();
		agent.register();
		
		//TODO: La frecuencia la da el dispatcher...
		agent.setFrecuency(10);
		agent.measureTemperature();			
	}
	
	
	public static void main(String[] args) throws IOException {
		GreenHouse gh = new GreenHouse();
		log.info("name: " + gh.getName());
		log.info("Ip: " + gh.getAddress());
		log.info("port: " + gh.getPort());
		
		String input = "";
		Scanner scanner = new Scanner(System.in);
		byte cont = 0;
		//System.out.println("Type your command :");
		do {
			String[] params = input.split(" ");
			if (params.length > 0) {
				if (params[0].equalsIgnoreCase("getTemp")) {
					if (params.length == 1) {
						gh.agent.printRegistry();	
					} else {
						for (byte b=1; b<params.length; b++) {
							String s = params[b];
							if (s.equalsIgnoreCase("all")) {
								gh.agent.askAllTemperature();
							} else {
								gh.agent.askTemperature(s);
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
							gh.agent.updateFrecuency(f);
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
		log.warn("Shutting down greenhouse "+ gh.name);
		log.warn("Bye !");
		scanner.close();
		gh.agent.shutDown();
	}
	
	public void askTemperature(String name) throws IOException {
		agent.askTemperature(name);
	}
	
	public void askAllTemperature() throws IOException {
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
