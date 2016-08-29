package entities;

import jgame.entities.ActorManager;
import jgame.entities.JMob;
import jgame.graphics.Animation;
import jgame.graphics.JGraphics;
import jgame.util.JRubyScriptingEngine;
import jgame.util.Vector2;

public class Mob extends JMob{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5444867353069679106L;

	protected transient Animation walkingUpAnimation;
	protected transient Animation walkingDownAnimation;
	protected transient Animation walkingRightAnimation;
	protected transient Animation walkingLeftAnimation;
	
	protected transient Animation runningUpAnimation;
	protected transient Animation runningDownAnimation;
	protected transient Animation runningRightAnimation;
	protected transient Animation runningLeftAnimation;

	protected transient Animation currentAnimation;
	
	protected int walkingSpeed = 2;
	protected int sprintingSpeed = 4;
	
	protected boolean isRunning = false;
	
	protected Vector2 lastVelocity = new Vector2();
	
	private String initalizeScriptPath;
	
	private String updateScriptPath;
	
	public Mob(ActorManager actorManager, String initializeScriptPath) {
		super(actorManager);
		this.initalizeScriptPath = initializeScriptPath;
	}

	@Override
	public void render(JGraphics g) {
		if(currentAnimation != null){
			currentAnimation.render(g, position);
		}
	}

	@Override
	public void update() {
		if(updateScriptPath != null && !updateScriptPath.equals("")){
			JRubyScriptingEngine.getScriptingContainer().put("mob", this);
			JRubyScriptingEngine.runScript(updateScriptPath);
		}

		position.add(velocity);
		
		if(currentAnimation != null){
			Vector2 direction = velocity.normalize();
			if(velocity.x == 0 && velocity.y == 0){
				if(currentAnimation.isRunning()){
					currentAnimation.stop();
				}
			}else{
				if(!Vector2.equals(velocity, lastVelocity)){
					currentAnimation.stop();
					if(Math.abs(direction.x) > Math.abs(direction.y)){
						if(direction.x < 0){
							Animation leftAnimation = isRunning ? runningLeftAnimation : walkingLeftAnimation;
							if(currentAnimation != leftAnimation){
								currentAnimation.reset();
								currentAnimation = leftAnimation;
							}
						}else if(direction.x > 0){
							Animation rightAnimation = isRunning ? runningRightAnimation : walkingRightAnimation;
							if(currentAnimation != rightAnimation){
								currentAnimation.reset();
								currentAnimation = rightAnimation;
							}
						}
						currentAnimation.start();
					}else{
						if(direction.y < 0){
							Animation upAnimation = isRunning ? runningUpAnimation : walkingUpAnimation;
							if(currentAnimation != upAnimation){
								currentAnimation.reset();
								currentAnimation = upAnimation;
							}
						}else if (direction.y > 0){
							Animation downAnimation = isRunning ? runningDownAnimation : walkingDownAnimation;
							if(currentAnimation != downAnimation){
								currentAnimation.reset();
								currentAnimation = downAnimation;
							}
						}
						currentAnimation.start();
					}
				}
			}
			currentAnimation.update();
		}
		lastVelocity = velocity.clone();
	}

	@Override
	protected void init() {
		if(initalizeScriptPath != null && !initalizeScriptPath.equals("")){
			JRubyScriptingEngine.addScript(initalizeScriptPath);
			JRubyScriptingEngine.getScriptingContainer().put("mob", this);
			JRubyScriptingEngine.runScript(initalizeScriptPath);
		}
	}
	
	public void setUpdateScriptPath(String scriptPath){
		updateScriptPath = scriptPath;
		JRubyScriptingEngine.clearScripts();
		JRubyScriptingEngine.addScript(updateScriptPath);
	}

}
