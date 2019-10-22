package com.intgames.tcpCommunication;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.intgames.tcpCommunication.BannedClientException;
import com.intgames.tcpCommunication.Log;
import com.intgames.tcpCommunication.LogAppender;
import com.intgames.tcpCommunication.Message;
import com.intgames.tcpCommunication.MessageOutputStream;
import com.intgames.tcpCommunication.ServerAccepterThread;
import com.intgames.tcpCommunication.ErrorProvider;

public class TCPServer {

	
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
	private LogProvider lg;
	public ErrorProvider ep;
	
	public TCPServer(String servername, String logPath, ErrorProvider ep, LogProvider lg, int port) {
		
		this.servername = servername;
		this.ep = ep; 
		this.log = new Log(servername, logPath, this,ep, new LogAppender());
		this.lg = lg;
		
		setnetwork(port);
		
	}

	public void setnetwork(int port) {
		
		try {
			
			lg.provide("ServerSosket 생성 중...");
			serversc = new ServerSocket(port);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ep.provide("ServerSocket 생성 실패!", "ServerSocket 생성에 실패했습니다!\n" + e.getMessage());
			lg.provide("ServerSocket 생성 실패-" + e.getMessage());
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
		
		ep.provide(title, message);
		
	}
	
	public void showtext(String string) {
		
		lg.provide(string);
		
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
				ep.provide("서버 전송 오류", co.ip + " 로 메시지를 전송할 수 없습니다.\n" + e.getMessage());
				lg.provide(co.ip + " 로 메시지를 전송할 수 없음-" + e.getMessage());
				
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
				ep.provide("서버를 닫을 수 없습니다!", co.ip + " 와 연결된 스트림을 닫는 도중 문제가 발생했습니다.\n" + e.getMessage());
				lg.provide(co.ip + " 와 연결된 스트림을 닫는 도중 문제 발생-" + e.getMessage());
				
			}
			
		}
		
	}
	
	
}






