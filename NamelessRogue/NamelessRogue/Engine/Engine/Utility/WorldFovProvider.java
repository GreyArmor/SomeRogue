package Engine.Utility;

import Engine.Components.ChunksAndTiles.Tile;
import Engine.Components.Rendering.ConsoleCamera;
import Engine.Components.Rendering.Screen;
import abstraction.IChunkProvider;
import com.jogamp.nativewindow.util.Point;
import data.GameSettings;
import rlforj.los.ILosBoard;

public class WorldFovProvider implements ILosBoard {

    private IChunkProvider world;
    private Screen screen;
    private ConsoleCamera camera;
    BoundingBox boundingBox;
    public WorldFovProvider(IChunkProvider world, Screen screen, ConsoleCamera camera, GameSettings settings)
    {

        this.world = world;
        this.screen = screen;
        this.camera = camera;
        int camX = camera.getPosition().getX();
        int camY = camera.getPosition().getY();
        boundingBox = new BoundingBox(camera.getPosition(),new Point(settings.getWidth()+camX,settings.getHeight()+camY));

    }

    @Override
    public boolean contains(int x, int y) {
        return boundingBox.isPointInside(x,y);
    }

    @Override
    public boolean isObstacle(int x, int y) {
        Tile tileToDraw = world.getTile(x, y);
        return !tileToDraw.getPassable() ;
    }

    @Override
    public void visit(int x, int y) {
        Point screenPoint = camera.PointToScreen(x, y);
        screen.ScreenBuffer[screenPoint.getX()][screenPoint.getY()].isVisible=true;
    }
}
