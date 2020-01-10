package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import TileMap.TileMap;
import javax.imageio.ImageIO;

public class FireBall extends MapObject{

	private boolean hit, remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	
	
	
	public FireBall(TileMap tm, boolean right) {
		
		super(tm);
		
		moveSpeed = 3.8;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		
		widht = 30;
		height = 30;
		cwidth = 14;
		cheight = 14;
		
		try {
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/fireball.gif"));
			
			sprites = new BufferedImage[4];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * widht, 0, widht, height);
			}
			
			hitSprites = new BufferedImage[3];
			for(int i = 0; i < hitSprites.length; i++) {
				hitSprites[i] = spritesheet.getSubimage(i * widht, height, widht, height);
			}
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void setHit() {
		
		if(hit) return;
		hit = true;
		
		animation.setFrames(hitSprites);
		animation.setDelay(70);
		dx = 0;
	}
	
	public boolean shouldRemove() {
		return remove;
	}
	
	public void update() {
		
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit) {
			setHit();
		}
		
		animation.update();
		if(hit && animation.hasPlayedOnce()) {
			remove = true; 
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		
		if(facingRight) {
			g.drawImage(animation.getImage(), (int)(x + xmap - widht / 2), (int)(y + ymap - height / 2), null);
		}else {
			g.drawImage(animation.getImage(), (int)(x + xmap - widht / 2 + widht), (int)(y + ymap - height / 2), -widht, height, null);

		}
		
	}
	
}















