package IRC;
import javax.swing.*;

import java.io.*;
import java.net.*;

public class ClientGUI {

	private static Client Client;
	public static String UserName = "Guest";
	
	//GUI Window
	public static JFrame MainWindow = new JFrame();
	
	private static JButton BSend = new JButton();
	private static JButton BConnect = new JButton();
	private static JButton BDisconnect = new JButton();
	
	private static JLabel LTitle = new JLabel();
	
	public static JTextArea TAConversation = new JTextArea();
	public static JTextField TFMessage = new JTextField(255);
	
	public static JList LUsers = new JList();

	private static JScrollPane SPConversation = new JScrollPane();
	private static JScrollPane SPUsers = new JScrollPane();
	
	//Log in Window
	public static JFrame LogInWindow = new JFrame();
	private static JButton BLogIn = new JButton();
	private static JLabel LLogInMessage = new JLabel();
	private static JTextField TFLogIn = new JTextField(20);

	
	//Start
	public static void main(String args[]){
		BuildMainWindow();
		Initialize();
	}
	
	public static void BuildMainWindow(){
		MainWindow.setTitle("Sara's Chat Client");
		MainWindow.setSize(450, 500);
		MainWindow.setResizable(false);
		ConfigureMainWindow();
		MainWindowAction();
		MainWindow.setVisible(true);
	}
	
	public static void ConfigureMainWindow(){
		MainWindow.setBackground(new java.awt.Color(255, 255, 255));
		MainWindow.getContentPane().setLayout(null);
		
		BSend.setText("Send");
		MainWindow.getContentPane().add(BSend);
		BSend.setBounds(350,450,80,24);
		
		BConnect.setText("Connect");
		MainWindow.getContentPane().add(BConnect);
		BConnect.setBounds(10,5,120,24);
		
		BDisconnect.setText("Disconnect");
		MainWindow.getContentPane().add(BDisconnect);
		BDisconnect.setBounds(130,5,120,24);
		
		MainWindow.getContentPane().add(LUsers);
		LUsers.setBounds(350,40,75,400);
		
		MainWindow.getContentPane().add(TFMessage);
		TFMessage.setBounds(10,450,330,24);
		
		MainWindow.getContentPane().add(TAConversation);
		TAConversation.setBounds(10,40,335,400);
		
		SPConversation.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		SPUsers.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		SPConversation.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		SPUsers.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		SPConversation.setViewportView(TAConversation);
		SPUsers.setViewportView(LUsers);
		
		MainWindow.getContentPane().add(SPConversation);
		MainWindow.getContentPane().add(SPUsers);
		SPConversation.setBounds(10,40,330,400);
		SPUsers.setBounds(350,40,80,400);

			}
	//Disable unusable buttons	
	public static void Initialize(){
		BSend.setEnabled(false);
		BDisconnect.setEnabled(false);
		BConnect.setEnabled(true);
	}
	
	//Create Button listeners
	public static void MainWindowAction() {
		BSend.addActionListener(
			new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt){
					Client.Send(TFMessage.getText());
				}
			}
		);
		
		BConnect.addActionListener(
			new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt){
					BuildLogInWindow();
				}
			}
		);
		
		BDisconnect.addActionListener(
			new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt){
					try {
						Client.Disconnect();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		);
	}
	
		public static void Connect(){
		try{
			final int Port = 4444;
			final String Host = "java";
			Socket Server = new Socket(Host, Port);
			UserName = TFLogIn.getText();
			
			Client = new Client(Server);
			
			//Send User name
			PrintWriter Out = new PrintWriter(Server.getOutputStream());
			Out.println(UserName);
			Out.flush();
			
			Thread X = new Thread(Client);
			X.start();
			
			BSend.setEnabled(true);
			BDisconnect.setEnabled(true);
			BConnect.setEnabled(false);
			System.out.println(Client);
			
			
		}catch(Exception X){}
	}
	
	//Build Log In
	public static void BuildLogInWindow(){
		LogInWindow.setTitle("LogIn");
		LogInWindow.setSize(300, 100);
		LogInWindow.setResizable(false);
		ConfigureLogInWindow();
		LogInWindowAction();
		LogInWindow.setVisible(true);
	}
	
	public static void ConfigureLogInWindow(){
		LogInWindow.setBackground(new java.awt.Color(255, 255, 255));
		LogInWindow.getContentPane().setLayout(null);
		
		BLogIn.setText("Sign On");
		LogInWindow.getContentPane().add(BLogIn);
		BLogIn.setBounds(10,50,280,24);
		
		LogInWindow.getContentPane().add(TFLogIn);
		TFLogIn.setBounds(10,30,280,24);
		
		LogInWindow.getContentPane().add(LLogInMessage);
		LLogInMessage.setBounds(10,10,280,24);
	}
	
	public static void LogInWindowAction(){
		BLogIn.addActionListener(
			new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt){
					Connect();
					LogInWindow.setVisible(false);
				}
			}
		);
	
	}
	
}
