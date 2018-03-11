package Engine.Serialization;

import Engine.Components.ChunksAndTiles.Chunk;
import Engine.Components.ChunksAndTiles.Tile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import shell.Game;
import java.io.*;


public class SaveManager {
    public static void LoadGame(String pathToFolder, Game game)
    {

    }

    public static void  SaveGame(String pathToFolder, Game game)
    {

    }

    public static void SaveChunk(String pathToFolder, Chunk chunk, String chunkId)//, Game game)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            File f = new File(pathToFolder);
            if (!f.exists()) {
                f.mkdir();
            }
//            if (chunkId.equals("1090_3070")) {
//                pathToFolder.toString();
//            }
            StringWriter stringTiles = new StringWriter();
            objectMapper.writeValue(stringTiles, chunk.getChunkTiles());
            String content = stringTiles.toString();
            fw = new FileWriter(pathToFolder + "\\" + chunkId);
            bw = new BufferedWriter(fw);
            bw.write(content);
            stringTiles.close();

            System.out.println("Saved");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static Tile[][] LoadChunk(String pathToFolder, String chunkId) {
        BufferedReader br = null;
        FileReader fr = null;
        String json  = "";
        Tile[][] tiles = null;
        try {
//            if (chunkId.equals("1090_3070")) {
//                pathToFolder.toString();
//            }
            File f = new File(pathToFolder + "\\" + chunkId);
            if (!f.exists()) {
                System.out.print("no file " + pathToFolder + "\\" + chunkId);
                return null;
            } else {
                System.out.print("found file " + pathToFolder + "\\" + chunkId);
            }

            fr = new FileReader(pathToFolder + "\\" + chunkId);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                json += sCurrentLine;
            }

            ObjectMapper objectMapper = new ObjectMapper();

            //convert json string to object
            tiles = objectMapper.readValue(json, Tile[][].class);
        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return tiles;

        }
    }
}
