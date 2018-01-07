package Engine.Components.Rendering;

import Engine.Components.Component;

import java.awt.*;

/**
 * Created by Admin on 16.06.2017.
 */
public class ScreenTile extends Component {
    public ScreenTile()
    {
        Char=' ';
        CharColor = Color.BLACK;
        BackGroundColor = Color.BLACK;
    }
    public Character Char;
    public Color CharColor;
    public Color BackGroundColor;
}
