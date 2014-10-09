package com.revoltagames.naves;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.revoltagames.managers.GameStateManager;

public class Naves extends ApplicationAdapter {
	
	public static int WIDTH;
	public static int HEIGTH;
	
	private OrthographicCamera camera;
	
	private GameStateManager gameStManager;
	
	@Override
	public void create () {
		
		HEIGTH = Gdx.graphics.getHeight();
		WIDTH = Gdx.graphics.getWidth();
		
		camera = new OrthographicCamera(WIDTH, HEIGTH);
		camera.translate(WIDTH/2, HEIGTH/2);
		
		gameStManager = new GameStateManager();
	}

	@Override
	public void render () {
		//PANTALLA EN NEGRO
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameStManager.update(Gdx.graphics.getDeltaTime());
		gameStManager.draw();
	}
	
	
}
