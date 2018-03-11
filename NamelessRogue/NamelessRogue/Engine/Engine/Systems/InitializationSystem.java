package Engine.Systems;

import Engine.Components.JustCreated;
import Engine.Components.Physical.OccupiesTile;
import Engine.Components.Physical.Position;
import Engine.Components.ChunksAndTiles.ChunkData;
import Engine.Components.ChunksAndTiles.Tile;
import Engine.Infrastructure.Entity;
import abstraction.IEntity;
import abstraction.ISystem;
import abstraction.IChunkProvider;
import shell.Game;

public class InitializationSystem implements ISystem {
    @Override
    public void Update(long gameTime, Game game) {

        IEntity worldEntity = game.GetEntityByComponentClass(ChunkData.class);
        IChunkProvider worldProvider = null;
        if(worldEntity!=null)
        {
            worldProvider = worldEntity.GetComponentOfType(ChunkData.class);
        }

        for (IEntity entity : game.GetEntities()) {
            JustCreated jc = entity.GetComponentOfType(JustCreated.class);
            if(jc!=null)
            {
                Position position = entity.GetComponentOfType(Position.class);
                OccupiesTile occupiesTile = entity.GetComponentOfType(OccupiesTile.class);
                if(occupiesTile!=null && position!=null) {
                    Tile tile = worldProvider.getTile(position.p.getX(), position.p.getY());
                    tile.getEntitiesOnTile().add((Entity)entity);
                    tile.setPassable(false);
                }
                entity.RemoveComponentOfType(JustCreated.class);
            }
        }
    }
}
