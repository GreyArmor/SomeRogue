package Engine.Components.Rendering;

import Engine.Components.Component;
import Engine.Utility.Color;


/**
 * Created by Admin on 16.06.2017.
 */
public class ScreenTile extends Component {
    public ScreenTile()
    {
        Char=' ';
        CharColor = new Color(0,0,0,0);
        BackGroundColor = new Color(0,0,0,0);
    }
    public Character Char;
    public  Engine.Utility.Color CharColor;
    public Engine.Utility.Color BackGroundColor;
    public boolean isVisible;
}
