package com.ostepropp.sidescroller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.ostepropp.sidescroller.screens.GameScreen;
import com.ostepropp.sidescroller.screens.MenuScreen;

public class GameClass extends Game {
	
	Screen gameScreen;
	Screen menuScreen;
	
	@Override
	public void create () {
		gameScreen = new GameScreen();
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}
	
	public void startGame() {
		setScreen(gameScreen);
	}
}
