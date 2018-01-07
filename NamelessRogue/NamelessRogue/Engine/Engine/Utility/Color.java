package Engine.Utility;

import java.lang.reflect.GenericArrayType;

public class Color {
    private float Red;
    private float Green;
    private float Blue;
    private float Alpha;

    public Color(){}

    public Color(int red, int green, int blue)
    {
        this(red,green,blue,0);
    }

    public Color(float red, float green, float blue)
    {
        this(red, green, blue, 1f);
    }

    public Color(double red, double green, double blue)
    {
        this(red, green, blue, 1);
    }

    public Color(double red, double green, double blue, double alpha)
    {
        this((float)red,(float)green,(float)blue, (float)alpha);
    }

    public Color(int red, int green, int blue, int alpha)
    {
        this((float)red /255,(float)green/255,(float)blue /255, (float)alpha/255);
    }

    public Color(float red, float green, float blue, float alpha)
    {
        Red = red;
        Green = green;
        Blue = blue;
        Alpha = alpha;
    }

    public float getRed() {
        return Red;
    }

    public void setRed(float red) {
        Red = red;
    }

    public float getGreen() {
        return Green;
    }

    public void setGreen(float green) {
        Green = green;
    }

    public float getBlue() {
        return Blue;
    }

    public void setBlue(float blue) {
        Blue = blue;
    }

    public float getAlpha() {
        return Alpha;
    }

    public void setAlpha(float alpha) {
        Alpha = alpha;
    }
}
