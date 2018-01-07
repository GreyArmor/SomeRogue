package Engine.Systems;

import Engine.Chunk;
import Engine.Components.Interaction.Player;
import Engine.Components.Physical.Position;
import Engine.Components.World.ChunkData;
import Engine.Constants;
import abstraction.IEntity;
import abstraction.ISystem;
import abstraction.IWorldProvider;
import com.jogamp.nativewindow.util.Point;
import shell.Game;

import java.sql.Time;
import java.util.ArrayList;

public class ChunkManagementSystem implements ISystem {
    @Override
    public void Update(Time gameTime, Game game) {
        //not elegant TODO: think of better way
        IEntity worldEntity = game.GetEntityByComponentClass(ChunkData.class);
        IWorldProvider worldProvider = null;
        if (worldEntity != null) {
            worldProvider = worldEntity.GetComponentOfType(ChunkData.class);
        }
        IEntity playerentity = game.GetEntityByComponentClass(Player.class);
        if(playerentity != null) {
            Chunk currentChunk = null;
            Point currentChunkKey = null;
            Position playerPosition = playerentity.GetComponentOfType(Position.class);
            //look for current chunk
            for (Point key : worldProvider.getRealityBubbleChunks().keySet()) {
                Chunk ch = worldProvider.getRealityBubbleChunks().get(key);
                if (ch.isPointInside(playerPosition.p)) {
                    currentChunk = ch;
                    currentChunkKey =  key;
                    break;
                }
            }
            //if there is none, that means we just loaded the game, look for current in all chunks
            if (currentChunk == null) {
                for (Point key : worldProvider.getChunks().keySet()) {
                    Chunk ch = worldProvider.getChunks().get(key);
                    if (ch.isPointInside(playerPosition.p)) {
                        currentChunk = ch;
                        currentChunkKey =  key;
                        break;
                    }
                }
            }

            if (currentChunk != null) {
                for (int x = -Constants.RealityBubbleRangeInChunks + currentChunkKey.getX(); x <= Constants.RealityBubbleRangeInChunks + currentChunkKey.getX(); x++) {
                    for (int y = -Constants.RealityBubbleRangeInChunks + currentChunkKey.getY(); y <= Constants.RealityBubbleRangeInChunks + currentChunkKey.getY(); y++) {
                        Point p = new Point(x, y);
                        if (!worldProvider.getRealityBubbleChunks().containsKey(p)) {
                            if (worldProvider.getChunks().containsKey(p)) {
                                worldProvider.getRealityBubbleChunks().put(p, worldProvider.getChunks().get(p));
                            }
                        }
                    }
                }
                ArrayList<Point> keysToRemove = new ArrayList<>();
                for (Point key : worldProvider.getRealityBubbleChunks().keySet()) {
                    double dist = Math.abs(key.getX() - currentChunkKey.getX()) + Math.abs(key.getY() - currentChunkKey.getY());
                    if (dist > Constants.RealityBubbleRangeInChunks) {
                        keysToRemove.add(key);
                    }
                }
                for (Point key : keysToRemove)
                {
                    worldProvider.getRealityBubbleChunks().get(key).Deactivate();
                    worldProvider.getRealityBubbleChunks().remove(key);
                }

            }
        }




    }
}
