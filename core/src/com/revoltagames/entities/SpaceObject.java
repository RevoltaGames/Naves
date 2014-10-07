package com.revoltagames.entities;

import com.revoltagames.naves.Naves;

public class SpaceObject {
	
	protected float x;
	protected float y;
	
	protected float dx;
	protected float dy;
	
	protected float alpha;
	protected float speed;
	protected float rotationSpeed;
	
	protected int width;
	protected int height;
	
	protected float[] shapex;
	protected float[] shapey;

	protected void wrap() {
		if (x < 0) x = Naves.WIDTH;
		if (x > Naves.WIDTH) x = 0;
		if (y < 0) y = Naves.HEIGTH;
		if (y > Naves.HEIGTH) y = 0;

	}
	
	public float getx() { return x; }
	public float gety() { return y; }
	
	public float[] getShapex() { return shapex; }
	public float[] getShapey() { return shapey; }
	
	public boolean contains(float x, float y) {
		boolean b = false;
		for (int i = 0, j = shapex.length -1; i < shapex.length; j = i++) {
			if ((shapey[i] > y) != (shapey[j] > y) && (x < (shapex[j] - shapex[i]) * (y - shapey[i]) / (shapey[j] - shapey[i]) + shapex[i])){
				b = !b;
			}
		}
		return b;
	}
	
	public boolean instersects(SpaceObject other) {
		float[] sx = other.getShapex();
		float[] sy = other.getShapey();
		for(int i = 0; i < sx.length; i++) {
			if (contains(sx[i], sy[i])) {
				return true;
			}
		}
		return false;
	}
	
}
