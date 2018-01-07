package Engine.Components.UI;

import Engine.Components.Component;

import java.util.ArrayList;
import java.util.List;

public class SimpleUiElement extends Component {
    public SimpleUiElement(SimpleUiElement parent)
    {
        Parent = parent;
    }
    public SimpleUiElement Parent;
    public ArrayList<SimpleUiElement> Children = new ArrayList<SimpleUiElement> ();
}
