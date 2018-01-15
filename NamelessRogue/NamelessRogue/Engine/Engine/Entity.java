package Engine;

import java.util.UUID;
import java.util.Vector;

import Engine.Components.Component;
import Engine.Components.IComponent;
import Engine.Components.JustCreated;
import abstraction.IEntity;

public class Entity implements IEntity{

	UUID Id;
	public Entity(IComponent... components)
	{
		Id = UUID.randomUUID();
		for (IComponent component : components) {
			EntityManager.AddComponent(Id,component);
		}
		EntityManager.AddComponent(Id,new JustCreated());
	}
	
	public Entity()
	{
		Id = UUID.randomUUID();
		EntityManager.AddComponent(Id,new JustCreated());
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
