package Engine.Systems;

import Engine.Components.Interaction.InputComponent;
import Engine.Components.ItemComponents.Item;
import Engine.Components.ItemComponents.ItemsHolder;
import Engine.EntityManager;
import Engine.Input.Intent;
import abstraction.IEntity;
import abstraction.ISystem;
import shell.Game;

public class InventorySystem implements ISystem {
    @Override
    public void Update(long gameTime, Game game) {
        for (IEntity entity : game.GetEntities()) {
            ItemsHolder itemsHolder = entity.GetComponentOfType(ItemsHolder.class);
            InputComponent inputComponent = entity.GetComponentOfType(InputComponent.class);
            if (itemsHolder != null && inputComponent != null) {
                for (Intent intent : inputComponent.Intents) {
                    switch (intent) {
                        case DropItem: {
                            Item item = EntityManager.GetComponent(inputComponent.Target);
                            item.setHolderId(null);
                        }
                            break;
                        case PlaceItem: {
                            Item item = EntityManager.GetComponent(inputComponent.Target);
                            item.setHolderId(itemsHolder.getId());
                        }
                            break;
                        default:
                            break;
                    }
                }
            }

        }
    }
}
