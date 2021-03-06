package Engine.Components.ChunksAndTiles;

import Engine.Infrastructure.Constants;
import Engine.Noise.SimplexNoise;
import com.jogamp.nativewindow.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Admin on 04.11.2017.
 */
public class TerrainGenerator {


    public List<SimplexNoise> TerrainNoises;
    public int Seed;
    private int forestSeed;
    int resolution=1000;
    int layer1 = 300, layer2 = 600, layer3 = 900;

    public TerrainGenerator(int seed)
    {
        Seed = seed;
        Random forestsRandom = new Random(seed);
        TerrainNoises =  new ArrayList<>();
       // Constants.
        //Const.

        SimplexNoise noise1 = new SimplexNoise( layer1,0.5, Seed);
        SimplexNoise noise2 = new SimplexNoise( layer2,0.5, Seed);
        SimplexNoise noise3 = new SimplexNoise( layer3,0.5, Seed);


        TerrainNoises.add(noise1);
        TerrainNoises.add(noise2);
        TerrainNoises.add(noise3);



        double xStart=0;
        double XEnd=1000;
        double yStart=0;
        double yEnd=1000;


        int borderthickness = resolution/10;



        double[][] result=new double[resolution][resolution];

        for(int i=0;i<resolution;i++){
            for(int j=0;j<resolution;j++) {

                int x = (int) (xStart + i * ((XEnd - xStart) / resolution));
                int y = (int) (yStart + j * ((yEnd - yStart) / resolution));
                double noise = 0;
                for (SimplexNoise s :TerrainNoises) {
                    noise += s.getNoise(x, y);
                }
                noise /= TerrainNoises.size();
                result[i][j] = 1 - (0.5 * (1 + noise));

            }
        }

        for(int i=0;i<resolution;i++) {
            for (int j = 0; j < resolution; j++) {
                if (i < borderthickness || j < borderthickness || i > resolution - borderthickness || j > resolution - borderthickness) {

                    int iDist = i > resolution - borderthickness ? resolution - i : i;
                    int jDist = j >  resolution - borderthickness ? resolution - j : j;
                    int edgePosition = iDist > jDist ? jDist : iDist;
                    result[i][j] *= (float) edgePosition / (resolution/10);
                }
                if (result[i][j] <= 0.5) {
                    result[i][j] = 0;
                }
            }
        }

//        ImageWriter.greyWriteImage(result);

    }

    public Tile GetTile(int x, int y)
    {
        double dX = (double)x/Constants.ChunkSize;
        double dY = (double)y/Constants.ChunkSize;
       // System.out.print("X ="+dX+"Y =" +dY +"\n");
        int resolutionZoomed = resolution * Constants.ChunkSize;
        int borderthickness = resolutionZoomed/10;

        double noise = 0;
        for (SimplexNoise s : TerrainNoises) {
            noise += s.getNoise(dX, dY);
        }

        noise /= TerrainNoises.size();
        double result = 1 - (0.5 * (1 + noise));

        if (x < borderthickness || y < borderthickness || x > resolutionZoomed - borderthickness || y > resolutionZoomed - borderthickness) {

            int iDist = x > resolutionZoomed - borderthickness ? resolutionZoomed - x : x;
            int jDist = y >  resolutionZoomed - borderthickness ? resolutionZoomed - y : y;
            int edgePosition = iDist > jDist ? jDist : iDist;
            //System.out.print(String.format("edgePosition = {0}\n", edgePosition));
            result *= (float) edgePosition / (resolutionZoomed/10);
        }
        if (result <= 0.5) {
            result = 0;
        }
        return new Tile(TileNoiseInterpreter.GetTerrain(result), new Point(x,y));
    }
}
