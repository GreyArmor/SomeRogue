package Engine.Systems;

import java.sql.Time;

import Engine.Components.World.ChunkData;
import Engine.TerrainTypes;
import Engine.Tile;
import abstraction.IWorldProvider;

import Engine.Components.Interaction.InputComponent;
import Engine.Components.Physical.Position;
import Engine.Input.Intent;
import abstraction.IEntity;
import abstraction.ISystem;
import shell.Game;

public class MovementSystem implements ISystem {

	@Override
	public void Update(Time gameTime, Game game) {
		for (IEntity entity : game.GetEntities()) {
			Position position = entity.GetComponentOfType(Position.class);
			InputComponent inputComponent = entity.GetComponentOfType(InputComponent.class);

			if(position != null && inputComponent!=null)
			{
				Position newPosition = new Position(position.p.getX(),position.p.getY());
				for (Intent intent : inputComponent.Intents) {
					 switch(intent) { 
				        case MoveUp:
							newPosition.p.setY(position.p.getY()-1);
							game.WriteLineToConsole("Moved up");
				            break;
				        case MoveDown:
							newPosition.p.setY(position.p.getY()+1);
				            break;
				        case MoveLeft:
							newPosition.p.setX(position.p.getX()-1);
				            break;
				        case MoveRight:
							newPosition.p.setX(position.p.getX()+1);
				            break;
						 case MoveTopLeft:
							newPosition.p.setY(position.p.getY()-1);
							newPosition.p.setX(position.p.getX()-1);
						 	break;
						 case MoveTopRight:
							 newPosition.p.setY(position.p.getY()-1);
							 newPosition.p.setX(position.p.getX()+1);
							 break;
						 case MoveBottomLeft:
							 newPosition.p.setY(position.p.getY()+1);
							 newPosition.p.setX(position.p.getX()-1);
							 break;
						 case MoveBottomRight:
							 newPosition.p.setY(position.p.getY()+1);
							 newPosition.p.setX(position.p.getX()+1);
						 	break;
				     }		
				}
				IEntity worldEntity = game.GetEntityByComponentClass(ChunkData.class);
				IWorldProvider worldProvider = null;
				if(worldEntity!=null)
				{
					worldProvider = worldEntity.GetComponentOfType(ChunkData.class);
				}
				Tile tile = worldProvider.getTile(newPosition.p.getX(),newPosition.p.getY());
				TerrainTypes type = tile.getTerrainType();
//				if(type==TerrainTypes.Nothingness )//|| type== TerrainTypes.Water)
//				{
//
//				}
//				else
//				{
					position.p.setX(newPosition.p.getX());
					position.p.setY(newPosition.p.getY());
//				}


			}
		}
	}
}


