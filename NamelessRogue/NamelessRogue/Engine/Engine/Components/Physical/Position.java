package Engine.Components.Physical;

		import Engine.Components.Component;
		import com.jogamp.nativewindow.util.Point;

public class Position extends Component {
	public Position(int x,int y)
	{
		p = new Point(x,y);
	}

	public Position()
	{
		p = new Point();
	}
	public Point p;
}
