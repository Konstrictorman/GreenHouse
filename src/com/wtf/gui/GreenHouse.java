package com.wtf.gui;

import java.util.ResourceBundle;
import org.apache.log4j.Logger;

public class GreenHouse {
	
    static {
        System.setProperty("log.name", System.getProperty("user.dir") + "/logs/log.log");
    }	
	
	private static Logger log = Logger.getLogger(GreenHouse.class.getName());
	public static ResourceBundle props = ResourceBundle.getBundle("greenHouse");

}
