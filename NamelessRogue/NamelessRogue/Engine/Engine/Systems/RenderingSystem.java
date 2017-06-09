package Engine.Systems;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.jogamp.nativewindow.util.Point;
import com.jogamp.opengl.util.awt.TextRenderer;

import Engine.TerrainTypes;
import Engine.Tile;
import Engine.Components.ConsoleCamera;
import Engine.Components.Drawable;
import Engine.Components.InputComponent;
import Engine.Components.Player;
import Engine.Components.Position;
import Engine.Components.Rendering.Screen;
import Engine.Components.World.ChunkData;
import Engine.Input.Intent;
import abstraction.IEntity;
import abstraction.ISystem;
import abstraction.IWorldProvider;
import data.GameSettings;
import shell.Game;

public class RenderingSystem implements ISystem {


	private Font font = null;
	private TextRenderer textRenderer;

	public RenderingSystem(GameSettings settings)
	{
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("Resources/square.ttf"));
			font = font.deriveFont(Font.PLAIN, settings.getFontSize());
			} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	   
		textRenderer = new TextRenderer(font);
	}
	
	@Override
	public void Update(Time gameTime, Game game) {
		
		IEntity worldEntity = game.GetEntityByComponentClass(ChunkData.class);
		IWorldProvider worldProvider = null;
		if(worldEntity!=null)
		{
			worldProvider = worldEntity.GetComponentOfType(ChunkData.class);
		}		
		for (IEntity entity : game.GetEntities()) {
			
			ConsoleCamera camera = entity.GetComponentOfType(ConsoleCamera.class);
			Screen screen = entity.GetComponentOfType(Screen.class);
			if(camera != null && screen != null && worldProvider!=null )
			{
				MoveCamera(game, camera);
				fillcharacterBuffersWithWorld(screen,camera,game.getSettings(),worldProvider);
				fillcharacterBuffersWithWorldObjects(screen,camera,game.getSettings(), game);
				
				renderScreen(game,screen,game.getSettings());
				break;
			}
		}
	}

	private void MoveCamera(Game game, ConsoleCamera camera) {
		IEntity input = game.GetEntityByComponentClass(InputComponent.class);
		if(input!=null)
		{
			Position playerPosition = game.GetEntityByComponentClass(Player.class).GetComponentOfType(Position.class);
			
			Point p = camera.getPosition();
			p.setX(playerPosition.p.getX() - game.getSettings().getWidth()/2);
			p.setY(playerPosition.p.getY() - game.getSettings().getHeight()/2);
		}
	}
	
	
	
	private void fillcharacterBuffersWithWorld(Screen screen, ConsoleCamera camera, GameSettings settings, IWorldProvider world){
		int camX = camera.getPosition().getX();
		int camY = camera.getPosition().getY();
		for(int x = camX; x<settings.getWidth()+camX; x++){
		   for(int y = camY; y<settings.getHeight()+camY; y++){
			   Tile tileToDraw = world.getTile(x, y);
			   Point screenPoint = camera.PointToScreen(x,y);
			   if(tileToDraw.getTerrainType()==TerrainTypes.Water)
			   {		      
				   screen.characterBuffer[screenPoint.getX()][screenPoint.getY()]='~';
				   screen.characterColorBuffer[screenPoint.getX()][screenPoint.getY()]= new Color(0, 128, 128);	
			   }
			   else  if(tileToDraw.getTerrainType()==TerrainTypes.Road)
			   {
				   screen.characterBuffer[screenPoint.getX()][screenPoint.getY()]='.';
				   screen.characterColorBuffer[screenPoint.getX()][screenPoint.getY()]=Color.GRAY;
			   }
			   else  if(tileToDraw.getTerrainType()==TerrainTypes.Dirt)
			   {
				   screen.characterBuffer[screenPoint.getX()][screenPoint.getY()]='&';
				   screen.characterColorBuffer[screenPoint.getX()][screenPoint.getY()]=Color.DARK_GRAY;
			   }
			   else
			   {
				   screen.characterBuffer[screenPoint.getX()][screenPoint.getY()]=' ';
				   screen.characterColorBuffer[screenPoint.getX()][screenPoint.getY()]=Color.black;
			   }
			   
		   }
		}
	}
	
	 private void fillcharacterBuffersWithWorldObjects(Screen screen, ConsoleCamera camera, GameSettings settings, Game game){
		for (IEntity entity : game.GetEntities()) {
			
			   Position position = entity.GetComponentOfType(Position.class);
			   Drawable drawable = entity.GetComponentOfType(Drawable.class);
			   if(drawable!=null && position!=null)
			   {
				   
				   Point screenPoint = camera.PointToScreen(position.p.getX(),position.p.getY());
				   int x = screenPoint.getX();
				   int y = screenPoint.getY();
				   if(x>=0 && x<settings.getWidth() && y>=0 && y < settings.getHeight())
				   {
					   screen.characterBuffer[screenPoint.getX()][screenPoint.getY()]=drawable.Representation;
					   screen.characterColorBuffer[screenPoint.getX()][screenPoint.getY()]=drawable.CharColor;					 
				   }
			   }
		}
	}
	
	
	private void renderScreen(Game gameInstance, Screen screen, GameSettings settings)
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
				textRenderer.setColor(screen.characterColorBuffer[x][y]);	
				textRenderer.draw(String.valueOf(screen.characterBuffer[x][y]), x*settings.getFontSize(), y*settings.getFontSize());			
		   }
	   }
	   textRenderer.endRendering();
	}
	

}
