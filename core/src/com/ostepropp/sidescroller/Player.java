package com.ostepropp.sidescroller;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {
	public float width = 100, height = 100, x = 300, y = 310, horSpeed, vertSpeed;
	public boolean flying, boosting, back;
	public int top = 1000, bot = 0;

	public void debugRender(ShapeRenderer renderer) {
		renderer.rect(x, y, width, height);
	}

	public void update(float delta, float speed) {

		if (x >= 0) {
			if (boosting && x < top) {
				horSpeed += delta*10 + .1;
				vertSpeed = 0;
			} else if (horSpeed > 0 && x > 0) {
				if (x > top) {
					horSpeed -= delta*top/10;
				} else {
					horSpeed -= delta*(top-y)/10;
				}

			} else if (horSpeed <= 0 && x > 0) {
				horSpeed = -delta*speed;

			}
			horSpeed = horSpeed * 0.95f;
			x += horSpeed;
		} else  {
			horSpeed = 0;

		}

		if(back){
			horSpeed -= delta*10;
			x += horSpeed;
		}

		if (y >= 0) {
			if (!boosting) {
				if (y <= 620) {
					if (flying) {
						vertSpeed += delta*20 ;

					} else {
						vertSpeed -= delta*20;
					}
				} else {
					vertSpeed = 0;
					y = 620;
				}
				vertSpeed = vertSpeed*0.95f;
				y += vertSpeed;

			}
		} else {
			vertSpeed = 0;
			y = 0;
		}
	}
	public boolean isColliding(float minX, float maxX, float minY, float maxY) {
		return !((x > maxX || x + width < minX) || (y > maxY || y + height < minY));
	}

}
