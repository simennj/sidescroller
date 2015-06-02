package com.ostepropp.sidescroller.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.ostepropp.sidescroller.Hindrance;
import com.ostepropp.sidescroller.LevelLoader;

public class LevelEditor implements Screen, InputProcessor {


	ShapeRenderer debugRenderer;
	LevelLoader loader = new LevelLoader("levels/test");
	List<Hindrance> hindrances;
	float SegmentLength;
	int currentSegment,totalSegments = loader.totalSegments();
	
	@Override
	public void show() {
		debugRenderer = new ShapeRenderer();
		Gdx.input.setInputProcessor(this);
		loadSegment(currentSegment);
	}
	
	public void loadSegment(int i){
		hindrances = loader.getSegment(i);
		SegmentLength = loader.getSegmentLength(i);
		System.out.println(hindrances);
	}

	@Override
	public void render(float delta) {
		debugRenderer.begin(ShapeType.Filled);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
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
