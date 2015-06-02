package com.ostepropp.sidescroller.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ostepropp.sidescroller.Hindrance;
import com.ostepropp.sidescroller.LevelLoader;
import com.ostepropp.sidescroller.Player;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen, InputProcessor {

	ShapeRenderer debugRenderer;
	float speed = 250;
	Player player;
	LevelLoader loader = new LevelLoader();
	List<Hindrance> hindrances;
	boolean gameOver;

    Stage stage;
    Label label;
    Skin skin;
    Table table;
    Label.LabelStyle style;

    FreeTypeFontGenerator font;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;


	@Override
	public void show() {
		debugRenderer = new ShapeRenderer();
		Gdx.input.setInputProcessor(this);
		start();

		
	}
	
	public void start() {
		player = new Player();
		hindrances = loader.loadLevel("levels/test");
		gameOver = false;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (!gameOver) {
			player.update(delta,speed);
			for (Hindrance hindrance : hindrances) {
				hindrance.update(delta, speed);
				if (player.isColliding(hindrance.x, hindrance.x
						+ hindrance.width, hindrance.y, hindrance.y
						+ hindrance.height)) {
					System.out.println("au"); // TODO: Få gameover tekst på
												// gamescreen

					gameOver = true;
				}
			}
			if(player.isColliding(0,0,0,720)) {
				gameOver = true;
			}
		}
        if(gameOver){
            gameover();
        }

		debugRenderer.begin(ShapeType.Filled);
		player.debugRender(debugRenderer);
		for (Hindrance hindrance : hindrances) {
			hindrance.debugRender(debugRenderer);
		}
		debugRenderer.end();
	}

    public void gameover(){
        //Egen font
        font = new FreeTypeFontGenerator(Gdx.files.internal("fonts/visitor1.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        BitmapFont visitor5 = font.generateFont(parameter);

        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        table = new Table(skin);
        table.setFillParent(true);

        //Label
        style = new Label.LabelStyle(visitor5, Color.RED);
        label = new Label("Game Over, taper", style);
        table.add(label);
        table.setDebug(true);
        stage.addActor(table);

        stage.draw();
        font.dispose();
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
		if(!gameOver) {
		if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
			player.boosting = true;
		} else if(keycode == Input.Keys.W || keycode == Input.Keys.UP || keycode == Input.Keys.SPACE) {
			player.flying = true;
		} else if(keycode == Input.Keys.A || keycode == Input.Keys.LEFT){
			player.back = true;
		} } else if(keycode == Input.Keys.SPACE || keycode == Input.Keys.ENTER) {
			start();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.D) {
			player.boosting = false;
		} else if(keycode == Input.Keys.W) {
			player.flying = false;
		} else if(keycode == Input.Keys.A){
			player.back = false;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(gameOver){
			start();
		}
		if (button == Buttons.RIGHT) {
			player.boosting = true;
		} else {
			player.flying = true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == Buttons.RIGHT) {
			player.boosting = false;
		} else {
			player.flying = false;
		}
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
