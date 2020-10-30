package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import controller.Camara;
import controller.Game;

public class World {
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			tiles = new Tile[map.getWidth()*map.getHeight()];
			int[] pixels = new int[map.getWidth()*map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx= 0; xx < map.getWidth();xx++) {
				for(int yy =0; yy<map.getHeight(); yy++) {
					int pixelAtual = pixels[xx+(yy*map.getWidth())];
					tiles[xx+(yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					
					
					if( pixelAtual == 0xFF000000) {
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
						// Chao
					}else if(pixelAtual == 0xFFFFFFFF) {
						// Parede
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
					}
					
					
					else if(pixelAtual == 0xFFFF006E) {
						// Player
						Game.player.setX(xx*TILE_SIZE);
						Game.player.setY(yy*TILE_SIZE);
					}else if(pixelAtual == 0xFF00FFFF) {
						// Player2
						Game.player2.setX(xx*TILE_SIZE);
						Game.player2.setY(yy*TILE_SIZE);
					}
					
					
					else if(pixelAtual == 0xFF00137F) {
						// lixoAzul -- PAPEL
						Game.entities.add(new GarbagemanBlue(xx*16, yy*16, 16, 16, Entity.Garbageman_B));
					}else if(pixelAtual == 0xFF7F0000) {
						// lixoVermelho -- PLASTICO
						Game.entities.add(new GarbagemanRed(xx*16, yy*16, 16, 16, Entity.Garbageman_R));
					}else if(pixelAtual == 0xFF7F6A00) {
						// lixoAmarelo -- METAL
						Game.entities.add(new GarbagemanYellow(xx*16, yy*16, 16, 16, Entity.Garbageman_Y));
					}else if(pixelAtual == 0xFF267F00) {
						//lixoVerde -- VIDRO
						Game.entities.add(new GarbagemanGreen(xx*16, yy*16, 16, 16, Entity.Garbageman_G));
					}
					
					
					else if(pixelAtual == 0xFF0026FF) {
						// lixeiraAzul
						Game.entities.add(new GarbageBlue(xx*16, yy*16, 16, 16, Entity.Garbage_B));
					}else if(pixelAtual == 0xFFFF0000) {
						// lixeiraVermelha
						Game.entities.add(new GarbageRed(xx*16, yy*16, 16, 16, Entity.Garbage_R));
					}else if(pixelAtual == 0xFFFFD800) {
						// lixeiraAmarela
						Game.entities.add(new GarbageYellow(xx*16, yy*16, 16, 16, Entity.Garbage_Y));
					}else if(pixelAtual == 0xFF4CFF00) {
						//lixeiraVerde
						Game.entities.add(new GarbageGreen(xx*16, yy*16, 16, 16, Entity.Garbage_G));
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext/ TILE_SIZE;
		int y1 = ynext/ TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1)/ TILE_SIZE;
		int y2 = ynext/ TILE_SIZE;
		
		int x3 = xnext/ TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1)/ TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1)/ TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1)/ TILE_SIZE;
		
		return !(tiles[x1+(y1*HEIGHT)] instanceof WallTile ||
				tiles[x2+(y2*HEIGHT)] instanceof WallTile ||
				tiles[x3+(y3*HEIGHT)] instanceof WallTile ||
				tiles[x4+(y4*HEIGHT)] instanceof WallTile 
										);
	}
	
	
	public static void restartGameWorld(String level) {
		Game.entities = new ArrayList<>();
		Game.garbagemansBlacks = new ArrayList<>();
		Game.garbagemansReds = new ArrayList<>();
		Game.garbagemansYellows = new ArrayList<>();
		Game.spritsheet = new Spritesheet("/spritesheet.png");
		Game.player =  new Player(0, 0, 16, 16, Game.spritsheet.getSprite(32, 0, 16, 16));
		Game.entities.add( Game.player);
		Game.world = new World("/"+level);
		return;
	}
	
	public void render(Graphics g) {
		int xstart = Camara.x >> 4;
		int ystart = Camara.y >> 4;
//		int xfinal = xstart +(Game.WIDTH>> 4);
//		int yfinal = ystart +(Game.HEIGHT>> 4);
		for(int xx =0; xx<= World.WIDTH; xx++) {
			for(int yy =0; yy<= World.HEIGHT ; yy++) {
				if(xx < 0 || yy< 0|| xx>= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);  
				
				
				
//				for(int xx =xstart; xx<= xfinal; xx++) {
//					for(int yy =ystart; yy<= yfinal ; yy++) {
			}
		}
	}

}
