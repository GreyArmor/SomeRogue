package Engine.Factories;

import Engine.Components.AI.NonPlayerCharacter.Character;
import Engine.Components.Interaction.Cursor;
import Engine.Components.Interaction.InputComponent;
import Engine.Components.Interaction.Player;
import Engine.Components.ItemComponents.ItemsHolder;
import Engine.Components.Physical.OccupiesTile;
import Engine.Components.Physical.Position;
import Engine.Components.Rendering.Drawable;
import Engine.Components.Rendering.LineToPlayer;
import Engine.Components.UI.Description;
import Engine.Infrastructure.Entity;
import abstraction.IEntity;

public class GameInitializer {

    public static IEntity CreateCursor()
    {
        Entity cursor = new Entity();
        cursor.AddComponent(new Cursor());
        cursor.AddComponent(new Position(0,0));
        Drawable dr = new Drawable('X', new Engine.Utility.Color(0.9,0.9,0.9));
        dr.setVisible(false);
        cursor.AddComponent(dr);
        cursor.AddComponent(new InputComponent());
        cursor.AddComponent(new LineToPlayer());
        return cursor;
    }
}
