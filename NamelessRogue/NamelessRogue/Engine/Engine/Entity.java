package Engine;

import java.util.UUID;
import java.util.Vector;

import abstraction.IEntity;

public class Entity implements IEntity{

	Vector<Object> Components;
	UUID Id;
	public Entity(Object... components)
	{
		Id = UUID.randomUUID();
		Components = new Vector<>();
		for (Object component : components) {
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
	public void AddComponent(Object component) {
		Components.add(component);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <ComponentType> ComponentType GetComponentOfType(Class<ComponentType> fType) {
		for (Object component : Components) {
			Boolean foundComponent = component.getClass() == fType; 
			if(foundComponent)
			{
				return (ComponentType)component;
			}
		}
		return null;
	}

	@Override
	public <ComponentType> void RemoveComponentOfType(Class<ComponentType> fType) {
		for (Object component : Components) {
			Boolean foundComponent = component.getClass() == fType; 
			if(foundComponent)
			{
				Components.remove(component);
			}
		}
		
	}
}
