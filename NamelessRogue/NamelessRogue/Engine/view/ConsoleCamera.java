package view;

import com.jogamp.nativewindow.util.Point;

import Engine.GameScene;

public class ConsoleCamera {
	private Point position;
	private GameScene scene;

	//position is a bottom left corner of camera
	public ConsoleCamera(GameScene scene, Point position)
	{
		this.setPosition(position);
		this.scene = scene;
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
	
	

}
