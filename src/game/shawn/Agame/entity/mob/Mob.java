package game.shawn.Agame.entity.mob;

import java.util.ArrayList;
import java.util.List;

import game.shawn.Agame.entity.Entity;
import game.shawn.Agame.entity.Projectile.Projectile;
import game.shawn.Agame.entity.Projectile.WizardProjectile;
import game.shawn.Agame.entity.particle.Particle;
import game.shawn.Agame.graphics.Screen;
import game.shawn.Agame.graphics.Sprite;

public abstract class Mob extends Entity{

	//protected Sprite sprite;
	protected boolean moving = false;
	protected boolean walking = false;
	//an array for projectiles
	//protected List<Projectile> projectiles = new ArrayList<Projectile>(); //move to level
	
	protected enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	protected Direction dir;
	
	//move method
	public void move(int xa, int ya) {
		if(xa != 0 && ya != 0) { //move on 2 axis to enable slide upon collision
			//Separate moves individually on x and y axis
			move(xa,0); 
			move(0,ya);
			return;
		}
		
		//make dir like a compass, 1 ease. 3 west. 2 south, 0 north
		if (xa > 0) dir = Direction.RIGHT;
		if (xa < 0) dir = Direction.LEFT;
		if (ya > 0) dir = Direction.DOWN;
		if (ya < 0) dir = Direction.UP;
		
		if(!collision(xa, ya)) { //only move when collision is off
			x += xa;
			y += ya;
		}
	}
	
	//abstract = master class
	public abstract void update();
	public abstract void render(Screen screen);
	
	protected void shoot(int x, int y, double dir) {
		//make new projectile
		Projectile p = new WizardProjectile(x, y, dir);
		//projectiles.add(p); //add to projectiles list/array
		level.add(p);
		//dir *= 180/Math.PI;
		//dir = Math.toDegrees(dir) //backup plan	
	}
	
	private boolean collision(int xa, int ya){
		boolean solid = false;
		for (int c=0; c<4; c++) {
//more precise collision with corner codes below, *x+x means offset pixels, micro-adjust these pixel for more precise collision
			int xt = ((x+xa)+c%2*10-5)/16;
			int yt = ((y+ya)+c/2*12+3)/16;
			if (level.getTile(xt, yt).solid()) solid =true; //collision detection
		}
		return solid;
	}
}
