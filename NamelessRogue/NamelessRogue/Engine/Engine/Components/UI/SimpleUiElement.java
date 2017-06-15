package Engine.Components.UI;

import java.util.ArrayList;
import java.util.List;

public class SimpleUiElement {

    //List

    public SimpleUiElement(SimpleUiElement parent)
    {
        Parent = parent;
    }
    public SimpleUiElement Parent;
    public ArrayList<SimpleUiElement> Children = new ArrayList<SimpleUiElement> ();
}
