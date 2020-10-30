package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Menu;

public class Mover implements KeyListener{
	
	public Menu menu;

	public Mover() {
		menu = new Menu();
	}
		
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			Game.player.right = true;

		}else if(e.getKeyCode() == KeyEvent.VK_LEFT|| e.getKeyCode() == KeyEvent.VK_A) {

			Game.player.left = true;
		}

		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			Game.player.up = true;
			if(Game.gameState == "MENU") {
				menu.up = true;
			}

		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			Game.player.down = true;
			if(Game.gameState == "MENU") {
				menu.down = true;
			}

		}else if(e.getKeyCode() == KeyEvent.VK_Q) {
			Game.player.pressButton =true;

		}else if(e.getKeyCode() == KeyEvent.VK_E) {
			Game.player.dropButton = true;
		}else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			Game.restartGame = true;
			if(Game.gameState == "MENU") {
				menu.enter = true;
			}
		}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Game.gameState ="MENU";
			menu.pause = true;
		}
			
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			Game.player.right = false;

		}else if(e.getKeyCode() == KeyEvent.VK_LEFT|| e.getKeyCode() == KeyEvent.VK_A) {

			Game.player.left = false;
		}

		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			Game.player.up = false;

		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			Game.player.down = false;

		}else if(e.getKeyCode() == KeyEvent.VK_Q) {
			Game.player.pressButton =false;

		}else if(e.getKeyCode() == KeyEvent.VK_E) {
			Game.player.dropButton = false;
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
