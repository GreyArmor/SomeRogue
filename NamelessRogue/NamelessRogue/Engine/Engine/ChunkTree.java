package Engine;

import com.jogamp.nativewindow.util.Point;

public class ChunkTree {
	ChunkTree children;
	private BoundingBox boundingBox;
	private Chunk leaf;
	BoundingBox getBoundingBox() {
		return boundingBox;
	}
	void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

}
