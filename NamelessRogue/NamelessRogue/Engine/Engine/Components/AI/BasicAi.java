package Engine.Components.AI;

import Engine.Components.Component;
import Engine.Components.Physical.Position;
import com.jogamp.nativewindow.util.Point;

import java.util.ArrayList;

public class BasicAi extends Component {
    private ArrayList<Point> route;

    private BasicAiStates state;
    public BasicAi() {
        route = new ArrayList<>();
        state = BasicAiStates.Idle;
    }

    public void setRoute(ArrayList<Point> route) {
        this.route = route;
    }

    public ArrayList<Point> getRoute() {
        return route;
    }

    public void setState(BasicAiStates state) {
        this.state = state;
    }

    public BasicAiStates getState() {
        return state;
    }
}
