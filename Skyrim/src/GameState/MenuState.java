package GameState;

import TileMap.Background;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.sun.glass.events.KeyEvent;

public class MenuState extends GameState{
	
	private Background bg;
	
	private String[] options = {"Start", "Help", "Quit"};
	
	private int currentChoice = 0;
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			
			bg = new Background("/Backgrounds/menubg.gif", 1);
			bg.setVector(-0.1, 0);
			
			titleColor = new Color(128, 0, 0);
			titleFont = new Font("Century Gothic", Font.PLAIN, 28);
			font  = new Font("Arial", Font.PLAIN, 12);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void init() {
		
	}
	
	public void update() {
		bg.update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		
		bg.draw(g);
		
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Skyrim", 118, 90);

		g.setFont(font);
		for(int i=0; i<options.length; i++) {
			if(i==currentChoice) {
				g.setColor(Color.RED);
			}else {
				g.setColor(Color.BLACK);
			}
			g.drawString(options[i], 145, 140 + i * 15);
		}
	}
	
	private void select() {
		if(currentChoice == 0) {
			
			FileWriter w;
		    try {
		    	
				w=new FileWriter("Save.txt");
				BufferedWriter b;
			    b=new BufferedWriter (w);

			    
			    b.write("160 \n");
			    b.write("100 \n");
			    b.write("0 \n");
			    b.write("0 \n");

			    b.flush();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.OPTIONSTATE);
		}
		if(currentChoice == 2) {
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		
		if(k==KeyEvent.VK_ENTER) {
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice --;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice ++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}

	}
	
	public void keyReleased(int k) {
		

	}
	
}
