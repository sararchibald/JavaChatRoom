package IRC;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	
	//Arrays of connected clients and their user names
	public static ArrayList<Socket> ActiveConnections = new ArrayList<Socket>();
	public static ArrayList<String> ActiveUsers = new ArrayList<String>();
	
	
	//Creates the socket and accepts the connection
	public static void main(String[]args)throws IOException{
		
		try{
			//Creates socket
			final int Port = 4444;
			ServerSocket ListeningSocket = new ServerSocket(Port);
			System.out.println("Server is running. Waiting for clients.");
		
			while(true){
				//Accept Client Connection
				Socket ConnectedClient = ListeningSocket.accept();
				ActiveConnections.add(ConnectedClient);
				AddUserName(ConnectedClient);
				System.out.println("Username has connected from " + ConnectedClient.getLocalAddress());
				
				
				//Create a Connection Thread 
				ServerConnection Connection = new ServerConnection(ConnectedClient);
				Thread ServerThread = new Thread(Connection);
				ServerThread.start();
			}
		}
		catch(Exception ServerThread){System.out.println(ServerThread);}; 
	}

//--AddUserName()-------------------------------------------------------
	public static void AddUserName(Socket X) throws IOException{
		//Adds user name to the array
		Scanner Input = new Scanner(X.getInputStream());
		String UserName = Input.nextLine();
		ActiveUsers.add(UserName);
		
		//Updates the Active Users for all connected clients
		for(int i = 1; i <= Server.ActiveUsers.size(); i++){
			Socket Temp = (Socket) Server.ActiveConnections.get(i-1);
			PrintWriter Out = new PrintWriter(Temp.getOutputStream());
			Out.println("?##!##!##!?" + ActiveUsers);
			Out.flush();
		}
			
	}
	
}
