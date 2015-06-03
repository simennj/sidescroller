package com.ostepropp.sidescroller.screens;

import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.ostepropp.sidescroller.GameClass;
import com.ostepropp.sidescroller.Hindrance;
import com.ostepropp.sidescroller.LevelLoader;

public class LevelEditor implements Screen, InputProcessor {

	GameClass game;

	public LevelEditor(GameClass game) {
		this.game = game;
	}

	ShapeRenderer debugRenderer;
	LevelLoader loader = new LevelLoader("levels/test");
	List<Hindrance> hindrances;
	float SegmentLength;
	int currentSegment, totalSegments = loader.totalSegments();
	int createX, createY, createWidth, createHeight;

	@Override
	public void show() {
		debugRenderer = new ShapeRenderer();
		Gdx.input.setInputProcessor(this);
		loadSegment(currentSegment);
	}

	public void loadSegment(int i) {
		hindrances = loader.getSegment(i).stream().peek(h -> h.x -= 1280)
				.collect(Collectors.toList());
		SegmentLength = loader.getSegmentLength(i);
		System.out.println(hindrances);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(Color.WHITE);
		for (Hindrance hindrance : hindrances) {
			hindrance.debugRender(debugRenderer);
		}
		debugRenderer.end();
		debugRenderer.begin(ShapeType.Line);
		debugRenderer.setColor(Color.RED);
		debugRenderer.rect(createX, createY, createWidth, createHeight);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenY = 720 - screenY;
		if (button == Buttons.RIGHT) {
			Hindrance tmp = null;
			for (Hindrance hindrance : hindrances) {
				if (hindrance.contains(screenX, screenY)) {
					tmp = hindrance;
				}
			}
			hindrances.remove(tmp);
		} else {
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
				createX = MathUtils.round((float) screenX / 10) * 10;
				createY = MathUtils.round((float) screenY / 10) * 10;
			} else {
				createX = screenX;
				createY = screenY;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == Buttons.RIGHT) {

		} else {
			hindrances.add(new Hindrance(createWidth, createHeight, createX,
					createY));
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
				createWidth = MathUtils.round((float) (screenX - createX) / 10) * 10;
				createHeight = MathUtils
						.round((float) (720 - screenY - createY) / 10) * 10;
			} else {
				createWidth = screenX - createX;
				createHeight = 720 - screenY - createY;
			}
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
