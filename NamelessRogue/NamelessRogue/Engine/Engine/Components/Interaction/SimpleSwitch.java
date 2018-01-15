package Engine.Components.Interaction;

import Engine.Components.Component;

public class SimpleSwitch extends Component {

    public SimpleSwitch(boolean switchActiv)
    {
        this.switchActive = switchActiv;
    }
    private boolean switchActive;

    public void setSwitchActive(boolean switchActive) {
        this.switchActive = switchActive;
    }

    public boolean isSwitchActive() {
        return switchActive;
    }
}
