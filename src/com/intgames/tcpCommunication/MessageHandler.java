package com.intgames.tcpCommunication;

@FunctionalInterface
public interface MessageHandler {

	public void Handle(Message ms, long ping);
	
}
