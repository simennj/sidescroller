package com.ostepropp.sidescroller.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.ostepropp.sidescroller.Hindrance;
import com.ostepropp.sidescroller.Player;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen, InputProcessor {

    ShapeRenderer debugRenderer;
    float speed = 120;
    Player player;
    List<Hindrance> hindrances;
    boolean gameOver;

    @Override
    public void show() {
        debugRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(this);
        player = new Player();
        hindrances = new ArrayList<>();
        hindrances.add(new Hindrance(640,-400));
        hindrances.add(new Hindrance(640,400));
        hindrances.add(new Hindrance(1040,-360));
        hindrances.add(new Hindrance(1040,440));
        hindrances.add(new Hindrance(1440,300));
        hindrances.add(new Hindrance(1440,-600)); // TODO: Random funksjon for hindrance
        gameOver = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!gameOver) {
            player.update(delta);
            for (Hindrance hindrance : hindrances) {
                hindrance.update(delta, speed);
                if (player.isColliding(hindrance.x, hindrance.x + hindrance.width, hindrance.y, hindrance.y + hindrance.height)) {
                    System.out.println("au"); // TODO: Få gameover tekst på gamescreen
                    gameOver = true;
                }
            }
        }
        debugRenderer.begin(ShapeType.Filled);
        player.debugRender(debugRenderer);
        for (Hindrance hindrance : hindrances) {
            hindrance.debugRender(debugRenderer);
        }
        debugRenderer.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        player.flying = true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        player.flying = false;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.flying = true;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        player.flying = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
