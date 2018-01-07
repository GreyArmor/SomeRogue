package Engine;

import java.util.UUID;
import java.util.Vector;

import Engine.Components.Component;
import Engine.Components.IComponent;
import abstraction.IEntity;

public class Entity implements IEntity{

	Vector<IComponent> Components;
	UUID Id;
	public Entity(IComponent... components)
	{
		Id = UUID.randomUUID();
		Components = new Vector<>();
		for (IComponent component : components) {
			Components.add(component);
		}
	}
	
	public Entity()
	{
		Id = UUID.randomUUID();
		Components = new Vector<>();
	}
	
	
	@Override
	public UUID GetID() {
		return Id;
	}

	@Override
	public <ComponentType extends IComponent> void AddComponent(ComponentType component) {
		EntityManager.AddComponent(Id,component);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ComponentType extends IComponent> ComponentType GetComponentOfType(Class<ComponentType> fType) {
		return EntityManager.GetComponent(Id,fType);
	}

	@Override
	public <ComponentType extends IComponent> void RemoveComponentOfType(Class<ComponentType> fType) {
		EntityManager.RemoveComponent(Id, fType);
	}
}
