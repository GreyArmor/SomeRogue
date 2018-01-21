package Engine.Systems;

import Engine.Components.Interaction.DeathCommand;
import Engine.Components.Stats.Health;
import Engine.Components.Status.Damage;
import abstraction.IEntity;
import abstraction.ISystem;
import shell.Game;

public class DamageHandlingSystem implements ISystem {
    @Override
    public void Update(long gameTime, Game game) {
        for (IEntity entity : game.GetEntities()) {
            Damage damage = entity.GetComponentOfType(Damage.class);
            if (damage != null){
                Health health = entity.GetComponentOfType(Health.class);
                if (health != null) {
                    health.setValue(health.getValue() - damage.getDamage());
                    if (health.getValue() < health.getMinValue()) {
                        entity.AddComponent(new DeathCommand(entity));
                    }
                }
                entity.RemoveComponentOfType(Damage.class);
            }
        }
    }
}
