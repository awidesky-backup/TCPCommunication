package com.intgames.tcpCommunication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.intgames.tcpCommunication.Exception.BannedClientException;
import com.intgames.tcpCommunication.GUI.ErrorGUI;
import com.intgames.tcpCommunication.GUI.ServerGUI;
import com.intgames.tcpCommunication.resources.Log;
import com.intgames.tcpCommunication.resources.LogAppender;
import com.intgames.tcpCommunication.resources.Message;
import com.intgames.tcpCommunication.resources.MessageOutputStream;
import com.intgames.tcpCommunication.runnables.ServerAccepterThread;

public class Server {

	
	/**
	 * @author Eugene Hong
	 * 
	 * 
	 * 
	 */
	
	
	private String servername;
	private ServerSocket serversc;
	private ServerAccepterThread sa;
	private List<ClientData> connectedClient = new LinkedList<>();
	private List<String> banned = new LinkedList<>();
	private Log log;
	private ServerGUI sg;
	
	public ErrorGUI mg;
	
	public Server(String servername, String path, ServerGUI sg, int port) {
		
		this.servername = servername;
		this.mg = new ErrorGUI(this.log); 
		this.log = new Log(servername, path, mg, new LogAppender());
		this.sg = sg;
		
		setnetwork(port);
		
	}

	public void setnetwork(int port) {
		
		try {
			
			sg.showtext("ServerSosket ���� ��...");
			serversc = new ServerSocket(port);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			mg.error("ServerSocket ���� ����!", "ServerSocket ������ �����߽��ϴ�!\n" + e.getMessage());
			sg.showtext("ServerSocket ���� ����-" + e.getMessage());
			return;
		
		}
		
		this.sa = new ServerAccepterThread(serversc, this);
		this.sa.setDaemon(true);
		this.sa.start();
		
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
		
		mg.error(title, message);
		
	}
	
	public void showtext(String string) {
		
		sg.showtext(string);
		
	}
	
	public void sendEveryone(Message msg, long ping) {
		
		log.println(msg, ping);
		
		Iterator<ClientData> it = connectedClient.iterator();
		
		while(it.hasNext()) {
			
			ClientData co = it.next();
			
			try {
				
				MessageOutputStream oo = co.mo;
				oo.writeMessage(msg);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				mg.error("���� ���� ����", co.ip + " �� �޽����� ������ �� �����ϴ�.\n" + e.getMessage());
				sg.showtext(co.ip + " �� �޽����� ������ �� ����-" + e.getMessage());
				
			}
			
		}
		
		
	}
	
	public void shutdown(int status) {
		
		log.serverstatus(status);
		
		this.sa.kill();
		
		Iterator<ClientData> it = connectedClient.iterator();
		
		while(it.hasNext()) {
			
			ClientData co = it.next();
			
			try {
				
				MessageOutputStream br = co.mo;
				br.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				mg.error("������ ���� �� �����ϴ�!", co.ip + " �� ����� ��Ʈ���� �ݴ� ���� ������ �߻��߽��ϴ�.\n" + e.getMessage());
				sg.showtext(co.ip + " �� ����� ��Ʈ���� �ݴ� ���� ���� �߻�-" + e.getMessage());
				
			}
			
		}
		
	}
	
	
}






