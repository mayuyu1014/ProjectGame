package game.shawn.Agame.entity;

import java.util.Random;

import game.shawn.Agame.graphics.Screen;
import game.shawn.Agame.graphics.Sprite;
import game.shawn.Agame.level.Level;

//abstract class
public class Entity {
	
	//entity attributes
	protected int x, y;
	protected Sprite sprite;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public Entity() {
		
	}
	
	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void update() {
		
	}
	
	public void render(Screen screen) {
			if (sprite != null) screen.renderSprite(x, y, sprite ,true);
	}
	
	public void remove() {
		//remove from level
		removed = true;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public boolean isRemoved() {
		//confirm it is removed
		return removed;
	}
	//initialize the level in entity class
	public void init(Level level) {
		this.level = level;
	}

}
