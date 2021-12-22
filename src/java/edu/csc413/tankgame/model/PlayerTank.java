package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.KeyboardReader;


public class PlayerTank extends Tank {


  public PlayerTank(String id, double x, double y, double angle) {
    super(id, x, y, angle);
  }


  @Override
  public boolean move(GameWorld gameWorld) {

    Entity playerTank = gameWorld.getEntity(Constants.PLAYER_TANK_ID);
    KeyboardReader keyboardReader = KeyboardReader.instance();

    if (keyboardReader.upPressed()) {
      playerTank.moveForward(Constants.TANK_MOVEMENT_SPEED);

    }
    if (keyboardReader.downPressed()) {
      playerTank.moveBackward(Constants.TANK_MOVEMENT_SPEED);

    }
    if (keyboardReader.leftPressed()) {
      playerTank.turnLeft(Constants.TANK_TURN_SPEED);
    }
    if (keyboardReader.rightPressed()) {
      playerTank.turnRight(Constants.TANK_TURN_SPEED);
    }
    if (keyboardReader.spacePressed()) {
      playerTank.fireShell(gameWorld);
    }
    if(keyboardReader.escapePressed()){
      //return false;
    }
    checkBounds(gameWorld);
    return true;
  }


}
