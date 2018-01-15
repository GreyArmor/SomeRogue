package Engine.Systems;

import Engine.Components.Interaction.ChangeSwitchStateCommand;
import Engine.Components.Interaction.MoveToCommand;
import Engine.Components.Interaction.SimpleSwitch;
import Engine.Components.Physical.Position;
import abstraction.IEntity;
import abstraction.ISystem;
import shell.Game;

public class SwitchSystem implements ISystem {
    @Override
    public void Update(long gameTime, Game game) {
        for (IEntity entity : game.GetEntities()) {
            ChangeSwitchStateCommand command = entity.GetComponentOfType(ChangeSwitchStateCommand.class);
            if(command!=null){
                command.getTarget().setSwitchActive(command.isActive());
                entity.RemoveComponentOfType(ChangeSwitchStateCommand.class);
            }
        }
    }
}
