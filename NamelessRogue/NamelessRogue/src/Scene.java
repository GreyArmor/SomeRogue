
public abstract class Scene {
	//every scene knows about game
	Game gameInstance;
	
	//and every scene has a screen buffer
	public char screenBuffer[][];
	
	protected int bufferSizeRows = 20;
	protected int buferSizeColumns = 20;
	protected GameSettings settings;
	public Scene(Game game) {
		gameInstance = game;
		//for now its fixed, later it will be dynamic, depending on the screen resolution;
		screenBuffer = new char[bufferSizeRows][buferSizeColumns];
		settings = gameInstance.getSettings();
	}
	//here scene updates game logic
	public abstract void update();
	//and here it draws
	public abstract void draw();
}
