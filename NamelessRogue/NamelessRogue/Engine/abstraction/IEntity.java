package abstraction;

import Engine.Components.Component;
import Engine.Components.IComponent;

import java.sql.Time;
import java.util.UUID;
import java.util.Vector;

public interface IEntity {
	public UUID GetID();
	public <ComponentType  extends IComponent> ComponentType GetComponentOfType(Class<ComponentType> fType);
	public <ComponentType extends IComponent> void AddComponent(ComponentType component);
	public <ComponentType  extends IComponent> void RemoveComponentOfType(Class<ComponentType> fType);
}
