package com.me.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends SpaceObject {
	
	private float lifeTime;
	private float lifeTimer;
	
	private boolean remove;
	
	public Bullet(float x, float y, float alpha) {
		
		this.x = x;
		this.y = y;
		this.alpha = alpha;
		
		float speed = 400;
		dx = MathUtils.cos(alpha) * speed;
		dy = MathUtils.sin(alpha) * speed;
		
		width = height = 4;
		
		lifeTimer = 0;
		lifeTime = 1;
	}

	public boolean shouldRemove() {return remove;}
	
	public void update(float dt) {
		
		x += dx * dt;
		y += dy * dt;
		
		lifeTimer += dt;
		if (lifeTimer > lifeTime) {
			remove = true;
		}
		
		wrap();
		
	}
	
	public void draw(ShapeRenderer sr) {
		
		sr.setColor(1, 1, 1, 1);
		
		sr.begin(ShapeType.Filled);
		sr.circle(x - width/2 , y - height / 2, width/2);
		sr.end();
		
		
	}
}
