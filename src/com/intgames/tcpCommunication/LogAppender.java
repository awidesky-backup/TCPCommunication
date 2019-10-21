package com.intgames.tcpCommunication;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.swing.JTextArea;

public class LogAppender {

	private BufferedWriter br = null;
	private JTextArea jta = null; 
	
	public LogAppender() {}

	public LogAppender(JTextArea jt) {
		
		jta = jt;
		
	}
	
	public void setBufferedReader(BufferedWriter b) {
		
		br = b;
		
	}
	
	public void append(String log) throws IOException {
		// TODO Auto-generated method stub
		  
		 br.write(log);
		 br.flush();
		 if(jta != null) jta.append(log);
		 
	}

}
