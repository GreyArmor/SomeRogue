package abstraction;

import data.GameSettings;
import shell.Game;

public abstract class Scene {
	//every scene knows about game
	protected Game gameInstance;
	
	//and every scene has a screen buffer
	public char screenBuffer[][];
	

	protected GameSettings settings;
	public Scene(Game game) {
		gameInstance = game;
		settings = gameInstance.getSettings();
		screenBuffer = new char[settings.getHeight()][settings.getWidth()];
	
	}
	//here scene updates game logic
	public abstract void update();
	//and here it draws
	public abstract void draw();
}
