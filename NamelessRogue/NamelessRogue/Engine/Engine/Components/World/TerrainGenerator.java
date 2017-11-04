package Engine.Components.World;

import Engine.Noise.SimplexNoise;

import java.util.Random;

/**
 * Created by Admin on 04.11.2017.
 */
public class TerrainGenerator {
    SimplexNoise noise;
    public TerrainGenerator(int seed)
    {
        noise = new SimplexNoise(1,1, seed);

    }
}
