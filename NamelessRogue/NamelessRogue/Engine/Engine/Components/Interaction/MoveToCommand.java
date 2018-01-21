package Engine.Components.Interaction;

import Engine.Components.Component;
import abstraction.IEntity;
import com.jogamp.nativewindow.util.Point;


public class MoveToCommand extends Component {

    private IEntity entityToMove;

    public MoveToCommand(int x, int y, IEntity entityToMove) {
        this.entityToMove = entityToMove;
        p = new Point(x, y);
    }

    public MoveToCommand() {
        p = new Point();
    }

    public Point p;

    public IEntity getEntityToMove() {
        return entityToMove;
    }
}
