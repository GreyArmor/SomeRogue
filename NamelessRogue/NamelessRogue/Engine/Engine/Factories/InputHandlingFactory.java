package Engine.Factories;

import Engine.Entity;
import Engine.Components.InputComponent;
import abstraction.IEntity;

public class InputHandlingFactory {
	public static IEntity CreateInput()
	{
		Entity input = new Entity();
		input.AddComponent(new InputComponent());
		return input;
	}
}

