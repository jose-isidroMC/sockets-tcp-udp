package edu.ieu.sockets.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerMultiUser {
	private ServerSocket serverSocket;
	
	public void start(int port) {
		try {
			serverSocket=new ServerSocket(port);
		    while (true) {
                  new EchoClienteHandler(serverSocket.accept()).start();				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void stop() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String []args) {
		EchoServerMultiUser echoServer=new EchoServerMultiUser();
		System.out.println("Servidor inicializado");
		echoServer.start(6000);
		System.out.println("Servidor finalizado");
	}

	private static class EchoClienteHandler extends Thread{

		private Socket clienSocket;
		private PrintWriter out;
		private BufferedReader in;	
		
		
		
		public EchoClienteHandler(Socket socket) {

			this.clienSocket=socket;
			
			
		}



		@Override
		public void run() {
			try {
				out=new PrintWriter(clienSocket.getOutputStream(),true);
				in=new BufferedReader(new InputStreamReader(clienSocket.getInputStream()));

				String inputline;
				while((inputline=in.readLine())!=null) {
					if (".".equals(inputline)) {
						out.println("good bye");
						break;
						
					}
					out.println(inputline);
				}
				in.close();
				out.close();
				clienSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
			
		}
		  
	}
}
