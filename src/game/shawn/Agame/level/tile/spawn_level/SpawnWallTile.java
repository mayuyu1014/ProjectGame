package game.shawn.Agame.level.tile.spawn_level;

import game.shawn.Agame.graphics.Screen;
import game.shawn.Agame.graphics.Sprite;
import game.shawn.Agame.level.tile.Tile;

public class SpawnWallTile extends Tile {

	public SpawnWallTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render (int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);//call render method from screen class
	}
	
	public boolean solid() {
		return true; 
	}

}
