package Engine.Components.ItemComponents;

import Engine.Components.Component;

import java.util.UUID;

public class Item extends Component {
    private UUID HolderId;
    private UUID entityId;

    public Item(UUID entityId)
    {
        this.entityId = entityId;
    }
    public UUID getHolderId() {
        return HolderId;
    }

    public void setHolderId(UUID holderId) {
        HolderId = holderId;
    }

    public UUID getEntityId() {
        return entityId;
    }
}
