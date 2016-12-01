package abstraction;

import java.awt.Color;
import java.awt.event.KeyEvent;

import data.GameSettings;
import shell.Game;

public abstract class Scene {
	//every scene knows about game
	protected Game gameInstance;
	
	//and every scene has a screen buffer
	public char screenBuffer[][];
	public Color screenColorBuffer[][];
	

	protected GameSettings settings;
	public Scene(Game game) {
		gameInstance = game;
		settings = gameInstance.getSettings();
		screenBuffer = new char[settings.getWidth()][settings.getHeight()];
		screenColorBuffer = new Color[settings.getWidth()][settings.getHeight()];
	
	}
	//here scene updates game logic
	public abstract void update();
	//and here it draws
	public abstract void draw();
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);
}
