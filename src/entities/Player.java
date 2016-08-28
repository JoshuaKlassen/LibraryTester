package entities;

import java.awt.image.BufferedImage;

import jgame.entities.ActorManager;
import jgame.game.INPUT_KEY;
import jgame.game.InputHandler;
import jgame.game.InputKey;
import jgame.graphics.Animation;
import jgame.graphics.GraphicsUtility;
import jgame.graphics.JGraphics;
import jgame.graphics.Sprite;
import jgame.util.Vector2;

public class Player extends Mob{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2670488022841742847L;

	private transient Animation walkingUpAnimation;
	private transient Animation walkingDownAnimation;
	private transient Animation walkingRightAnimation;
	private transient Animation walkingLeftAnimation;
	
	private transient Animation runningUpAnimation;
	private transient Animation runningDownAnimation;
	private transient Animation runningRightAnimation;
	private transient Animation runningLeftAnimation;
	
	private transient Animation currentAnimation;
	
	private transient BufferedImage playerSpriteSheet;
	
	private boolean isRunning = false;
	
	private Vector2 lastVelocity = new Vector2();
	
	private int walkingSpeed = 2;
	private int sprintingSpeed = 4;
	
	private InputKey up = new InputKey(false, INPUT_KEY.UP);
	private InputKey down = new InputKey(false, INPUT_KEY.DOWN);
	private InputKey left = new InputKey(false, INPUT_KEY.LEFT);
	private InputKey right = new InputKey(false, INPUT_KEY.RIGHT);
	private InputKey shift = new InputKey(false, INPUT_KEY.VK_SHIFT);
	
	public Player(ActorManager actorManager) {
		super(actorManager);
		init();
	}

	@Override
	public void render(JGraphics g) {
		currentAnimation.render(g, position);
	}

	@Override
	public void update() {
		super.update();
		isRunning = shift.isPressed();
		
		if(up.isPressed()){
			velocity.y = - (isRunning ? sprintingSpeed : walkingSpeed);
		}else if(down.isPressed()){
			velocity.y = (isRunning ? sprintingSpeed : walkingSpeed);
		}else{
			velocity.y = 0;
		}
		
		if(left.isPressed()){
			velocity.x = - (isRunning ? sprintingSpeed : walkingSpeed);
		}else if(right.isPressed()){
			velocity.x = (isRunning ? sprintingSpeed : walkingSpeed);
		}else{
			velocity.x = 0;
		}

		
		position.add(velocity);
		
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
		
		
		
//		if(Math.abs(direction.x) > Math.abs(direction.y)){
//			if(direction.x < 0){
//				if(currentAnimation != walkingLeftAnimation){
//					currentAnimation.stop();
//					currentAnimation.reset();
//					currentAnimation = walkingLeftAnimation;
//				}
//				if(!currentAnimation.isRunning()){
//					currentAnimation.start();
//				}
//			}else if(direction.x > 0){
//				if(currentAnimation != walkingRightAnimation){
//					currentAnimation.stop();
//					currentAnimation.reset();
//					currentAnimation = walkingRightAnimation;
//				}
//				if(!currentAnimation.isRunning()){
//					currentAnimation.start();
//				}
//			}
//			
//		}else if (Math.abs(direction.x) < Math.abs(direction.y)){
//			if(direction.y < 0){
//				if(currentAnimation != walkingUpAnimation){
//					currentAnimation.stop();
//					currentAnimation.reset();
//					currentAnimation = walkingUpAnimation;
//				}
//				if(!currentAnimation.isRunning()){
//					currentAnimation.start();
//				}
//			}else if (direction.y > 0){
//				if(currentAnimation != walkingDownAnimation){
//					currentAnimation.stop();
//					currentAnimation.reset();
//					currentAnimation = walkingDownAnimation;
//				}
//				if(!currentAnimation.isRunning()){
//					currentAnimation.start();
//				}
//			}
//		}else if(direction.x == 0 && direction.y == 0){
//			currentAnimation.stop();
//		}
		currentAnimation.update();
		lastVelocity = velocity.clone();
	}

	@Override
	protected void init() {
		InputHandler.add(up, down, left, right, shift);
		
		playerSpriteSheet = GraphicsUtility.loadImage("/PlayerSpriteSheet.png");
		
		createWalkingAnimations();
		createRunningAnimations();
		
		currentAnimation = walkingDownAnimation;
		currentAnimation.start();
	}
	
	private void createWalkingAnimations(){
		int widthOfSprites = 13;
		int heightOfSprites = 21;
		int spriteWidth = widthOfSprites * 2;
		int spriteHeight = heightOfSprites * 2;
		
		int numDownSprites = 4;
		Sprite[] walkingDownSprites = new Sprite[numDownSprites];
		BufferedImage[] walkingDownImages = GraphicsUtility.ripColumnFromImage(playerSpriteSheet, 3, 0, 0, widthOfSprites, heightOfSprites);

		walkingDownSprites[0] = new Sprite(walkingDownImages[0]);
		walkingDownSprites[0].setSpriteWidth(spriteWidth);
		walkingDownSprites[0].setSpriteHeight(spriteHeight);;
		walkingDownSprites[1] = new Sprite(walkingDownImages[1]);
		walkingDownSprites[1].setSpriteWidth(spriteWidth);
		walkingDownSprites[1].setSpriteHeight(spriteHeight);
		walkingDownSprites[2] = walkingDownSprites[0];
		walkingDownSprites[3] = new Sprite(walkingDownImages[2]);
		walkingDownSprites[3].setSpriteWidth(spriteWidth);
		walkingDownSprites[3].setSpriteHeight(spriteHeight);
		
		walkingDownAnimation = new Animation(walkingDownSprites, 10, true);
		
		int numUpSprites = 4;
		Sprite[] walkingUpSprites = new Sprite[numUpSprites];
		BufferedImage[] walkingUpImages = GraphicsUtility.ripColumnFromImage(playerSpriteSheet, 3, 15, 0, widthOfSprites, heightOfSprites);

		walkingUpSprites[0] = new Sprite(walkingUpImages[0]);
		walkingUpSprites[0].setSpriteWidth(spriteWidth);
		walkingUpSprites[0].setSpriteHeight(spriteHeight);
		walkingUpSprites[1] = new Sprite(walkingUpImages[1]);
		walkingUpSprites[1].setSpriteWidth(spriteWidth);
		walkingUpSprites[1].setSpriteHeight(spriteHeight);
		walkingUpSprites[2] = walkingUpSprites[0];
		walkingUpSprites[3] = new Sprite(walkingUpImages[2]);
		walkingUpSprites[3].setSpriteWidth(spriteWidth);
		walkingUpSprites[3].setSpriteHeight(spriteHeight);
		
		walkingUpAnimation = new Animation(walkingUpSprites, 10, true);
		
		int numLeftSprites = 4;
		Sprite[] walkingLeftSprites = new Sprite[numLeftSprites];
		Sprite[] walkingRightSprites = new Sprite[numLeftSprites];
		BufferedImage[] walkingLeftImages = GraphicsUtility.ripColumnFromImage(playerSpriteSheet, 3, 30, 0, widthOfSprites, heightOfSprites);

		walkingLeftSprites[0] = new Sprite(walkingLeftImages[0]);
		walkingLeftSprites[0].setSpriteWidth(spriteWidth);
		walkingLeftSprites[0].setSpriteHeight(spriteHeight);
		walkingLeftSprites[1] = new Sprite(walkingLeftImages[1]);
		walkingLeftSprites[1].setSpriteWidth(spriteWidth);
		walkingLeftSprites[1].setSpriteHeight(spriteHeight);
		walkingLeftSprites[2] = walkingLeftSprites[0];
		walkingLeftSprites[3] = new Sprite(walkingLeftImages[2]);
		walkingLeftSprites[3].setSpriteWidth(spriteWidth);
		walkingLeftSprites[3].setSpriteHeight(spriteHeight);
		
		walkingLeftAnimation = new Animation(walkingLeftSprites, 10, true);
		
		walkingRightSprites[0] = new Sprite(GraphicsUtility.flipImage(walkingLeftImages[0]));
		walkingRightSprites[0].setSpriteWidth(spriteWidth);
		walkingRightSprites[0].setSpriteHeight(spriteHeight);
		walkingRightSprites[1] = new Sprite(GraphicsUtility.flipImage(walkingLeftImages[1]));
		walkingRightSprites[1].setSpriteWidth(spriteWidth);
		walkingRightSprites[1].setSpriteHeight(spriteHeight);
		walkingRightSprites[2] = walkingRightSprites[0];
		walkingRightSprites[3] = new Sprite(GraphicsUtility.flipImage(walkingLeftImages[2]));
		walkingRightSprites[3].setSpriteWidth(spriteWidth);
		walkingRightSprites[3].setSpriteHeight(spriteHeight);
		
		walkingRightAnimation = new Animation(walkingRightSprites, 10, true);
	}

	private void createRunningAnimations(){
		int widthOfSprites = 15;
		int heightOfSprites = 20;
		int spriteWidth = widthOfSprites * 2;
		int spriteHeight = heightOfSprites * 2;
		
		int numDownSprites = 4;
		Sprite[] runningDownSprites = new Sprite[numDownSprites];
		BufferedImage[] runningDownImages = GraphicsUtility.ripColumnFromImage(playerSpriteSheet, numDownSprites - 1, 0, 65, widthOfSprites, heightOfSprites);
		
		runningDownSprites[0] = new Sprite(runningDownImages[0]);
		runningDownSprites[0].setSpriteWidth(spriteWidth);
		runningDownSprites[0].setSpriteHeight(spriteHeight);
		runningDownSprites[1] = new Sprite(runningDownImages[1]);
		runningDownSprites[1].setSpriteWidth(spriteWidth);
		runningDownSprites[1].setSpriteHeight(spriteHeight);
		runningDownSprites[2] = runningDownSprites[0];
		runningDownSprites[3] = new Sprite(runningDownImages[2]);
		runningDownSprites[3].setSpriteWidth(spriteWidth);
		runningDownSprites[3].setSpriteHeight(spriteHeight);
		
		runningDownAnimation = new Animation(runningDownSprites, 10, true);
		
		int numUpSprites = 4;
		Sprite[] runningUpSprites = new Sprite[numUpSprites];
		BufferedImage[] runningUpImages = GraphicsUtility.ripColumnFromImage(playerSpriteSheet, numUpSprites - 1, 15, 65, widthOfSprites, heightOfSprites);
		
		runningUpSprites[0] = new Sprite(runningUpImages[0]);
		runningUpSprites[0].setSpriteWidth(spriteWidth);
		runningUpSprites[0].setSpriteHeight(spriteHeight);
		runningUpSprites[1] = new Sprite(runningUpImages[1]);
		runningUpSprites[1].setSpriteWidth(spriteWidth);
		runningUpSprites[1].setSpriteHeight(spriteHeight);
		runningUpSprites[2] = runningUpSprites[0];
		runningUpSprites[3] = new Sprite(runningUpImages[2]);
		runningUpSprites[3].setSpriteWidth(spriteWidth);
		runningUpSprites[3].setSpriteHeight(spriteHeight);
		
		runningUpAnimation = new Animation(runningUpSprites, 10, true);
		
		int numLeftSprites = 4;
		Sprite[] runningLeftSprites = new Sprite[numLeftSprites];
		Sprite[] runningRightSprites = new Sprite[numLeftSprites];
		BufferedImage[] runningLeftImages = GraphicsUtility.ripColumnFromImage(playerSpriteSheet, numLeftSprites - 1, 30, 65, widthOfSprites, heightOfSprites);
		
		runningLeftSprites[0] = new Sprite(runningLeftImages[0]);
		runningLeftSprites[0].setSpriteWidth(spriteWidth);
		runningLeftSprites[0].setSpriteHeight(spriteHeight);
		runningLeftSprites[1] = new Sprite(runningLeftImages[1]);
		runningLeftSprites[1].setSpriteWidth(spriteWidth);
		runningLeftSprites[1].setSpriteHeight(spriteHeight);
		runningLeftSprites[2] = runningLeftSprites[0];
		runningLeftSprites[3] = new Sprite(runningLeftImages[2]);
		runningLeftSprites[3].setSpriteWidth(spriteWidth);
		runningLeftSprites[3].setSpriteHeight(spriteHeight);
		
		runningLeftAnimation = new Animation(runningLeftSprites, 10, true);
		
		runningRightSprites[0] = new Sprite(GraphicsUtility.flipImage(runningLeftImages[0]));
		runningRightSprites[0].setSpriteWidth(spriteWidth);
		runningRightSprites[0].setSpriteHeight(spriteHeight);
		runningRightSprites[1] = new Sprite(GraphicsUtility.flipImage(runningLeftImages[1]));
		runningRightSprites[1].setSpriteWidth(spriteWidth);
		runningRightSprites[1].setSpriteHeight(spriteHeight);
		runningRightSprites[2] = runningRightSprites[0];
		runningRightSprites[3] = new Sprite(GraphicsUtility.flipImage(runningLeftImages[2]));
		runningRightSprites[3].setSpriteWidth(spriteWidth);
		runningRightSprites[3].setSpriteHeight(spriteHeight);
		
		runningRightAnimation = new Animation(runningRightSprites, 10, true);
	}
}
