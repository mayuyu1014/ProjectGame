/**
 * 
 */
package game.shawn.Agame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import game.shawn.Agame.entity.mob.Player;
import game.shawn.Agame.graphics.Screen;
import game.shawn.Agame.graphics.Sprite;
import game.shawn.Agame.input.Keyboard;
import game.shawn.Agame.input.Mouse;
import game.shawn.Agame.level.Level;
import game.shawn.Agame.level.RandomLevel;
import game.shawn.Agame.level.SpawnLevel;
import game.shawn.Agame.level.TileCoordinate;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L; //just add it
	
	//resolution of the actual game screen, scale = *3
	private static int width = 300;
	private static int height = 168;
	private static int scale = 3;
	public static String title = "NotSureYet";
	private Level level; //create a level, and 1 level a time
	private Player player; //need a player
	private Thread thread; //creating a sub process beside main
	private JFrame frame; // a var for a game window
	private Keyboard key;
	
	private boolean running = false; //a variable for game loop	
	
	private Screen screen; //import from screen package
	
	//frequent class for image view, gonna be the final view
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	//use raster data structure to convert image to an array of int, and use data buffer to modify raster and eventually create the image
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); //create the image and access the image
	
	//main function
	public Game() {
		Dimension size = new Dimension(width*scale, height*scale); //actual window
		setPreferredSize(size); //return the above size
		screen = new Screen(width, height);//creating a screen obj
		frame = new JFrame(); //set frame to new JFrame
		key = new Keyboard();//create a new keyboard obj
		level = Level.spawn;
		TileCoordinate playerSpawn = new TileCoordinate(30,64);
		player = new Player(playerSpawn.x(),playerSpawn.y(),key);
		//player.init(level); //initialize player level which equals spawn level
		level.add(player);//new method to add player
		addKeyListener(key); //add listener after keyboard obj created
		
		//create mouse here, dont forget!
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public static int getWindowWidth() {
		return width*scale;
	}
	
	public static int getWindowHeight() {
		return height*scale;
	}
	
	//synchronized makes it visible
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display"); //"this" direct thread to attach main class
		thread.start(); //create a new thread by start it
	}
	
	//stop the thread
	public synchronized void stop() {
		running = false;
		try {
		thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//the loop
	public void run() {
		//setup some counting timers and also show the frames and updates 
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0/60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		//boolean c =false; //for testing
		requestFocus(); //make the game window focus by default, so dont need to click first to make it respond
		while (running) {
			long now = System.nanoTime();
			//System.out.println(now-lastTime); //testing time difference in nano secs
			//return; // yeah, need to get out
			
			//not sure how it works below, but it is a timer thing
			delta += (now-lastTime)/ns; 
			lastTime = now;
			/* testing how much time needed for rendering 1 frame
			if(c) {
				System.out.println("Time taken : " + (now-lastTime));
				System.exit(0);
			}
			*/
			while (delta >= 1) {
				update();//handle logic and update the game
				updates++;//update count +1 every time it is called
				delta--;
			}
			render();//display image to screen, unlimited
			frames++; //frame count +1 every time it is called
			//c = true; //testing same as above
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000; //happening once per sec
				System.out.println(updates + " ups, " + frames + " fps");
				frame.setTitle(title + " | " + updates + " ups, " + frames + " fps ");
				updates = 0;
				frames = 0;
			}
		}
		stop();//for testing
	}
	
	//int x=0, y=0; //testing
	
	//buffer for update and render - temporary storage space
	public void update() {
		key.update(); //implement the key class
		//control maps in basic directions for testing purpose, moved to player class
		/*
		if (key.up) y--;
		if (key.down) y++;
		if (key.left) x--;
		if (key.right) x++;
		*/
		//player.update();
		level.update();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		//just create it once
		if(bs==null) {
			createBufferStrategy(3); //always triple buffering! For efficiency!
			return;
		}
		
		screen.clear();//clear before render();
		//to center player, we need some offset values
		int xScroll = player.getX() - screen.width/2;
		int yScroll = player.getY() - screen.height/2;
		//screen.render(x,y);//run this method endlessly to update the screen; testing
		level.render(xScroll, yScroll, screen);
		//call and render the player and call the screen pointer
		//player.render(screen);
		
		/*//for particles testing
		Sprite sprite = new Sprite(2, 2, 0xffffff);
		Random random = new Random();
		//for testing of particles
		for (int i=0; i<100; i++) {
			int x = random.nextInt(20);
			int y = random.nextInt(20);
			screen.renderSprite(width - 60 + x, 50 + y, sprite, true);
		}*/
		
		for (int i=0; i<pixels.length; i++) {
			pixels[i]=screen.pixels[i]; //assign game pixels to get rendered
		}
		
		Graphics g = bs.getDrawGraphics(); //create a link between graphic and buffer
		g.setColor(Color.BLACK); //set graphical color to black - before fill anything
		//fill with rectangle from 0,0 (top left) and get width and height from above parameter
		g.fillRect(0, 0, getWidth(), getHeight()); 
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);//draw the pink background
		//these 3 lines are testing moving distances
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana",0,50));
		//g.drawString("X: " + player.x + ", Y: " + player.y, 350, 300);
		//g.fillRect(Mouse.getX(), Mouse.getY(), 64, 64);//testing for tracking mouse
		//if (Mouse.getButton() != 1)g.drawString("Button : " + Mouse.getButton(), 80, 80);
		g.dispose(); //dispose current graphics to release memory
		bs.show(); //show buffer screen
	}
	
	public static void main(String[] args) {
		Game game =  new Game(); //new Obj
		game.frame.setResizable(false); //prevent graphic error : important
		game.frame.setTitle(Game.title); //game title
		game.frame.add(game); //add game component into the frame
		game.frame.pack(); //set the size of the frame according to setting
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close app
		game.frame.setLocationRelativeTo(null);//center window
		game.frame.setVisible(true); //actually show the window
		
		game.start();
	}
}
