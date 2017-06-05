package abstraction;

import com.jogamp.nativewindow.util.Point;

import Engine.BoundingBox;

public interface IBoundsProvider {
	public BoundingBox getBoundingBox();
	public void setBoundingBox(BoundingBox boundingBox);
	public boolean isPointInside(Point point);
	public boolean isPointInside(int x, int y);
}
