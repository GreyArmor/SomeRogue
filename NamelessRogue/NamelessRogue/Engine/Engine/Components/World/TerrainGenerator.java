package Engine.Components.World;

import Engine.Noise.ImageWriter;
import Engine.Noise.SimplexNoise;

import java.util.Random;

/**
 * Created by Admin on 04.11.2017.
 */
public class TerrainGenerator {
    SimplexNoise noise;
    public TerrainGenerator(int seed)
    {
        SimplexNoise noise1 = new SimplexNoise(300,0.5, seed);
        SimplexNoise noise2 = new SimplexNoise(600,0.5, seed);
        SimplexNoise noise3 = new SimplexNoise(900,0.5, seed);
        Random r = new Random(seed);



        double xStart=0;
        double XEnd=1000;
        double yStart=0;
        double yEnd=1000;

        int xResolution=1000;
        int yResolution=1000;




        double[][] result=new double[xResolution][yResolution];

        for(int i=0;i<xResolution;i++){
            for(int j=0;j<yResolution;j++) {

                int x = (int) (xStart + i * ((XEnd - xStart) / xResolution));
                int y = (int) (yStart + j * ((yEnd - yStart) / yResolution));
                double n1 = +noise1.getNoise(x, y);
                double n2 = +noise2.getNoise(x, y);
                double n3 = +noise3.getNoise(x, y);

                result[i][j] = 1 - (0.5 * (1 + ((n1 + n2 + n3) / 3)));

            }
        }

        for(int i=0;i<xResolution;i++) {
            for (int j = 0; j < yResolution; j++) {
                if (i < 100 || j < 100 || i > 900 || j > 900) {

                    int iDist = i > 900 ? 1000 - i : i;
                    int jDist = j > 900 ? 1000 - j : j;
                    int edgePosition = iDist > jDist ? jDist : iDist;
                    result[i][j] *= (float) edgePosition / 100;
                }
                if (result[i][j] <= 0.5) {
                    result[i][j] = 0;
                }
            }
        }

        ImageWriter.greyWriteImage(result);

    }
}
