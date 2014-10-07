package com.revoltagames.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.revoltagames.entities.Player;
import com.revoltagames.managers.GameInputProcessor;
import com.revoltagames.managers.GameKeys;
import com.revoltagames.managers.GameStateManager;
import com.revoltagames.mygdxgame.MainGame;

public class GameOverState extends GameState {
	
	private SpriteBatch sb;
	
	private BitmapFont font;
	
	private static final String go = "Game Over";
	private static final String score = "Score: ";
	
	Player player;
	
	
	public GameOverState (GameStateManager gsm, Player player){ 
		super(gsm);
		
		this.player = player;
		
	}

	@Override
	public void init() {
		
		sb = new SpriteBatch();
		
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/DroidSans-Bold.ttf"));
		font = gen.generateFont(30);

	}

	@Override
	public void update(float dt) {
		handleInput();

	}

	@Override
	public void draw() {
		
		sb.setColor(1, 1, 1, 1);
		sb.begin();
		font.drawWrapped(sb, go, 0, MainGame.HEIGTH / 2 + 30, MainGame.WIDTH, HAlignment.CENTER);
		font.drawWrapped(sb, score + player.getScore(), 0, MainGame.HEIGTH / 2 - 60, MainGame.WIDTH, HAlignment.CENTER);
		sb.end();

	}

	@Override
	public void handleInput() {
		if (GameKeys.isPressed(GameKeys.ENTER)) {
			gsm.setState(GameStateManager.PLAY);
		}
		


	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
