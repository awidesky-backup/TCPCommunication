package com.intgames.tcpCommunication;

import java.net.InetAddress;

public class ClientData {

	public InetAddress ip;
	public MessageOutputStream mo;
	
	public ClientData(MessageOutputStream mo2, InetAddress ip2) {
		// TODO Auto-generated constructor stub
		
		ip = ip2;
		mo = mo2;
				
	}
	
}
