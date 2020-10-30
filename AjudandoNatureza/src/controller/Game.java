package controller;


import java.awt.Canvas;
import model.Entity;
import model.GarbagemanBlue;
import model.GarbagemanGreen;
import model.GarbagemanRed;
import model.GarbagemanYellow;
import model.Menu;
import model.Player;
import model.Player2;
import model.Spritesheet;
import model.World;
import view.Tela;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame; 

public class Game extends Canvas implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;

	private int CUR_LEVEL = 1, MAX_LEVEL = 2;
	private BufferedImage image;

	public static List<Entity> entities;
	public static List<GarbagemanBlue> garbagemansBlacks;
	public static List<GarbagemanYellow> garbagemansYellows;
	public static List<GarbagemanRed> garbagemansReds;
	public static List<GarbagemanGreen> garbagemansGreens;
	public static Spritesheet spritsheet;
	public static Player player;
	public static Player2 player2;
	public static World world;
	private boolean showMassageGameOver =false;
	private int framesGameOver = 0;
	public static boolean restartGame = false;
	public Menu menu;
	public Tela tela;
	public Mover mover;

	public static String gameState = "MENU";
	

	public Game(){
		Sound.musicBackground.loop();
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		tela = new Tela();
		//tela.initFrame();
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<>();
		garbagemansBlacks = new ArrayList<>();
		garbagemansReds = new ArrayList<>();
		garbagemansYellows = new ArrayList<>();
		garbagemansGreens = new ArrayList<>();
		spritsheet = new Spritesheet("/spritesheet.png");
		player =  new Player(0, 0, 16, 16, spritsheet.getSprite(32, 0, 16, 16));
		player2 = new Player2(0, 0, 16, 16, spritsheet.getSprite(32, 0, 16, 16));
		entities.add( player);
		//sentities.add(player2);
		world = new World("/level2.png");
		menu = new Menu();
		mover = new Mover();

		
		
	}

	public void initFrame(){
		frame = new JFrame("Projeto MPOO v1.5");
		frame.add(this);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}

	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}



	public void tick(){

		if(gameState == "NORMAL"){
			for(int i = 0; i< entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();

				if(player.theEnd == true) {
					//Proximo Level
					CUR_LEVEL++;
					if(CUR_LEVEL > MAX_LEVEL) {
						gameState = "MENU";
						CUR_LEVEL = 1;		
					}
					String newWolrd = "level"+CUR_LEVEL+".png";	
					World.restartGameWorld(newWolrd);
				}
			}
		}else if(gameState == "GAME_OVER") {
			this.framesGameOver++;
			if(this.framesGameOver == 25) {
				this.framesGameOver = 0;
				if(this.showMassageGameOver)
					this.showMassageGameOver = false;
				else
					this.showMassageGameOver = true;	
				
			}
			if(this.restartGame == true) {
				this.restartGame = false;
//				this.gameState = "MENU";
//				CUR_LEVEL = 1;
//				String newWolrd = "level"+CUR_LEVEL+".png";	
//				World.restartGameWorld(newWolrd);
			}
		}else if(gameState == "MENU") {
			menu.tick();

		}




	}
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		world.render(g);
		for(int i = 0; i< entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);

		}
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,16));
		g.drawString("Missão: Leve o lixo a lixeira",0 , 14);
		
		//g.drawString("Pegar Lixo Tecla Q ",0 , 14);
		//g.drawString("Largar Lixo Tecla E ",0 , 34);

		if(gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial",Font.BOLD,36));
			g.drawString("Game Over: ",(WIDTH*SCALE)/2-50 , (HEIGHT*SCALE)/2-20);
			g.setFont(new Font("arial",Font.BOLD,32));
			if(showMassageGameOver) {
				g.drawString(">Pressione Enter para reiniciar< ",(WIDTH*SCALE)/2-220 , (HEIGHT*SCALE)/2+40);
		}
		}

		else if(gameState == "MENU") {
			menu.render(g);
		}

		bs.show();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}

			if(System.currentTimeMillis() - timer >= 1000){
				//System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
			}

		}

		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;

		}else if(e.getKeyCode() == KeyEvent.VK_A) {

			player.left = true;
		}

		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			if(gameState == "MENU") {
				menu.up = true;
			}

		}else if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			if(gameState == "MENU") {
				menu.down = true;
			}

		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player2.right2 = true;

		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {

			player2.left2 = true;
		}

		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player2.up2 = true;
			if(gameState == "MENU") {
				menu.up = true;
			}

		}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player2.down2 = true;
			if(gameState == "MENU") {
				menu.down = true;
			}

		}else if(e.getKeyCode() == KeyEvent.VK_Q) {
			player.pressButton =true;

		}else if(e.getKeyCode() == KeyEvent.VK_E) {
			player.dropButton = true;
		}else if(e.getKeyCode() == KeyEvent.VK_O) {
			player2.pressButton2 =true;

		}else if(e.getKeyCode() == KeyEvent.VK_P) {
			player2.dropButton2 = true;
		}else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
			if(gameState == "MENU") {
				menu.enter = true;
			}
		}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState ="MENU";
			menu.pause = true;
		}
			
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if( e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;

		}else if(e.getKeyCode() == KeyEvent.VK_A) {

			player.left = false;
		}

		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;

		}else if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;

		}if(e.getKeyCode() == KeyEvent.VK_RIGHT ) {
			player2.right2= false;

		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {

			player2.left2 = false;
		}

		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player2.up2 = false;

		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ) {
			player2.down2 = false;

		}else if(e.getKeyCode() == KeyEvent.VK_Q) {
			player.pressButton =false;

		}else if(e.getKeyCode() == KeyEvent.VK_E) {
			player.dropButton = false;
		}else if(e.getKeyCode() == KeyEvent.VK_O) {
			player2.pressButton2 =false;

		}else if(e.getKeyCode() == KeyEvent.VK_P) {
			player2.dropButton2 = false;
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}}

