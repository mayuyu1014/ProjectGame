package game.shawn.Agame.level;

import java.util.ArrayList;
import java.util.List;

import game.shawn.Agame.entity.Entity;
import game.shawn.Agame.entity.Projectile.Projectile;
import game.shawn.Agame.entity.mob.Player;
import game.shawn.Agame.entity.particle.Particle;
import game.shawn.Agame.entity.spawner.Spawner;
import game.shawn.Agame.graphics.Screen;
import game.shawn.Agame.level.tile.Tile;

public class Level {
	
	protected int width, height;
	//protected Tile[] tiles; //precise way but maybe slower
	protected int[] tilesInt;
	protected int[] tiles;
	protected int tile_size;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	
	private List<Player> players = new ArrayList<Player>();
	
	public static Level spawn = new SpawnLevel("/res/levels/spawn.png");
	
	//constructor to generate random level
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width*height]; //amount of tiles for whole map
		generateLevel();
	}
	
	//another constructor to load level
	public Level(String path) {
		loadLevel(path);
		generateLevel();
		
	}
	
	//method to create a level
	protected void generateLevel() {
		for (int y=0; y<64; y++) {
			for (int x=0; x<64; x++) {
				getTile(x,y);
			}
		}
		tile_size = 16;
	}
	
	//method to load a level from a path
	protected void loadLevel(String path) {
		
	}
	
	//method to update a level
	public void update() { 
		for (int i=0; i<entities.size(); i++) {//update entiites
		entities.get(i).update();
		}
		
		for (int i=0; i<projectiles.size(); i++) {
			projectiles.get(i).update();
			}
		for (int i=0; i<particles.size(); i++) {
			particles.get(i).update();
			}
		for (int i=0; i<players.size(); i++) {
			players.get(i).update();
			}
		remove();
	}
	
	private void remove() {
		for (int i=0; i<entities.size(); i++) {//update entiites
			if (entities.get(i).isRemoved()) entities.remove(i);
			}
			
			for (int i=0; i<projectiles.size(); i++) {
				if (projectiles.get(i).isRemoved()) projectiles.remove(i);
				}
			for (int i=0; i<particles.size(); i++) {
				if (particles.get(i).isRemoved()) particles.remove(i);
				}
			for (int i=0; i<players.size(); i++) {
				if (players.get(i).isRemoved()) players.remove(i);
				}
	}
	
	public List<Projectile> getProjectiles(){
		return projectiles;
	}
	
	//time is gonna be in subclass level
	private void time() {
		
	}
	
	//x y would be position of the entity, xa and ya is the direction heading
	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset){
		boolean solid = false;
		for (int c=0; c<4; c++) {
			//detailed four corner collision detection
			int xt = (x-c%2*size + xOffset) >> 4; //micro adjust
			int yt = (y-c/2*size + yOffset) >> 4;
			if (getTile(xt, yt).solid()) solid =true; //collision detection
		}
		return solid;
	}
	
	//method to render everything
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll); //actual location of the player
	//corner pins method in tile position, cuz scroll is where the character is, these 4 define the render region
		int x0 = xScroll >> 4; //bitwise operator for /16, cuz every pixel is 16*16, x0 is left most
		int x1 = (xScroll + screen.width + 16) >> 4; //x1 is the right most position
		int y0 = yScroll >> 4;//y0 top left?
		int y1 = (yScroll + screen.height + 16) >> 4;//y1 is bottom cornor?
		
		//looping through left to right corners, top to bottom corners of the screen
		for (int y=y0; y<y1; y++) {
			for (int x=x0; x<x1; x++) {
				getTile(x, y).render(x, y, screen); //get the tile at x,y coordinate to render
				/*bad way to get tiles
				if (x+y*16 < 0 || x+y*16 > 256) {
					Tile.voidTile.render(x, y, screen);
					continue;
				}
					tiles[x+y*16].render(x, y, screen);
				*/
			}
			
			for (int i=0; i<entities.size(); i++) {
				entities.get(i).render(screen);
				}
			
			for (int i=0; i<projectiles.size(); i++) {
				projectiles.get(i).render(screen);
				}
			for (int i=0; i<particles.size(); i++) {
				particles.get(i).render(screen);
				}
			for (int i=0; i<players.size(); i++) {
				players.get(i).render(screen);
				}
		}
		//render every entity
		for (int i=0; i<entities.size(); i++) {
		entities.get(i).render(screen);
		}
	}
	
	public void add(Entity e) {
		e.init(this);
	if(e instanceof Particle) {
			particles.add((Particle)e);
		}else if (e instanceof Projectile) {
			projectiles.add((Projectile)e);
		}else if (e instanceof Player){
			players.add((Player) e);
		}else {
			entities.add(e);
		}
	}
//three methods below to get player
	public List<Player> getPlayers() {
		return players;
	}
	
	public Player getPlayerAt(int index) {
		return players.get(index);
	}
	
	public Player getClientPlayer() {
		return players.get(0);
	}
	
	//find out the position of each tile and return it, just getter thing
	public Tile getTile(int x, int y) {
		//fix out of bounds problem by generating void tiles
		if (x<0 || y<0 || x>=width || y>=height) return Tile.voidTile; 
		if (tiles[x+y*width] == Tile.col_spawn_grass) return Tile.spawn_grass; //match color with tile
		if (tiles[x+y*width] == Tile.col_spawn_floor) return Tile.spawn_floor;  
		if (tiles[x+y*width] == Tile.col_spawn_hedge) return Tile.spawn_hedge;
		if (tiles[x+y*width] == Tile.col_spawn_wall1) return Tile.spawn_wall1;
		if (tiles[x+y*width] == Tile.col_spawn_wall2) return Tile.spawn_wall2;
		if (tiles[x+y*width] == Tile.col_spawn_water) return Tile.spawn_water;
		
		return Tile.voidTile; //return void tiles, out of map protection
	}

}
