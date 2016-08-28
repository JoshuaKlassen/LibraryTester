package entities;

import jgame.entities.ActorManager;
import jgame.entities.JMob;
import jgame.graphics.JGraphics;
import jgame.util.JRubyScriptingEngine;

public class Mob extends JMob{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5444867353069679106L;

	private String updateScriptPath;
	
	public Mob(ActorManager actorManager) {
		super(actorManager);
	}

	@Override
	public void render(JGraphics g) {
		
	}

	@Override
	public void update() {
		if(updateScriptPath != null && !updateScriptPath.equals("")){
			JRubyScriptingEngine.getScriptingContainer().put("mob", this);
			JRubyScriptingEngine.runScript(updateScriptPath);
		}
	}

	@Override
	protected void init() {
		
	}
	
	public void setUpdateScriptPath(String scriptPath){
		updateScriptPath = scriptPath;
		JRubyScriptingEngine.clearScripts();
		JRubyScriptingEngine.addScript(updateScriptPath);
	}

}
