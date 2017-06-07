package Engine.Components.Rendering;


import java.awt.Color;
import java.awt.event.KeyEvent;

import data.GameSettings;
import shell.Game;

public class Screen {
		public char characterBuffer[][];
	public Color characterColorBuffer[][];
	public Screen(int width,int height) {	
		characterBuffer = new char[width][height];
		characterColorBuffer = new Color[width][height];
	}
}
