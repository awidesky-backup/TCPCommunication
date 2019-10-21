package com.intgames.tcpCommunication.resources;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTextArea;

import com.intgames.tcpCommunication.TCPClient;
import com.intgames.tcpCommunication.TCPServer;
import com.intgames.tcpCommunication.GUI.ErrorGUI;

@SuppressWarnings("unused")
public class Log {

	final String[] week = { "일", "월", "화", "수", "목", "금", "토" };
	private Calendar oCalendar;
	private String name;
	private BufferedWriter br;
	
	private LogAppender lp;
	private ErrorGUI mg;
	private SimpleDateFormat filef = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
	private SimpleDateFormat dayf = new SimpleDateFormat("dd");
	private SimpleDateFormat timeinlogf = new SimpleDateFormat("[a hh:mm] ");
	private SimpleDateFormat dayinlogf = new SimpleDateFormat(" yyyy년 MM월 dd일 ");
	private String today;
	

	public Log(String name, String logpath, ErrorGUI mg, LogAppender lp) {
		// TODO Auto-generated constructor stub
		
		today = dayf.format(System.currentTimeMillis());
		
		this.name = name;
		this.mg = mg;
		this.lp = lp;
		
		try {
			br = new BufferedWriter(new FileWriter(new File(logpath + "\\" + filef.format(System.currentTimeMillis()) + "-" + name + ".txt")));
			lp.setBufferedReader(br);
			lp.append("---------------" + dayinlogf.format(System.currentTimeMillis()) + week[oCalendar.get(Calendar.DAY_OF_WEEK) - 1] + "요일" + " ---------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			mg.error("로그 파일 제작 실패!", "서버의 로그 파일을 제작하는 도중 오류가 발생했습니다!\n" + e.getMessage());
		}
		
	}
	
	public void println(Message log, long ping) {

		String who = log.getWho();
		String sping = Long.toString(ping);
		StringBuilder sb = new StringBuilder(sping); //used to insert dot
		
		
		// 핑이 -1.0이면 안적기
		if (ping != -1) {
			
			sping = sb.insert(sping.length() - 6, ".").toString();
			
		} else {
			// Message by System
			sping = "-";
			
		}
		
		try {
		
			if (today != dayf.format(System.currentTimeMillis())) {
				
				lp.append("---------------" + dayinlogf.format(System.currentTimeMillis()) + week[oCalendar.get(Calendar.DAY_OF_WEEK) - 1] + "요일" + " ---------------");
				today = dayf.format(System.currentTimeMillis());
	
			}
			
			if (who != null) who = "[" + who + "] [ " + sping +"ms] ";
			else who = "_SYSTEM_ ";
		
			lp.append(who + timeinlogf.format(System.currentTimeMillis()) + log.getMsg() + "\n");
			
		} catch (IOException e) {
			
			mg.error("로그 파일 제작 중 오류", "로그 파일에 적어넣는 도중 실패했습니다!\n" + e.getMessage());
			
		}
	}
	
	public void serverstatus(int status) {
		
		/**
		 * 
		 * 0 means good exit
		 * 1 means exit with error
		 * 
		 * */
		
		switch(status) {
		
			case 0:
			
				println(new Message(null, "서버 종료 시도... 요류 없음"), -1);
			
			case 1:
			
				println(new Message(null, "서버 종료 시도... errorlevel : 1"), -1);
			
			default:
		
				println(new Message(null, "서버 종료 시도... errorlevel : unknown"), -1);
				
		}
		
	}
	
	
}
