package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;


/** Entity class representing all tanks in the game. */
public class Tank extends Entity {
    // TODO: Implement. A lot of what's below is relevant to all Entity types, not just Tanks. Move it accordingly to
    //       Entity class.

    public Tank(String id, double x, double y, double angle) {
        super(id, x, y, angle);
    }

    public void checkBounds(GameWorld gameWorld) {
        //A tank that is off-screen should have its location updated so that it is back on-screen.

       /* If the x coordinate is less than Constants.TANK_X_LOWER_BOUND, it should be
        set to Constants.TANK_X_LOWER_BOUND.
       */
        if (getX() < Constants.TANK_X_LOWER_BOUND) {
            x = Constants.TANK_X_LOWER_BOUND;
        }
        /*        If the x coordinate is greater than Constants.TANK_X_UPPER_BOUND, it should
        be set to Constants.TANK_X_UPPER_BOUND.
         */
        if (getX() > Constants.TANK_X_UPPER_BOUND) {
            x = Constants.TANK_X_UPPER_BOUND;
        }
        /*If the y coordinate is less than Constants.TANK_Y_LOWER_BOUND, it should be
        set to Constants.TANK_Y_LOWER_BOUND.
         */
        if (getY() < Constants.TANK_Y_LOWER_BOUND) {
            y = Constants.TANK_Y_LOWER_BOUND;
        }
        /*If the y coordinate is greater than Constants.TANK_Y_UPPER_BOUND, it should
        be set to Constants.TANK_Y_UPPER_BOUND.
         */
        if (getY() > Constants.TANK_Y_UPPER_BOUND) {
            y = Constants.TANK_Y_UPPER_BOUND;
        }
    }




    /*
 1. If tankA.getXBound() - tankB.getX() is the smallest distance, then we move Tank A
     to the left by half that distance and Tank B to the right by half that distance.
 2. If tankB.getXBound() - tankA.getX() is the smallest distance, then we move Tank A
     to the right by half that distance and Tank B to the left by half that distance.
 3. If tankA.getYBound() - tankB.getY() is the smallest distance, then we move Tank A
     upward by half that distance and Tank B downward by half that distance.
 4. If tankB.getYBound() - tankA.getY() is the smallest distance, then we move Tank A
     downward by half that distance and Tank B upward by half that distance.
 */
   protected static void findSmallestDistance(Tank tankA, Tank tankB){
            double moveAForward = tankB.getXBounds() - tankA.getX();
            double moveAForward1 = tankA.getYBounds() - tankB.getY();

            double moveABackwards = tankA.getXBounds() - tankB.getX();
            double moveABackwards1 = tankB.getYBounds() - tankA.getY();

            double smallestForwards;
            double smallestBackwards;

            double smallest;

       smallestForwards = Math.min(moveAForward, moveAForward1);
       smallestBackwards = Math.min(moveABackwards, moveABackwards1);
        smallest = Math.min(smallestForwards, smallestBackwards);
        smallest = smallest/2;

                tankA.x = tankA.getX() - smallest;
                tankA.y = tankA.getY() - smallest;

                tankB.x = tankB.getX() + smallest;
                tankB.y = tankB.getY() + smallest;
        }


        protected static void pushTankAway(Tank entity, Wall entity1){

       double right = entity1.getXBounds() - entity.getX();

       double left =  entity.getXBounds() - entity1.getX();

       double up = entity.getYBounds() - entity1.getY();
       double down = entity1.getYBounds() - entity.getY();

       double smallestx = Math.min(right,left);
       double smallesty = Math.min(up, down);

       double smallest = Math.min(smallestx,smallesty);
            if(smallest == right) {
                entity.x = entity.getX() + smallest;
            }else if(smallest == left){
                entity.x = entity.getX() - smallest;
            }else if(smallest == up){
                entity.y = entity.getY() - smallest;
            }else {
                entity.y = entity.getY() + smallest;
            }


        }


    @Override
    public boolean move(GameWorld gameWorld) {
        return false;
    }

    protected void turnLeft(double turnSpeed) {
        angle -= turnSpeed;
    }

    protected void turnRight(double turnSpeed) {
        angle += turnSpeed;
    }


    public double getXBounds() {
        return getX() + Constants.TANK_WIDTH;
    }

    public double getYBounds() {
        return getY() + Constants.TANK_HEIGHT;
    }

    }
