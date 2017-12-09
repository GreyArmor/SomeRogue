package Engine.Components.World;

import Engine.TerrainTypes;
import Engine.Tile;

import java.awt.*;
import java.io.Console;

/**
 * Created by Admin on 05.12.2017.
 */
public class TileNoiseInterpreter {
    public static Tile GetTile(double noiseValue)
    {
        if (noiseValue>1){
            noiseValue=1;
        }
        if (noiseValue<0){
            noiseValue=0;
        }
        System.out.print(noiseValue);
        System.out.print("\n");
        Tile t = null;
        if(noiseValue>0.80) {
            t  = new Tile(TerrainTypes.Snow);
        }
        else if(noiseValue>0.75) {
            t  = new Tile(TerrainTypes.HardRocks);
        }

        else if(noiseValue>0.7) {
            t  = new Tile(TerrainTypes.Rocks);
        }
        else if(noiseValue>0.65) {
        t  = new Tile(TerrainTypes.LightRocks);
        }
        else if(noiseValue>0.51) {
            t  = new Tile(TerrainTypes.Grass);
        }
        else if(noiseValue>=0.5) {
            t  = new Tile(TerrainTypes.Sand);
        }
        else if(noiseValue<0.5) {
            t  = new Tile(TerrainTypes.Water);
        }
        return t;
    }
}
