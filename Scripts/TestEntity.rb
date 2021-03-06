require 'java'

java_import 'states.GameState'
java_import 'entities.Mob'
java_import 'jgame.util.JRubyScriptingEngine'

updateScript = 'Scripts/updateScript.rb'

JRubyScriptingEngine.addScript(updateScript);

class TestMob < Mob
  def initialize(actorManager, initalizeScriptPath)
    super(actorManager, initalizeScriptPath)
    #position = Vector2.new
  end
  
  def render(graphics)
    graphics.fillRect(position, 20, 20);
  end
end

testMob = TestMob.new(gameState.getCurrentLevel().getActorManager(), "")
testMob.setUpdateScriptPath("")
testMob.velocity.x = 1
testMob.spawn()
