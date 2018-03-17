package Engine.Components.Rendering;

import Engine.Components.Component;
import Engine.Utility.Color;


public class Drawable extends Component {
	
	public Drawable(char representation, Color charColor)
	{
		Representation=representation;
		CharColor = charColor;
	}

	private boolean visible = true;

	private char Representation;
	private Engine.Utility.Color CharColor;

	public char getRepresentation() {
		return Representation;
	}

	public void setRepresentation(char representation) {
		Representation=representation;
	}

	public void setCharColor(Color charColor)
	{
		CharColor = charColor;
	}

	public Color getCharColor() {
		return CharColor;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}
}
