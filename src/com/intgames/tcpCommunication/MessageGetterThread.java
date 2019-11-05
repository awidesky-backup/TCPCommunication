package com.intgames.tcpCommunication;

import java.io.IOException;
import java.io.ObjectInputStream;

import com.intgames.tcpCommunication.TCPServer;
import com.intgames.tcpCommunication.resources.Message;

public class MessageGetterThread extends Thread {

	private ObjectInputStream oi;
	private TCPServer svr;
	private String ip;
	private boolean isrunning;
	
	public MessageGetterThread(ObjectInputStream oi, TCPServer svr, String ip) {
		// TODO Auto-generated constructor stub
		this.oi = oi;
		this.svr = svr;
		this.ip = ip;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	
		while (this.isrunning) {
			
			this.getmessage();
			
		}
		
	}
	
	private void getmessage() {
		Message msg = null;
		long ping = 0L;
		
		try {
			
			msg = (Message)oi.readObject();
			ping = msg.getPing(System.nanoTime());
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			svr.eh.provide("������ ���� ����!", "�޽����� �޾ƿ��� �� �����߽��ϴ�.\n" + e.getMessage());
			svr.addlog(ip + "���Լ� �޽����� �޾ƿ��� ���� ����-" + e.getMessage(), Logtype.ERROR);
			
		} catch (ClassNotFoundException e) {
			
			// TODO Auto-generated catch block
			svr.eh.provide("������ ���� ����!", "���ŵ� �����͸� ��ȯ�ϴ� ���� ������ �߻��߽��ϴ�.\n" + e.getMessage());
			svr.addlog(ip + "���Լ� ���ŵ� �����͸� ��ȯ�ϴ� ���� ���� �߻�-" + e.getMessage(), Logtype.ERROR);
			
		}
	
		svr.sendEveryone(msg, ping);
	
	}

	public void kill() {
		// TODO Auto-generated method stub
		
		this.isrunning = false;
		
		try {
			
			this.oi.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block

			svr.eh.provide("������ ���� �� �����ϴ�!",e.getMessage());
		
		}
		
	}

}
