package Engine.Factories;

import Engine.Infrastructure.Entity;
import Engine.Components.Interaction.InputComponent;
import abstraction.IEntity;

public class InputHandlingFactory {
	public static IEntity CreateInput()
	{
		Entity input = new Entity();
		input.AddComponent(new InputComponent());
		return input;
	}
}

