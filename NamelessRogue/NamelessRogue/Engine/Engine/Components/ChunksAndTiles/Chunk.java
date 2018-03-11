package Engine.Components.ChunksAndTiles;

import Engine.Infrastructure.Constants;
import Engine.Infrastructure.TerrainTypes;
import Engine.Serialization.SaveManager;
import Engine.Utility.BoundingBox;
import com.jogamp.nativewindow.util.Point;
import abstraction.IBoundsProvider;
import shell.EntryPoint;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Chunk implements IBoundsProvider {
	private Point worldPositionBottomLeftCorner;
	private transient  ChunkData chunkContainer;

	private Tile[][] chunkTiles;
	private transient  BoundingBox boundingBox;
	private transient boolean isActive;

	public Chunk() {
	}

	public boolean IsActive(){return isActive;}
	public Point getWorldPosition() {
		return worldPositionBottomLeftCorner;
	}

	public void setWorldPosition(Point worldPosition) {
		this.worldPositionBottomLeftCorner = worldPosition;
	}	


	public Chunk(Point bottomLeftCornerWorld, ChunkData chunkContainer)
	{
		boundingBox = new BoundingBox(bottomLeftCornerWorld, new Point(bottomLeftCornerWorld.getX()+ Constants.ChunkSize,bottomLeftCornerWorld.getY()+Constants.ChunkSize));
		worldPositionBottomLeftCorner = bottomLeftCornerWorld;
		this.chunkContainer = chunkContainer;

		isActive = false;
	}


	public void FillWithTiles(TerrainGenerator generator)
	{

		for(int x = 0; x<Constants.ChunkSize;x++)
		{
			for(int y = 0;y<Constants.ChunkSize;y++)
			{
				chunkTiles[x][y] = generator.GetTile(x+worldPositionBottomLeftCorner.getX(),y + worldPositionBottomLeftCorner.getY());
			}
		}
	}

	public void FillWithDebugTiles(TerrainGenerator generator)
	{

		for(int x = 0; x<Constants.ChunkSize;x++)
		{
			for(int y = 0;y<Constants.ChunkSize;y++)
			{
				chunkTiles[x][y] = new Tile(TerrainTypes.HardRocks,  new Point(x+worldPositionBottomLeftCorner.getX(), y + worldPositionBottomLeftCorner.getY()));
			}
		}
	}

	public Tile[][] getChunkTiles() {
		return chunkTiles;
	}

	public void setChunkTiles(Tile[][] chunkTiles) {
		this.chunkTiles = chunkTiles;
	}

	public boolean isPointInside(Point p) {
		return isPointInside(p.getX(),p.getY());	
	}
	public boolean isPointInside(int x, int y) {
		return boundingBox.isPointInside(x,y);	
	}

	public Tile getTile(Point p) {
		return getTile(p.getX(),p.getY());
	}
	
	public Tile getTile(int x,int y) {

		if(!isActive)
		{
			Activate();
		}

		int bottomLeftX = worldPositionBottomLeftCorner.getX();
		int bottomLeftY = worldPositionBottomLeftCorner.getY();
		
		int localX = Math.abs(bottomLeftX - x);
		int localY = Math.abs(bottomLeftY - y);
		
		return chunkTiles[localX][localY];
	}

	public void Activate() {
		System.out.print("Activate\n");
		isActive = true;
		chunkTiles = new Tile[Constants.ChunkSize][Constants.ChunkSize];
		if(!LoadFromDisk())
		{
			FillWithTiles(chunkContainer.getWorldGenerator());
		}

	}
//TODO: serialization
	private boolean LoadFromDisk() {

		String appPath = "";
		try {
			appPath = new File(EntryPoint.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getPath();
		}catch (URISyntaxException e) {
			e.printStackTrace();
		}
		Tile[][] tiles = SaveManager.LoadChunk(appPath+"\\Chunks",
				String.valueOf(worldPositionBottomLeftCorner.getX()) + "_" + String.valueOf(worldPositionBottomLeftCorner.getY()));
		if (tiles != null) {

			chunkTiles = tiles;
			System.out.print("LoadFromDisk true\n");
			return true;
		}
		System.out.print("LoadFromDisk false\n");
		return false;
	}

	public void Deactivate()
	{
		String appPath = "";
		try {
			appPath = new File(EntryPoint.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getPath();
		}catch (URISyntaxException e) {
			e.printStackTrace();
		}
		System.out.print("Deactivate\n");
		SaveManager.SaveChunk(appPath+"\\Chunks",this,
				String.valueOf(worldPositionBottomLeftCorner.getX()) + "_" + String.valueOf(worldPositionBottomLeftCorner.getY()));

		isActive = false;
		chunkTiles = null;
	}

	public void setTile(int x, int y, Tile tile) {
		int bottomLeftX = worldPositionBottomLeftCorner.getX();
		int bottomLeftY = worldPositionBottomLeftCorner.getY();

		int localX = Math.abs(bottomLeftX - x);
		int localY = Math.abs(bottomLeftY - y);

		chunkTiles[localX][localY] = tile;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}
}
