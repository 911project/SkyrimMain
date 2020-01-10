package Entity;

import java.util.ArrayList;
import javax.imageio.ImageIO;

import TileMap.TileMap;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MapObject{

	//player stuff
	private int health, maxHealth, fire, maxFire;
	private boolean dead, flinching;
	private long flinchTimer;
	
	//fireBall
	private boolean firing;
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<FireBall> fireBalls;
	
	//scratch
	private boolean scratching;
	private int scratchDamage, scratchRange;
	
	///gliding
	private boolean gliding;
	
	//animazione
	//devo modificare la distanza tra i personaggi nel progetto perchè quando ha l'animazione di caduta si vedono i piedi della camminata probabilmente
	private ArrayList<BufferedImage[]> sprites;
	//ongi animazione ha un tot di passaggi
	private final int[] numFrames = {2, 8, 1, 2, 4, 2, 5};
	
	//animation actions
	
	private static final int IDLE = 0;
	//cammina
	private static final int WALKING = 1;
	//salta
	private static final int JUMPING = 2;
	//cade
	private static final int FALLING = 3;
	//vola
	private static final int GLIDING = 4;
	//lancio palla
	private static final int FIREBALL = 5;
	//attacco corpo a corpo
	private static final int SCRATCHING = 6;
	
	public Player(TileMap tm) {
		
		//le immagini dei vari stati del giocatore sono di 30 pixel
		//quelle dell'attacco sono di 60
		
		super(tm);
		
		widht = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = 0.3;//0.3
		maxSpeed = 1.6;//1.6
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		
		health = maxHealth = 5;
		fire = maxFire = 2500;
		
		fireCost = 200;
		fireBallDamage = 5;
		
		fireBalls = new ArrayList<FireBall>();
		
		scratchDamage = 8;
		scratchRange = 40;
		
		try {
			
			
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/playersprites.gif"));
			
			sprites = new ArrayList<BufferedImage[]>();
			
			for(int i = 0; i<7; i++) {
				
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j=0;j<numFrames[i];j++) {
					if(i != 6) {
						bi[j] = spritesheet.getSubimage(j * widht, i*height, widht, height);
					}else {
						bi[j] = spritesheet.getSubimage(j * widht * 2, i*height, widht * 2, height);
					}
				}
				
				sprites.add(bi);
				
			}
			
			
			
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		
		
		
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getFire() {
		return fire;
	}
	
	public int getMaxFire() {
		return maxFire;
	}
	
	public void setFiring() {
		firing = true;
	}
	
	public void setScratching() {
		scratching = true;
	}
	
	public void setGliding(boolean b) {
		gliding = b;
	}
	
	private void getNextPosition() {
		
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		
		
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}
		
		if(falling) {
			
			if(dy > 0 && gliding) dy += fallSpeed * 0.1;
			else dy += fallSpeed;
			
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
			
		}
		
		
	}
	
	public void update() {
		
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(currentAction == SCRATCHING) {
			if(animation.hasPlayedOnce()) scratching = false;
		}
		if(currentAction == FIREBALL) {
			if(animation.hasPlayedOnce()) firing = false;
		}
		
		fire += 1;
		if(fire > maxFire) fire = maxFire;
		if(firing && currentAction != FIREBALL) {
			if(fire > fireCost) {
				fire -= fireCost;
				FireBall fb = new FireBall(tileMap, facingRight);
				fb.setPosition(x, y);
				fireBalls.add(fb);
			}
		}
		for(int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).update();
			if(fireBalls.get(i).shouldRemove()) {
				fireBalls.remove(i);
				i--;
			}
		}
		
		
		if(scratching) {
			if(currentAction != SCRATCHING) {
				currentAction = SCRATCHING;
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(50);
				widht = 60;
			}
		}else if(firing) {
			if(currentAction != FIREBALL) {
				currentAction = FIREBALL;
				animation.setFrames(sprites.get(FIREBALL));
				animation.setDelay(100);
				widht = 30;
			}
		}else if(dy > 0) {
			if(gliding) {
				if(currentAction != GLIDING) {
					currentAction = GLIDING;
					animation.setFrames(sprites.get(GLIDING));
					animation.setDelay(100);
					widht = 30;
				}
			}else if(currentAction != FALLING) {
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				widht = 30;
			}
			
		}else if(dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				widht = 30;
			}
		}else if(left || right) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				widht = 30;
			}
		}else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				widht = 30;
			}
			
		}
		
		animation.update();
		
		if(currentAction != SCRATCHING && currentAction != FIREBALL) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
		
	}
	
	
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		for(int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).draw(g);
		}
		
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		if(facingRight) {
			g.drawImage(animation.getImage(), (int)(x + xmap - widht / 2), (int)(y + ymap - height / 2), null);
		}else {
			g.drawImage(animation.getImage(), (int)(x + xmap - widht / 2 + widht), (int)(y + ymap - height / 2), -widht, height, null);

		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
