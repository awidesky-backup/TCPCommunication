package com.intgames.tcpCommunication;

@FunctionalInterface
public interface Logger {

	public void provide(String message, Logtype lt);
	
}
