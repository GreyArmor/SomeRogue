package Engine.Systems;

import Engine.Components.Interaction.DeathCommand;
import Engine.Components.JustCreated;
import Engine.Components.Physical.OccupiesTile;
import Engine.Components.Physical.Position;
import Engine.Components.Rendering.Drawable;
import Engine.Components.Status.Damage;
import Engine.Components.Status.Dead;
import Engine.Components.UI.Description;
import Engine.Components.World.ChunkData;
import Engine.Tile;
import abstraction.IEntity;
import abstraction.ISystem;
import abstraction.IWorldProvider;
import shell.Game;

public class DeathSystem implements ISystem {
    @Override
    public void Update(long gameTime, Game game) {
        for (IEntity entity : game.GetEntities()) {
            DeathCommand dc = entity.GetComponentOfType(DeathCommand.class);
            if (dc != null) {
                IEntity entityToKill = dc.getToKill();
                entityToKill.AddComponent(new Dead());

                Drawable drawable = entityToKill.GetComponentOfType(Drawable.class);
                if (drawable != null) {
                    drawable.setRepresentation('%');
                    entityToKill.RemoveComponentOfType(DeathCommand.class);
                }

                IEntity worldEntity = game.GetEntityByComponentClass(ChunkData.class);
                IWorldProvider worldProvider = null;
                if (worldEntity != null) {
                    worldProvider = worldEntity.GetComponentOfType(ChunkData.class);
                }

                Position position = entityToKill.GetComponentOfType(Position.class);
                OccupiesTile occupiesTile = entityToKill.GetComponentOfType(OccupiesTile.class);
                if (occupiesTile != null && position != null) {
                    Tile tile = worldProvider.getTile(position.p.getX(), position.p.getY());
                    tile.getEntitiesOnTile().remove(entityToKill);
                }
                entityToKill.RemoveComponentOfType(OccupiesTile.class);

                Description d = entityToKill.GetComponentOfType(Description.class);

                if(d!=null) {
                    game.WriteLineToConsole(d.Name +" is dead!");
                }
            }
        }
    }
}