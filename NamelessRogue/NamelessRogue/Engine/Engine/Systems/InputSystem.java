package Engine.Systems;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Engine.Components.Interaction.InputComponent;
import Engine.Components.Interaction.InputReceiver;
import Engine.Components.Interaction.Player;
import Engine.Input.KeyIntentTraslator;
import abstraction.IEntity;
import abstraction.ISystem;
import shell.Game;

public class InputSystem implements ISystem {

	List<KeyEvent> pressedKeys;
	
	public InputSystem() {
		pressedKeys = new ArrayList<>();
	}
	long currentgmatime = 0;
	private long previousGametimeForMove = 0;
	@Override
	public void Update(long gameTime, Game game){
		if (gameTime - previousGametimeForMove > 60) {
			previousGametimeForMove = gameTime;
			for (IEntity entity : game.GetEntities()) {
				InputComponent inputComponent = entity.GetComponentOfType(InputComponent.class);
				InputReceiver receiver = entity.GetComponentOfType(InputReceiver.class);
				if (receiver != null && inputComponent != null) {
					for (KeyEvent keyEvent : pressedKeys) {
						inputComponent.Intents.addAll(KeyIntentTraslator.Translate(keyEvent.getKeyCode()));
					}
				}
			}
		}
	}		

	public void keyPressed(KeyEvent e) {
		pressedKeys = new ArrayList<>();
		pressedKeys.add(e);		
	}

	public void keyReleased(KeyEvent e) {
		Optional<KeyEvent> key = pressedKeys.stream().filter(x->x.getKeyCode()==e.getKeyCode()).findFirst();
		if(key.isPresent())
		{
			pressedKeys.remove(key.get());
		}
	}

}
