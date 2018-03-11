package Engine.Factories;

import com.jogamp.nativewindow.util.Point;

import Engine.Infrastructure.Entity;
import Engine.Components.Rendering.ConsoleCamera;
import Engine.Components.Rendering.Screen;
import data.GameSettings;

public class RenderFactory {
	
	public static Entity CreateViewport(GameSettings settings)
	{
		Entity viewport = new Entity();
		ConsoleCamera camera = new ConsoleCamera(new Point(0,0));
		Screen screen = new Screen(settings.getWidth(),settings.getHeight());
		viewport.AddComponent(camera);
		viewport.AddComponent(screen);
		return viewport;
		
	}
}
