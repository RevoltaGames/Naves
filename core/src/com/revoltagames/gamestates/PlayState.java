package com.revoltagames.gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.revoltagames.entities.Asteroid;
import com.revoltagames.entities.Bullet;
import com.revoltagames.entities.Particle;
import com.revoltagames.entities.Player;
import com.revoltagames.managers.GameStateManager;
import com.revoltagames.naves.Naves;

public class PlayState extends GameState {
	
	private ShapeRenderer sr;
	private SpriteBatch sb;
	
	private BitmapFont font;
	
	private Player player;
	public ArrayList<Bullet> bullets;
	
	private ArrayList<Asteroid> asteroids;
	
	private ArrayList<Particle> particles;
	
	private int level;
	private int totalAsteroids;
	private int numAsteroidsLeft;

	public PlayState(GameStateManager gsm) {
		super(gsm);
	
	}

	@Override
	public void init() {
		sr = new ShapeRenderer();
		sb = new SpriteBatch();
		
		//FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/DroidSans-Bold.ttf"));
		//font = gen.generateFont(30);
		
		player = new Player();
		
		bullets = player.getBullets();
		
		asteroids = new ArrayList<Asteroid>();
		
		particles = new ArrayList<Particle>();
		
		level = 1;
		spawnAsteroids();
		
	}
	
	private void createParticles(float x, float y) {
		for (int i = 0; i < 6; i++) {
			particles.add(new Particle(x, y));
		}
	}

	private void spawnAsteroids() {
		
		asteroids.clear();
		
		int numToSpawn = 4 + level -1;
		totalAsteroids = numToSpawn * 7;
		numAsteroidsLeft = totalAsteroids;
		
		for (int i = 0; i < numToSpawn; i++) {
			
			float x, y, dx, dy, dist;
			
			do {
				x = MathUtils.random(Naves.WIDTH);
				y = MathUtils.random(Naves.HEIGTH);
			
				dx = x - player.getx();
				dy = y - player.gety();
				dist = (float) Math.sqrt(dx*dx + dy*dy);
			}while(dist < 100);
			
			asteroids.add(new Asteroid(x, y, Asteroid.LARGE));
			
		}
		
	}

	@Override
	public void update(float dt) {
	
		handleInput();
		
		//next lvl
		if (asteroids.size() == 0) {
			level++;
			spawnAsteroids();
		}
		
		player.update(dt);
		if (player.isDead()) {
			player.reset();
			player.loseLife();
			return;
		}
		
		for (int i = 0; i < bullets.size() ; i++) {
			bullets.get(i).update(dt);
			if(bullets.get(i).shouldRemove()){
				bullets.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < asteroids.size() ; i++) {
			asteroids.get(i).update(dt);
			if(asteroids.get(i).shouldRemove()){
				asteroids.remove(i);
				i--;
			}
		}
		
		for (int i = 0; i < particles.size() ; i++) {
			particles.get(i).update(dt);
			if(particles.get(i).shouldRemove()){
				particles.remove(i);
				i--;
			}
		}
		
	
		
		checkCollisions();
	
	}

	private void checkCollisions() {
		
		// bullet asteroid
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			for(int j = 0; j < asteroids.size(); j++) {
				Asteroid a = asteroids.get(j);
				// si asteroide contiene bala...
				if(a.contains(b.getx(), b.gety())) {
					bullets.remove(i);
					i--;
					asteroids.remove(j);
					j--;
					splitAsteroid(a);
					// Puntuacion
					player.incrementScore(a.getScore());
					break;
				}
			}
		}
		
		// Player asteroid
		if (!player.isHit()) {
			for (int i = 0; i < asteroids.size(); i++) {
				Asteroid a = asteroids.get(i);
				if(a.instersects(player)) {
					
					if (player.getLives() == 0) {
						gsm.setState(GameStateManager.GAMEOVER, player);
					}
					
					player.hit();
					asteroids.remove(i);
					i--;
					splitAsteroid(a);
				}
			}
		}
		
	}

	private void splitAsteroid(Asteroid a) {
		createParticles(a.getx(), a.gety());
		numAsteroidsLeft--;
		if (a.getType() == Asteroid.LARGE) {
			asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
			asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
		} else if (a.getType() == Asteroid.MEDIUM) {
			asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.SMALL));
			asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.SMALL));
		}
		
	}

	@Override
	public void draw() {
		
		for (int i = 0; i < asteroids.size() ; i++) {
			asteroids.get(i).draw(sr);
		}
		
		for (int i = 0; i < bullets.size() ; i++) {
			bullets.get(i).draw(sr);
		}
		
		player.draw(sr);
		
		for (int i = 0; i < particles.size() ; i++) {
			particles.get(i).draw(sr);
		}
		
		sb.setColor(1, 1, 1, 1);
		sb.begin();
		//font.draw(sb, Long.toString(player.getScore()), 40, Naves.HEIGTH - 15);
		//font.draw(sb, Integer.toString(player.getLives()), Naves.WIDTH - 40, Naves.HEIGTH -15);
		sb.end();
		

	}

	@Override
	public void handleInput() {
		
		// TODO imputmanager

	/*	player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGTH));
		player.setUp(GameKeys.isDown(GameKeys.UP));
		*/
		if (Gdx.input.isTouched()){
			player.shoot();
		}

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	


}
