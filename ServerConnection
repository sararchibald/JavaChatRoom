package IRC;

import java.io.*;
import java.net.*;
import java.util.*;



public class ServerConnection implements Runnable {
	//Global Variables
	Socket sock;
	private Scanner Input;
	private PrintWriter Output;
	String Message = "";
	
	// Persists Socket
	public ServerConnection(Socket sock){
		this.sock = sock;
	}
	
		public void CheckConnection() throws IOException{
		
		//If the socket is not connected remove the connection from the Arrays
		if(!sock.isConnected()){
			//Remove from Connections Array
			System.out.print("User disconnected from: " + sock.getLocalPort());
			for(int i = 0; i < Server.ActiveConnections.size(); i++){
				if(Server.ActiveConnections.get(i) == sock){
					Server.ActiveConnections.remove(i);
					Server.ActiveUsers.remove(i);
					updateUsers();
					
				}
			}
			
		}
		updateUsers();
		
	}
		
	public void updateUsers() throws IOException{
		//Updates the Active Users for all connected clients
		for(int i = 0; i < Server.ActiveUsers.size(); i++){
			Socket Temp = (Socket) Server.ActiveConnections.get(i);
			PrintWriter Out = new PrintWriter(Temp.getOutputStream());
			Out.println("?##!##!##!?" + Server.ActiveUsers);
			Out.flush();
			
			System.out.println(Server.ActiveUsers);
		}
	}
	public void run() {
		try{
			try{
				Input = new Scanner(sock.getInputStream());
				Output = new PrintWriter(sock.getOutputStream());
				
				while(true){
					CheckConnection();
					
					if(Input.hasNext()){
					Message = Input.nextLine();
					
					if(Message.contains("##@##@##@")){
						sock.close();
					}
						
					else
					for(int i = 1; i <= Server.ActiveUsers.size(); i++){
						Socket Temp = (Socket) Server.ActiveConnections.get(i-1);
						PrintWriter Out = new PrintWriter(Temp.getOutputStream());
						Out.println(Message);
						Out.flush();
						
						System.out.println(Message);

					}	
						
					}
				}
			}
			finally{
				sock.close();
			}
		}
		catch(Exception X){}		
	}
}
