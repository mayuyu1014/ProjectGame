package game.shawn.Agame.level.tile;

import game.shawn.Agame.graphics.Screen;
import game.shawn.Agame.graphics.Sprite;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
	}
	
	//inherit from parent class(tile), and it needs to be in every subclass
	public void render (int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);//call render method from screen class
	}

}
