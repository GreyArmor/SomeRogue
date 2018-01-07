package Engine.Factories;

import java.awt.Color;

import Engine.Components.Interaction.InputComponent;
import Engine.Components.Interaction.Movable;
import Engine.Components.Interaction.Player;
import Engine.Components.ItemComponents.ItemsHolder;
import Engine.Components.Physical.Position;
import Engine.Components.Rendering.Drawable;
import Engine.Components.Stats.Health;
import Engine.Components.UI.Description;
import Engine.Constants;
import Engine.Entity;

public class CharacterFactory {
	public static Entity CreateSimplePlayerCharacter()
	{
		Entity playerCharacter = new Entity();
		playerCharacter.AddComponent(new Player());
		playerCharacter.AddComponent(new InputComponent());
		playerCharacter.AddComponent(new Movable());
		//for debug;
		playerCharacter.AddComponent(new Position(109* Constants.ChunkSize,307*Constants.ChunkSize));
		playerCharacter.AddComponent(new Drawable('@', new Engine.Utility.Color(0.7,0.7,0.7)));
		playerCharacter.AddComponent(new Description("Player",""));
		playerCharacter.AddComponent(new ItemsHolder());
		playerCharacter.AddComponent(new Health(100,0,100));
		
		return playerCharacter;
	}
	
	public static Entity CreateBlankNpc()
	{
		Entity playerCharacter = new Entity();
		
		playerCharacter.AddComponent(new InputComponent());
		playerCharacter.AddComponent(new Movable());
		playerCharacter.AddComponent(new Position(10,10));
		playerCharacter.AddComponent(new Drawable('D',new Engine.Utility.Color(1,0,0)));

		return playerCharacter;
	}
	
	
	
}
