package servidor;

import processing.core.PApplet;

public class Main extends PApplet implements onMessage {
	

	private TCPServidor tcp;
	private String usuarioNombre;
	private int x;
	private int y;
	private int r, g, b;

	public static void main(String[] args) {
		PApplet.main("servidor.Main");
	}

	public void settings() {
		size(500, 500);
	}

	public void setup() {
		tcp = TCPServidor.getInstance();
		tcp.setObserver(this);
		
		usuarioNombre = null;
		r=0;
		g=0;
		b=0;
	}

	public void draw() {
	background(255);
	
	if(usuarioNombre!=null) {
		fill(0);
		text(usuarioNombre, x, y -50);
	}
	if(x!=0 && y!=0) {
		ellipse(x, y, 40, 40);
	}
	
	if(r!=0 && g!=0 && b!=0) {
		fill(r,g,b);
	}
	

	}
	@Override
	public void coordenada(int x, int y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
	}

	@Override
	public void nombre(String nombre) {
		// TODO Auto-generated method stub
		usuarioNombre = nombre;
	}

	@Override
	public void colorNuevo(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
		
	}

	
	
	


	
}
