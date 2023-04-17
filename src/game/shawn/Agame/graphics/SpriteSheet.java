package game.shawn.Agame.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	//major variables
	private String path;
	public final int SIZE;
	public final int WIDTH, HEIGHT;
	public int[] pixels;
	
	//be aware of file path!!! have to be in src folder!!!and include /res!!!! damn
	public static SpriteSheet tiles = new SpriteSheet("/res/textures/sheets/spritesheet.png", 256);
	public static SpriteSheet spawn_level = new SpriteSheet("/res/textures/sheets/spawn_level.png", 48);
	public static SpriteSheet projectile_wizard = new SpriteSheet("/res/textures/wizard.png", 48);
	//sub sprite sheet
	public static SpriteSheet player = new SpriteSheet("/res/textures/sheets/player_sheet.png", 128,96);
	
	//actual player sprite
	public static SpriteSheet player_down = new SpriteSheet(player, 0, 0, 1, 3, 32);
	public static SpriteSheet player_up = new SpriteSheet(player, 1, 0, 1, 3, 32);
	public static SpriteSheet player_left = new SpriteSheet(player, 2, 0, 1, 3, 32);
	public static SpriteSheet player_right = new SpriteSheet(player, 3, 0, 1, 3, 32);

	public static SpriteSheet dummy = new SpriteSheet("/res/textures/sheets/npc.png", 128,96);
	public static SpriteSheet dummy_down = new SpriteSheet(dummy, 2, 0, 1, 3, 32);
	public static SpriteSheet dummy_up = new SpriteSheet(dummy, 0, 0, 1, 3, 32);
	public static SpriteSheet dummy_left = new SpriteSheet(dummy, 3, 0, 1, 3, 32);
	public static SpriteSheet dummy_right = new SpriteSheet(dummy, 1, 0, 1, 3, 32);
	
	private Sprite[] sprites;
	
	//spritesheet constructor for extract sprite sheet
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		int xx = x*spriteSize;
		int yy = y*spriteSize;
		int w = width*spriteSize;
		int h = height*spriteSize;
		if (width == height) SIZE = width;
		else SIZE = -1;
		WIDTH = w;
		HEIGHT = h;
		pixels = new int [w * h];
		for (int y0=0; y0<h; y0++) {
			int yp = yy + y0;
			for (int x0=0; x0<w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.WIDTH];
			}
		}
		int frame = 0;
		sprites = new Sprite[width * height];
		for(int ya=0; ya<height; ya++) {
			for(int xa=0; xa<width; xa++) {//first 2 loops iterate through physical sprite
				int[] spritePixels = new int[spriteSize * spriteSize]; //collect pixels from above loops
				for (int y0=0; y0<spriteSize; y0++) {
					for (int x0=0; x0<spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0+xa*spriteSize) + (y0+ya*spriteSize)*WIDTH]; 
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}
	
	//set up a sprite sheet constructor
	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		WIDTH = size;
		HEIGHT = size;
		pixels = new int [SIZE*SIZE];
		load(); //call the loaded image
	}
	
	//constructor for sub sprite sheet
	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		SIZE = -1;
		WIDTH = width;
		HEIGHT = height;
		pixels = new int [WIDTH*HEIGHT];
		load();
	}
	
	//getter
	public Sprite[] getSprites() {
		return sprites;
	}
	
	private void load() {
		try { //need a try, catch here
		//create a buffered image and set it to get from the path
		BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
		//backup plan down below
		//ImageIO.read(new File("C:/Shawn/Coding/Java/ProjectGame/res/textures/spritesheet.png"));
		
		
		//define the height and width of loaded image
		int w = image.getWidth();
		int h = image.getHeight();
		
		//put loaded image into pixel array
		image.getRGB(0,0,w,h,pixels,0,w);
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
}