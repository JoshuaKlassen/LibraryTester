package main;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import jgame.game.JGame;
import jgame.game.State;
import jgame.graphics.GraphicsUtility;
import jgame.graphics.JGraphics;
import jgame.graphics.Sprite;
import jgame.util.AnimationLoader;
import jgame.util.ResourceManager;
import jgame.util.Utility;
import jgame.util.Vector2;
import states.GameState;

public class Game extends JGame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7192771433770627519L;

	private State gameState;

	public Game(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);
		super.setJFrame(getDefaultJFrame());
		// super.toggleFullScreen();

		ResourceManager.registerLoader("Animation", new AnimationLoader());
		gameState = new GameState(this);
		transitionState(gameState);
	}

	public void transitionState(State state) {
		super.transitionState(state);
	}
	
	public void render(JGraphics g){
		super.render(g);
		if(sprite2 != null)
			sprite2.render(g, new Vector2(0, 0));
	}
	
	static BufferedImage image;
	static Sprite sprite2;
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "True");
		System.setProperty("sun.java2d.nodraw", "True");
		System.setProperty("sun.java2d.d3d", "False");
		// JRubyScriptingEngine.initialize();
		
		String path = System.getProperty("user.home") + "/Desktop/Temp.ser";

//		BufferedImage image = GraphicsUtility.loadImage("/PlayerSpriteSheet.png");
//		try {
//			writeImage(image, path);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		try {
//			image = readImage(path);
//		} catch (ClassNotFoundException | IOException e) {
//			e.printStackTrace();
//		}
		
		Sprite sprite = new Sprite(GraphicsUtility.loadImage("/TileSet.png"));
		Utility.writeObject(sprite, path);
		
		sprite2 = (Sprite)Utility.readObject(path);
		
		Game game = new Game(500, 300);
		game.start();
	}
}
