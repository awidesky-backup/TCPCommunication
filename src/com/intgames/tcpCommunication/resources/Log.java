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

	final String[] week = { "��", "��", "ȭ", "��", "��", "��", "��" };
	private Calendar oCalendar;
	private String name;
	private BufferedWriter br;
	
	private LogAppender lp;
	private ErrorGUI mg;
	private SimpleDateFormat filef = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
	private SimpleDateFormat dayf = new SimpleDateFormat("dd");
	private SimpleDateFormat timeinlogf = new SimpleDateFormat("[a hh:mm] ");
	private SimpleDateFormat dayinlogf = new SimpleDateFormat(" yyyy�� MM�� dd�� ");
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
			lp.append("---------------" + dayinlogf.format(System.currentTimeMillis()) + week[oCalendar.get(Calendar.DAY_OF_WEEK) - 1] + "����" + " ---------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			mg.error("�α� ���� ���� ����!", "������ �α� ������ �����ϴ� ���� ������ �߻��߽��ϴ�!\n" + e.getMessage());
		}
		
	}
	
	public void println(Message log, long ping) {

		String who = log.getWho();
		String sping = Long.toString(ping);
		StringBuilder sb = new StringBuilder(sping); //used to insert dot
		
		
		// ���� -1.0�̸� ������
		if (ping != -1) {
			
			sping = sb.insert(sping.length() - 6, ".").toString();
			
		} else {
			// Message by System
			sping = "-";
			
		}
		
		try {
		
			if (today != dayf.format(System.currentTimeMillis())) {
				
				lp.append("---------------" + dayinlogf.format(System.currentTimeMillis()) + week[oCalendar.get(Calendar.DAY_OF_WEEK) - 1] + "����" + " ---------------");
				today = dayf.format(System.currentTimeMillis());
	
			}
			
			if (who != null) who = "[" + who + "] [ " + sping +"ms] ";
			else who = "_SYSTEM_ ";
		
			lp.append(who + timeinlogf.format(System.currentTimeMillis()) + log.getMsg() + "\n");
			
		} catch (IOException e) {
			
			mg.error("�α� ���� ���� �� ����", "�α� ���Ͽ� ����ִ� ���� �����߽��ϴ�!\n" + e.getMessage());
			
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
			
				println(new Message(null, "���� ���� �õ�... ��� ����"), -1);
			
			case 1:
			
				println(new Message(null, "���� ���� �õ�... errorlevel : 1"), -1);
			
			default:
		
				println(new Message(null, "���� ���� �õ�... errorlevel : unknown"), -1);
				
		}
		
	}
	
	
}
