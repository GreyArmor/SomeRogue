package Engine.Components;

import com.jogamp.nativewindow.util.Point;

public class ConsoleCamera  {
	private Point position;

	//position is a bottom left corner of camera
	public ConsoleCamera(Point position)
	{
		this.setPosition(position);
	}
	
	
	public Point PointToScreen(Point p)	{
		Point result = new Point();
		
		int cameraX = position.getX();
		int cameraY = position.getY();
		int worldX = p.getX();
		int worldY = p.getY();
		int screenX = worldX - cameraX;
		int screenY = worldY - cameraY;
		
		result.setX(screenX);
		result.setY(screenY);
		return result;
	}
	
	
	public Point PointToScreen(int x,int y)	{
		return PointToScreen(new Point(x,y));
	}


	public Point getPosition() {
		return position;
	}


	public void setPosition(Point position) {
		this.position = position;
	}
	
	public void setPosition(int x, int y)
	{
		setPosition(new Point(x,y));
	}
}
