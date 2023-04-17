package game.shawn.Agame.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.shawn.Agame.entity.mob.Chaser;
import game.shawn.Agame.entity.mob.Dummy;

public class SpawnLevel extends Level{
	
	//private int[] tiles;

	public SpawnLevel(String path) {
		super(path);
	}
	
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			//tiles = new Tile[w*h];
			tiles = new int [w*h]; //initiation
			image.getRGB(0, 0, w, h, tiles, 0, w); //convert image to pixels
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("Exception! Could not load level file !");
		}
		for (int i=0; i<1; i++) {
			add(new Chaser(25,55));
		}
	}
	
	//convert level pixels to tiles
	//Grass = 0xFF00, 0xFF00FF00 = 00256, Flower = 0xFFFF00, Rock = 0x7F7F00
	protected void generateLevel() {
		/* bad way to get tiles
		for (int i=0; i<levelPixels.length; i++) {
			if (levelPixels[i] == 0xff00ff00) tiles[i] = Tile.grass;
			if (levelPixels[i] == 0xffffff00) tiles[i] = Tile.flower;
			if (levelPixels[i] == 0xff7f7f00) tiles[i] = Tile.rock;
		}
		*/
		for (int y=0; y<64; y++) {
			for (int x=0; x<64; x++) {
				getTile(x,y);
			}
		}
		tile_size = 16;
	}

}
