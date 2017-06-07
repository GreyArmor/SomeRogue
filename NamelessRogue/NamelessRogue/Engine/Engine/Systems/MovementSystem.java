package Engine.Systems;

import java.sql.Time;

import com.jogamp.nativewindow.util.Point;

import Engine.Components.ConsoleCamera;
import Engine.Components.InputComponent;
import Engine.Components.Position;
import Engine.Components.Rendering.Screen;
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
				for (Intent intent : inputComponent.Intents) {
					 switch(intent) { 
				        case MoveUp:
				        	position.p.setY(position.p.getY()+1);
				            break;
				        case MoveDowm:
				        	position.p.setY(position.p.getY()-1);
				            break;
				        case MoveLeft:
				        	position.p.setX(position.p.getX()-1);
				            break;
				        case MoveRight:
				        	position.p.setX(position.p.getX()+1);
				            break;
				     }		
				}
			}
		}
	}
}


