package com.intgames.tcpCommunication.GUI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.intgames.tcpCommunication.resources.Log;
import com.intgames.tcpCommunication.resources.Message;

@SuppressWarnings("serial")
public class ErrorGUI extends JFrame {

	/**
	 * @author Eugene Hong
	 * 
	 * this class manage log   
	 * 
	 * */
	
	private Log log;
	
	public ErrorGUI(Log log) {
		// TODO Auto-generated constructor stub
		this.log = log;
		
	}

	public void error(String title, String message) {
		
		JOptionPane.showMessageDialog(null, message , title , JOptionPane.ERROR_MESSAGE);
		log.println(new Message(null, title + " : " + message), -1L);
		
	}

}
