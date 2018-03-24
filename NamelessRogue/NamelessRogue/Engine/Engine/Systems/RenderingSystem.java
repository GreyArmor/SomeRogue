package Engine.Systems;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Engine.Components.Interaction.Player;
import Engine.Components.Rendering.*;
import Engine.Infrastructure.Constants;
import Engine.Utility.BoundingBox;
import Engine.Utility.Color;
import Engine.Utility.PointUtil;
import Engine.Utility.WorldFovProvider;
import com.jogamp.nativewindow.util.Point;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;

import Engine.Infrastructure.TerrainTypes;
import Engine.Components.ChunksAndTiles.Tile;
import Engine.Components.Interaction.InputComponent;
import Engine.Components.Physical.Position;
import Engine.Components.ChunksAndTiles.ChunkData;
import abstraction.IEntity;
import abstraction.ISystem;
import abstraction.IChunkProvider;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import data.GameSettings;
import rlforj.los.IFovAlgorithm;
import rlforj.los.ILosAlgorithm;
import rlforj.los.PrecisePermissive;
import shell.Game;

public class RenderingSystem implements ISystem {

	Map<Character,AtlasTileData> characterToTileMap;
    private float gameTime;
    private float angle = 0;
    private float step = 0.04f;
    private Random graphicalRandom = new Random();
    public RenderingSystem(GameSettings settings){

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
        characterToTileMap.put('#',new AtlasTileData(3,13));
		characterToTileMap.put('$',new AtlasTileData(4,13));
		characterToTileMap.put('%',new AtlasTileData(5,13));
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
		characterToTileMap.put('P',new AtlasTileData(0,10));
		characterToTileMap.put('Q',new AtlasTileData(1,10));
		characterToTileMap.put('R',new AtlasTileData(2,10));
		characterToTileMap.put('S',new AtlasTileData(3,10));
		characterToTileMap.put('T',new AtlasTileData(4,10));
		characterToTileMap.put('U',new AtlasTileData(5,10));
		characterToTileMap.put('V',new AtlasTileData(6,10));
		characterToTileMap.put('W',new AtlasTileData(7,10));
		characterToTileMap.put('X',new AtlasTileData(8,10));
		characterToTileMap.put('Y',new AtlasTileData(9,10));
		characterToTileMap.put('Z',new AtlasTileData(10,10));
		//row change
		characterToTileMap.put('a',new AtlasTileData(1,9));
		characterToTileMap.put('b',new AtlasTileData(2,9));
		characterToTileMap.put('c',new AtlasTileData(3,9));
		characterToTileMap.put('d',new AtlasTileData(4,9));
		characterToTileMap.put('e',new AtlasTileData(5,9));
		characterToTileMap.put('f',new AtlasTileData(6,9));
		characterToTileMap.put('g',new AtlasTileData(7,9));
		characterToTileMap.put('h',new AtlasTileData(8,9));
		characterToTileMap.put('i',new AtlasTileData(9,9));
		characterToTileMap.put('j',new AtlasTileData(10,9));
		characterToTileMap.put('k',new AtlasTileData(11,9));
		characterToTileMap.put('l',new AtlasTileData(12,9));
		characterToTileMap.put('m',new AtlasTileData(13,9));
		characterToTileMap.put('n',new AtlasTileData(14,9));
		characterToTileMap.put('o',new AtlasTileData(15,9));
		//row change
		characterToTileMap.put('p',new AtlasTileData(0,8));
		characterToTileMap.put('q',new AtlasTileData(1,8));
		characterToTileMap.put('r',new AtlasTileData(2,8));
		characterToTileMap.put('s',new AtlasTileData(3,8));
		characterToTileMap.put('t',new AtlasTileData(4,8));
		characterToTileMap.put('u',new AtlasTileData(5,8));
		characterToTileMap.put('v',new AtlasTileData(6,8));
		characterToTileMap.put('w',new AtlasTileData(7,8));
		characterToTileMap.put('x',new AtlasTileData(8,8));
		characterToTileMap.put('y',new AtlasTileData(9,8));
		characterToTileMap.put('z',new AtlasTileData(10,8));
	}
	
	@Override
	public void Update(long gameTime, Game game) {
        this.gameTime = gameTime;
        //todo move to constructor or some other place better suited for initialization
		if(tileAtlas==null) {
			initializeTexture(game.getCanvas().getGL().getGL2());
		}
		IEntity worldEntity = game.GetEntityByComponentClass(ChunkData.class);
		IChunkProvider worldProvider = null;
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
				fillcharacterBufferVisibility(game, screen,camera,game.getSettings(),worldProvider);
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
			Position playerPosition = game.GetEntityByComponentClass(FollowedByCamera.class).GetComponentOfType(Position.class);
			
			Point p = camera.getPosition();
			p.setX(playerPosition.p.getX() - game.getSettings().getWidth()/2);
			p.setY(playerPosition.p.getY() - game.getSettings().getHeight()/2);
		}
	}

	private void fillcharacterBufferVisibility(Game game ,Screen screen, ConsoleCamera camera, GameSettings settings, IChunkProvider world){
		int camX = camera.getPosition().getX();
		int camY = camera.getPosition().getY();
		Position playerPosition = game.GetEntityByComponentClass(Player.class).GetComponentOfType(Position.class);
		BoundingBox b = new BoundingBox(camera.getPosition(),new Point(settings.getWidth()+camX,settings.getHeight()+camY));

		for(int x = 0; x<settings.getWidth(); x++) {
			for (int y = 0; y < settings.getHeight(); y++) {
				screen.ScreenBuffer[x][y].isVisible = false;
			}
		}


		IFovAlgorithm fov = new PrecisePermissive();

		WorldFovProvider wfp = new WorldFovProvider(world,screen,camera,settings);
		fov.visitFieldOfView(wfp,playerPosition.p.getX(),playerPosition.p.getY(),60);

//		for(int x = camX; x<settings.getWidth()+camX; x++){
//			for(int y = camY; y<settings.getHeight()+camY; y++){
//				if(x==camX||x==(settings.getWidth()+camX-1) || y == camY  ||  y==(settings.getHeight()+camY-1)) {
//					ArrayList<Point> rayToBorder = PointUtil.getLine(playerPosition.p, new Point(x,y));
//					for (int i = 0; i < rayToBorder.size(); i++) {
//						Point raypoint = rayToBorder.get(i);
//						if(b.isPointInside(raypoint.getX(),raypoint.getY()))
//						{
//							Tile tileToDraw = world.getTile(raypoint.getX(), raypoint.getY());
//							Point screenPoint = camera.PointToScreen(raypoint.getX(), raypoint.getY());
//							screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].isVisible=true;
//							if(!tileToDraw.getPassable() && i!= 0)
//							{
//								break;
//							}
//						}
//					}
//				}
//			}
//		}
	}
	
	private void fillcharacterBuffersWithWorld(Screen screen, ConsoleCamera camera, GameSettings settings, IChunkProvider world){
		int camX = camera.getPosition().getX();
		int camY = camera.getPosition().getY();
		if(angle>360){
			angle = 0;
		}
		angle +=step;

		for(int x = camX; x<settings.getWidth()+camX; x++){
		   for(int y = camY; y<settings.getHeight()+camY; y++){
			   Point screenPoint = camera.PointToScreen(x,y);
			   if( screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].isVisible) {
				   Tile tileToDraw = world.getTile(x, y);

				   if (tileToDraw.getTerrainType() == TerrainTypes.Water) {
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char = '~';

					   float waveValue1 = (float) ((Math.sin(((x + y) / 1.5) + angle)) + 1) / 2;
					   float waveValue2 = (float) ((Math.sin(((x + y)) + angle)) / 2 + 1) / 2;
					   float waveValue3 = (float) ((Math.sin(((x + y) / 3) + angle)) + 1) / 2;
					   float resultingColor = 0.3f + (0.5f * (waveValue1 + waveValue2 + waveValue3) / 3);

					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor = new Color(0, 0, 255);
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor = new Color(0, resultingColor, resultingColor);
				   } else if (tileToDraw.getTerrainType() == TerrainTypes.Road) {
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char = '.';
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor = new Color(0.5, 0.5, 0.5);
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor = new Color();
				   } else if (tileToDraw.getTerrainType() == TerrainTypes.Dirt) {
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char = '&';
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor = new Color(1f, 1f, 0);
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor = new Color();
				   } else if (tileToDraw.getTerrainType() == TerrainTypes.Grass) {
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char = '.';
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor = new Color(0, 1f, 0);
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor = new Color();
				   } else if (tileToDraw.getTerrainType() == TerrainTypes.HardRocks) {
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char = '.';
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor = new Color(0.2, 0.2, 0.2);
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor = new Color();
				   } else if (tileToDraw.getTerrainType() == TerrainTypes.Rocks) {
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char = '.';
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor = new Color(0.5, 0.5, 0.5);
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor = new Color();
				   } else if (tileToDraw.getTerrainType() == TerrainTypes.LightRocks) {
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char = '.';
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor = new Color(0.8, 0.8, 0.8);
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor = new Color();
				   } else if (tileToDraw.getTerrainType() == TerrainTypes.Sand) {
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char = '~';
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor = new Color(1f, 1f, 0);
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor = new Color();
				   } else if (tileToDraw.getTerrainType() == TerrainTypes.Snow) {
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char = 's';
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor = new Color(1f, 1f, 1f);
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor = new Color();
				   } else {
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char = ' ';
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor = new Color();
					   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor = new Color();
				   }
			   }
			   else
			   {
				   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char = ' ';
				   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor = new Color();
				   screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor = new Color();
			   }
			   
		   }
		}
	}
	
	 private void fillcharacterBuffersWithWorldObjects(Screen screen, ConsoleCamera camera, GameSettings settings, Game game) {
		 for (IEntity entity : game.GetEntities()) {

			 Position position = entity.GetComponentOfType(Position.class);
			 Drawable drawable = entity.GetComponentOfType(Drawable.class);
			 LineToPlayer lineToPlayer = entity.GetComponentOfType(LineToPlayer.class);
			 if (drawable != null && position != null) {
				 if (drawable.isVisible()) {
					 Point screenPoint = camera.PointToScreen(position.p.getX(), position.p.getY());
					 int x = screenPoint.getX();
					 int y = screenPoint.getY();
						 if (x >= 0 && x < settings.getWidth() && y >= 0 && y < settings.getHeight()) {
							 if( screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].isVisible) {
							 screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char = drawable.getRepresentation();
							 screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor = drawable.getCharColor();
							 }
							 else {
								 screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char = ' ';
								 screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor = new Color();
								 screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].BackGroundColor = new Color();
							 }
						 }

				 }
			 }
			 if (drawable != null && position != null && lineToPlayer != null) {
				 if (drawable.isVisible()) {
					 Position playerPosition = game.GetEntityByComponentClass(Player.class).GetComponentOfType(Position.class);
					 ArrayList<Point> line = PointUtil.getLine(playerPosition.p, position.p);
					 for (int i = 1; i < line.size()-1; i++) {
						 Point p = line.get(i);
						 Point screenPoint = camera.PointToScreen(p.getX(), p.getY());
						 int x = screenPoint.getX();
						 int y = screenPoint.getY();
						 if (x >= 0 && x < settings.getWidth() && y >= 0 && y < settings.getHeight()) {
							 screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].Char = 'x';
							 screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].CharColor = drawable.getCharColor();
						 }
					 }
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
		gl.glOrtho(0.0,game.getCanvas().getWidth(), game.getCanvas().getHeight(),0.0, -1.0, 1.0);
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

		gl.glTexCoord2f(textureX,textureY);
		gl.glVertex3f(positionX, positionY + tileHeight, 0);

		gl.glTexCoord2f(textureX, textureYend);
		gl.glVertex3f(positionX, positionY, 0);

		gl.glTexCoord2f(textureXend, textureYend);
		gl.glVertex3f(positionX + tileWidth, positionY, 0);

		gl.glTexCoord2f(textureXend, textureY);
		gl.glVertex3f(positionX + tileWidth, positionY + tileHeight, 0);


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
