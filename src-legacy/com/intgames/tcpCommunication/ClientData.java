package com.intgames.tcpCommunication;

import java.net.InetAddress;

public class ClientData {

	public InetAddress ip;
	public MessageOutputStream mo;
	public ServerMessageGetter messageGetter;
	public boolean isExcluded;

	public ClientData(MessageOutputStream mo2, InetAddress ip2, ServerMessageGetter messageGetter) {
		// TODO Auto-generated constructor stub
		
		ip = ip2;
		mo = mo2;
		this.messageGetter = messageGetter;
	}
	
}
