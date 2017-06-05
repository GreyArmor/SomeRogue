package abstraction;

import java.sql.Time;
import java.util.UUID;
import java.util.Vector;

public interface IEntity {
	public UUID GetID();
	public <ComponentType> ComponentType GetComponentOfType(Class<ComponentType> fType);
	public void AddComponent(Object component);
	public <ComponentType> void RemoveComponentOfType(Class<ComponentType> fType);
}
