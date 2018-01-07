package Engine.Input;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class KeyIntentTraslator {
	public static List<Intent> Translate(int keyCode)
	{
		List<Intent> result = new ArrayList<Intent>();
		
		//TODO: add dictionary for actions, based on game config files
		    switch(keyCode) { 
		        case KeyEvent.VK_UP:
				case KeyEvent.VK_NUMPAD8:
		        	result.add(Intent.MoveUp);
		            break;
				case KeyEvent.VK_NUMPAD2:
		        case KeyEvent.VK_DOWN:
		        	result.add(Intent.MoveDown);
		            break;
				case KeyEvent.VK_NUMPAD4:
		        case KeyEvent.VK_LEFT:
		        	result.add(Intent.MoveLeft);
		            break;
				case KeyEvent.VK_NUMPAD6:
		        case KeyEvent.VK_RIGHT :
		        	result.add(Intent.MoveRight);
		            break;
				case KeyEvent.VK_NUMPAD7:
					result.add(Intent.MoveTopLeft);
					break;
				case KeyEvent.VK_NUMPAD9:
					result.add(Intent.MoveTopRight);
					break;
				case KeyEvent.VK_NUMPAD1:
					result.add(Intent.MoveBottomLeft);
					break;
				case KeyEvent.VK_NUMPAD3:
					result.add(Intent.MoveBottomRight);
					break;
		     }	
		
		return result;
		
	}
}
