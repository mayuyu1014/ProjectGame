package game.shawn.Agame.level.tile;

import game.shawn.Agame.graphics.Screen;
import game.shawn.Agame.graphics.Sprite;

public class GrassTile extends Tile {

	public GrassTile(Sprite sprite) {
		super(sprite);
	}
	
	//inherit from parent class(tile), and it needs to be in every subclass
	public void render (int x, int y, Screen screen) {
		//need to shift/times 16 because it has to be consistent with screen class render method
		//time 16 here transfer from pixel level to tile level, like in screen class
		screen.renderTile(x << 4, y << 4, this);//call render method from screen class
	}
	

}
