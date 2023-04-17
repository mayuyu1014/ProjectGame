package game.shawn.Agame.entity.spawner;

import java.util.ArrayList;
import java.util.List;

import game.shawn.Agame.entity.Entity;
import game.shawn.Agame.entity.particle.Particle;
import game.shawn.Agame.level.Level;

public abstract class Spawner extends Entity {
	
	//private List<Entity> entities = new ArrayList<Entity>();
	
	//create a custom int called type, and it has some possible options 
public enum Type {
		MOB, PARTICLE
	}
	
private Type type; //no need to make it static, because it is needed in level
	
public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
}
