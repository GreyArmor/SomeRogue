package Engine.Components;

import java.util.ArrayList;
import java.util.List;

import Engine.Input.Intent;

public class InputComponent {
	
	public InputComponent()
	{
		Intents = new ArrayList<>();
	}
	public List<Intent> Intents;
}
