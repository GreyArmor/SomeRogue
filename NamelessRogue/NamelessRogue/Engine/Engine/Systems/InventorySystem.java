package Engine.Systems;

import Engine.Components.Interaction.InputComponent;
import Engine.Components.ItemComponents.ItemsHolder;
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
//                            Item item = EntityManager.GetComponent(inputComponent.Target);
//                            item.setHolderId(null);
//                            itemsHolder.getItems().remove(item);
                        }
                            break;
                        case PlaceItem: {
//                            Item item = EntityManager.GetComponent(inputComponent.Target);
//                            item.setHolderId(itemsHolder.getId());
//                            itemsHolder.getItems().add(item);
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
