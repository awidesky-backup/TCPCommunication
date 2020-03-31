package com.awidesky.tcpCommunication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.awidesky.tcpCommunication.ClientData;
import com.awidesky.tcpCommunication.TCPServer;

public class ServerAccepterThread extends Thread {

	private ServerSocket sock;
	private MessageOutputStream mo;
	private ObjectInputStream oi;
	private TCPServer svr;
	private ExecutorService threadPool;
	private List<ServerMessageGetter> msggetter = new LinkedList<>();
	private boolean isrunning;
	
	public ServerAccepterThread(ServerSocket server, TCPServer svr, ExecutorService messageGetterThreadPool) {
		// TODO Auto-generated constructor stub
		this.sock = server;
		this.svr = svr;
		this.isrunning = true;
		this.threadPool = messageGetterThreadPool;
		
	}

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isrunning) {
				
			this.Accept();
				
		}
	}
	
	private void Accept() {
		
		Socket sc = null;
		InetAddress ip;
		
		try {
			
			sc = sock.accept();
			
			svr.checkClient(sc);
			
			ip = sc.getLocalAddress();
			mo = new MessageOutputStream(sc.getOutputStream());
			oi = new ObjectInputStream(sc.getInputStream());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			svr.error("클라이언트 연결 오류!", "클라이언트와 연결하는 도중 문제가 발생했습니다!\n" + e.getMessage());
			svr.addlog("클라이언트 연결 불가-"+ e.getMessage(), Logtype.ERROR);
			return;
			
		} catch (BannedClientException e) {
			
			svr.addlog("허용되지 않은 사용자 차단 - "+ e.getMessage(), Logtype.PLANE);
			return;
			
		}
		
		ServerMessageGetter messageGetter = new ServerMessageGetter(oi, this.svr, ip.toString());
		
		ClientData cd = new ClientData(mo, ip, messageGetter);
		svr.addClientData(cd);
		messageGetter.setSender(cd);
		svr.addlog(ip.toString() + " 에서 클라이언트 접속 성공", Logtype.PLANE);
		
		threadPool.submit(messageGetter);
		
	}
	
	public void kill() {
		
		this.isrunning = false;
		Iterator<ServerMessageGetter> it = this.msggetter.iterator();
		
		while(it.hasNext()) {
			
			it.next().kill();
			
		}
	}

}
