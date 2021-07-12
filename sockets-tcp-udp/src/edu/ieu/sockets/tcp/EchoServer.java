package edu.ieu.sockets.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
private Socket clienteSocket;
private ServerSocket serverSocket;
private PrintWriter out;
private BufferedReader in;

	
	public void start(int port) {
		try {
			
		serverSocket =new ServerSocket(port);
		System.out.println("Servidor inicializado en el puerto: "+ port);
		clienteSocket=serverSocket.accept();
		System.out.println("se conecto el cliente"+ clienteSocket.getInetAddress().toString());

		out=new PrintWriter(clienteSocket.getOutputStream(),true);
		in=new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
		
		String inputline="";
		while((inputline=in.readLine())!=null) {
			System.out.println("recbimos del cliente "+ inputline);
			if (".".contentEquals(inputline)) {
				out.println("good bye");
				System.out.println("Servidor recibio orden de apagarse");
				break;
				
			}
			out.println(inputline);
		}
		out.close();
		in.close();
		clienteSocket.close();
		serverSocket.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String []args) {
		EchoServer echoServer=new EchoServer();
		echoServer.start(6000);
		System.out.println("Servidor finalizado");
	}
}
