package game.shawn.Agame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	
	//create an array for keys
	private boolean [] keys = new boolean [120];
	public boolean up, down, left, right; //basic movement directions
	
	//this method updates on keys
	public void update() {
		
		//setting up basic directions
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		
		//this loop is testing keys working state 
		for(int i=0; i<keys.length; i++) {
			if(keys[i]) {
				System.out.println("key : " + i);
			}
		}
	}
	
	//auto generate methods for keys
	public void keyPressed(KeyEvent e) {

		keys[e.getKeyCode()] = true; //pressed - true
	}

	public void keyReleased(KeyEvent e) {

		keys[e.getKeyCode()] = false; //released - false
	}

	public void keyTyped(KeyEvent e) {

		
	}

}
