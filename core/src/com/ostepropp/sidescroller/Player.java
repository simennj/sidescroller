package com.ostepropp.sidescroller;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {
	public float width = 100, height = 100, x, y, horSpeed, vertSpeed;
	public boolean flying, boosting;

	public void debugRender(ShapeRenderer renderer) {
		renderer.rect(x, y, width, height);
	}

	public void update(float delta, float speed) {

		if (boosting && x <= 620) {
			vertSpeed += delta * 10;
		} else if (x > 0) {
			vertSpeed = -speed*delta;
		} else {
			if (flying && y <= 620) {
				horSpeed += delta * 20;
			} else if (y > 0) {
				horSpeed -= delta * 20;
			} else {
				horSpeed = 0;
			}
			horSpeed = horSpeed * .95f;
			y += horSpeed;
			vertSpeed = 0;
		}
		vertSpeed = vertSpeed * .95f;
		x += vertSpeed;

	}

	public boolean isColliding(float minX, float maxX, float minY, float maxY) {
		return !((x > maxX || x + width < minX) || (y > maxY || y + height < minY));
	}

}
