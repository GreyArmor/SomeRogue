package Engine.Components.ItemComponents;

import Engine.Components.Component;

import java.util.UUID;

public class Item extends Component {
    private UUID HolderId;

    public Item()
    {
    }
    public UUID getHolderId() {
        return HolderId;
    }

    public void setHolderId(UUID holderId) {
        HolderId = holderId;
    }
}
