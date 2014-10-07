package com.revoltagames.entities;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.revoltagames.naves.Naves;

public class Player extends SpaceObject{
	
	protected boolean left;
	protected boolean rigth;
	protected boolean up;

	private float maxSpeed;
	private float acceleration;
	private float deceleration;
	
	private float[] flamex;
	private float[] flamey;
	
	private float flametimer;
	private float maxFlameTime;
	
	private ArrayList<Bullet> bullets;
	private final int maxBullets = 7;
	
	private boolean hit;
	private boolean dead;
	
	//MAGIA
	private float hitTimer;
	private float hitTime;
	private Line2D.Float[] hitLines;
	private Point2D.Float[] hitLinesVector;
	
	private long score;
	private int extraLives;
	private long requiredScore;
	
	public Player(ArrayList<Bullet> bullets) {
		
		x = Naves.WIDTH / 2;
		y = Naves.HEIGTH / 2;
		
		maxSpeed = 300;
		acceleration = 200;
		deceleration = 20;
		
		shapex = new float[4];
		shapey = new float[4];
		
		alpha = 3.1415f / 2;
		rotationSpeed = 3;
		
		flamex = new float[3];
		flamey = new float[3];
		
		
		maxFlameTime = 0.1f;
		
		this.bullets = bullets;
		
		hit = false;
		dead = false;
		hitTimer = 0;
		hitTime = 2;
		
		score = 0;
		extraLives = 3;
		requiredScore = 10000;
	
	}
	
	private void setShape() {
		
		shapex[0] = x + MathUtils.cos(alpha) * 16;
		shapey[0] = y + MathUtils.sin(alpha) * 16;
		
		shapex[1] = x + MathUtils.cos(alpha - 4 * 3.1415f / 5) * 16;
		shapey[1] = y + MathUtils.sin(alpha - 4 * 3.1415f / 5) * 16;
		
		shapex[2] = x + MathUtils.cos(alpha + 3.1415f) * 8;
		shapey[2] = y + MathUtils.sin(alpha + 3.1415f) * 8;
		
		shapex[3] = x + MathUtils.cos(alpha + 4 * 3.1415f / 5) * 16;
		shapey[3] = y + MathUtils.sin(alpha + 4 * 3.1415f / 5) * 16;

	}
	
	private void setFlames() {
		
		flamex[0] = x + MathUtils.cos(alpha - 5*3.1415f/6)*10;
		flamey[0] = y + MathUtils.sin(alpha - 5*3.1415f/6)*10;
		
		flamex[1] = x + MathUtils.cos(alpha + 3.1415f) * (12 + flametimer * 100);
		flamey[1] = y + MathUtils.sin(alpha + 3.1415f) * (12 + flametimer * 100);
		
		flamex[2] = x + MathUtils.cos(alpha + 5*3.1415f/6)*10;
		flamey[2] = y + MathUtils.sin(alpha + 5*3.1415f/6)*10;
		
	}
	
	public void shoot() {
		if (bullets.size() == maxBullets) return;
		
		bullets.add(new Bullet(x, y, alpha));
	}
	
	public void setLeft(boolean b) { left = b;}
	public void setRight(boolean b){ rigth = b;}
	public void setUp(boolean b) { up = b;}
	public boolean isDead() { return dead; }
	public boolean isHit() {return hit; }
	public long getScore() { return score; }
	public int getLives() { return extraLives; }
	
	public void loseLife() { extraLives--; }
	public void incrementScore(long l) { score += l; }

	public void update(float dt) {
		
		//hit
		if(hit) {
			hitTimer += dt;
			if (hitTimer > hitTime) {
				dead = true;
				hitTimer = 0;
			}
			for(int i = 0; i < hitLines.length; i++){
				hitLines[i].setLine(
					hitLines[i].x1 + hitLinesVector[i].x * 10 * dt,
					hitLines[i].y1 + hitLinesVector[i].y * 10 * dt,
					hitLines[i].x2 + hitLinesVector[i].x * 10 * dt,
					hitLines[i].y2 + hitLinesVector[i].y * 10 * dt
						);
			}
			return;
		}
		
		// check extra lives
		if (score >= requiredScore) {
			extraLives++;
			requiredScore += 10000;
		}
		
		// girando
		if (left) {
			alpha += rotationSpeed * dt; 
		} else if (rigth) {
			alpha -= rotationSpeed * dt;
		}
		
		// Acelerando
		if (up) {
			dx += MathUtils.cos(alpha) * acceleration * dt;
			dy += MathUtils.sin(alpha) * acceleration * dt;

			flametimer += dt;
			if (flametimer > maxFlameTime){
				flametimer = 0;
			}
		} else flametimer = 0;
		
		// frenando
		float vec = (float) Math.sqrt(dx * dx + dy * dy);
		if (vec > 0) {
			dx -= (dx/vec) * deceleration * dt;
			dy -= (dy/vec) * deceleration * dt;
		}
		if (vec > maxSpeed) {
			dx = (dx / vec) * maxSpeed;
			dy = (dy / vec) * maxSpeed;
		}
		
		// posicion
		x += dx * dt;
		y += dy * dt;
		
		// Flames
		if (up) {
			setFlames();
		}
		
		// forma
		setShape();

		// Mantener dentro de la pantalla
		wrap();
		
	}
	
	public void draw(ShapeRenderer sr) {
		
		sr.setColor(1,1,1,1);
		
		sr.begin(ShapeType.Line);
		
		if (hit) {
			for(int i = 0; i < hitLines.length; i++) {
				sr.line(hitLines[i].x1, hitLines[i].y1, hitLines[i].x2, hitLines[i].y2);
			}
			sr.end();
			return;
		}
		
		// jugador
		for (int i = 0, j = shapex.length -1; i < shapex.length; j = i++) {
			sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);			
		}
		
		// flames
		if (up){
			for (int i = 0, j = flamex.length -1; i < flamex.length; j = i++) {
				sr.line(flamex[i], flamey[i], flamex[j], flamey[j]);
			}
		}
		
		
		sr.end();
		
	}

	public void hit() {
		if (hit) return;
		
		hit = true;
		dx = dy = 0;
		left = rigth = up = false;
		
		hitLines = new Line2D.Float[4];
		for(int i = 0, j = hitLines.length -1; i < hitLines.length; j = i++) {
			hitLines[i] = new Line2D.Float(shapex[i], shapey[i], shapex[j], shapey[j]);
		}
		
		hitLinesVector = new Point2D.Float[4];
		hitLinesVector[0] = new Point2D.Float(MathUtils.cos(alpha + 1.5f), MathUtils.sin(alpha + 1.5f));
		hitLinesVector[1] = new Point2D.Float(MathUtils.cos(alpha - 1.5f), MathUtils.sin(alpha - 1.5f));
		hitLinesVector[2] = new Point2D.Float(MathUtils.cos(alpha - 2.8f), MathUtils.sin(alpha - 2.8f));
		hitLinesVector[3] = new Point2D.Float(MathUtils.cos(alpha + 2.8f), MathUtils.sin(alpha + 2.8f));
	}

	public void reset() {
		x = Naves.WIDTH / 2;
		y = Naves.HEIGTH / 2;
		
		setShape();
		hit = dead = false;
		
	}
	

}
