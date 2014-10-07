package com.revoltagames.gamestates;

import com.badlogic.gdx.Gdx;
import com.revoltagames.entities.Player;
import com.revoltagames.managers.GameStateManager;


public class GameOverState extends GameState {
	
	private static final String go = "Game Over";
	private static final String score = "Score: ";
	
	Player player;
	
	
	public GameOverState (GameStateManager gsm, Player player){ 
		super(gsm);
		
		this.player = player;
		
	}

	@Override
	public void init() {
		
		

	}

	@Override
	public void update(float dt) {
		handleInput();

	}

	@Override
	public void draw() {
		


	}

	@Override
	public void handleInput() {
		if (Gdx.input.isTouched()) {
			gsm.setState(GameStateManager.PLAY);
		}
		


	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
