package game.shawn.Agame.entity.particle;

import java.util.ArrayList;
import java.util.List;

import game.shawn.Agame.entity.Entity;
import game.shawn.Agame.graphics.Screen;
import game.shawn.Agame.graphics.Sprite;

public class Particle extends Entity {
	
	//use list for hundreds of particles and moved to spawner
	//private static List<Particle> particles = new ArrayList<Particle>();
	private Sprite sprite;
	
	private int life;
	private int time;
	
	protected double xx, yy, zz;
	protected double xa, ya, za;//amount of pixels moving on x and y	
	
	public Particle(int x, int y, int life) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(25) - 10);
		sprite = Sprite.particle_normal;
		
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}
	
	/* for testing
	public Particle(int x, int y, int life, int amount) {
		this(x, y, life);//calling the above constructor
		//the for loop will add all the particles to the list
		for (int i=0; i<amount-1; i++) {
			particles.add(new Particle(x,y,life));
		}
		particles.add(this);//add particle objects/instances to the array list above
	}
	*/
	public void update() {
		time++;
		if (time >= 7400) time = 0;
		if(time > life) remove();
		//animate za
		za -= 0.1; //per tick which means it decrease in a curve instead of linear
		//create a z axis
		if(zz <= 0) {
			zz=0;
			za *= -0.6;//reverse its direction, the small the number the faster it bounces
			xa *= 0.3;//go half way x axis each bounce
			ya *= 0.3;
		}
		
		move(xx + xa, (yy+ya)+(zz+za));
	}
	
	//this is similar to the move method in Mob class
	private void move(double x, double y) {
		//reverse direction or bounce after collision
		if (collision (x,y)) {
			this.xa *= -0.5;
			this.ya *= -0.5;
			this.za *= -0.5;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;
	}
	
	//this is copied from tileCollision in Level class
	public boolean collision(double x, double y){
		boolean solid = false;
		for (int c=0; c<4; c++) {
			//detailed four corner collision detection
			double xt = (x-c%2*16) / 16; //micro adjust
			double yt = (y-c/2*16) /16;
			int ix = (int) Math.ceil(xt); //round up
			int iy = (int) Math.ceil(yt);
			if(c%2 ==0 ) ix = (int) Math.floor(xt);
			if(c/2 ==0 ) iy = (int) Math.floor(yt);
			if (level.getTile(ix, iy).solid()) solid =true; //collision detection
		}
		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int)xx, (int)yy - (int)zz, sprite, true);//yy-zz because both negative
	}
	
}
