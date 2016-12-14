package Engine;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import com.jogamp.nativewindow.util.Point;
import com.jogamp.opengl.util.awt.TextRenderer;

import abstraction.Scene;
import shell.Game;
import view.ConsoleCamera;

public class GameScene extends Scene {

	Font font = null;
	TextRenderer textRenderer;
	ConsoleCamera camera;
	private World world;
	public GameScene(Game game) {
		super(game);
/*		
		for(int i = 0;i<settings.getHeight();i++)
		{
			for(int j = 0;j<settings.getWidth();j++)
			{
				if(i==0||j==0||i == settings.getHeight()-1||j ==settings.getWidth()-1 )
				{
					screenBuffer[i][j] = '#';
				}
				else
				{
					screenBuffer[i][j]= '.';
				}
			}
		}
		*/
		camera = new ConsoleCamera(this,new Point(0,0));
		world = new World();
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("Resources/square.ttf"));
			font = font.deriveFont(Font.PLAIN, settings.getFontSize());
			} catch (FontFormatException | IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	 
		textRenderer = new TextRenderer(font);
	}

	@Override
	public void update() {
	}

	@Override
	public void draw() {
	   
		
		fillScreenbuffersWithWorld();		
		renderScreen();
		
   	 
	}
	
	void fillScreenbuffersWithWorld(){
		int camX = camera.getPosition().getX();
		int camY = camera.getPosition().getY();
		for(int x = camX; x<settings.getWidth()+camX; x++){
		   for(int y = camY; y<settings.getHeight()+camY; y++){
			   Tile tileToDraw = world.getTile(x, y);
			   Point screenPoint = camera.PointToScreen(x,y);
			   if(tileToDraw.getTerrainType()==TerrainTypes.Water)
			   {		      
				   screenBuffer[screenPoint.getX()][screenPoint.getY()]='~';
				   screenColorBuffer[screenPoint.getX()][screenPoint.getY()]= new Color(0, 128, 128);	
			   }
			   else  if(tileToDraw.getTerrainType()==TerrainTypes.Road)
			   {
				   screenBuffer[screenPoint.getX()][screenPoint.getY()]='.';
				   screenColorBuffer[screenPoint.getX()][screenPoint.getY()]=Color.GRAY;
			   }
			   else  if(tileToDraw.getTerrainType()==TerrainTypes.Dirt)
			   {
				   screenBuffer[screenPoint.getX()][screenPoint.getY()]='&';
				   screenColorBuffer[screenPoint.getX()][screenPoint.getY()]=Color.DARK_GRAY;
			   }
			   else
			   {
				   screenBuffer[screenPoint.getX()][screenPoint.getY()]=' ';
				   screenColorBuffer[screenPoint.getX()][screenPoint.getY()]=Color.black;
			   }
			   
		   }
		}
	}
	
	void renderScreen()
	{
		//TODO: move drawing from this text renderer to tile rendering;
	   textRenderer.beginRendering(gameInstance.getActualWidth(), gameInstance.getActualHeight());
	   textRenderer.setColor(Color.gray);
	   textRenderer.setSmoothing(false);

	   Random rand = new Random();
	   for(int x = 0; x<settings.getWidth(); x++){
		   for(int y = 0; y<settings.getHeight(); y++){				
				/*float r = rand.nextFloat();
				float g = rand.nextFloat();
				float b = rand.nextFloat();
				Color randomColor = new Color(r, g, b);*/	
				textRenderer.setColor(screenColorBuffer[x][y]);	
				textRenderer.draw(String.valueOf(screenBuffer[x][y]), x*settings.getFontSize(), y*settings.getFontSize());			
		   }
	   }
	   textRenderer.endRendering();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Point p = camera.getPosition();
		int keyCode = e.getKeyCode();
		    switch( keyCode ) { 
		        case KeyEvent.VK_UP:
		        	p.setY(p.getY()+1);
		        	camera.setPosition(p);
		            break;
		        case KeyEvent.VK_DOWN:
		        	p.setY(p.getY()-1);
		        	camera.setPosition(p);
		            break;
		        case KeyEvent.VK_LEFT:
		        	p.setX(p.getX()-1);
		        	camera.setPosition(p);
		            break;
		        case KeyEvent.VK_RIGHT :
		        	p.setX(p.getX()+1);
		        	camera.setPosition(p);
		            break;
		     }	
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
