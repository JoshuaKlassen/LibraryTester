require 'java'

java_import 'states.GameState'
java_import 'jgame.entities.Mob'
java_import 'jgame.util.JRubyScriptingEngine'

updateScript = 'Scripts/updateScript.rb'

JRubyScriptingEngine.addScript(updateScript);

class TestMob < Mob
  def initialize(actorManager)
    super(actorManager)
    #position = Vector2.new
  end
  
  def render(graphics)
    graphics.fillRect(position, 20, 20);
  end
  
  def update
#    JRubyScriptingEngine.getScriptingContainer().put('testMob', self)
#    JRubyScriptingEngine.runScript('Scripts/updateScript.rb')
#    JRubyScriptingEngine.getScriptingContainer().remove('testMob')
  end
end

puts gameState

testMob = TestMob.new(gameState.getCurrentLevel().getActorManager())
testMob.spawn()
gameState.getCurrentLevel()