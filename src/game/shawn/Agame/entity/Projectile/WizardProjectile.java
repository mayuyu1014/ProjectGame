package game.shawn.Agame.entity.Projectile;

import game.shawn.Agame.entity.particle.Particle;
import game.shawn.Agame.entity.spawner.ParticleSpawner;
import game.shawn.Agame.entity.spawner.Spawner;
import game.shawn.Agame.graphics.Screen;
import game.shawn.Agame.graphics.Sprite;

public class WizardProjectile extends Projectile{
	
	public static final int FIRE_RATE = 15; //higher is slower

	public WizardProjectile(int x, int y, double dir) {
		super(x, y, dir);
		range = random.nextInt(100)+150;
		speed = 5;
		damage = 20;
		sprite = Sprite.projectile_wizard;
		//vectors: stores angle and length, cos for x, sin for y
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update() {//the bullet is 6 pixels in size, 5 pixel to left, 6 pixels to top
		if (level.tileCollision((int)(x+nx), (int)(y+ny),6,5,6)) {
			level.add(new ParticleSpawner ((int)x,(int)y,40,20,level));
		remove();
	}
		move();
	}
	
	protected void move() {
		//projectiles goes in a particular direction
		x += nx;
		y += ny;
		if (distance() > range) remove();
	}
	
	private double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs(xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)); 
		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int)x-12, (int)y-2, this);
	}

}
