package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

public class Wall extends Entity  {
    private static int uniqueId = 0;
    protected String imageFile;

    public Wall(String imageFile, double x, double y) {
        super("wall-"+uniqueId, x, y, 0);
        uniqueId++;
        this.imageFile = imageFile;
    }

    public String getImageFile() {
        return imageFile;
    }


    public double getXBounds() {
        return getX() + Constants.WALL_WIDTH;
    }

    public double getYBounds() {
        return getY() + Constants.WALL_HEIGHT;
    }

    public void checkBounds(GameWorld gameWorld){}
    public boolean move(GameWorld gameWorld){
        return false;
    }
    protected void turnRight(double tankTurnSpeed){}
    protected void turnLeft(double tankTurnSpeed){}
}