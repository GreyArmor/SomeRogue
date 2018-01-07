package Engine.Utility;

import Engine.Chunk;
import com.jogamp.nativewindow.util.Point;

public class BoundingBox {
	private Point min;
	private Point max;
	Chunk leaf;
	Point getMin() {
		return min;
	}
	void setMin(Point min) {
		this.min = min;
	}
	Point getMax() {
		return max;
	}
	void setMax(Point max) {
		this.max = max;
	}
	
	public BoundingBox(Point min, Point max)
	{
		this.min = min;
		this.max = max;
	}
	
	public boolean isPointInside(int x, int y) {
		int bottomLeftX = min.getX();
		int bottomLeftY = min.getY();
		int topRightX = max.getX();
		int topRightY = max.getY();
		
		if(x>=bottomLeftX && x<topRightX&&y>=bottomLeftY&&y<topRightY){
			return true;
		}
		else{
			return false;
		}
	}
}
