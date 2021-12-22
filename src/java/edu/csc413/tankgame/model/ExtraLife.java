package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class ExtraLife extends Entity {

    public ExtraLife( double x, double y) {
        super("extraLife.png", x, y, 0);
    }



    public double getXBounds() {
        return getX() + Constants.EXTRA_LIFE_WIDTH;
    }

    public double getYBounds() {
        return getY() + Constants.EXTRA_LIFE_HEIGHT;
    }

    protected static void addHealth(Entity entity) {
     entity.currentHealth = entity.currentHealth + 100;
    }

    public void checkBounds(GameWorld gameWorld){}
    public boolean move(GameWorld gameWorld){
        return false;
    }
    protected void turnRight(double tankTurnSpeed){}
    protected void turnLeft(double tankTurnSpeed){}
}
