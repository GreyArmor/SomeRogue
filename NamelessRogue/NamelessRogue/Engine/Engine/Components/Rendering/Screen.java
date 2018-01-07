package Engine.Components.Rendering;


import java.awt.Color;
import java.awt.event.KeyEvent;

import Engine.Components.Component;
import data.GameSettings;
import shell.Game;

public class Screen extends Component {
	public ScreenTile[][] ScreenBuffer;
	public Screen(int width,int height) {
		ScreenBuffer = new ScreenTile[width][height];
		for (int i = 0; i <width;i++)
		{
			for (int j = 0;j<height;j++)
			{
				ScreenBuffer[i][j]= new ScreenTile();
			}
		}
	}
}
