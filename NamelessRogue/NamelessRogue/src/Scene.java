
public abstract class Scene {
	//every scene knows about game
	Game gameInstance;
	
	//and every scene has a screen buffer
	public char screenBuffer[][];
	
	public Scene(Game game) {
		gameInstance = game;
		//for now 100x100, later it will be dynamic, depending on the screen resolution;
		screenBuffer = new char[100][100];
	}
	//here scene updates game logic
	public abstract void update();
	//and here it draws
	public abstract void draw();
}
