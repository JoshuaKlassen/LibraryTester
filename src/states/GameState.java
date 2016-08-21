package states;

import java.awt.Color;

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
import jgame.graphics.JGraphics;
import jgame.util.Vector2;
import main.Game;
import entities.Player;

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
	
	Camera camera;
	
	public GameState(JGame game) {
		super(game);
		this.game = (Game)game;
		
		InputHandler.add(escape, up, down, left, right);
		
		camera = new Camera(game);
		
		EnvironmentLayer layer = new EnvironmentLayer();
		
		ActorManager actorManager = new ActorManager();
		EnvironmentManager environmentManager = new EnvironmentManager();
		
		Tile.TILE_SIZE = 32;
		
		for(int i = 0; i < 90; i ++){
			for(int j = 0; j < 90; j ++){
				Tile t = new Tile(environmentManager, 1, new Vector2(i*Tile.TILE_SIZE, j*Tile.TILE_SIZE), null);
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
	}


	@Override
	public void update() {
		if(escape.isPressed()){
			System.exit(0);
		}
		
		if(up.isPressed()){
			player.velocity().y = -5;
		}else if(down.isPressed()){
			player.velocity().y = 5;
		}else{
			player.velocity().y = 0;
		}
		
		
		if(left.isPressed()){
			player.velocity().x = -5;
		}else if(right.isPressed()){
			player.velocity().x = 5;
		}else{
			player.velocity().x = 0;
		}
		
		camera.update();
		levelOne.update();
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
