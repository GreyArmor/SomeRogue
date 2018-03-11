package Engine.Factories;

import Engine.Components.ItemComponents.Item;
import Engine.Components.Physical.Position;
import Engine.Components.Rendering.Drawable;
import Engine.Infrastructure.Constants;
import Engine.Infrastructure.Entity;
import Engine.Utility.Color;

public  class ItemFactory {

    public static Entity CreateItem()
    {
        Entity item = new Entity();
        item.AddComponent(new Item(item.GetId()));
        item.AddComponent(new Position(10,10));
        item.AddComponent(new Drawable('I',new Color(1f,0,0)));
        //for debug;
        item.AddComponent(new Position(109* Constants.ChunkSize + 3,307*Constants.ChunkSize + 3));
        return item;

    }

}
