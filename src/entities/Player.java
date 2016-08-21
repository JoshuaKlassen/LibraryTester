package entities;

import java.awt.Color;

import jgame.entities.ActorManager;
import jgame.entities.Mob;
import jgame.graphics.IMesh;
import jgame.graphics.JGraphics;
import jgame.util.Vector2;

public class Player extends Mob{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2670488022841742847L;

	private IMesh mesh;
	
	public Player(ActorManager actorManager) {
		super(actorManager);
		mesh = new IMesh(){
			@Override
			public void render(JGraphics g, Vector2 position) {
				g.setColor(Color.BLUE);
				g.fillCircle(position, 10, 10);
			}
		};
		
		
	}

	@Override
	public void render(JGraphics g) {
		mesh.render(g, position);
	}

	@Override
	public void update() {
		position.add(velocity);
	}

	@Override
	protected void init() {
		
	}

}
