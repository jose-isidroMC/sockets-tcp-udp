package edu.ieu.sockets.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoClient {

private Socket clienteSocket;
private ServerSocket serverSocket;
private PrintWriter out;
private BufferedReader in;

public void startConnection(String ip,int port) {
	try {
		clienteSocket=new Socket(ip,port);
		out=new PrintWriter(clienteSocket.getOutputStream(),true);
		in=new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
		
	} catch (IOException ex) {
		ex.printStackTrace();
	}

	}
	public String sendMessage(String msg) {
		try {
			out.println(msg);
			String resp=in.readLine();
			return resp;
		} catch (IOException ex) {
		ex.printStackTrace();
		return null;
		
		}
	}
	public void stopConnection() {
		try {
			in.close();
			out.close();
			clienteSocket.close();
		} catch (IOException e) {
          e.printStackTrace();
		}
	}
	public static void main(String[]args) {
		BufferedReader inTeclado=new BufferedReader(new InputStreamReader(System.in));
		EchoClient echoClient =new EchoClient(); 
		echoClient.startConnection("localhost", 6000);
		try {
		while (true) {
			System.out.println("Escriba un mensaje para el servidor");
			String  mensaje=inTeclado.readLine();
			System.out.println("Mensaje para el Servidor "+ mensaje);
			String respuesta=echoClient.sendMessage(mensaje);
			if (respuesta.equals("good bye")) {
				System.out.println("conexion finalizada");
				break;
			}
			
		}
		echoClient.stopConnection();
		} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
