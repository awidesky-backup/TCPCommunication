package com.intgames.tcpCommunication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

@Deprecated
public class MessageInputStream extends ObjectInputStream {

	/**
	 * 
	 * not needed now.
	 * 
	 * */
	
	public MessageInputStream() throws IOException, SecurityException {
		// TODO Auto-generated constructor stub
		super();
	}

	public MessageInputStream(InputStream in) throws IOException {
		super(in);
		// TODO Auto-generated constructor stub
	}

	
}
