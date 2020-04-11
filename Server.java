import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.*;



public class Server{  
	
	private ArrayList<User> users = new ArrayList<User>();
	
	public static void main(String[] args) {
		Server aux = new Server();
		
		ServerSocket socketServer = null;
		Socket socketDespachador = null;
		
		
		
		
	
		try 
		{
			
			socketServer = new ServerSocket(1236);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println("CONECTANDO: " + socketServer + "......");
        
		while(true){
			try {
				socketDespachador = socketServer.accept();
				System.out.println("CONECTADO A: " + socketDespachador);
				new OPERACIONES(socketDespachador,aux.users).start();//PROCESA LA SOLICITUD DEL CLIENTE
				socketDespachador = null;
			} 
			catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}



}

