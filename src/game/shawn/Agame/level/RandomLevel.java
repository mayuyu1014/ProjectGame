package game.shawn.Agame.level;

import java.util.Random;

public class RandomLevel extends Level {
	
	private static final Random random = new Random(); //random method to generate stuff

	public RandomLevel(int width, int height) {
		super(width, height); //same as a virtual constructor, I guess?
	}
	
	//method to fill tiles with values
	protected void generateLevel() {
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				//give a random number for tile id
				tilesInt[x+y*width] = random.nextInt(4);
			}
		}
	}

}
