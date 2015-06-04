package com.ostepropp.sidescroller.screens;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.ostepropp.sidescroller.GameClass;
import com.ostepropp.sidescroller.Hindrance;
import com.ostepropp.sidescroller.LevelLoader;

public class LevelEditor implements Screen, InputProcessor {

	GameClass game;
	InputMultiplexer input;

	public LevelEditor(GameClass game) {
		this.game = game;
	}

	ShapeRenderer debugRenderer;
	Stage stage;
	Skin skin;
	LevelLoader loader = new LevelLoader("levels/test");
	List<Hindrance> hindrances;
	float SegmentLength;
	int currentSegment, totalSegments = loader.totalSegments();
	int createX, createY, createWidth, createHeight, offset;

	@Override
	public void show() {
		debugRenderer = new ShapeRenderer();
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		Table table = new Table();
		table.setFillParent(true);
		SelectBox<String> segments = new SelectBox<String>(skin);
		segments.setItems(loader.getSegmentsList());
		segments.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				int i = ((SelectBox<String>)actor).getSelectedIndex();
				if(i!=currentSegment)
					loadSegment(i);
				System.out.println(hindrances);
			}
		});
		table.add(segments);
		table.debug();
		stage.addActor(table);
		input = new InputMultiplexer(stage,this);
		Gdx.input.setInputProcessor(input);
		loadSegment(currentSegment);
	}

	public void loadSegment(int i) {
		hindrances = loader.getSegment(i).stream().peek(h -> h.x -= 1280)
				.collect(Collectors.toList());
		SegmentLength = loader.getSegmentLength(i);
		currentSegment=i;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRenderer.begin(ShapeType.Filled);
		debugRenderer.setColor(Color.LIGHT_GRAY);
		debugRenderer.rect(0, 0, 1280, 720);
		debugRenderer.setColor(Color.GRAY);
		for (Hindrance hindrance : hindrances) {
			hindrance.debugRender(debugRenderer);
		}
		debugRenderer.end();
		debugRenderer.begin(ShapeType.Line);
		debugRenderer.setColor(Color.RED);
		debugRenderer.rect(createX, createY, createWidth, createHeight);
		debugRenderer.end();
		stage.act();
		stage.draw();
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
		switch (keycode) {
		case Keys.S:
			try {
				Writer writer = new FileWriter(Gdx.files
						.internal("levels/test").file());
				for (Hindrance hindrance : hindrances) {
					writer.write(hindrance + "\n");
				}
				double asdf = hindrances.stream()
						.mapToDouble(h -> h.x + h.width).max().orElse(1280);
				writer.write(Double.toString(asdf));
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Keys.ESCAPE:
			game.showMenu();
		default:
			break;
		}
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
		screenX = screenX - offset;
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
		screenY = 720 - screenY;
		screenX = screenX - offset;
		if (button == Buttons.RIGHT) {

		} else if (createWidth != 0 && createHeight != 0) {
			hindrances.add(new Hindrance(Math.abs(createWidth), Math
					.abs(createHeight), createWidth < 0 ? createX + createWidth
					: createX, createHeight < 0 ? createY + createHeight
					: createY));
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		screenY = 720 - screenY;
		screenX = screenX - offset;
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
				createWidth = MathUtils.round((float) (screenX - createX) / 10) * 10;
				createHeight = MathUtils
						.round((float) (720 - screenY - createY) / 10) * 10;
			} else {
				createWidth = screenX - createX;
				createHeight = screenY - createY;
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
		final float deltaOffset = Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) ? amount * 100
				: amount * 10;
		if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
			hindrances.forEach(h -> h.x += deltaOffset);
		} else {
			offset += deltaOffset;
			debugRenderer.translate(deltaOffset, 0, 0);
		}
		return false;
	}

}
