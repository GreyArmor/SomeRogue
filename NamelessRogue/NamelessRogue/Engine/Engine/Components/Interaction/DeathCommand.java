package Engine.Components.Interaction;

import Engine.Components.Component;
import abstraction.IEntity;

public class DeathCommand extends Component {
    private IEntity toKill;

    public DeathCommand(IEntity toKill)
    {
        this.toKill = toKill;
    }

    public void setToKill(IEntity toKill) {
        this.toKill = toKill;
    }

    public IEntity getToKill() {
        return toKill;
    }
}
