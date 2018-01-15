package Engine.Components.Interaction;

import Engine.Components.Component;
import com.jogamp.nativewindow.util.Point;


public class MoveToCommand extends Component {

    public MoveToCommand(int x, int y) {
        p = new Point(x, y);
    }

    public MoveToCommand() {
        p = new Point();
    }

    public Point p;
}
