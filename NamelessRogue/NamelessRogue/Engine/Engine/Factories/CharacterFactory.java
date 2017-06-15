package Engine.Factories;

import java.awt.Color;

import Engine.Components.*;
import Engine.Entity;

public class CharacterFactory {
	public static Entity CreateSimplePlayerCharacter()
	{
		Entity playerCharacter = new Entity();
		playerCharacter.AddComponent(new Player());
		playerCharacter.AddComponent(new InputComponent());
		playerCharacter.AddComponent(new Movable());
		playerCharacter.AddComponent(new Position(0,0));
		playerCharacter.AddComponent(new Drawable('@',Color.LIGHT_GRAY));
		playerCharacter.AddComponent(new Description("Player",""));
		playerCharacter.AddComponent(new Health());
		
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
