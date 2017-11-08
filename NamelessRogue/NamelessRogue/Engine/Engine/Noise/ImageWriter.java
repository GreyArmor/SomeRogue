package Engine.Noise;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageWriter {
    //just convinence methods for debug

    public static void greyWriteImage(double[][] data){
        //this takes and array of doubles between 0 and 1 and generates a grey scale image from them

        BufferedImage image = new BufferedImage(data.length,data[0].length, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < data[0].length; y++)
        {
            for (int x = 0; x < data.length; x++)
            {
                if (data[x][y]>1){
                    data[x][y]=1;
                }
                if (data[x][y]<0){
                    data[x][y]=0;
                }
                Color col = Color.black;
                if(data[x][y]>0.80) {
                    col  = Color.white;
                }
                else if(data[x][y]>0.75) {
                    col  = Color.DARK_GRAY;
                }

                else if(data[x][y]>0.7) {
                    col  = Color.gray;
                }
                else if(data[x][y]>0.65) {
                    col  = Color.lightGray;
                }
                else if(data[x][y]>0.51) {
                    col  = Color.green;
                }
                else if(data[x][y]>=0.5) {
                    col  = Color.yellow;
                }
                else if(data[x][y]<0.5) {
                    col =  Color.blue;
                }



                image.setRGB(x, y, col.getRGB());
            }
        }

        try {
            // retrieve image
            File outputfile = new File("C:\\saved.png");
            outputfile.createNewFile();

            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            //o no! Blank catches are bad
            throw new RuntimeException("I didn't handle this very well");
        }
    }



}