package com.awidesky.tcpCommunication;

import java.io.Serializable;

public class Message implements Serializable {

	/**
	 * MUST CALL readyToSend() BEFORE SERIALIZE!!
	 * writeMessage() method of MessageOutputStream class calls readyToSend() before serializing and sending.
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String who, msg;
	private long starttime;
	
	public Message(String who, String msg) {
		// TODO Auto-generated constructor stub
		
		this.who = who;
		this.msg = msg;
		
	}
	
	public Message readyToSend() {
		
		this.starttime = System.nanoTime();
		return this;
		
	}

	public String getWho() {
		
		return who;
		
	}
	
	public String getMsg() {
		
		return msg;
		
	}

	public long getPing(long now) {
		
		return now - starttime;
		
	}

	
}
