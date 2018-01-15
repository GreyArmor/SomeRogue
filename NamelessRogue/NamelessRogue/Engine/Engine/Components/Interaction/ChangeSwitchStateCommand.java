package Engine.Components.Interaction;

import Engine.Components.Component;

public class ChangeSwitchStateCommand extends Component {

    private SimpleSwitch target;
    private boolean active;

    public ChangeSwitchStateCommand(SimpleSwitch target, boolean active) {
        this.target = target;
        this.active = active;
    }


    public SimpleSwitch getTarget() {
        return target;
    }

    public boolean isActive() {
        return active;
    }
}
