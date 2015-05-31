package com.ostepropp.sidescroller;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {
	public float width = 100, height = 100, x, y, speed;
	public boolean flying;

	public void debugRender(ShapeRenderer renderer) {
		renderer.rect(x, y, width, height);
	}

	public void update(float delta) {
		if (flying && y <= 620) {
			speed += delta * 10;
		} else if (y > 0) {
			speed -= delta * 10;
		} else {
			speed = 0;
		}
		speed = speed * .95f;
		y += speed;
	}

}
