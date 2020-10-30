package model;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.awt.Rectangle;

import controller.Camara;
import controller.Game;

public class Entity {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	private BufferedImage sprite;
	public static BufferedImage Garbage_B = Game.spritsheet.getSprite(6*16, 0, 16, 16);
	public static BufferedImage Garbage_Y = Game.spritsheet.getSprite(7*16, 0, 16, 16);
	public static BufferedImage Garbage_R = Game.spritsheet.getSprite(8*16, 0, 16, 16);
	public static BufferedImage Garbage_G = Game.spritsheet.getSprite(9*16, 0, 16, 16);
	public static BufferedImage Garbageman_B = Game.spritsheet.getSprite(6*16, 16, 16, 16);
	public static BufferedImage Garbageman_Y = Game.spritsheet.getSprite(7*16, 16, 16, 16);
	public static BufferedImage Garbageman_R = Game.spritsheet.getSprite(8*16, 16, 16, 16);
	public static BufferedImage Garbageman_G = Game.spritsheet.getSprite(9*16, 16, 16, 16);
	public int masky, maskx, mwidth, mheight;
	
	public Entity(int x, int y, int weidth, int height, BufferedImage sprite) {

		this.x = x;
		this.y = y;
		this.width = weidth;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx= 0;
		this.masky= 0;
		this.mwidth = width;
		this.mheight = height;
		
	}
	
	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public double DistanceCal(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	public static boolean isColidding(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx,e1.getY()+e1.masky,e1.mwidth,e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx,e2.getY()+e2.masky,e2.mwidth,e2.mheight);
		
		return e1Mask.intersects(e2Mask);
		}
	
	public void render(Graphics g ) {
		g.drawImage(sprite, this.getX() -Camara.x, this.getY() - Camara.y, null );
		//g.setColor(Color.RED);
		//g.fillRect(this.getX() + maskx - Camara.x, this.getY() +masky - Camara.y, mwidth, mheight);
		
	}
	
	public void tick() {}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int)this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWeidth() {
		return width;
	}

	public void setWeidth(int weidth) {
		this.width = weidth;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	
}
