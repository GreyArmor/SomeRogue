package Engine.Systems;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Engine.Constants;
import com.jogamp.nativewindow.util.Point;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.TileRenderer;
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
import abstraction.IEntity;
import abstraction.ISystem;
import abstraction.IWorldProvider;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import data.GameSettings;
import shell.Game;

public class RenderingSystem implements ISystem {

	Map<Character,AtlasTileData> characterToTileMap;
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

        InitializeCharacterTileMap();
	}

	void InitializeCharacterTileMap()
	{
		characterToTileMap = new HashMap<>();
        characterToTileMap.put(' ',new AtlasTileData(0,15));
		characterToTileMap.put('.',new AtlasTileData(14,13));
        characterToTileMap.put('@',new AtlasTileData(1,15));
        characterToTileMap.put('&',new AtlasTileData(6,13));
        characterToTileMap.put('~',new AtlasTileData(1,14));
	}
	
	@Override
	public void Update(Time gameTime, Game game) {

		//todo move to constructor or some other place better suited for initialization
		if(tileAtlas==null) {
			initializeTexture(game.getCanvas().getGL().getGL2());
		}
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
				   screen.characterColorBuffer[screenPoint.getX()][screenPoint.getY()]= new Color(0, 0, 255);
			   }
			   else  if(tileToDraw.getTerrainType()==TerrainTypes.Road)
			   {
				   screen.characterBuffer[screenPoint.getX()][screenPoint.getY()]='.';
				   screen.characterColorBuffer[screenPoint.getX()][screenPoint.getY()]=Color.GRAY;
			   }
			   else  if(tileToDraw.getTerrainType()==TerrainTypes.Dirt)
			   {
				   screen.characterBuffer[screenPoint.getX()][screenPoint.getY()]='&';
				   screen.characterColorBuffer[screenPoint.getX()][screenPoint.getY()]=Color.yellow;
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
	   GL2  gl = gameInstance.getCanvas().getGL().getGL2();
	   beginDrawingTiles(gl,gameInstance);

		for(int x = 0; x<settings.getWidth(); x++){
			for(int y = 0; y<settings.getHeight(); y++) {
				DrawTile(gl, gameInstance,
						x * settings.getFontSize(),
						y * settings.getFontSize(),
						characterToTileMap.get(screen.characterBuffer[x][y]),
						screen.characterColorBuffer[x][y]);
			}
	   }
	   endDrawingTile(gl);

	}

	class AtlasTileData{
	 	public int X;
	 	public int Y;
	 	public AtlasTileData(int x,int y)
        {
            X = x;
            Y = y;
        }
	}

	Texture tileAtlas = null;
	private Texture initializeTexture(GL2 gl) {

		tileAtlas = null;

		try {
			tileAtlas = TextureIO.newTexture(new FileInputStream("Resources/DFfont.png"),false,".png");

			tileAtlas.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_POINT);
			tileAtlas.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_POINT);
			tileAtlas.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
			tileAtlas.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
		} catch (IOException | GLException ex) {
			//Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
		}

		return tileAtlas;
	}

	void beginDrawingTiles(GL2 gl, Game game)
	{
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glOrtho(0.0,game.getCanvas().getWidth(), 0.0,game.getCanvas().getHeight(), -1.0, 1.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glDisable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		tileAtlas.enable(gl);
		tileAtlas.bind(gl);
	}

	void DrawTile(GL2 gl, Game game ,int positionX, int positionY, AtlasTileData atlasTileData, Color color) {

		if(atlasTileData==null) {
			atlasTileData = new AtlasTileData(1, 1);
		}

		gl.glColor3f(color.getRed(), color.getGreen(),color.getBlue());

		int tileHeight = game.getSettings().getFontSize();
		int tileWidth = game.getSettings().getFontSize();

		// Draw a textured quad
		gl.glBegin(GL2.GL_QUADS);
		float textureX = (float)atlasTileData.X*Constants.tileAtlasTileSize / tileAtlas.getImageWidth();
		float textureY = (float)atlasTileData.Y * Constants.tileAtlasTileSize / tileAtlas.getImageHeight();

		float textureXend = (((float)atlasTileData.X*Constants.tileAtlasTileSize) + Constants.tileAtlasTileSize) / tileAtlas.getImageWidth();
		float textureYend = (((float)atlasTileData.Y*Constants.tileAtlasTileSize) + Constants.tileAtlasTileSize) / tileAtlas.getImageWidth();

		gl.glTexCoord2f(textureX,textureY );
		gl.glVertex3f(positionX, positionY, 0);
        gl.glTexCoord2f(textureX, textureYend);
		gl.glVertex3f(positionX, positionY + tileHeight, 0);
        gl.glTexCoord2f(textureXend, textureYend);
		gl.glVertex3f(positionX + tileWidth, positionY + tileHeight, 0);
        gl.glTexCoord2f(textureXend, textureY);
		gl.glVertex3f(positionX + tileWidth, positionY, 0);

		gl.glEnd();
	}
	void endDrawingTile(GL2 gl)
	{
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glPopMatrix();


		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPopMatrix();

		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}
}
