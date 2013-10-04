package IRC;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client implements Runnable{
	Socket sock;
	Scanner Input;
	Scanner Send = new Scanner(System.in);
	PrintWriter Out;
	
	public Client(Socket sock) {
		this.sock = sock;
	}
	
	public void run() {
		try{
			try{
				Input = new Scanner(sock.getInputStream());
				Out = new PrintWriter(sock.getOutputStream());
				Out.flush();
				CheckStream();
			}finally{sock.close();}
		}catch(Exception X){}
	}
	
	public void CheckStream(){
		while(true){
			Recieve();
		}
	}
	
	public void Recieve(){
		if(Input.hasNext()){
			String Message = Input.nextLine();
			
			if(Message.contains("?##!##!##!?")){
				String Temp = Message.substring(11);
				Temp = Temp.replace("[", "");
				Temp = Temp.replace("]", "");
				String[] ActiveConnections = Temp.split(", ");
				
				ClientGUI.LUsers.setListData(ActiveConnections);
			}
			else{
				ClientGUI.TAConversation.append(Message + "\n");
			}
			
		}
	}

	public void Disconnect() throws IOException {
		Out.println("##@##@##@");
		Out.flush();
		sock.close();
		System.exit(0);
	}
	
	public void Send(String Message) {
		Out.println(ClientGUI.UserName + ": " + Message);
		Out.flush();
		ClientGUI.TFMessage.setText("");
		
	}
	

	
}
