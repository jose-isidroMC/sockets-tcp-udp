package edu.ieu.sockets.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import edu.ieu.sockets.tcp.EchoClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteGrafico {

	private JFrame frame;
	private JTextField textMensaje;
	private JTextPane textRespuesta;
	private JButton btnEnviar;
	private EchoClient echoClient = new EchoClient();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClienteGrafico window = new ClienteGrafico();
					window.frame.setVisible(true);
					window.conectar();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void conectar() {
		echoClient.startConnection("localhost", 6000);
		textRespuesta.setText("conectado al servidor localhost:6000");
		
	}
	public String agregarTextoRespuesta(String nuevoTexto) {
		StringBuilder builder=new StringBuilder();
		builder.append(textRespuesta.getText()  );
		builder.append(nuevoTexto+"\n");
		return builder.toString();
	}

	
	/**
	 * Create the application.
	 */
	public ClienteGrafico() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mensaje= textMensaje.getText();
				System.out.println("Escriba un mensaje para el servidor");				
			    System.out.println("Mensaje para el Servidor "+ mensaje);
			    
				String respuesta=echoClient.sendMessage(mensaje);
				System.out.println("respuesta: " + respuesta);
				String historialRespuesta=agregarTextoRespuesta(respuesta);
				textRespuesta.setText(historialRespuesta);
				
				if (respuesta.equals("good bye")) {
					System.out.println("conexion finalizada");
				    textRespuesta.setText("conexion finalizada..\n"+"Recinicie el programa");
				    echoClient.stopConnection();
				    btnEnviar.setEnabled(false);
				}
			}
		});
		frame.getContentPane().add(btnEnviar, BorderLayout.EAST);
		
		textMensaje = new JTextField();
		frame.getContentPane().add(textMensaje, BorderLayout.NORTH);
		textMensaje.setColumns(10);
		
	 textRespuesta = new JTextPane();
		frame.getContentPane().add(textRespuesta, BorderLayout.CENTER);
	}

}
