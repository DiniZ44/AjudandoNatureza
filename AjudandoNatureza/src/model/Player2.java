package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import controller.Camara;
import controller.Game;
import controller.Sound;

public class Player2 extends Entity{
	public boolean right2, up2 ,left2, down2;
	public int right_dir2 = 0 , left_dir2 = 1;
	public int dir2 = right_dir2;
	public double speed2 = 1.2;
	private int frames2 =0, maxFrames2 = 5, index2 = 0, maxIndex2 = 3;
	private boolean moved2 = false;
	private boolean getBlue = false;
	private boolean getRed = false;
	private boolean getYellow = false;
	private boolean getGreen = false;
	public boolean pressButton2 = false;
	public boolean dropButton2 = false;
	public boolean theEnd = false;
	public int all2 = 0;
	public int ptLixoVerde2 = 0;
	public int ptLixoVermelho2 = 0;
	public int ptLixoAzul2 = 0;
	public int ptLixoAmarelo2 = 0;
	private GarbagemanBlue garbagemanBlue;
	private GarbagemanGreen garbagemanGreen;
	private GarbagemanYellow garbagemanYellow;
	private GarbagemanRed garbagemanRed;

	private BufferedImage[] rightPlayer2;
	private BufferedImage[] leftPlayer2;

	public Player2(int x, int y, int weidth, int height, BufferedImage sprite) {
		super(x, y, weidth, height, sprite);

		rightPlayer2 = new BufferedImage[4];
		leftPlayer2 = new BufferedImage[4];

		for(int i = 0; i< 4; i++) {
			rightPlayer2[i] = Game.spritsheet.getSprite(32+(i*16), 32, 16, 16);
		}
		for(int i = 0; i< 4; i++) {
			leftPlayer2[i] = Game.spritsheet.getSprite(32+(i*16), 48, 16, 16);
		}
	}
	
	public void tick() {
		
		Camara.x = Camara.clamp((Game.player.getX()) - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH); // Sempre ficar no meio, vendo quando falta, Centralizar o jogador
		Camara.y = Camara.clamp((Game.player.getY()) - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
		
//		if(this.getX() > 0 && this.getY() > 0 &&  &&  this.getY() < Game.HEIGHT) {
		System.out.println(this.getX());
//		System.out.println(this.getX());
		if(this.getX() != ((Game.player.getX()) - (Game.WIDTH/2)) && this.getY() != ((Game.player.getY()) - (Game.HEIGHT/2))) {
		moved2 = false;
		if(right2 && World.isFree((int)(x+speed2), this.getY())){
			moved2 = true;
			dir2 = right_dir2;
			x+=speed2;
			
		}
		else if(left2 && World.isFree((int)(x-speed2), this.getY())){
			moved2 = true;
			dir2 = left_dir2;
			x-=(speed2-0.2);
		}
		if(up2 && World.isFree(this.getX(), (int)(y-speed2))){
			moved2 = true;
			y-=(speed2-0.2);
			
		}
		else if(down2 && World.isFree(this.getX(), (int)(y+speed2))){
			moved2 = true;
			y+=speed2;
			
		}
		
		if(moved2) {
			frames2++;
			if(frames2 == maxFrames2) {
				frames2 = 0;
				index2++;
				if(index2 > maxIndex2)
					index2 = 0;
			}
		}
		
		}

		checkCollisionGBBlue();
		checkCollisionGBRed();
		checkCollisionGBYellow();
		checkCollisionGBGreen();
		
		
//		Camara.x = Camara.clamp((Game.player.getX()) - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH); // Sempre ficar no meio, vendo quando falta, Centralizar o jogador
//		Camara.y = Camara.clamp((Game.player.getY()) - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
	}
	

	public void allGarbages() {
		if(all2 == 20) {
			theEnd = true;
		}
	}
	
	
	public void checkCollisionGBBlue() {
		for(int i =0; i< Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof GarbagemanBlue) {
				if(Entity.isColidding(this, atual) && pressButton2 && !getYellow && !getRed  && !getGreen) {
					getBlue =true;
		
					//System.out.println("Colidou Lixo Preto");
					Game.entities.remove(i);
					Sound.collectEffect.play();
					
				}
			}


		}
		if(dropButton2 && getBlue) {
			getBlue = false;
			if(dir2 == right_dir2) {
				garbagemanBlue = new GarbagemanBlue(Game.player2.getX()+12, Game.player2.getY()+1, 16, 16, Entity.Garbageman_B);

				if(this.collisionGabageBlack(garbagemanBlue)) {
				}else {
					Game.entities.add(garbagemanBlue);
					Sound.collectEffect.play();

				}}
			else if(dir2 == left_dir2) {
				garbagemanBlue = new GarbagemanBlue(Game.player2.getX()-9, Game.player2.getY()+1, 16, 16, Entity.Garbageman_B);

				if(this.collisionGabageBlack(garbagemanBlue)) {

				}else {
					Game.entities.add(garbagemanBlue);
					Sound.collectEffect.play();
				}
			}
		}

	}

	public void checkCollisionGBYellow() {
		for(int i =0; i< Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof GarbagemanYellow) {
				if(Entity.isColidding(this, atual) && pressButton2 && !getBlue && !getRed && !getGreen) {
					getYellow =true;
					//System.out.println("Colidou Lixo Amarelo");
					Game.entities.remove(i);
					Sound.collectEffect.play();
			}


		}if(dropButton2 && getYellow) {
			getYellow = false;
			if(dir2 == right_dir2) {
				garbagemanYellow = new GarbagemanYellow(Game.player2.getX()+12, Game.player2.getY()+1, 16, 16, Entity.Garbageman_Y);

				if(this.collisionGabageYellow(garbagemanYellow)) {
				}else {
					Game.entities.add(garbagemanYellow);
					Sound.collectEffect.play();

				}}
			else if(dir2 == left_dir2) {
				garbagemanYellow = new GarbagemanYellow(Game.player2.getX()-9, Game.player2.getY()+1, 16, 16, Entity.Garbageman_Y);

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
				if(Entity.isColidding(this, atual) && pressButton2 && !getYellow && !getBlue && !getGreen) {
					getRed = true;
					//System.out.println("Colidou Lixo Vermelho");
					Game.entities.remove(i);
					Sound.collectEffect.play();
				}
			}


		}
	if(dropButton2 && getRed) {
		getRed = false;
		if(dir2 == right_dir2) {
			garbagemanRed = new GarbagemanRed(Game.player2.getX()+12, Game.player2.getY()+1, 16, 16, Entity.Garbageman_R);
			if(this.collisionGabageRed(garbagemanRed)) {
			}else {
				Game.entities.add(garbagemanRed);
				Sound.collectEffect.play();

			}}
		else if(dir2 == left_dir2) {
			garbagemanRed = new GarbagemanRed(Game.player2.getX()-9, Game.player2.getY()+1, 16, 16, Entity.Garbageman_R);

			if(this.collisionGabageRed(garbagemanRed)) {

			}else {
				Game.entities.add(garbagemanRed);
				Sound.collectEffect.play();
			}
		} 
	}
	}

	public void checkCollisionGBGreen() {
		for(int i =0; i< Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof GarbagemanGreen) {
				if(Entity.isColidding(this, atual) && pressButton2 && !getYellow && !getRed && !getBlue ) {
					getGreen =true;
		
					//System.out.println("Colidou Lixo Preto");
					Game.entities.remove(i);
					Sound.collectEffect.play();
					
				}
			}


		}
		if(dropButton2 && getGreen) {
			getGreen = false;
			if(dir2 == right_dir2) {
				garbagemanGreen = new GarbagemanGreen(Game.player2.getX()+12, Game.player2.getY()+1, 16, 16, Entity.Garbageman_G);

				if(this.collisionGabageGreen(garbagemanGreen)) {
				}else {
					Game.entities.add(garbagemanGreen);
					Sound.collectEffect.play();

				}}
			else if(dir2 == left_dir2) {
				garbagemanGreen = new GarbagemanGreen(Game.player2.getX()-9, Game.player2.getY()+1, 16, 16, Entity.Garbageman_G);

				if(this.collisionGabageGreen(garbagemanGreen)) {

				}else {
					Game.entities.add(garbagemanGreen);
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
					all2++;
					ptLixoAzul2+=5;
					System.out.println("Foi Plastico 2");
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
					all2++;
					ptLixoAmarelo2+=15;
					System.out.println("Foi Metal 2");
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
					all2++;
					ptLixoVermelho2+=20;
					System.out.println("Foi Plastico 2");
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
					all2++;
					ptLixoVerde2+=25;
					System.out.println("Foi Vidro 2");
					return true;
				}
			}
			
		}
		return false;
	}
	
	public void render(Graphics g) {
		if(!getYellow && !getBlue && !getRed && !getGreen ) {
			if(dir2 == right_dir2) {
				g.drawImage(rightPlayer2[index2], this.getX() -Camara.x,  this.getY()- Camara.y, null);
			}else if(dir2 == left_dir2) {
				g.drawImage(leftPlayer2[index2], this.getX()-Camara.x, this.getY()-Camara.y, null);	
			}
		}else if(getBlue) {
			if(dir2 == right_dir2) {
				g.drawImage(rightPlayer2[index2], this.getX() -Camara.x,  this.getY()- Camara.y, null);
				g.drawImage(Garbageman_B, this.getX() -Camara.x+12, this.getY()+1- Camara.y, null);
			}else if(dir2 == left_dir2) {
				g.drawImage(leftPlayer2[index2], this.getX()-Camara.x, this.getY()-Camara.y, null);
				g.drawImage(Garbageman_B, this.getX() -Camara.x-9, this.getY()+1- Camara.y, null);
			}
		}else if(getRed) {
			if(dir2 == right_dir2) {
				g.drawImage(rightPlayer2[index2], this.getX() -Camara.x,  this.getY()- Camara.y, null);
				g.drawImage(Garbageman_R, this.getX() -Camara.x+12, this.getY()+1- Camara.y, null);
			}else if(dir2 == left_dir2) {
				g.drawImage(leftPlayer2[index2], this.getX()-Camara.x, this.getY()-Camara.y, null);
				g.drawImage(Garbageman_R, this.getX() -Camara.x-9, this.getY()+1- Camara.y, null);
			}
		}else if(getYellow) {
			if(dir2 == right_dir2) {
				g.drawImage(rightPlayer2[index2], this.getX() -Camara.x,  this.getY()- Camara.y, null);
				g.drawImage(Garbageman_Y, this.getX() -Camara.x+12, this.getY()+1- Camara.y, null);
			}else if(dir2 == left_dir2) {
				g.drawImage(leftPlayer2[index2], this.getX()-Camara.x, this.getY()-Camara.y, null);
				g.drawImage(Garbageman_Y, this.getX() -Camara.x-9, this.getY()+1- Camara.y, null);
			}
		}else if(getGreen) {
			if(dir2 == right_dir2) {
				g.drawImage(rightPlayer2[index2], this.getX() -Camara.x,  this.getY()- Camara.y, null);
				g.drawImage(Garbageman_G, this.getX() -Camara.x+12, this.getY()+1- Camara.y, null);
			}else if(dir2 == left_dir2) {
				g.drawImage(leftPlayer2[index2], this.getX()-Camara.x, this.getY()-Camara.y, null);
				g.drawImage(Garbageman_G, this.getX() -Camara.x-9, this.getY()+1- Camara.y, null);
			}
		}


	}
	

}
