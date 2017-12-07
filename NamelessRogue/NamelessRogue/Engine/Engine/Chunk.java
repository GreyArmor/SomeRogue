package Engine;

import Engine.Components.World.ChunkData;
import Engine.Components.World.TerrainGenerator;
import abstraction.IWorldProvider;
import com.jogamp.nativewindow.util.Point;
import abstraction.IBoundsProvider;
public class Chunk implements IBoundsProvider {
	private Point worldPositionBottomLeftCorner;
	private ChunkData chunkContainer;

	private Tile[][] chunkTiles;
	private BoundingBox boundingBox;
	private boolean isActive;
	
		
	public Point getWorldPosition() {
		return worldPositionBottomLeftCorner;
	}

	public void setWorldPosition(Point worldPosition) {
		this.worldPositionBottomLeftCorner = worldPosition;
	}	


	public Chunk(Point bottomLeftCornerWorld, ChunkData chunkContainer)
	{
		boundingBox = new BoundingBox(bottomLeftCornerWorld, new Point(bottomLeftCornerWorld.getX()+Constants.ChunkSize,bottomLeftCornerWorld.getY()+Constants.ChunkSize));
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
	
	public void fillWithTestTiles()
	{
		for(int x = 0;x<Constants.ChunkSize;x++)
		{
			for(int y = 0;y<Constants.ChunkSize;y++)
			{
				if(x==0||y==0||x == Constants.ChunkSize-1||y == Constants.ChunkSize-1 )
				{
					chunkTiles[x][y] = new Tile(TerrainTypes.Road);
				}
				else if(x>Constants.ChunkSize/2)
				{
					chunkTiles[x][y] = new Tile(TerrainTypes.Water);
				}
				else
				{
					chunkTiles[x][y] = new Tile(TerrainTypes.Dirt);
				}
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
		isActive = true;
		chunkTiles = new Tile[Constants.ChunkSize][Constants.ChunkSize];
		if(!LoadFromDisk())
		{
			FillWithTiles(chunkContainer.getWorldGenerator());
		}

	}
//TODO: serialization
	private boolean LoadFromDisk() {
		return false;
	}

	public void Deactivate()
	{
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
