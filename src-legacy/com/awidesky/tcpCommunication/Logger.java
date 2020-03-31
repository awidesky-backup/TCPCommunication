package com.awidesky.tcpCommunication;

@FunctionalInterface
public interface Logger {

	public void provide(String message, Logtype lt);
	
}
