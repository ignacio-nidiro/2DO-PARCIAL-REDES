import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.*;

class OPERACIONES extends Thread {
	
	private ArrayList<User> users = new ArrayList<User>();
	
	BufferedReader input = null;
	PrintWriter output = null;
	
	Socket socket;

	public OPERACIONES(Socket socket,ArrayList<User> users){
		this.socket = socket;
		this.users = users;
	}

	public void run(){
		User user;
		int indexUser = 0;
		String textoAleatorio = "";
		String textoMezclado  = "";
		
		clearScreen();
		System.out.print("PROCESANDO SOLICITUD DEL CLIENTE......");  
		System.out.println("\n\n---------------------------------------"); 
		
		
		try{
			
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output= new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			String str;
			while (true){
			String x = desencriptar(input.readLine());	
				switch(x){
					case "LOGIN":						
						User logeado;
						Mezclador M = new Mezclador();
						MD5 md5 = new MD5();
						printUsers();
						String name1,PW1;
						name1= desencriptar(input.readLine());//1i
						PW1=desencriptar(input.readLine());//2i
						indexUser = findUser(name1);
							System.out.println(""+indexUser+"");
							if(indexUser==-1){
								System.out.println("USUARIO NO REGISTRADO, INTENTELO DE NUEVO");
								output.println(encriptar("0"));//1o
								break;
							}else{
								System.out.println("USUARIO REGISTRADO, VALIDANDO CONTRASEÑA");
								logeado = users.get(indexUser);
								textoAleatorio = randomText();
								output.println(encriptar(textoAleatorio));//1o
								//se manda texto aleatorio al cliente
								textoMezclado = M.mezcla(textoAleatorio, logeado.getPW());	
								//se mezcla con la contraseña
								String md5ser = md5.M5D(textoMezclado);
								
								String str1 = desencriptar(input.readLine());//3i
								
								if(str1.equals(md5ser)){	
										output.println(encriptar("VALIDADA"));//2o
										System.out.println("USUARIO CONECTADO: "+logeado.getName());
								
								}else{
									output.println(encriptar("NO VALIDA"));//2o
									System.out.println("CONTRASEÑA NO VALIDA");
								
									}
								
									}
									String o="";
									int y=0;
								while(y==0){
									o = desencriptar(input.readLine());
									if(o.equals("+-SALIR-+")){
										y=30;
									}else{
										System.out.println("MENSAJE RECIBIDO DE: "+logeado.getName());
										System.out.println(o);
										}
										
								
								}
								
								System.out.println("TERMINANDO CONEXION CON "+ logeado.getName());
						break;
					
					
					case "REGISTRO":
							User registrado;
							String name,PW;
							name= desencriptar(input.readLine());
							PW=desencriptar(input.readLine());
							indexUser = findUser(name);
							System.out.println(""+indexUser+"");
							if(indexUser==-1){
								registrado = new User(name,PW);
								users.add(registrado);
								output.println(encriptar("APROBADO"));
								System.out.println("NUEVO USUARIO REGISTRADO COMO:" + registrado.getName());
								printUsers();
							}else{
								output.println(encriptar("NO APROBADO"));
								System.out.println("YA HAY UN USUARIO REGISTRADO CON EL NOMBRE DE :" + name);
								}
								
									System.out.println("TERMINANDO CONEXION");				
						break;
					
					default:
						break;
					}
				
				break;	
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	
	private String encriptar(String s) throws UnsupportedEncodingException{
        return Base64.getEncoder().encodeToString(s.getBytes("utf-8"));
    }
    
    
    private String desencriptar(String s) throws UnsupportedEncodingException{
        byte[] decode = Base64.getDecoder().decode(s.getBytes());
        return new String(decode, "utf-8");
    }


	private int findUser(String userName){
		int posicion = -1;
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			String nombre = user.getName();
			if (nombre.equals(userName)) {
				posicion = i;
				break;
			}
		}
		return posicion;
	}

	private String randomText(){
		SecureRandom random = new SecureRandom();
 		String text = new BigInteger(586, random).toString(32);
 		return text;
	}
	public static void clearScreen() {  
		System.out.print("\033[H\033[2J");  
		System.out.flush();  
	}
	
	private void printUsers(){
		
		System.out.println("--------------------------------------\nUSUARIOS REGISTRADOS EN EL SERVER\n-----------------------------");
		
		for (User user : users) {
			String aux = "Nombre: "+user.getName()+" Contraseña: "+user.getPW();
			System.out.println(aux);
		}
		
		System.out.println("---------------------------------------\n");
	}
	
}

