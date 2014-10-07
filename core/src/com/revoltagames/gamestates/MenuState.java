package com.revoltagames.gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.revoltagames.entities.Particle;
import com.revoltagames.managers.GameStateManager;
import com.revoltagames.naves.Naves;

public class MenuState extends GameState {
	
	private SpriteBatch sb;
	private ShapeRenderer sr;
	
	private BitmapFont fontB;
	private BitmapFont fontS;
	
	private Music mus;
	
	private int fps;
	private int count;
	private float time;
	private float seg;
	
	private ArrayList<Particle> particles;

	public MenuState(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		
		//mus = Gdx.audio.newMusic(Gdx.files.internal(""));
		//mus.setLooping(true);
		//mus.play();
		
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		
		particles = new ArrayList<Particle>();
		
		// TODO fuentes
		
		
		
		fps = 0;
		count = 0;
		seg = 1;
		

	}

	@Override
	public void update(float dt) {
		
		for (int i = 0; i < 200; i++) {
			float x, y;
			x = MathUtils.random(Naves.WIDTH);
			y = MathUtils.random(Naves.HEIGTH);
			createParticles(x, y);
		}
		
		for (int i = 0; i < particles.size() ; i++) {
			particles.get(i).update(dt);
			if(particles.get(i).shouldRemove()){
				particles.remove(i);
				i--;
			}
		}
		
		
		
		if (time > seg) {
			fps = count;
			count = 0;
			time = 0;
		}
		time += dt;
		
		
	}

	@Override
	public void draw() {
		
		for (int i = 0; i < particles.size() ; i++) {
			particles.get(i).draw(sr);
		}

		sb.setColor(1, 1, 1, 1);
		sb.begin();
		fontB.drawWrapped(sb, "ASTEROIDS" , 0, Naves.HEIGTH / 2 + 30, Naves.WIDTH, HAlignment.CENTER);
		fontS.drawWrapped(sb, Integer.toString(fps), 0, Naves.HEIGTH / 2 - 60, Naves.WIDTH, HAlignment.CENTER);
		sb.end();
		count++;
		
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	private void createParticles(float x, float y) {
		for (int i = 0; i < 6; i++) {
			particles.add(new Particle(x, y));
		}
	}

}
