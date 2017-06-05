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
		        	result.add(Intent.MoveUp);
		            break;
		        case KeyEvent.VK_DOWN:
		        	result.add(Intent.MoveDowm);
		            break;
		        case KeyEvent.VK_LEFT:
		        	result.add(Intent.MoveLeft);
		            break;
		        case KeyEvent.VK_RIGHT :
		        	result.add(Intent.MoveRight);
		            break;
		     }	
		
		return result;
		
	}
}
