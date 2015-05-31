package com.ostepropp.sidescroller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.ostepropp.sidescroller.screens.GameScreen;

public class GameClass extends Game {
	
	Screen gameScreen;
	
	@Override
	public void create () {
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}
}
