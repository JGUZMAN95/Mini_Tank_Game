package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;
import edu.csc413.tankgame.view.*;


import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameDriver {
    private final MainView mainView;
    private final RunGameView runGameView;
    public GameWorld entityList;


    public GameDriver() {
        mainView = new MainView(this::startMenuActionPerformed);
        runGameView = mainView.getRunGameView();
        entityList = new GameWorld();
    }

    public void start() {
        mainView.setScreen(MainView.Screen.START_GAME_SCREEN);
    }

    private void startMenuActionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case StartMenuView.START_BUTTON_ACTION_COMMAND -> runGame();
            case StartMenuView.EXIT_BUTTON_ACTION_COMMAND -> mainView.closeGame();
            default -> throw new RuntimeException("Unexpected action command: " + actionEvent.getActionCommand());
        }
    }

    private void runGame() {
        mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
        Runnable gameRunner = () -> {
            setUpGame();
            while (updateGame()) {
                runGameView.repaint();
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }
            mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
            resetGame();
        };
        new Thread(gameRunner).start();
    }

    /**
     * setUpGame is called once at the beginning when the game is started. Entities that are present from the start
     * should be initialized here, with their corresponding sprites added to the RunGameView.
     */
    private void setUpGame() {
        // TODO: Implement.
        List<WallInformation> wallInfo = WallInformation.readWalls();

        for (WallInformation wall: wallInfo) {
            Wall walls =
                    new Wall(
                            wall.getImageFile(),
                            wall.getX(),
                            wall.getY()
                    );

            entityList.addEntity(walls);
        }

        PlayerTank playerTank =
                new PlayerTank(
                        Constants.PLAYER_TANK_ID,
                        Constants.PLAYER_TANK_INITIAL_X,
                        Constants.PLAYER_TANK_INITIAL_Y,
                        Constants.PLAYER_TANK_INITIAL_ANGLE);
        AITank aITank1 =
                new AITank(
                        Constants.AI_TANK_1_ID,
                        Constants.AI_TANK_1_INITIAL_X,
                        Constants.AI_TANK_1_INITIAL_Y,
                        Constants.AI_TANK_1_INITIAL_ANGLE);

        AITank aITank2 =
                new AITank(
                        Constants.AI_TANK_2_ID,
                        Constants.AI_TANK_2_INITIAL_X,
                        Constants.AI_TANK_2_INITIAL_Y,
                        Constants.AI_TANK_2_INITIAL_ANGLE);


        entityList.addEntity(playerTank);
        entityList.addEntity(aITank1);
        entityList.addEntity(aITank2);

        runGameView.addSprite(
                playerTank.getId(),
                RunGameView.PLAYER_TANK_IMAGE_FILE,
                playerTank.getX(),
                playerTank.getY(),
                playerTank.getAngle());
        runGameView.addSprite(
                aITank1.getId(),
                RunGameView.AI_TANK_IMAGE_FILE,
                aITank1.getX(),
                aITank1.getY(),
                aITank1.getAngle());

        runGameView.addSprite(
                aITank2.getId(),
                RunGameView.AI_TANK_IMAGE_FILE,
                aITank2.getX(),
                aITank2.getY(),
                aITank2.getAngle());

        for (Entity entity : entityList.getEntities()) {
            if (entity instanceof Wall) {
                runGameView.addSprite(
                        entity.getId(),
                       ((Wall) entity).getImageFile(),
                        entity.getX(),
                        entity.getY(),
                        entity.getAngle());
            }
        }
    }


    /**
     * updateGame is repeatedly called in the gameplay loop. The code in this method should run a single frame of the
     * game. As long as it returns true, the game will continue running. If the game should stop for whatever reason
     * (e.g. the player tank being destroyed, escape being pressed), it should return false.
     */
    private boolean updateGame() {
        // TODO: Implement.
        int entityCount = 0;
        int collided;

        //make copy of original enteries
        List<Entity> copy = new ArrayList<>(entityList.getEntities());


        //iterate through list of copy
        for (Entity entity : copy) {
            entity.move(entityList);
            }

        for (Entity entity : entityList.getEntities()) {
            entityCount++;
            if ((entityCount > copy.size())) {
                runGameView.addSprite(
                        entity.getId(),
                        RunGameView.SHELL_IMAGE_FILE,
                        entity.getX(),
                        entity.getY(),
                        entity.getAngle());
                entityCount++;

            }

            if (entity instanceof ExtraLife) {
                runGameView.addSprite(
                        entity.getId(),
                        RunGameView.EXTRA_LIFE_FILE,
                        entity.getX(),
                        entity.getY(),
                        entity.getAngle());
            }
            runGameView.setSpriteLocationAndAngle(entity.getId(), entity.getX(), entity.getY(), entity.getAngle());

        }



        for (int i = 0; i < copy.size(); i++) {
            for(int j = 1; j < copy.size(); j++)
                if (Entity.collisionDetection(copy.get(i), copy.get(j)) && j < i + 1) {
                    collided = Entity.handleCollision(copy.get(i), copy.get(j), entityList);
                    if (collided == 1) {
                        runGameView.removeSprite(copy.get(i).getId());
                        entityCount--;
                    } else if (collided == 2) {
                        runGameView.removeSprite(copy.get(j).getId());
                        entityCount--;
                    } else if (collided == 3) {
                        runGameView.removeSprite(copy.get(i).getId());
                        runGameView.removeSprite(copy.get(j).getId());
                        entityCount = entityCount - 2;
                    }else if(collided == 4){
                        return false;
                    }
                }
        }

        return true;

    }



    /**
     * resetGame is called at the end of the game once the gameplay loop exits. This should clear any existing data from
     * the game so that if the game is restarted, there aren't any things leftover from the previous run.
     */
    public void resetGame() {
        runGameView.reset();
    }

    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
        SoundEffects.play_once("resources/Music.wav");

    }
}


