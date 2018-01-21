package Engine.Systems;

import Engine.Components.JustCreated;
import Engine.Components.Physical.OccupiesTile;
import Engine.Components.Physical.Position;
import Engine.Components.World.ChunkData;
import Engine.Tile;
import abstraction.IEntity;
import abstraction.ISystem;
import abstraction.IWorldProvider;
import shell.Game;

public class InitializationSystem implements ISystem {
    @Override
    public void Update(long gameTime, Game game) {

        IEntity worldEntity = game.GetEntityByComponentClass(ChunkData.class);
        IWorldProvider worldProvider = null;
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
                    tile.getEntitiesOnTile().add(entity);
                    tile.setPassable(false);
                }
                entity.RemoveComponentOfType(JustCreated.class);
            }
        }
    }
}
