package Engine.Components.Interaction;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Engine.Components.Component;
import Engine.Input.Intent;

public class InputComponent extends Component {
	
	public InputComponent()
	{
		Intents = new ArrayList<>();
	}
	public List<Intent> Intents;
	public UUID Target;
}
