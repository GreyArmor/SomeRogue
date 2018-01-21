package Engine.Utility;

import Engine.Components.Status.Damage;
import abstraction.IEntity;

public class DamageHelper {
    public static void ApplyDamage(IEntity target, IEntity source, int damage)
    {
        Damage d = target.GetComponentOfType(Damage.class);
        if(d==null)
        {
            d = new Damage(source,damage);
            target.AddComponent(d);
        }
        else
        {
            d.setDamage(d.getDamage()+damage);
        }
    }
}
