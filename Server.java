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
		
		
		
		
		aux.printUsers();
		
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

	private void printUsers(){
		
		System.out.println("USUARIOS REGISTRADOS EN EL SERVER\n------------------------");
		
		for (User user : users) {
			System.out.println(user.toString());
		}
		
		System.out.println("------------------------\n");
	}
}

