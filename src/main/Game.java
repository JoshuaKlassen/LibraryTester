package main;
import jgame.game.JGame;
import jgame.game.State;
import states.GameState;


public class Game extends JGame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7192771433770627519L;
	
	private State gameState;
	
	public Game(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);
		
		gameState = new GameState(this);
		transitionState(gameState);
	}
	
	public static void main(String[] args){
		System.setProperty("sun.java2d.opengl", "True");
		System.setProperty("sun.java2d.nodraw", "True");
		System.setProperty("sun.java2d.d3d", "False");
		
		
		Game game = new Game(500, 300);
		game.start();
	}
	
}
