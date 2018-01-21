package Engine.Systems;

import Engine.Components.EngineInfrastructure.ToRemove;
import Engine.Components.JustCreated;
import abstraction.IEntity;
import abstraction.ISystem;
import shell.Game;

public class EntityRemovalSystem implements ISystem {
    @Override
    public void Update(long gameTime, Game game) {
        for (IEntity entity : game.GetEntities()) {
            ToRemove toRemove = entity.GetComponentOfType(ToRemove.class);
            if(toRemove!=null)
            {
               game.RemoveEntity(entity);
            }
        }
    }
}
