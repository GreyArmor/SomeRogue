package Engine.Components.Status;

import Engine.Components.Component;
import abstraction.IEntity;

public class Damage extends Component {
    private IEntity source;
    private int damage;

    public Damage(IEntity source, int damage)
    {
        this.source = source;
        this.damage = damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setSource(IEntity source) {
        this.source = source;
    }

    public IEntity getSource() {
        return source;
    }
}
