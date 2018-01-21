package Engine;

import Engine.Components.IComponent;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.*;

public class EntityManager {
    static {
        Components = new HashMap<>();
    }

    public static <ComponentType extends IComponent> void AddComponent(UUID entityID, ComponentType component) {
        Map<UUID, IComponent> componentsOfType = Components.get(component.getClass());
        if (componentsOfType == null) {
            componentsOfType = new Hashtable();
            Components.put(component.getClass(), componentsOfType);
        }
        componentsOfType.put(entityID, component);
    }

    public static <ComponentType extends IComponent> void RemoveComponent(UUID entityID, Class<ComponentType> fType) {
        Map<UUID, IComponent> componentsOfType = Components.get(fType);
        if (componentsOfType != null) {
            componentsOfType.remove(entityID);
        }
    }

    public static <ComponentType extends IComponent> ComponentType GetComponent(UUID entityID, Class<ComponentType> fType) {
        Map<UUID, IComponent> componentsOfType = Components.get(fType);
        if (componentsOfType != null) {
            return (ComponentType) componentsOfType.get(entityID);
        }
        return null;
    }

    public static <ComponentType extends IComponent> ComponentType GetComponent(UUID componentId) {
        for (Map<UUID, IComponent> dict : Components.values()) {
            Optional component = dict.values().stream().findFirst().filter(x -> x.getId() == componentId);
            if (component.isPresent()) {
                return (ComponentType) component.get();
            }
        }
        return null;
    }

    public static Map<Type, Map<UUID, IComponent>> Components;

    public static void RemoveEntity(UUID uuid) {
        for (Map<UUID, IComponent> dict : Components.values()) {
            dict.remove(uuid);
        }
    }
}
