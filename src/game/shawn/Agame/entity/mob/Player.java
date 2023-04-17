package game.shawn.Agame.entity.mob;

import game.shawn.Agame.Game;
import game.shawn.Agame.entity.Projectile.Projectile;
import game.shawn.Agame.entity.Projectile.WizardProjectile;
import game.shawn.Agame.graphics.AnimatedSprite;
import game.shawn.Agame.graphics.Screen;
import game.shawn.Agame.graphics.Sprite;
import game.shawn.Agame.graphics.SpriteSheet;
import game.shawn.Agame.input.Keyboard;
import game.shawn.Agame.input.Mouse;

public class Player extends Mob {
	
	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	//for shooting
	Projectile p;
	private int fireRate = 0;
	
	public Player(Keyboard input) {
		this.input = input;
		sprite = sprite.player_back;
	}
	
	//created at a specific location
	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = sprite.player_back;
		fireRate = WizardProjectile.FIRE_RATE;
	}
	
	//update player location
	public void update() {
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		if (fireRate > 0)fireRate--;
		/*//for testing 
		if (input.up) y--;
		if (input.down) y++;
		if (input.left) x--;
		if (input.right) x++;
		*/
		int xa=0, ya=0;
		//safe guard if method to update animation
		//if (anim < 7500) anim++;
		//else anim = 0;
		if (input.up) {
			animSprite = up;
			ya--;
			}else if (input.down) {
			animSprite = down;
			ya++;
			}if (input.left) {
			animSprite = left;
			xa--;
			}else if (input.right) {
			animSprite = right;
			xa++;
			}
		//the correct method to move character
		if(xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		}else {
			walking = false;
		}
		clear();
		updateShooting();
	}
	
	//clear unwanted projectiles
	private void clear() {
		for (int i=0; i<level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if(p.isRemoved()) level.getProjectiles().remove(i);
		}
		
	}

	public void updateShooting() {
		//update mouse shooting
				if (Mouse.getButton()==1 && fireRate <= 0) {
					//basic math for the mouse direction, tan-1 (dy/dx)or arctan(dy/dx)
					double dx = Mouse.getX() - Game.getWindowWidth()/2;
					double dy = Mouse.getY() - Game.getWindowHeight()/2;
					double dir = Math.atan2(dy, dx); //atan2 handles dx=0, better than atan
					shoot(x,y,dir);
					fireRate = WizardProjectile.FIRE_RATE;
	}
}

	public void render(Screen screen) {
		/*
		//using the dir variable defined in Mob class
		if (dir==0) {
			sprite = sprite.player_forward;
			if (walking) {
				if (anim%20 > 10) {
					sprite = sprite.player_forward_1;
				}else {
					sprite = sprite.player_forward_2;
				}
			}
		}
		if (dir==1) {
			sprite = sprite.player_right;
		if (walking) {
			if (anim%20 > 10) {
				sprite = sprite.player_right_1;
			}else {
				sprite = sprite.player_right_2;
				}
			}
		}
		if (dir==2) {
			sprite = sprite.player_back;
			if (walking) {
				if (anim%20 > 10) {
					sprite = sprite.player_back_1;
				}else {
					sprite = sprite.player_back_2;
				}
			}
		}
		if (dir==3) {
			sprite = sprite.player_left;
			if (walking) {
				if (anim%20 > 10) {
					sprite = sprite.player_left_1;
				}else {
					sprite = sprite.player_left_2;
				}
			}
		}
		*/
		sprite = animSprite.getSprites();
		//-16 to center the player, not sure its necessary
		int xx = x - 16;
		int yy = y - 16;
		screen.renderMob(xx, yy, sprite); //nned a flip paramenter when flipping
		/*
		//track player locally doing it 16
		screen.renderPlayer(xx, yy, sprite.player0);
		screen.renderPlayer(xx+16, yy, sprite.player1);
		screen.renderPlayer(xx, yy+16, sprite.player2);
		screen.renderPlayer(xx+16, yy+16, sprite.player3);
		*/
	}
}
