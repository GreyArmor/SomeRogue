package view;

import com.jogamp.nativewindow.util.Point;

import Engine.GameScene;

public class ConsoleCamera {
	private Point position;
	private GameScene scene;

	//position is a bottom left corner of camera
	public ConsoleCamera(GameScene scene, Point position)
	{
		this.position = position;
		this.scene = scene;
	}
	
	
	public Point PointToScreen(Point p)	{
		Point result = new Point();
		result.setX(Math.abs(Math.abs(position.getX()) - Math.abs(p.getX())));
		result.setY(Math.abs(Math.abs(position.getY()) - Math.abs(p.getY())));
		return result;
	}
	public Point PointToScreen(int x,int y)	{
		return PointToScreen(new Point(x,y));
	}
	
	

}
