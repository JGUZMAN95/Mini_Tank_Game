package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;


public class Shell extends Entity{
  private static int uniqueId = 0;
  public Shell(double x, double y, double angle){
    super("shell-" + uniqueId, x, y, angle);
    uniqueId++;
  }


  @Override
  public void checkBounds(GameWorld gameWorld) {

    if (getX() < Constants.SHELL_X_LOWER_BOUND
            || getX() > Constants.SHELL_X_UPPER_BOUND
            || getY() < Constants.SHELL_Y_LOWER_BOUND
            || getY() > Constants.SHELL_Y_UPPER_BOUND) {

      gameWorld.removeEntity(gameWorld.getEntity(getId()));
    }
  }

  @Override
  public boolean move(GameWorld gameWorld) {moveForward(Constants.SHELL_MOVEMENT_SPEED);
    return false;
  }

  //bound the entity by adding the width of an entity
  public double getXBounds() {
    return getX() + Constants.SHELL_WIDTH;
  }

  public double getYBounds() {
    return getY() + Constants.SHELL_HEIGHT;
  }

  protected void turnRight(double tankTurnSpeed) {
  }
  protected void turnLeft(double tankTurnSpeed) {
  }

}