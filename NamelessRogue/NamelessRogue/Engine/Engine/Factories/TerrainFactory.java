package Engine.Factories;

import Engine.Entity;
import Engine.Components.World.ChunkData;

public class TerrainFactory {
	public static Entity CreateWorld()
	{
		Entity world = new Entity();
		ChunkData chunkData = new ChunkData();
		world.AddComponent(chunkData);		
		return world;
	}
	
}
