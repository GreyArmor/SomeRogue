package Engine.Components.Environment;

import Engine.Components.Component;
import abstraction.IEntity;

import java.util.ArrayList;

public class Building extends Component {
    private ArrayList<IEntity> buildingParts = new ArrayList<>();

    public void setBuildingParts(ArrayList<IEntity> buildingParts) {
        this.buildingParts = buildingParts;
    }

    public ArrayList<IEntity> getBuildingParts() {
        return buildingParts;
    }
}
