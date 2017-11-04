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
	//TODO move this hardcode to configuration file for tileset
	void InitializeCharacterTileMap()
	{
		characterToTileMap = new HashMap<>();
        characterToTileMap.put(' ',new AtlasTileData(0,15));
		characterToTileMap.put('.',new AtlasTileData(14,13));
        characterToTileMap.put('@',new AtlasTileData(0,11));
		characterToTileMap.put('&',new AtlasTileData(6,13));
        characterToTileMap.put('~',new AtlasTileData(14,8));
        //alphabet
		characterToTileMap.put('A',new AtlasTileData(1,11));
		characterToTileMap.put('B',new AtlasTileData(2,11));
		characterToTileMap.put('C',new AtlasTileData(3,11));
		characterToTileMap.put('D',new AtlasTileData(4,11));
		characterToTileMap.put('E',new AtlasTileData(5,11));
		characterToTileMap.put('F',new AtlasTileData(6,11));
		characterToTileMap.put('G',new AtlasTileData(7,11));
		characterToTileMap.put('H',new AtlasTileData(8,11));
		characterToTileMap.put('I',new AtlasTileData(9,11));
		characterToTileMap.put('J',new AtlasTileData(10,11));
		characterToTileMap.put('K',new AtlasTileData(11,11));
		characterToTileMap.put('L',new AtlasTileData(12,11));
		characterToTileMap.put('M',new AtlasTileData(13,11));
		characterToTileMap.put('N',new AtlasTileData(14,11));
		characterToTileMap.put('O',new AtlasTileData(15,11));
		//row change
		characterToTileMap.put('P',new AtlasTileData(1,12));
		characterToTileMap.put('Q',new AtlasTileData(2,12));
		characterToTileMap.put('R',new AtlasTileData(3,12));
		characterToTileMap.put('S',new AtlasTileData(4,12));
		characterToTileMap.put('T',new AtlasTileData(5,12));
		characterToTileMap.put('U',new AtlasTileData(6,12));
		characterToTileMap.put('V',new AtlasTileData(7,12));
		characterToTileMap.put('W',new AtlasTileData(8,12));
		characterToTileMap.put('X',new AtlasTileData(9,12));
		characterToTileMap.put('Y',new AtlasTileData(10,12));
		characterToTileMap.put('Z',new AtlasTileData(11,12));
		//row change
		characterToTileMap.put('a',new AtlasTileData(1,13));
		characterToTileMap.put('b',new AtlasTileData(2,13));
		characterToTileMap.put('c',new AtlasTileData(3,13));
		characterToTileMap.put('d',new AtlasTileData(4,13));
		characterToTileMap.put('e',new AtlasTileData(5,13));
		characterToTileMap.put('f',new AtlasTileData(6,13));
		characterToTileMap.put('g',new AtlasTileData(7,13));
		characterToTileMap.put('h',new AtlasTileData(8,13));
		characterToTileMap.put('i',new AtlasTileData(9,13));
		characterToTileMap.put('j',new AtlasTileData(10,13));
		characterToTileMap.put('k',new AtlasTileData(11,13));
		characterToTileMap.put('l',new AtlasTileData(12,13));
		characterToTileMap.put('m',new AtlasTileData(13,13));
		characterToTileMap.put('n',new AtlasTileData(14,13));
		characterToTileMap.put('o',new AtlasTileData(15,13));
		//row change
		characterToTileMap.put('p',new AtlasTileData(1,14));
		characterToTileMap.put('q',new AtlasTileData(2,14));
		characterToTileMap.put('r',new AtlasTileData(3,14));
		characterToTileMap.put('s',new AtlasTileData(4,14));
		characterToTileMap.put('t',new AtlasTileData(5,14));
		characterToTileMap.put('u',new AtlasTileData(6,14));
		characterToTileMap.put('v',new AtlasTileData(7,14));
		characterToTileMap.put('w',new AtlasTileData(8,14));
		characterToTileMap.put('x',new AtlasTileData(9,14));
		characterToTileMap.put('y',new AtlasTileData(10,14));
		characterToTileMap.put('z',new AtlasTileData(11,14));
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
				   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char='~';
				   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor= new Color(0, 0, 255);
				   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor=new Color(0, 255, 255);
			   }
			   else  if(tileToDraw.getTerrainType()==TerrainTypes.Road)
			   {
				   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char='.';
				   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor=Color.GRAY;
				   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor=Color.BLACK;
			   }
			   else  if(tileToDraw.getTerrainType()==TerrainTypes.Dirt)
			   {
				   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char='&';
				   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor=Color.yellow;
				    screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor=Color.BLACK;
			   }
			   else
			   {
				   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char=' ';
				   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor=Color.black;
				    screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor=Color.BLACK;
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
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char=drawable.Representation;
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor=drawable.CharColor;					 
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
				disableTexture(gl);
				DrawTileBackGround(gl, gameInstance,
						x * settings.getFontSize(),
						y * settings.getFontSize(),
						screen.ScreenBuffer[x][y].BackGroundColor);
				enableTexture(gl);
				DrawTile(gl, gameInstance,
						x * settings.getFontSize(),
						y * settings.getFontSize(),
						characterToTileMap.get(screen.ScreenBuffer[x][y].Char),
						screen.ScreenBuffer[x][y].CharColor);
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
		tileAtlas.bind(gl);
	}


	void DrawTileBackGround(GL2 gl, Game game , int positionX, int positionY, Color color) {

		gl.glColor3f(color.getRed(), color.getGreen(),color.getBlue());

		int tileHeight = game.getSettings().getFontSize();
		int tileWidth = game.getSettings().getFontSize();

		// Draw a quad
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(positionX, positionY, 0);
		gl.glVertex3f(positionX, positionY + tileHeight, 0);
		gl.glVertex3f(positionX + tileWidth, positionY + tileHeight, 0);
		gl.glVertex3f(positionX + tileWidth, positionY, 0);
		gl.glEnd();
	}

	public void enableTexture(GL2 gl)
	{
		tileAtlas.enable(gl);
	}

	public void disableTexture(GL2 gl)
	{
		tileAtlas.disable(gl);
	}

	// dont forget to enable and bind texture before drawing the tiles
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
