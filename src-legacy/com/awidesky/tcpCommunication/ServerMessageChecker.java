package com.awidesky.tcpCommunication;

@FunctionalInterface
public interface ServerMessageChecker {

	public void check(Message ms);
	
}
