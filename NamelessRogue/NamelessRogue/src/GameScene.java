import static org.lwjgl.opengl.GL11.glClearColor;

public class GameScene extends Scene {

	public GameScene(Game game) {
		super(game);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		// Set the clear color
		glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
		
	}

}
