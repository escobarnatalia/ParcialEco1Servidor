package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import modelo.Color;
import modelo.Coordenada;
import modelo.Nombre;

public class TCPServidor extends Thread {

	// Objeto unico que se va a usar en todas las demás sesiones
	protected static TCPServidor instanciaUnica;

	// Constructor inaccesible por medios normales
	private TCPServidor() {

	}

	// crear y llama el valor unico para las demás clases
	protected static TCPServidor getInstance() {
		if (instanciaUnica == null) {
			instanciaUnica = new TCPServidor();
			instanciaUnica.start();
		}
		return instanciaUnica;

	}
	
	private onMessage observer;
	
	public void setObserver(onMessage observer) {
		this.observer = observer;
	}
	
	private Socket socket;
	private ServerSocket server;
	private BufferedReader reader;
	private BufferedWriter writer;

	@Override
	public void run() {
		try {
			// 1.esperando conexion, saludar
			server = new ServerSocket(7000);
			System.out.println("Esperando conexion");
			socket = server.accept();
			// 3.cliente y servidor conectados
			System.out.println("Cliente conectado");

			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			reader = new BufferedReader(isr);

			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);

			writer = new BufferedWriter(osw);

			while (true) {
				System.out.println("Esperando...");
				String line = reader.readLine();
				System.out.println("Recibido: " + line);
				Gson gson = new Gson();
				
				if(line.contains("nombre")) {
					Nombre name = gson.fromJson(line, Nombre.class);
					observer.nombre(name.getNombre());
				}
				if(line.contains("x") && line.contains("y") ) {
					Coordenada coordenada = gson.fromJson(line, Coordenada.class);
					observer.coordenada(coordenada.getX(), coordenada.getY());
				}
				if(line.contains("r") && line.contains("g") && line.contains("b")) {
					Color color = gson.fromJson(line, Color.class);
					observer.colorNuevo(color.getR(), color.getG(), color.getB());
				
				}
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void SendMessage(String msg) {
		new Thread(() -> {
			try {
				writer.write(msg + "\n");
				writer.flush();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}).start();
		{

		}
	}

}
