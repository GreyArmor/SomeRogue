package Engine.Systems;

import Engine.Components.AI.NonPlayerCharacter.Character;
import Engine.Components.Environment.Door;
import Engine.Components.Interaction.*;
import Engine.Components.Physical.OccupiesTile;
import Engine.Components.Physical.Position;
import Engine.Components.Rendering.Drawable;
import Engine.Components.ChunksAndTiles.ChunkData;
import Engine.Input.Intent;
import Engine.Components.ChunksAndTiles.Tile;
import abstraction.IEntity;
import abstraction.ISystem;
import abstraction.IChunkProvider;
import shell.Game;

import javax.sound.midi.Receiver;

public class IntentSystem implements ISystem {



    @Override
    public void Update(long gameTime, Game game) {
        for (IEntity entity : game.GetEntities()) {
            InputComponent inputComponent = entity.GetComponentOfType(InputComponent.class);
            if (inputComponent != null) {
                for (Intent intent : inputComponent.Intents) {
                    switch (intent) {
                        case MoveUp:
                        case MoveDown:
                        case MoveLeft:
                        case MoveRight:
                        case MoveTopLeft:
                        case MoveTopRight:
                        case MoveBottomLeft:
                        case MoveBottomRight:
                                Position position = entity.GetComponentOfType(Position.class);
                                if (position != null) {

                                    int newX =
                                            intent == Intent.MoveLeft || intent == Intent.MoveBottomLeft || intent == Intent.MoveTopLeft ?
                                                    position.p.getX() - 1 :
                                                    intent == Intent.MoveRight || intent == Intent.MoveBottomRight || intent == Intent.MoveTopRight ?
                                                            position.p.getX() + 1 :
                                                            position.p.getX();
                                    int newY =
                                            intent == Intent.MoveDown || intent == Intent.MoveBottomLeft || intent == Intent.MoveBottomRight ?
                                                    position.p.getY() + 1 :
                                                    intent == Intent.MoveUp || intent == Intent.MoveTopLeft || intent == Intent.MoveTopRight ?
                                                            position.p.getY() - 1 :
                                                            position.p.getY();

                                    IEntity worldEntity = game.GetEntityByComponentClass(ChunkData.class);
                                    IChunkProvider worldProvider = null;
                                    if (worldEntity != null) {
                                        worldProvider = worldEntity.GetComponentOfType(ChunkData.class);
                                    }

                                    Tile tileToMoveTo = worldProvider.getTile(newX, newY);
                                    Cursor cursor = entity.GetComponentOfType(Cursor.class);

                                    //if cursor is moving then it passes through everything
                                    if (cursor != null) {
                                        entity.AddComponent(new MoveToCommand(newX, newY, entity));
                                    } else {

                                        IEntity entityThatOccupiedTile = null;
                                        for (IEntity tileEntity : tileToMoveTo.getEntitiesOnTile()) {
                                            OccupiesTile occupiesTile = tileEntity.GetComponentOfType(OccupiesTile.class);
                                            if (occupiesTile != null) {
                                                entityThatOccupiedTile = tileEntity;
                                                break;
                                            }
                                        }


                                        if (entityThatOccupiedTile != null) {
                                            Door door = entityThatOccupiedTile.GetComponentOfType(Door.class);
                                            Character characterComponent = entityThatOccupiedTile.GetComponentOfType(Character.class);
                                            if (door != null) {
                                                SimpleSwitch simpleSwitch = entityThatOccupiedTile.GetComponentOfType(SimpleSwitch.class);
                                                if (simpleSwitch != null == simpleSwitch.isSwitchActive()) {
                                                    entityThatOccupiedTile.GetComponentOfType(Drawable.class).setRepresentation('o');
                                                    entityThatOccupiedTile.AddComponent(new ChangeSwitchStateCommand(simpleSwitch, false));
                                                    tileToMoveTo.setPassable(true);
                                                    game.WriteLineToConsole("Opened the door!");
                                                } else {
                                                    entity.AddComponent(new MoveToCommand(newX, newY, entity));
                                                }
                                            }

                                            if (characterComponent != null) {
                                                //TODO: if hostile
                                                entity.AddComponent(new AttackCommand(entity, entityThatOccupiedTile));
                                                //TODO: do something else if friendly: chat, trade, etc

                                            }
                                        } else{
                                            entity.AddComponent(new MoveToCommand(newX, newY, entity));
                                        }
                                    }
                                }
                            break;
                        case LookAtMode:
                            InputReceiver receiver = new InputReceiver();
                            Player player = entity.GetComponentOfType(Player.class);
                            Cursor cursor = entity.GetComponentOfType(Cursor.class);
                            entity.RemoveComponentOfType(InputReceiver.class);
                            if(player!=null)
                            {
                                IEntity cursorEntity = game.GetEntityByComponentClass(Cursor.class);
                                cursorEntity.AddComponent(receiver);
                                Drawable cursorDrawable = cursorEntity.GetComponentOfType(Drawable.class);
                                cursorDrawable.setVisible(true);
                                Position cursorPosition = cursorEntity.GetComponentOfType(Position.class);
                                Position playerPosition = entity.GetComponentOfType(Position.class);
                                cursorPosition.p.setX(playerPosition.p.getX());
                                cursorPosition.p.setY(playerPosition.p.getY());

                            }
                            else if(cursor != null)
                            {
                                IEntity playerEntity = game.GetEntityByComponentClass(Player.class);
                                playerEntity.AddComponent(receiver);
                                Drawable cursorDrawable = entity.GetComponentOfType(Drawable.class);
                                cursorDrawable.setVisible(false);

                            }
                    }
                }
                inputComponent.Intents.clear();
            }
        }
    }
}
