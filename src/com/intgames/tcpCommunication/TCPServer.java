package com.intgames.tcpCommunication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {

	
	/**
	 * @author Eugene Hong
	 * 
	 * 
	 * 
	 */
	
	
	private String servername;
	private ServerSocket serversc;
	private ServerMessageHandleType st;
	private ExecutorService messageGetterThreadPool;
	private ServerAccepterThread sa;
	private List<ClientData> connectedClient = new LinkedList<>();
	private List<String> banned = new LinkedList<>();
	private Logger lg;
	private ErrorHandler eh;
	
	public TCPServer(String servername, ServerMessageHandleType st, Logger lg, ErrorHandler ep, int port, int maxclient) {
		
		this.servername = servername;
		this.st = st;
		this.eh = ep; 
		this.lg = lg;
		
		messageGetterThreadPool = Executors.newFixedThreadPool(maxclient); 
		
		setnetwork(port, maxclient);
		
	}

	public void setnetwork(int port, int maxclient) {
		
		try {
			
			lg.provide("ServerSosket ���� ��...", Logtype.PLANE);
			serversc = new ServerSocket(port, maxclient);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			eh.provide("ServerSocket ���� ����!", "ServerSocket ������ �����߽��ϴ�!\n" + e.getMessage());
			lg.provide("ServerSocket ���� ����-" + e.getMessage(), Logtype.ERROR);
			return;
		
		}
		
		this.sa = new ServerAccepterThread(serversc, this, messageGetterThreadPool);
		this.sa.setDaemon(true);
		this.sa.start();
		
	}
	
	public void setMessageHandling(ServerMessageHandleType st, MessageHandler mh) {
		
		this.st = st;
		this.mh = mh;
		
	}
	
	public ServerMessageHandleType getMessageHandleType() {
		
		return st;
		
	}
	
	public void checkClient(Socket client) throws BannedClientException {
		
		String ip = client.getInetAddress().toString().split("/")[1];
		if (!banned.contains(ip)) throw new BannedClientException("Banned ip : " + ip);
		
	}
	
	public void addBannedClient(InetAddress client) {
		
		banned.add(client.toString().split("/")[1]);
		
	}
	
	public String getservername() {
		
		return this.servername;
		
	}

	public void addClientData(ClientData oo) {
		// TODO Auto-generated method stub
		this.connectedClient.add(oo);
		
	}
	
	public void error(String title, String message) {
		
		eh.provide(title, message);
		
	}
	
	public void addlog(String string, Logtype lt) {
		
		lg.provide(string, lt);
		
	}
	
	public void gotMessage(Message msg, long ping) {
		
		switch (st) {

		case SELECTIVELY:
			
			mh.Handle(msg, ping);
			break;
		
		case ECHO:
			
			sendEveryone(msg, ping);
			break;
		
		}
		
	}
	
	public void sendEveryone(Message msg, long ping) {
		
		Iterator<ClientData> it = connectedClient.iterator();
		
		while(it.hasNext()) {
			
			ClientData co = it.next();
			
			try {
				
				MessageOutputStream oo = co.mo;
				oo.writeMessage(msg);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				eh.provide("���� ���� ����", co.ip + " �� �޽����� ������ �� �����ϴ�.\n" + e.getMessage());
				lg.provide(co.ip + " �� �޽����� ������ �� ����-" + e.getMessage(), Logtype.ERROR);
				
			}
			
		}
		
		
	}
	
	public void shutdown(int status) {
		
		lg.provide(Integer.toString(status), Logtype.SERVERSTATUS);
		
		this.sa.kill();
		
		Iterator<ClientData> it = connectedClient.iterator();
		
		while(it.hasNext()) {
			
			ClientData co = it.next();
			
			try {
				
				MessageOutputStream br = co.mo;
				br.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				eh.provide("������ ���� �� �����ϴ�!", co.ip + " �� ����� ��Ʈ���� �ݴ� ���� ������ �߻��߽��ϴ�.\n" + e.getMessage());
				lg.provide(co.ip + " �� ����� ��Ʈ���� �ݴ� ���� ���� �߻�-" + e.getMessage(), Logtype.ERROR);
				
			}
			
		}
		
	}
	
	
}






