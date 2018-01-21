package Engine.Components.Interaction;

import Engine.Components.Component;
import abstraction.IEntity;

public class AttackCommand extends Component {
    private IEntity source;
    private IEntity target;

    public AttackCommand(IEntity source, IEntity target)
    {
        this.source = source;
        this.target = target;
    }

    public void setSource(IEntity source) {
        this.source = source;
    }

    public IEntity getSource() {
        return source;
    }

    public void setTarget(IEntity target) {
        this.target = target;
    }

    public IEntity getTarget() {
        return target;
    }
}
