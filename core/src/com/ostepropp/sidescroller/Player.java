package com.ostepropp.sidescroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {
	public float width = 106, height = 46, x = 300, y = 310, horSpeed,
			vertSpeed;
	public boolean flying, boosting, back, hold;
	public int top = 1000, bot = 0, score;
	public Texture texture = new Texture(Gdx.files.internal("heli3.png"));
	
	
	public void debugRender(ShapeRenderer renderer) {
		renderer.rect(x, y, width, height);
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, x-16, y-10);
	}

	public void update(float delta, float speed) {
		score++;
		if (boosting) { // Bevegelse i x
			if (x < top) {
				horSpeed += delta * 10 + .1;
			} else {
				horSpeed -= delta * top / 10;
			}
		} else if (horSpeed > 0) {
			horSpeed -= delta * top / 10;
		} else if (back) {
			horSpeed -= delta * 30 + .1;
		} else {
			horSpeed = -delta * speed;
		}

		horSpeed = horSpeed * 0.95f;
		x += horSpeed;

		if (y >= 0) { // Bevegelse i y
			if (y <= 620) {
				if (flying) {
					vertSpeed += delta * 20;
				} else {
					vertSpeed -= delta * 20;
				}
			} else {
				vertSpeed = 0;
				y = 620;
			}
			vertSpeed = vertSpeed * 0.95f;
			y += vertSpeed;
		} else {
			vertSpeed = 0;
			y = 0;
		}
	}

	public int getScore(float delta, float speed) {
		return score;
	}

	public boolean isColliding(float minX, float maxX, float minY, float maxY) {
		return !((x > maxX || x + width < minX) || (y > maxY || y + height < minY));
	}

}
