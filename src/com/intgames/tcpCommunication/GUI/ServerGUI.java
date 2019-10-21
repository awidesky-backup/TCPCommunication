package com.intgames.tcpCommunication.GUI;

import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.intgames.tcpCommunication.Server;

@SuppressWarnings("serial")
public class ServerGUI extends JFrame {

	private Server server;
	private Setup setup;
	private Status status;
	private SimpleDateFormat time = new SimpleDateFormat("[a hh:mm] ");
	
	
	public ServerGUI() {
		
		setup = new Setup(this);
		status = new Status();
		
		
		setVisible(true);
		
	}
	
	private class Setup extends JFrame {
		
		private JButton done;
		private JTextField port;
		private JTextField servername;
		private JTextField logpath;
		
		public Setup(ServerGUI sg) {
			
			done.addActionListener( (e) -> {
				
					server = new Server(servername.getText(), logpath.getText(), sg, Integer.parseInt(port.getText()));
					dispose();
					
			});
			
			setVisible(true);
		}
		
		
	}
	
	private class Status extends JFrame {
		
		private JScrollPane jsp;
		private JTextArea jta;
		
		public Status() {
			// TODO Auto-generated constructor stub

			jsp.add(jta);
		
		}
		
		
	}
	
	
	public void showtext(String arg) {
		
		status.jta.append(time.format(System.currentTimeMillis()) + arg + "\n");
		
	}
	

}
