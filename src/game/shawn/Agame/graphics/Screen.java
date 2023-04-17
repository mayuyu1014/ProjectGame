package game.shawn.Agame.graphics;

import java.util.Random;

import game.shawn.Agame.entity.Projectile.Projectile;
import game.shawn.Agame.entity.mob.Chaser;
import game.shawn.Agame.entity.mob.Mob;
import game.shawn.Agame.level.tile.Tile;

public class Screen {
	
	public int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 64; //assign value to a variable
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	
	public int xOffset, yOffset; 
	
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE]; //total tiles for the game
	
	private Random random = new Random();
	
	//creating 1 pixel for testing
	//int xtime = 100, ytime = 50;
	//int counter = 0;
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width*height]; //a new obj, create 1 int for each pixel in the screen, total 50k;
	
		//this loop gonna assign random colors to my tiles
		for (int i=0; i<MAP_SIZE*MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}
	
	//clear screen method
	public void clear() {
		for (int i=0; i<pixels.length; i++) {
			pixels[i] = 0; //reset screen
		}
	}
	
	//render sprites we see currently
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
		xp -= xOffset;
		yp -= yOffset;
		}
		for (int y=0; y<sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x=0; x<sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa<0 || xa>=width || ya<0 || ya>=height) continue; //out of bounds check
				//scan every single y object
				pixels[xa + ya*width] = sprite.pixels[x + y*sprite.getWidth()];
			}
		}
	}
	
	/* method for testing below
	public void render(int xOffset, int yOffset) {
		//rendering animation
		/*counter++; //for testing purpose
		if(counter%100 == 0) xtime--; 
		if(counter%80 == 0) ytime--;*/
	/*
		//2d loops for rendering
		for (int y=0; y<height; y++) {
			int yp = y + yOffset;
			if (yp<0 || yp >= height) continue; //out of bound protection for y axis
			for (int x=0; x<width; x++) {
				int xp = x + xOffset;//moving tiles
				if (xp<0 || xp >= width) continue;//out of bound protection for x axis
				//& means when reach mapsize, return to 0
				//int tileIndex = ((xx >> 4) & MAP_SIZE_MASK) + ((yy >> 4) & MAP_SIZE_MASK) * MAP_SIZE;//use bitwise operator (/16) for faster result 
				//fill the image with tiles or whatever
				pixels[xp+yp*width] = Sprite.grass.pixels[(x&15)+(y&15)*Sprite.grass.SIZE];
			} 
			}
		}
	 */
	
	//offset rendering method
	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;//fix map moving to player moving
		yp -= yOffset; //fix map moving to player moving
		for (int y=0; y<tile.sprite.SIZE; y++) {
			int ya = y+yp;//set a absolute position, offset by yp
			for (int x=0; x<tile.sprite.SIZE; x++) {
				int xa = x+xp;//set a absolute position, offset by xp	
				//stop rendering out of bounds, basically render what I see
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break; 
				if (xa < 0) xa=0; //fix the left side rendering problem by move down 1 tile and reset negative xa to 0
				//where the sprite is rendered = where the pixel is rendered. use x & y because I dont need offset
				pixels[xa+ya*width] = tile.sprite.pixels[x+y*tile.sprite.SIZE];
		}
		}
	}
	
	public void renderProjectile(int xp, int yp, Projectile p) {
		xp -= xOffset;
		yp -= yOffset; 
		for (int y=0; y<p.getSpriteSize(); y++) {
			int ya = y+yp;
			for (int x=0; x<p.getSpriteSize(); x++) {
				int xa = x+xp;
				if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height) break; 
				if (xa < 0) xa=0; 
				int col = p.getSprite().pixels[x+y*p.getSpriteSize()];
				if (col != 0xffff00ff)
				pixels[xa+ya*width] = col;
		}
		}
	}
	
	//actually renderMob
	public void renderMob(int xp, int yp, Mob mob) { //need a int flip parameter if flipping needed
		xp -= xOffset;
		yp -= yOffset; 
		for (int y=0; y<32; y++) {
			int ya = y+yp;
			for (int x=0; x<32; x++) {
				int xa = x+xp;
				if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; 
				if (xa < 0) xa=0; 
				int col = mob.getSprite().pixels[x+y*32];
				if ((mob instanceof Chaser) && (col == 0xff696FD2)) col = 0xffBD0000;
				if(col != 0xffff00ff)pixels[xa+ya*width] = col;
			}
	}
}
	
	//method to render a tile
	public void renderMob(int xp, int yp, Sprite sprite) { //need a int flip parameter if flipping needed
		xp -= xOffset;
		yp -= yOffset; 
		for (int y=0; y<32; y++) {
			int ya = y+yp;
			//int ys = y; //flip
			//if (flip ==2 || flip == 3) {int ys = 31-y}; //for flipping the sprite
			for (int x=0; x<32; x++) {
				int xa = x+xp;
				//int xs = x;//flip
			//if (flip == 1 || flip ==3) //{int xs = 31-x}; //for flipping the sprite
				if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; 
				if (xa < 0) xa=0; 
				int col = sprite.pixels[x+y*32];//sprite.pixels[xs+ys*32] for flipping the sprite
				if(col != 0xffff00ff)pixels[xa+ya*width] = col;
			}
	}
}
	
	
	//offset method
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
}




