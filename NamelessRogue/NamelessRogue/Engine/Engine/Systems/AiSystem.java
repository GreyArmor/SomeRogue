package Engine.Systems;

import Engine.Components.AI.NonPlayerCharacter.AIControlled;
import Engine.Components.AI.NonPlayerCharacter.AStarPathfinderSimple;
import Engine.Components.AI.NonPlayerCharacter.BasicAi;
import Engine.Components.AI.NonPlayerCharacter.BasicAiStates;
import Engine.Components.Interaction.MoveToCommand;
import Engine.Components.Interaction.Player;
import Engine.Components.Physical.Position;
import Engine.Components.ChunksAndTiles.ChunkData;
import Engine.Components.ChunksAndTiles.Tile;
import abstraction.IEntity;
import abstraction.ISystem;
import abstraction.IChunkProvider;
import com.jogamp.nativewindow.util.Point;
import shell.Game;

import java.util.ArrayList;

public class AiSystem implements ISystem {
    private long previousGametimeForMove = 0;

    @Override
    public void Update(long gameTime, Game game) {
        if (gameTime - previousGametimeForMove > 150) {
            previousGametimeForMove = gameTime;
            IEntity worldEntity = game.GetEntityByComponentClass(ChunkData.class);
            IChunkProvider worldProvider = null;
            if(worldEntity!=null)
            {
                worldProvider = worldEntity.GetComponentOfType(ChunkData.class);
            }
            if(worldProvider!=null)
            {
                for (IEntity entity : game.GetEntities()) {
                    AIControlled ac = entity.GetComponentOfType(AIControlled.class);
                    if (ac != null) {
                        BasicAi basicAi = entity.GetComponentOfType(BasicAi.class);

                        if (basicAi != null) {
                            switch (basicAi.getState()) {
                                case Idle: {

                                    Position playerPosition = game.GetEntityByComponentClass(Player.class).GetComponentOfType(Position.class);
                                    Position position = entity.GetComponentOfType(Position.class);
                                  //  game.WriteLineToConsole("Starting movement state idle position = " + position.p.toString());
                                    if (position != null) {
                                        AStarPathfinderSimple pathfinder = new AStarPathfinderSimple();


                                        ArrayList<Point> path = pathfinder.FindPath(position.p, new Point(playerPosition.p.getX(), playerPosition.p.getY()), worldProvider, true);
                                        if (path == null) {
                                            path = pathfinder.FindPath(position.p,  new Point(playerPosition.p.getX(), playerPosition.p.getY()), worldProvider, true);
                                        }
                                        if (path != null) {
                                            basicAi.setRoute(path);
                                            basicAi.setState(BasicAiStates.Moving);
                                        } else {
                                            basicAi.setState(BasicAiStates.Idle);
                                        }
                                    }
                                }
                                    break;
                                case Moving: {
                                    Position position = entity.GetComponentOfType(Position.class);
                             //       game.WriteLineToConsole("moving position = " + position.p.toString());

                                    ArrayList<Point> route = basicAi.getRoute();
                                    if (route.stream().count() > 0 && position!=null) {
                                        Point nextPosition = route.get(0);
                                        Tile tileToMoveTo = worldProvider.getTile(nextPosition.getX(), nextPosition.getY());

                                        if (!tileToMoveTo.getPassable()) {
                                            AStarPathfinderSimple pathfinder = new AStarPathfinderSimple();
                                            ArrayList<Point> path = pathfinder.FindPath(position.p, route.get((int) (route.stream().count())), worldProvider,true);
                                            if (path == null) {
                                                path = pathfinder.FindPath(position.p, route.get((int) (route.stream().count())), worldProvider, true);
                                            }
                                            if (path != null) {
                                                basicAi.setRoute(path);
                                            } else {
                                                route.clear();
                                            }
                                        }
                                       // game.WriteLineToConsole("moving to nextPosition  = " + nextPosition.toString());
                                        MoveToCommand mc = new MoveToCommand(nextPosition.getX(), nextPosition.getY(), entity);
                                        entity.AddComponent(mc);
                                        if(route.stream().count()>0) {
                                            route.remove(0);
                                        }
                                    }
                                    if (route.stream().count() == 0) {
                                        basicAi.setState(BasicAiStates.Idle);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
