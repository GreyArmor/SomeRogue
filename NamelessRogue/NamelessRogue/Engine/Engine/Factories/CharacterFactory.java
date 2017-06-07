package Engine.Factories;

import java.awt.Color;

import Engine.Entity;
import Engine.Components.Drawable;
import Engine.Components.InputComponent;
import Engine.Components.Movable;
import Engine.Components.Player;
import Engine.Components.Position;

public class CharacterFactory {
	public static Entity CreateSimplePlayerCharacter()
	{
		Entity playerCharacter = new Entity();
		playerCharacter.AddComponent(new Player());
		playerCharacter.AddComponent(new InputComponent());
		playerCharacter.AddComponent(new Movable());
		playerCharacter.AddComponent(new Position());
		playerCharacter.AddComponent(new Drawable('@',Color.LIGHT_GRAY));
		
		return playerCharacter;
	}
	
	public static Entity CreateBlankNpc()
	{
		Entity playerCharacter = new Entity();
		
		playerCharacter.AddComponent(new InputComponent());
		playerCharacter.AddComponent(new Movable());
		playerCharacter.AddComponent(new Position(10,10));
		playerCharacter.AddComponent(new Drawable('D',Color.RED));
		
		return playerCharacter;
	}
	
	
	
}
