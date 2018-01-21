package Engine.Systems;

import Engine.Components.Interaction.AttackCommand;
import Engine.Components.JustCreated;
import Engine.Components.UI.Description;
import Engine.Utility.DamageHelper;
import abstraction.IEntity;
import abstraction.ISystem;
import shell.Game;

import java.util.Random;

public class CombatSystem implements ISystem {
    @Override
    public void Update(long gameTime, Game game) {
        for (IEntity entity : game.GetEntities()){
            AttackCommand ac = entity.GetComponentOfType(AttackCommand.class);
            if (ac!=null)
            {
                Random r = new Random();
                //TODO: attack damage based on stats, equipment etc.
                int damage = r.nextInt(5) + 5;
                DamageHelper.ApplyDamage(ac.getTarget(),ac.getSource(),damage);

                Description targetDescription = ac.getTarget().GetComponentOfType(Description.class);
                Description sourceDescription = ac.getSource().GetComponentOfType(Description.class);
                if(targetDescription!=null&&sourceDescription!=null) {
                    game.WriteLineToConsole(sourceDescription.Name + " deals " + String.valueOf(damage) + " damage to " +targetDescription.Name);
                }

                entity.RemoveComponentOfType(AttackCommand.class);
            }
        }
    }
}
