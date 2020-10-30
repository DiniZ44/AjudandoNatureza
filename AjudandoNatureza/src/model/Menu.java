package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import controller.Game;

public class Menu {

	public String[] options ={"novo jogo", "multiplayer", "ajuda", "creditos" ,"sair"};
	public int currentOption = 0;
	public int maxOptions = options.length-1;
	public boolean single = false;
	public boolean multi = false;
	public boolean up, down, enter;
	public boolean pause = false;
	BufferedImage menu = null;
	BufferedImage fundoPause= null;
	private int CUR_LEVEL = 1, MAX_LEVEL = 2;
	SalvarXML salvarXML ;
	
	public Menu() {
		salvarXML = new SalvarXML();
	}
//	public Menu() {
//		try {
//			menu = ImageIO.read(getClass().getResource("/menu.png"));
//			fundoPause = ImageIO.read(getClass().getResource("/fundoPause.jpg"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public void tick() {
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0)
				currentOption = maxOptions;
		}
		if(down){
			down= false;
			currentOption++;
			if(currentOption > maxOptions)
				currentOption = 0;
		}
		if(enter) {
			enter = false; 
			if(options[currentOption] == "novo jogo") {
				Game.gameState = "NORMAL";
				pause = false;
			}else if(options[currentOption] == "multiplayer"){
				String newWolrd = "levelm2.png";
				Game.world.restartGameWorld(newWolrd);
				Game.entities.add(Game.player2);
				Game.gameState = "NORMAL";
				if(Game.player2.theEnd) {
					CUR_LEVEL++;
					if(CUR_LEVEL > MAX_LEVEL) {
						Game.gameState = "MENU";
						CUR_LEVEL = 1;	}
					String newWolrd1 = "levelM"+CUR_LEVEL+".png";	
					World.restartGameWorld(newWolrd1);
				}
			}else if(options[currentOption] == "ajuda"){
				JOptionPane.showMessageDialog(null, "Mover: W,A,S,D /Up,Down,Left,Right --- Pegar: Q/O --- Soltar: E/P", "Controles", JOptionPane.INFORMATION_MESSAGE);
				//Game.ajudaPanel.setVisible(true);
			}else if(options[currentOption] == "creditos"){
				JOptionPane.showMessageDialog(null, "Aluno: Hakkinen Diniz --- Projeto: MPOO --- Prof: Richarlyson --- Turma: 2018.2", "Créditos", JOptionPane.INFORMATION_MESSAGE);
				//Game.creditosPanel.setVisible(true);
			}else if(options[currentOption] == "sair" ) {
					salvarXML.salvar(Game.player);
				for(int i = 0;i <Game.entities.size() ;i++) {
					Entity player = Game.entities.get(i);
					if(player instanceof Player2) {
						salvarXML.salvar2(Game.player2);
					}
				}
				System.exit(0);

			}
		}
	}
	public void render(Graphics g) {
		//Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.black);
		//g2.setColor(new Color(0, 0, 0, 100));
		//g.setColor(Color.black);
		//g.drawImage(fundoPause, 0, 0, null);
		g.fillRect(0, 0, (Game.WIDTH*Game.SCALE), (Game.HEIGHT*Game.SCALE));
		//g.drawImage(menu, 0, 0, null);
		g.setColor(Color.red);
		g.setFont(new Font("arial",Font.BOLD,36));
		g.drawString("Reciclando",(Game.WIDTH*Game.SCALE)/2-80, (Game.HEIGHT*Game.SCALE)/2-130);
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,28));
		if(pause == false) {
			g.drawString("Single Player",(Game.WIDTH*Game.SCALE)/2-70, 200);
			g.drawString("Multiplayer",(Game.WIDTH*Game.SCALE)/2-60, 240);
			g.drawString("Ajuda",(Game.WIDTH*Game.SCALE)/2-30, 280);
			g.drawString("Créditos",(Game.WIDTH*Game.SCALE)/2-45, 320);
			g.drawString("Sair",(Game.WIDTH*Game.SCALE)/2-15, 355);
		}else {
			g.drawString("Resumir Jogo",(Game.WIDTH*Game.SCALE)/2-80, 200);
			g.drawString("Multiplayer",(Game.WIDTH*Game.SCALE)/2-60, 240);
			g.drawString("Ajuda",(Game.WIDTH*Game.SCALE)/2-30, 280);
			g.drawString("Créditos",(Game.WIDTH*Game.SCALE)/2-45, 320);
			g.drawString("Sair",(Game.WIDTH*Game.SCALE)/2-15, 355);
		}

		if(options[currentOption] == "novo jogo") {
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2-100, 200);
		}else if(options[currentOption] == "multiplayer") {
			g.drawString(">",(Game.WIDTH*Game.SCALE)/2-90, 240);
		}else if(options[currentOption] == "ajuda") {
			g.drawString(">",(Game.WIDTH*Game.SCALE)/2-60, 280);
		}else if(options[currentOption] == "creditos") {
			g.drawString(">",(Game.WIDTH*Game.SCALE)/2-80, 320);
		}else if(options[currentOption] == "sair") {
			g.drawString(">",(Game.WIDTH*Game.SCALE)/2-60, 360);
		}
	}
}
