package com.ostepropp.sidescroller;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Hindrance {

	public float width, height, x, y;

	public Hindrance(float width, float height, float x, float y) {
		super();
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}

	public void debugRender(ShapeRenderer renderer) {
		renderer.rect(x, y, width, height);
	}

	public void update(float delta, float speed) {
		x -= speed * delta;
	}

	public boolean contains(int x, int y) {
		return x >= this.x && x <= this.x + width && y >= this.y
				&& y <= this.y + height;
	}

	@Override
	public String toString() {
		return "hindrance " + width + " " + height + " " + x + " " + y;
	}

	@Override
	public Object clone() {
		return new Hindrance(this.width, this.height, this.x, this.y);
	}
}
