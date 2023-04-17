package game.shawn.Agame.graphics;

public class Sprite {
	
	//basic variables
	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	protected SpriteSheet sheet;
	
	//create a static instance of grass sprite Obj from the loaded image and its position is 0,0
	public static Sprite grass = new Sprite(16, 4, 0, SpriteSheet.tiles);
	public static Sprite flower = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite rock = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0x1e81b0);
	
	//spawn level sprites
	public static Sprite spawn_grass = new Sprite(16, 2, 1, SpriteSheet.spawn_level);
	public static Sprite spawn_hedge = new Sprite(16, 0, 1, SpriteSheet.spawn_level);
	public static Sprite spawn_water = new Sprite(16, 1, 2, SpriteSheet.spawn_level);
	public static Sprite spawn_wall1 = new Sprite(16, 1, 1, SpriteSheet.spawn_level);
	public static Sprite spawn_wall2 = new Sprite(16, 0, 2, SpriteSheet.spawn_level);
	public static Sprite spawn_floor = new Sprite(16, 2, 2, SpriteSheet.spawn_level);
	
	//player sprite
	/*
	//player sprite doing it 16
	public static Sprite player0 = new Sprite(16,0,10,SpriteSheet.tiles);
	public static Sprite player1 = new Sprite(16,1,10,SpriteSheet.tiles);
	public static Sprite player2 = new Sprite(16,0,11,SpriteSheet.tiles);
	public static Sprite player3 = new Sprite(16,1,11,SpriteSheet.tiles);
	*/
	public static Sprite player_forward = new Sprite(32,0,5,SpriteSheet.tiles);
	public static Sprite player_back = new Sprite(32,2,5,SpriteSheet.tiles);
	public static Sprite player_left = new Sprite(32,3,5,SpriteSheet.tiles);
	public static Sprite player_right = new Sprite(32,1,5,SpriteSheet.tiles);
	
	public static Sprite player_forward_1 = new Sprite(32,0,6,SpriteSheet.tiles);
	public static Sprite player_forward_2 = new Sprite(32,0,7,SpriteSheet.tiles);
	
	public static Sprite player_back_1 = new Sprite(32,2,6,SpriteSheet.tiles);
	public static Sprite player_back_2 = new Sprite(32,2,7,SpriteSheet.tiles);
	
	public static Sprite player_left_1 = new Sprite(32,3,6,SpriteSheet.tiles);
	public static Sprite player_left_2 = new Sprite(32,3,7,SpriteSheet.tiles);
	
	public static Sprite player_right_1 = new Sprite(32,1,6,SpriteSheet.tiles);
	public static Sprite player_right_2 = new Sprite(32,1,7,SpriteSheet.tiles);
	
	public static Sprite dummy = new Sprite (32, 0, 0, SpriteSheet.dummy_down);
	
	//projectile sprites here
	public static Sprite projectile_wizard = new Sprite(16,0,0,SpriteSheet.projectile_wizard);
	
	//Particles
	public static Sprite particle_normal = new Sprite(3,0xeab676);
	
	
	protected Sprite(SpriteSheet sheet, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}
	
	//basic class constructor
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE]; //creating a new pixel array
		this.x = x * size; //need to time size(16, the size of individual sprite) 
		this.y = y * size; //find target sprite same as x
		this.sheet = sheet;
		load();
	}
	
	//allow the tile to be non-square shapes
	public Sprite(int width, int height, int colour) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColour(colour);
	}
	
	//create a constructor to just generate a sprite with color not from sheet
	public Sprite(int size, int colour) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int [SIZE*SIZE];
		setColour(colour);//call the method
	}
	
	public Sprite(int[] pixels, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}

	//setColour method to fill the tile with color
	private void setColour(int colour) {
		for (int i=0; i<width*height; i++) {
			pixels[i] = colour;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	private void load() {
		//nested for loop to go through all pixels in the image
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
//this loop is to extract individual sprite from the sprite sheet, assign them a specific pixel or color
				pixels[x+y*width] = sheet.pixels[(x+this.x) + (y+this.y)*sheet.WIDTH];
			}
		}
	}
	
}
