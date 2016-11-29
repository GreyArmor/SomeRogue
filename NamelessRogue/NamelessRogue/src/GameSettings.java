import java.awt.*;
public class GameSettings {
	//window resolution
	private int width;
	private int height;
	private int fontSize = 12;
	//some font
	private Font  font;
	public GameSettings(int width,int height)
	{
		setWidth(width);
		setHeight(height);
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
}
