package Engine.Factories;

import java.awt.Color;

import Engine.Components.Interaction.InputComponent;
import Engine.Components.Interaction.Movable;
import Engine.Components.Interaction.Player;
import Engine.Components.ItemComponents.ItemsHolder;
import Engine.Components.Physical.OccupiesTile;
import Engine.Components.Physical.Position;
import Engine.Components.Rendering.Drawable;
import Engine.Components.Stats.Health;
import Engine.Components.UI.Description;
import Engine.Constants;
import Engine.Entity;

public class CharacterFactory {
	public static Entity CreateSimplePlayerCharacter(int x,int y)
	{
		Entity playerCharacter = new Entity();
		playerCharacter.AddComponent(new Player());
		playerCharacter.AddComponent(new InputComponent());
		playerCharacter.AddComponent(new Movable());
		//for debug;
		playerCharacter.AddComponent(new Position(x,y));
		playerCharacter.AddComponent(new Drawable('@', new Engine.Utility.Color(0.9,0.9,0.9)));
		playerCharacter.AddComponent(new Description("Player",""));
		playerCharacter.AddComponent(new ItemsHolder());
		playerCharacter.AddComponent(new Health(100,0,100));
		playerCharacter.AddComponent(new OccupiesTile());
		return playerCharacter;
	}
	
	public static Entity CreateBlankNpc()
	{
		Entity playerCharacter = new Entity();
		
		playerCharacter.AddComponent(new InputComponent());
		playerCharacter.AddComponent(new Movable());
		playerCharacter.AddComponent(new Position(10,10));
		playerCharacter.AddComponent(new Drawable('D',new Engine.Utility.Color(1,0,0)));
		playerCharacter.AddComponent(new OccupiesTile());
		return playerCharacter;
	}
	
	
	
}
