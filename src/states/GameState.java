package states;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.jruby.embed.PathType;

import entities.Player;
import jgame.entities.ActorManager;
import jgame.environment.EnvironmentLayer;
import jgame.environment.EnvironmentManager;
import jgame.environment.JLevel;
import jgame.environment.LevelData;
import jgame.environment.Tile;
import jgame.game.INPUT_KEY;
import jgame.game.InputHandler;
import jgame.game.InputKey;
import jgame.game.JGame;
import jgame.game.State;
import jgame.graphics.Camera;
import jgame.graphics.GraphicsUtility;
import jgame.graphics.IMesh;
import jgame.graphics.JGraphics;
import jgame.util.AnimationLoader;
import jgame.util.JRubyScriptingEngine;
import jgame.util.ResourceManager;
import jgame.util.Vector2;
import main.Game;

public class GameState extends State {

	private JLevel levelOne;
	
	private Game game;
	
	private Player player;
	
	private InputKey escape = new InputKey(true, INPUT_KEY.VK_ESCAPE);
	private InputKey up = new InputKey(false, INPUT_KEY.UP);
	private InputKey down = new InputKey(false, INPUT_KEY.DOWN);
	private InputKey left = new InputKey(false, INPUT_KEY.LEFT);
	private InputKey right = new InputKey(false, INPUT_KEY.RIGHT);
	
	private LevelData levelOneData;
	
	private Camera camera;
	
	private String testEntityScriptPath = "Scripts/TestEntity.rb";
	
	public GameState(JGame game) {
		super(game);
		this.game = (Game)game;
		
		InputHandler.add(escape, up, down, left, right);
		
		camera = new Camera(game);
		
		EnvironmentLayer layer = new EnvironmentLayer();
		
		ActorManager actorManager = new ActorManager();
		EnvironmentManager environmentManager = new EnvironmentManager();
		
		Tile.TILE_SIZE = 32;
		
		BufferedImage tileImage = GraphicsUtility.loadImage("/TileSet.png");
		
		IMesh tileMesh = new IMesh(){
			public void render(JGraphics g, Vector2 position){
				g.drawImage(tileImage, position, Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 2);
			}
		};
		
		for(int i = 0; i < 90; i ++){
			for(int j = 0; j < 90; j ++){
				Tile t = new Tile(environmentManager, 1, new Vector2(i*Tile.TILE_SIZE, j*Tile.TILE_SIZE), tileMesh);
				layer.spawn(t);
			}
		}
		
		levelOneData = new LevelData(actorManager, environmentManager);
		levelOneData.getEnvironmentLayers().add(layer);
		
		levelOne = new JLevel(game, levelOneData);
		player = new Player(levelOne.getActorManager());
		player.spawn();
		camera.follow(player);
		camera.startFollowing();
		levelOne.setCamera(camera);
		
		JRubyScriptingEngine.addScript(testEntityScriptPath);
		JRubyScriptingEngine.getScriptingContainer().put("gameState", this);
		JRubyScriptingEngine.getScriptingContainer().runScriptlet(PathType.ABSOLUTE, testEntityScriptPath);
	}


	@Override
	public void update() {
		if(escape.isPressed()){
			System.exit(0);
		}
		
		
		camera.update();
		levelOne.update();
	}
	
	public JLevel getCurrentLevel(){
		return levelOne;
	}

	@Override
	public void render(JGraphics g) {
		camera.startCapture(g);
		levelOne.render(g);
		camera.endCapture(g);
		g.setColor(Color.WHITE);
		g.drawString("FPS: " + getGame().getCurrentFramesPerSecond(), 10, 10);
	}

}
