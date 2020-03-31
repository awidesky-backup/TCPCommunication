package com.intgames.tcpCommunication;

//import java.io.IOException;
//import java.net.Socket;
//import java.net.UnknownHostException;

public class TCPClient {

	// private Socket sock;

	public TCPClient() {

	}

	/*
	 * public void setnetwork(String ip, int port) { // TODO Auto-generated method
	 * stub
	 * 
	 * try { sock = new Socket(ip, port); } catch (UnknownHostException e) {
	 * //mg.error("UnknownHostException","호스트를 알 수 없습니다!\n" + e.getMessage());
	 * 
	 * } catch (IOException e) {
	 * //mg.error("IOException","서버와의 통신을 위해 소켓을 생성하는 도중 문제가 발생했습니다!\n" +
	 * e.getMessage()); }
	 * 
	 * // try { // br = new BufferedReader(new
	 * InputStreamReader(sock.getInputStream())); // } catch (IOException e) { //
	 * mg.error("InputStreamReader 생성 중 오류","서버에서 데이터를 받을 준비를 하는 도중 문제가 발생했습니다!");
	 * // } // // try { // bw = new BufferedWriter(new
	 * OutputStreamWriter(sock.getOutputStream())); // } catch (IOException e) { //
	 * mg.error("getOutputStream 생성 중 오류","서버로 데이터를 보낼 준비를 하는 도중 문제가 발생했습니다!"); // }
	 * //
	 * 
	 * 
	 * new Thread(() -> {
	 * 
	 * 
	 * String msg = null;
	 * 
	 * while(true) {
	 * 
	 * try { while((msg = br.readLine()) != null) {
	 * 
	 * MainGUI.clientgot(null,msg);
	 * 
	 * } } catch (IOException e) {
	 * mg.error("데이터 수신 오류","서버에서 데이터를 받는 도중 문제가 발생했습니다!"); }
	 * 
	 * if (Launcher.clientstate.equals("ClientStopSend")) break;
	 * 
	 * }
	 * 
	 * }).start();
	 * 
	 * }
	 */

	public String getclientname() {
		// TODO Auto-generated method stub
		return null;
	}
}
