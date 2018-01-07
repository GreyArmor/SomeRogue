package Engine.Components.Rendering;

import Engine.Components.Component;

import java.awt.Color;

public class Drawable extends Component {
	
	public Drawable(char representation, Color charColor)
	{
		Representation=representation;
		CharColor = charColor;
	}
	
	public char Representation;
	public Color CharColor;
}
