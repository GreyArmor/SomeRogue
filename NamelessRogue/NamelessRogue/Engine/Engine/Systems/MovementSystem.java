package Engine.Systems;

import Engine.Components.Interaction.MoveToCommand;
import Engine.Components.World.ChunkData;
import Engine.Tile;
import abstraction.IWorldProvider;

import Engine.Components.Physical.Position;
import abstraction.IEntity;
import abstraction.ISystem;
import shell.Game;

public class MovementSystem implements ISystem {

	@Override
	public void Update(long gameTime, Game game) {
		for (IEntity entity : game.GetEntities()) {
			Position position = entity.GetComponentOfType(Position.class);
			MoveToCommand moveCommand = entity.GetComponentOfType(MoveToCommand.class);
			if(position != null && moveCommand!=null)
			{
				IEntity worldEntity = game.GetEntityByComponentClass(ChunkData.class);
				IWorldProvider worldProvider = null;
				if(worldEntity!=null)
				{
					worldProvider = worldEntity.GetComponentOfType(ChunkData.class);
				}
				Tile oldTile = worldProvider.getTile(position.p.getX(), position.p.getY());
				Tile newTile = worldProvider.getTile(moveCommand.p.getX(), moveCommand.p.getY());

				oldTile.getEntitiesOnTile().remove(entity);
				newTile.getEntitiesOnTile().add(entity);

				position.p.setX(moveCommand.p.getX());
				position.p.setY(moveCommand.p.getY());

				entity.RemoveComponentOfType(MoveToCommand.class);
				//game.WriteLineToConsole("Moved to x = " + String.valueOf(position.p.getX()));



			}
		}
	}
}


