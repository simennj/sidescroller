package com.ostepropp.sidescroller.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ostepropp.sidescroller.GameClass;


public class MenuScreen implements Screen {

	GameClass game;
	Stage stage;
	Skin skin;
	Table table;

    Music intro;
	
	public MenuScreen(GameClass game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		table = new Table(skin);
		table.setFillParent(true);
		table.center();
		TextButton start = new TextButton("start", skin);
		start.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.startGame();
                if(intro.isPlaying()){
                    dispose();
                }
			}
		});
		TextButton editor = new TextButton("editor", skin);
		editor.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.startEditor();
                if(intro.isPlaying()){
                    dispose();
                }
			}
		});
		table.add(new Label("Super Heli 2", skin)).colspan(2);
		table.row();
		table.add(start);
		table.add(editor);
		table.setDebug(true);
		stage.addActor(table);

        audio();
	}

    public void audio(){
        intro = Gdx.audio.newMusic(Gdx.files.internal("audio/intro.wav"));
    }

	@Override
	public void render(float delta) {
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
        //intro.play();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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
        intro.dispose();
	}

}
