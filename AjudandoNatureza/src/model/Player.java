package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import controller.Camara;
import controller.Game;
import controller.Sound;

public class Player extends Entity{
	public boolean right, up ,left, down;
	public int right_dir = 0 , left_dir = 1;
	public int dir = right_dir;
	public double speed = 1.2;
	private int frames =0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private boolean getBlack = false;
	private boolean getRed = false;
	private boolean getYellow = false;
	private boolean getGreen = false;
	public boolean pressButton = false;
	public boolean dropButton = false;
	public boolean theEnd = false;
	public int all = 0;
	public int ptLixoVerde = 0;
	public int ptLixoVermelho = 0;
	public int ptLixoAzul = 0;
	public int ptLixoAmarelo = 0;
	private GarbagemanBlue garbagemanBlue;
	private GarbagemanYellow garbagemanYellow;
	private GarbagemanRed garbagemanRed;
	private GarbagemanGreen garbagemanGreen;

	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;


	public Player(int x, int y, int weidth, int height, BufferedImage sprite) {
		super(x, y, weidth, height, sprite);

		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];

		for(int i = 0; i< 4; i++) {
			rightPlayer[i] = Game.spritsheet.getSprite(32+(i*16), 0, 16, 16);
		}
		for(int i = 0; i< 4; i++) {
			leftPlayer[i] = Game.spritsheet.getSprite(32+(i*16), 16, 16, 16);
		}

	}


	public void tick() {
		moved = false;
		if(right && World.isFree((int)(x+speed), this.getY())){
			moved = true;
			dir = right_dir;
			x+=speed;
		}
		else if(left && World.isFree((int)(x-speed), this.getY())){
			moved = true;
			dir = left_dir;
			x-=(speed-0.2);
		}
		if(up && World.isFree(this.getX(), (int)(y-speed))){
			moved = true;
			y-=(speed-0.2);
			
		}
		else if(down && World.isFree(this.getX(), (int)(y+speed))){
			moved = true;
			y+=speed;
			
		}
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}
		

		checkCollisionGBBlue();
		checkCollisionGBRed();
		checkCollisionGBYellow();
		checkCollisionGBGreen();
		allGarbages();
	
		Camara.x = Camara.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH); // Sempre ficar no meio, vendo quando falta, Centralizar o jogador
		Camara.y = Camara.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
	
	}

	
	public void allGarbages() {
		if(all == 20) {
			theEnd = true;
		}
	}

	public void checkCollisionGBBlue() {
		for(int i =0; i< Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof GarbagemanBlue) {
				if(Entity.isColidding(this, atual) && pressButton && !getYellow && !getRed &&!getGreen) {
					getBlack =true;
		
					//System.out.println("Colidou Lixo Preto");
					Game.entities.remove(i);
					Sound.collectEffect.play();
					
				}
			}


		}
		if(dropButton && getBlack) {
			getBlack = false;
			if(dir == right_dir) {
				garbagemanBlue = new GarbagemanBlue(Game.player.getX()+12, Game.player.getY()+1, 16, 16, Entity.Garbageman_B);

				if(this.collisionGabageBlack(garbagemanBlue)) {
				}else {
					Game.entities.add(garbagemanBlue);
					Sound.collectEffect.play();

				}}
			else if(dir == left_dir) {
				garbagemanBlue = new GarbagemanBlue(Game.player.getX()-9, Game.player.getY()+1, 16, 16, Entity.Garbageman_B);

				if(this.collisionGabageBlack(garbagemanBlue)) {

				}else {
					Game.entities.add(garbagemanBlue);
					Sound.collectEffect.play();
				}
			}
		}

	}	
	
	public void checkCollisionGBGreen() {
		for(int i =0; i< Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof GarbagemanGreen) {
				if(Entity.isColidding(this, atual) && pressButton && !getYellow && !getRed && !getBlack ) {
					getGreen =true;
		
					//System.out.println("Colidou Lixo Preto");
					Game.entities.remove(i);
					Sound.collectEffect.play();
					
				}
			}


		}
		if(dropButton && getGreen) {
			getGreen = false;
			if(dir == right_dir) {
				garbagemanGreen = new GarbagemanGreen(Game.player.getX()+12, Game.player.getY()+1, 16, 16, Entity.Garbageman_G);

				if(this.collisionGabageGreen(garbagemanGreen)) {
				}else {
					Game.entities.add(garbagemanGreen);
					Sound.collectEffect.play();

				}}
			else if(dir == left_dir) {
				garbagemanGreen = new GarbagemanGreen(Game.player.getX()-9, Game.player.getY()+1, 16, 16, Entity.Garbageman_G);

				if(this.collisionGabageGreen(garbagemanGreen)) {

				}else {
					Game.entities.add(garbagemanGreen);
					Sound.collectEffect.play();
				}
			}
		}

	}

	public void checkCollisionGBYellow() {
		for(int i =0; i< Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof GarbagemanYellow) {
				if(Entity.isColidding(this, atual) && pressButton && !getBlack && !getRed &&!getGreen) {
					getYellow =true;
					//System.out.println("Colidou Lixo Amarelo");
					Game.entities.remove(i);
					Sound.collectEffect.play();
			}


		}if(dropButton && getYellow) {
			getYellow = false;
			if(dir == right_dir) {
				garbagemanYellow = new GarbagemanYellow(Game.player.getX()+12, Game.player.getY()+1, 16, 16, Entity.Garbageman_Y);

				if(this.collisionGabageYellow(garbagemanYellow)) {
				}else {
					Game.entities.add(garbagemanYellow);
					Sound.collectEffect.play();

				}}
			else if(dir == left_dir) {
				garbagemanYellow = new GarbagemanYellow(Game.player.getX()-9, Game.player.getY()+1, 16, 16, Entity.Garbageman_Y);

				if(this.collisionGabageYellow(garbagemanYellow)) {

				}else {
					Game.entities.add(garbagemanYellow);
					Sound.collectEffect.play();
				}
			}
		}}
	}
	
	public void checkCollisionGBRed() {
		for(int i =0; i< Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof GarbagemanRed) {
				if(Entity.isColidding(this, atual) && pressButton && !getYellow && !getBlack &&!getGreen) {
					getRed = true;
					//System.out.println("Colidou Lixo Vermelho");
					Game.entities.remove(i);
					Sound.collectEffect.play();
				}
			}


		}
	if(dropButton && getRed) {
		getRed = false;
		if(dir == right_dir) {
			garbagemanRed = new GarbagemanRed(Game.player.getX()+12, Game.player.getY()+1, 16, 16, Entity.Garbageman_R);
			if(this.collisionGabageRed(garbagemanRed)) {
			}else {
				Game.entities.add(garbagemanRed);
				Sound.collectEffect.play();

			}}
		else if(dir == left_dir) {
			garbagemanRed = new GarbagemanRed(Game.player.getX()-9, Game.player.getY()+1, 16, 16, Entity.Garbageman_R);

			if(this.collisionGabageRed(garbagemanRed)) {

			}else {
				Game.entities.add(garbagemanRed);
				Sound.collectEffect.play();
			}
		} 
	}
	}


	public boolean collisionGabageBlack(GarbagemanBlue garbagemanBlack) {
		Game.garbagemansBlacks.add(garbagemanBlack);
		for(int i = 0; i < Game.entities.size();i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof GarbageBlue) {
				if (Entity.isColidding(garbagemanBlack, e)) {
					Sound.hitEffect.play();
					all++;
					System.out.println(all);
					ptLixoAzul+=5 ;
					System.out.println("Foi Papel");
					return true;
				}
			}
		}
		return false;
		
	}
	
	public boolean collisionGabageYellow(GarbagemanYellow garbagemanYellow) {
		Game.garbagemansYellows.add(garbagemanYellow);
		for(int i = 0; i < Game.entities.size();i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof GarbageYellow) {
				if (Entity.isColidding(garbagemanYellow, e)) {
					Sound.hitEffect.play();
					all++;
					System.out.println(all);
					ptLixoAmarelo+=15;
					System.out.println("Foi Metal");
					return true;
				}
			}
			
		}
		return false;
	}
	
	public boolean collisionGabageRed(GarbagemanRed garbagemanRed) {
		Game.garbagemansReds.add(garbagemanRed);
		for(int i = 0; i < Game.entities.size();i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof GarbageRed) {
				if (Entity.isColidding(garbagemanRed, e)) {
					Sound.hitEffect.play();
					all++;
					System.out.println(all);
					ptLixoVermelho+=20;
					System.out.println("Foi Plastico");
					return true;
				}
			}
			
		}
		return false;
	}
	
	public boolean collisionGabageGreen(GarbagemanGreen garbagemanGreen) {
		Game.garbagemansGreens.add(garbagemanGreen);
		for(int i = 0; i < Game.entities.size();i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof GarbageGreen) {
				if (Entity.isColidding(garbagemanGreen, e)) {
					Sound.hitEffect.play();
					all++;
					System.out.println(all);
					ptLixoVerde+=25;
					System.out.println("Foi Vidro");
					return true;
				}
			}
			
		}
		return false;
	}
	

	public void render(Graphics g) {
		if(!getYellow && !getBlack && !getRed && !getGreen) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() -Camara.x,  this.getY()- Camara.y, null);
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX()-Camara.x, this.getY()-Camara.y, null);	
			}
		}else if(getBlack) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() -Camara.x,  this.getY()- Camara.y, null);
				g.drawImage(Garbageman_B, this.getX() -Camara.x+12, this.getY()+1- Camara.y, null);
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX()-Camara.x, this.getY()-Camara.y, null);
				g.drawImage(Garbageman_B, this.getX() -Camara.x-9, this.getY()+1- Camara.y, null);
			}
		}else if(getRed) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() -Camara.x,  this.getY()- Camara.y, null);
				g.drawImage(Garbageman_R, this.getX() -Camara.x+12, this.getY()+1- Camara.y, null);
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX()-Camara.x, this.getY()-Camara.y, null);
				g.drawImage(Garbageman_R, this.getX() -Camara.x-9, this.getY()+1- Camara.y, null);
			}
		}else if(getYellow) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() -Camara.x,  this.getY()- Camara.y, null);
				g.drawImage(Garbageman_Y, this.getX() -Camara.x+12, this.getY()+1- Camara.y, null);
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX()-Camara.x, this.getY()-Camara.y, null);
				g.drawImage(Garbageman_Y, this.getX() -Camara.x-9, this.getY()+1- Camara.y, null);
			}
		}else if(getGreen) {
			if(dir == right_dir) {
				g.drawImage(rightPlayer[index], this.getX() -Camara.x,  this.getY()- Camara.y, null);
				g.drawImage(Garbageman_G, this.getX() -Camara.x+12, this.getY()+1- Camara.y, null);
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX()-Camara.x, this.getY()-Camara.y, null);
				g.drawImage(Garbageman_G, this.getX() -Camara.x-9, this.getY()+1- Camara.y, null);
			}
		}


	}
}
