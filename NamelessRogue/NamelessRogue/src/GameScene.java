import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import com.jogamp.opengl.util.awt.TextRenderer;

public class GameScene extends Scene {

	Font font = null;
	TextRenderer textRenderer;
	public GameScene(Game game) {
		super(game);
		
		for(int i = 0;i<super.buferSizeColumns;i++)
		{
			for(int j = 0;j<super.bufferSizeRows;j++)
			{
				if(i==0||j==0||i == super.buferSizeColumns-1||j ==super.bufferSizeRows-1 )
				{
					screenBuffer[i][j] = '#';
				}
				else
				{
					screenBuffer[i][j]= '.';
				}
			}
		}
		
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("Resources/square.ttf"));
			font = font.deriveFont(Font.PLAIN, settings.getFontSize());
			} catch (FontFormatException | IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	 
		textRenderer = new TextRenderer(font);
	}

	@Override
	public void update() {
	}

	@Override
	public void draw() {
	   //TODO: move drawing from this text renderer to tile rendering;

   	   textRenderer.beginRendering(settings.getWidth(), settings.getHeight());
   	   textRenderer.setColor(Color.WHITE);
   	   textRenderer.setSmoothing(true);

   	   Random rand = new Random();
   	   for(int i = 0;i<super.buferSizeColumns;i++)
   		{
   			for(int j = 0;j<super.bufferSizeRows;j++)
			{	
   				
   				float r = rand.nextFloat();
   				float g = rand.nextFloat();
   				float b = rand.nextFloat();
   				Color randomColor = new Color(r, g, b);
   				textRenderer.setColor(randomColor);
				textRenderer.draw(String.valueOf(screenBuffer[i][j]), i*(settings.getFontSize()-0), j*(settings.getFontSize()-0));			
			}
		}

   	   textRenderer.endRendering();
	}
}
