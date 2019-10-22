package com.intgames.tcpCommunication;

@FunctionalInterface
public interface ErrorProvider {

	public void provide(String title, String content);
}
