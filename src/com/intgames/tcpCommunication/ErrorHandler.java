package com.intgames.tcpCommunication;

@FunctionalInterface
public interface ErrorHandler {

	public void provide(String title, String content);
}
