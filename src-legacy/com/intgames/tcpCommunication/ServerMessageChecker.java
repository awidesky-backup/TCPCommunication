package com.intgames.tcpCommunication;

@FunctionalInterface
public interface ServerMessageChecker {

	public void check(Message ms);
	
}
