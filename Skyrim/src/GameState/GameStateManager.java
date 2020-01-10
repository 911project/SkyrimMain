package GameState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import TileMap.TileMap;

public class GameStateManager {
	
	private ArrayList<GameState> gameStates;
	private int currentState;
	
	
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int OPTIONSTATE = 2;
	public static final int PAUSESTATE = 3;
	public static final int PAUSEOPTIONSTATE = 4;
	public static int previousState = 0;
	
	
	public GameStateManager() {
		
		gameStates = new ArrayList<GameState>();
		currentState = MENUSTATE;
		previousState = currentState;
		gameStates.add(new MenuState(this));		
		try {
			gameStates.add(new Level1State(this));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		gameStates.add(new OptionState(this));		
		gameStates.add(new PauseState(this));		
		gameStates.add(new PauseOptionState(this));
		
		
	}
	
	public void setState(int state) {
		
		previousState = currentState;
		currentState = state;
		gameStates.get(currentState).init();
		
		
		
	}
	
	public void update() {
		
		gameStates.get(currentState).update();
		
	}
	
	public void draw(java.awt.Graphics2D g) {
		gameStates.get(currentState).draw(g);

	}
	
	public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);

	}
	
	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);

	}

	
	
	
	
	
	
	
	
	
	
	
}
