package Engine.Components;

import com.jogamp.nativewindow.util.Point;

public class Position {
	public Position(int x,int y)
	{
		p = new Point(x,y);
	}
	
	public Position()
	{
		p = new Point();
	}
	public Point p;
}
