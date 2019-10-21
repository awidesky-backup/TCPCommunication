package com.intgames.tcpCommunication.resources;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MessageOutputStream extends ObjectOutputStream {

	public MessageOutputStream() throws IOException, SecurityException {
		// TODO Auto-generated constructor stub
		super();
	}

	public MessageOutputStream(OutputStream out) throws IOException {
		// TODO Auto-generated constructor stub
		super(out);
	}
	
	public void writeMessage(Message msg) throws IOException {
		
		/**
		 * writes Message to Client after calling msg.readyToSend()
		 * flushes the buffer after sending, no flush() needed after calling this method.
		 * */
		
		writeObject(msg.readyToSend());
		flush();
		
	}

}
