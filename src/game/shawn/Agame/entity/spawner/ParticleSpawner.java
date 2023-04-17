package game.shawn.Agame.entity.spawner;

import game.shawn.Agame.entity.particle.Particle;
import game.shawn.Agame.entity.spawner.Spawner.Type;
import game.shawn.Agame.level.Level;

public class ParticleSpawner extends Spawner{

	private int life;
	
	public ParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i=0; i<amount; i++) {
				level.add(new Particle(x, y, life));
		}
	}

}
