package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;
import edu.csc413.tankgame.KeyboardReader;

public class AITank extends Tank{
  public AITank(String id, double x, double y, double angle){
  super(id,x,y,angle);
  }

  //need to add one more AiTank
  @Override
  public boolean move(GameWorld gameWorld) {
   Entity playerTank = gameWorld.getEntity(Constants.PLAYER_TANK_ID);

      KeyboardReader keyboardReader = KeyboardReader.instance();

    //for AiTank
    double dx = playerTank.getX() - getX();
    double dy = playerTank.getY() - getY();


    // atan2 applies arctangent to the ratio of the two provided values.
    double angleToPlayer = Math.atan2(dy, dx);

    double angleDifference = getAngle() - angleToPlayer;

    // We want to keep the angle difference between -180 degrees and 180
    // degrees for the next step. This ensures that anything outside of that
    // range is adjusted by 360 degrees at a time until it is, so that the
    // angle is still equivalent.
      angleDifference -=
             Math.floor(angleDifference / Math.toRadians(360.0) + 0.5)
                     * Math.toRadians(360.0);

// The angle difference being positive or negative determines if we turn
// left or right. However, we don’t want the Tank to be constantly
// bouncing back and forth around 0 degrees, alternating between left
// and right turns, so we build in a small margin of error.

    if (angleDifference < -Math.toRadians(3.0)) {
      turnRight(Constants.TANK_TURN_SPEED);

    } else if (angleDifference > Math.toRadians(3.0)) {
    turnLeft(Constants.TANK_TURN_SPEED);
    }



          if(keyboardReader.spacePressed() && playerTank.getY() == getY()){
              moveBackward(Constants.TANK_MOVEMENT_SPEED);
              moveBackward(Constants.TANK_MOVEMENT_SPEED);

          }


      if(angleToPlayer < 20 ) {
          moveForward(Constants.TANK_MOVEMENT_SPEED);
          //fireShell(gameWorld);
      }


    checkBounds(gameWorld);
      return false;
  }
}
