import java.net.*;
import java.util.*;
import java.io.*;


public class Client {
	
	public static void main(String[] args) throws InterruptedException {		
		Client aux = new Client();
		User user;
		
		String server = "127.0.0.1";//PRIMER PARAMETRO PARA OBTENER A QUE SERVIDOR SE CONECTA
		int gate = 1236;//SEGUNDO PARAMETRO PARA OBTENER EL PUERTO
		
		
		Mezclador M = new Mezclador();
		MD5 md5 = new MD5();
		
		String mensajeAleatorio;
		String textoMezclado = "";
		
		
		System.out.println("\n--------------RECUERDE QUE DEBE DE TENER EL SERVIDOR ABIERTO EN OTRA TERMINAL----------");
		System.out.println("CONECTANDOSE AL SERVIDOR: " + server);
		System.out.println("PUERTO: " + gate);
		System.out.println("----------------------------------------------------------------------------------------\n");
		
			String opcion;
			Scanner s = new Scanner(System.in);
			
			while (true) {
				menu();
			opcion = s.nextLine();
			switch (opcion) {
				case "1":
					user = login(s);//LOGIN DE USUARIO
					try{
						Socket socket = new Socket(server,gate);
						BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						PrintWriter output = new PrintWriter( new OutputStreamWriter(socket.getOutputStream() ),true );	
				
						clearScreen();
						output.println(encriptar("LOGIN"));
						output.println(encriptar(user.getName()));//1o
						output.println(encriptar(user.getPW()));	//20
						
						
						mensajeAleatorio = desencriptar(input.readLine());//1i
						//SE RECIBE EL MENSAJE ALEATORIO DESDE SERVER
			
					if(mensajeAleatorio.equals("0")){
							System.out.println("NO SE ENCONTRO NINGUN USUARIO CON ESE NOMBRE, INTENTELO DE NUEVO");
							//socket.close();
							//System.exit(1);
							break;
						}
					System.out.println("HAS SIDO IDENTIFICADO COMO USUARIO REGISTRADO");
					System.out.println("VALIDANDO CONTRASEÑA............");
					textoMezclado = M.mezcla(mensajeAleatorio, user.getPW());//MEZCLANDO

			
					String md5cli = md5.getMD5(textoMezclado);
					
		//GENERANDO MD5
					output.println(encriptar(md5cli));//3o				
		

					String conf = desencriptar(input.readLine());//2i
					//OBTENIENDO LA CONFIRMACION DE CONTRASEÑA
			
					if (conf.equals("VALIDADA")) {
						System.out.println("CONTRASEÑA VALIDADA");
						System.out.println("HAZ INGRESADO COMO: " + user.getName());
					}else{
						System.out.println("CONTRASEÑA INVALIDA, INTENTELO DE NUEVO");
					}
					
					System.out.println("¿"+ user.getName() + ", QUE DESEAS HACER?\n1.-MANDAR MENSAJES AL SERVER\n2.-SALIR");
					
					String mensaje;
					
					String c = s.nextLine();
					if(c.equals("1"))
					System.out.println("ESCRIBE TUS MENSAJES:(SOLO ENTER PARA SALIR)");
					while(c.equals("1")){
							mensaje = s.nextLine();
							if(mensaje.equals("")){
								c="2";
								output.println(encriptar("+-SALIR-+"));
								}else{
								output.println(encriptar(mensaje));
								System.out.println("MENSAJE ENVIADO A SERVER: " + mensaje);
							}
						}
					

				//TERMINANDO LA CONEXION CON SERVIDOR
					socket.close();
				}
				catch(UnknownHostException e){
					e.printStackTrace();
				}
				catch(IOException e){
					e.printStackTrace();
				}
				break;
				case "2": 
					user = registro(s);//REGISTRO DE USUARIO
					try{
						Socket socket = new Socket(server,gate);
						BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						PrintWriter output = new PrintWriter( new OutputStreamWriter(socket.getOutputStream() ),true );	
				
						clearScreen();
						output.println(encriptar("REGISTRO"));
						output.println(encriptar(user.getName()));//SE ENVIA EL USUARIO A SERVER
						output.println(encriptar(user.getPW()));//MEZCLANDO
						
					String status = desencriptar(input.readLine());//OBTENIENDO LA CONFIRMACION DE CONTRASEÑA
			
					if (status.equals("APROBADO")) {
						System.out.println("USUARIO REGISTRADO");
						
						}else{
							System.out.println("NOMBRE DE USUARIO YA USADO, INTENTELO DE NUEVO");
						}

						
						//TERMINANDO LA CONEXION CON SERVIDOR
						break;
					}
					catch(UnknownHostException e){
						e.printStackTrace();
					}
					catch(IOException e){
						e.printStackTrace();
					}
					break;
				case "3":
					System.out.println("SALIENDO DEL CLIENTE.......");
					System.exit(1);
					
				default:
					System.out.println("OPCION NO DISPONIBLE");
					break;
					}
					
				}
			
			
			
			
	}

private static User login(Scanner s){
	
	String name,PW;
	User user;	
		clearScreen();
		System.out.print("INTRODUZCA SU USUARIO: \n");
		name = s.nextLine();					
		System.out.print("INTRODUZCA SU CONTRASEÑA:\n");
		PW = s.nextLine();
		
		user = new User(name, PW);
	return user;
	
	}

private static User registro(Scanner s){
	
	String name,PW;
	User user;
		clearScreen();
		System.out.print("INTRODUZCA EL USUARIO A REGISTRAR: \n");
		name = s.nextLine();					
		System.out.print("INTRODUZCA LA CONTRASEÑA DEL USUARIO A REGISTRAR:\n");
		PW = s.nextLine();
		
		user=new User(name,PW);
	return user;
	
	}	
	

private static void menu(){
		System.out.println("----------------MENU DE CLIENTE DEL 2DO PARCIAL------------------");
		System.out.println("1.- INICIAR SESION COMUNICANDOSE CON EL SERVIDOR");
		System.out.println("2.- REGISTRAR USUARIO");
		System.out.println("3.- SALIR DEL CLIENTE");
		System.out.println("--------------------IGNACIO DIAZ ROMERO--------------------------");
	}
	private static String encriptar(String c) throws UnsupportedEncodingException{
        return Base64.getEncoder().encodeToString(c.getBytes("utf-8"));
    }
    
    private static String desencriptar(String c) throws UnsupportedEncodingException{
        byte[] decode = Base64.getDecoder().decode(c.getBytes());
        return new String(decode, "utf-8");
    }
    
	public static void clearScreen() {  
		System.out.print("\033[H\033[2J");  
		System.out.flush();  
	}


}
  
	
