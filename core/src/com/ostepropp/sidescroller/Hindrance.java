package com.ostepropp.sidescroller;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Hindrance {

    public float width = 100, height = 600, x, y;

    public Hindrance(float x,float y) {
        this.x = x;
        this.y = y;
    }

    public void debugRender(ShapeRenderer renderer) {
        renderer.rect(x, y, width, height);
    }

    public void update(float delta, float speed) {
        x -=speed*delta;
        if (x < -100) {
            x = 1200;
        }
    }
}
