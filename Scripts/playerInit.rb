require 'java'

java_import 'entities.Player'
java_import 'jgame.util.ResourceManager'

ResourceManager.load("Animation", "res/PlayerAnimation/walkingUpAnimation", "walkingUpAnimation");
ResourceManager.load("Animation", "res/PlayerAnimation/walkingDownAnimation", "walkingDownAnimation");
ResourceManager.load("Animation", "res/PlayerAnimation/walkingLeftAnimation", "walkingLeftAnimation");
ResourceManager.load("Animation", "res/PlayerAnimation/walkingRIghtAnimation", "walkingRightAnimation");
   
ResourceManager.load("Animation", "res/PlayerAnimation/runningUpAnimation", "runningUpAnimation"); 
ResourceManager.load("Animation", "res/PlayerAnimation/runningDownAnimation", "runningDownAnimation");
ResourceManager.load("Animation", "res/PlayerAnimation/runningLeftAnimation", "runningLeftAnimation");
ResourceManager.load("Animation", "res/PlayerAnimation/runningRightAnimation", "runningRightAnimation");

class Player
  field_accessor :walkingUpAnimation, :walkingDownAnimation, :walkingLeftAnimation, :walkingRightAnimation
  field_accessor :runningUpAnimation, :runningDownAnimation, :runningLeftAnimation, :runningRightAnimation
  field_accessor :currentAnimation
end

mob.walkingUpAnimation = ResourceManager.get("walkingUpAnimation")
mob.walkingDownAnimation = ResourceManager.get("walkingDownAnimation")
mob.walkingLeftAnimation = ResourceManager.get("walkingLeftAnimation")
mob.walkingRightAnimation = ResourceManager.get("walkingRightAnimation")

mob.runningUpAnimation = ResourceManager.get("runningUpAnimation")
mob.runningDownAnimation = ResourceManager.get("runningDownAnimation")
mob.runningLeftAnimation = ResourceManager.get("runningLeftAnimation")
mob.runningRightAnimation = ResourceManager.get("runningRightAnimation")

mob.currentAnimation = mob.walkingDownAnimation
mob.currentAnimation().start()