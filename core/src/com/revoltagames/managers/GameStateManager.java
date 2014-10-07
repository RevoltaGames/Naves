package com.revoltagames.managers;

import com.revoltagames.entities.Player;
import com.revoltagames.gamestates.GameOverState;
import com.revoltagames.gamestates.GameState;
import com.revoltagames.gamestates.MenuState;
import com.revoltagames.gamestates.PlayState;

public class GameStateManager {

	private GameState gameState;
	
	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int GAMEOVER = 2;
	
	public GameStateManager() {
		setState(PLAY);
	}
	
	public void setState(int state) {
		if (gameState != null) gameState.dispose();
		if (state == MENU) {
			// Cambiar a menu
			gameState = new MenuState(this);
		}
		if (state == PLAY) {
			// Cambiar a juego
			gameState = new PlayState(this);
		}
	}
	
	public void setState (int state, Player player) {
		if (gameState != null) gameState.dispose();
		if (state == GAMEOVER) {
			gameState = new GameOverState(this, player);
		}
	}
	
	public void update(float dt) {
		gameState.update(dt);
	}
	
	public void draw() {
		gameState.draw();
	}
	
}
