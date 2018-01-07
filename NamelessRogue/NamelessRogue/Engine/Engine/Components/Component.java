package Engine.Components;

import java.util.UUID;

public class Component implements IComponent {
    private UUID Id;
    public Component()
    {
        Id = UUID.randomUUID();
    }

    public UUID getId() {
        return Id;
    }
}
