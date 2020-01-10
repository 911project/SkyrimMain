package GameState;

import TileMap.*;
import java.awt.*;



import Main.GamePanel;
import Entity.*;
import java.awt.event.KeyEvent;
import java.io.*;

import javax.xml.bind.ParseConversionEvent;

public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	private Player player;
	
	
	public Level1State(GameStateManager gsm) throws IOException{
		
		this.gsm = gsm;	
		init();
		
	}
	
	
	public void init()  {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		
		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
		player = new Player(tileMap);
		
		
        //////////LETTURA FILE/////////////
        
        double x = 100, y = 160, xM = 0, yM = 0;
        
        BufferedReader reader;
		try {
			
			reader = new BufferedReader(new FileReader("Save.txt"));
		
			
		
			x = Double.parseDouble(reader.readLine());
			y = Double.parseDouble(reader.readLine());
			xM = Double.parseDouble(reader.readLine());
			yM = Double.parseDouble(reader.readLine());
			
			
			
			
			
			
			
		} catch (IOException e) {
				
				e.printStackTrace();
			
        }
        
		
        
        
		

		///////////////////////////////////
		
		player.setPosition(x, y);
		tileMap.setPosition(xM, yM);
	}
	
	
	public void update() {		
		
		player.update();
		tileMap.setPosition(GamePanel.HEIGHT / 2 - player.getx(), GamePanel.HEIGHT / 2 -player.gety());		
		
		
	}
	
	public void draw(Graphics2D g) {
			
		
		bg.draw(g);
		
		tileMap.draw(g);
		
		player.draw(g);
	}
	
	public void keyPressed(int k) {
		
		if(k==KeyEvent.VK_A) player.setLeft(true);
		if(k==KeyEvent.VK_D) player.setRight(true);
		if(k==KeyEvent.VK_S) player.setDown(true);
		if(k==KeyEvent.VK_W) player.setUp(true);
		if(k==KeyEvent.VK_W) player.setJumping(true);
		if(k==KeyEvent.VK_J) player.setScratching();
		if(k==KeyEvent.VK_K) player.setFiring();
		
		if(k==KeyEvent.VK_ESCAPE) {
			
			
		    FileWriter w;
		    try {
		    	
				w=new FileWriter("Save.txt");
				BufferedWriter b;
			    b=new BufferedWriter (w);
			    
			    String Xs = String.valueOf(player.getx());
			    String Ys = String.valueOf(player.gety());
			    String Xms = String.valueOf(tileMap.getx());
			    String Yms = String.valueOf(tileMap.getx());
			    
			   
		
			    
			    b.write(Xs + "\n");
			    b.write(Ys + "\n");
			    b.write(Xms + "\n");
			    b.write(Yms + "\n");

			    b.flush();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    
			
			
			
			
			gsm.setState(gsm.PAUSESTATE);
			
		}
		
	}
	
	public void keyReleased(int k) {
		
		if(k==KeyEvent.VK_A) player.setLeft(false);
		if(k==KeyEvent.VK_D) player.setRight(false);
		if(k==KeyEvent.VK_S) player.setDown(false);
		if(k==KeyEvent.VK_W) player.setUp(false);
		if(k==KeyEvent.VK_W) player.setJumping(false);
		
	}


	
	
	
}
