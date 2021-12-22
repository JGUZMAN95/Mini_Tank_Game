package edu.csc413.tankgame.model;

import edu.csc413.tankgame.Constants;

/**
 * A general concept for an entity in the Tank Game. This includes everything that can move or be interacted with, such
 * as tanks, shells, walls, power ups, etc.
 */
public abstract class Entity {
    /**
     * All entities can move, even if the details of their move logic may vary based on the specific type of Entity.
     */
    protected String id;
    protected double x;
    protected double y;
    protected double angle;

    protected double coolDown;

    public int currentHealth = 50;


    public Entity(String id, double x, double y, double angle) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }


    public void addExtraLife(GameWorld gameWorld){
        ExtraLife extraLife = new ExtraLife(getExtraLifeX(),getExtraLifeY());
        for(Entity entity: gameWorld.getEntities()){
            if(entity instanceof Tank){
                gameWorld.addEntity(extraLife);
            }
        }
    }

    public void fireShell(GameWorld gameWorld) {
        Shell shell = new Shell(getShellX(), getShellY(), getShellAngle());

        if (coolDown == 0) {
            gameWorld.addEntity(shell);
            coolDown = 15;

        } else {
            coolDown--;
        }
    }


    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }


    // TODO: The methods below are provided so you don't have to do the math for movement. You should call these methods
    //       from the various subclasses of Entity in their implementations of move.
    protected void moveForward(double movementSpeed) {
        x += movementSpeed * Math.cos(angle);
        y += movementSpeed * Math.sin(angle);
    }

    protected void moveBackward(double movementSpeed) {
        x -= movementSpeed * Math.cos(angle);
        y -= movementSpeed * Math.sin(angle);
    }
    //checks whether an object has collided with another
    public static boolean collisionDetection(Entity entity, Entity entity1) {
        return !entity.getId().equals(entity1.getId())
                && entity.getX() < entity1.getXBounds()
                && entity.getXBounds() > entity1.getX()
                && entity.getY() < entity1.getYBounds()
                && entity.getYBounds() > entity1.getY();
    }



    public static int handleCollision(Entity entity, Entity entity1, GameWorld gameWorld) {
        int count = 0;

         if (entity instanceof Tank && entity1 instanceof Tank) {
            Tank.findSmallestDistance((Tank) entity, (Tank) entity1);

        } else if (entity instanceof Tank && entity1 instanceof Shell) {

            checkHealth(entity);
            gameWorld.removeEntity(entity1);

             if(entity.currentHealth < 1){
                    count =  3;
                    gameWorld.removeEntity(entity);

                 if(entity.getId().equals(Constants.PLAYER_TANK_ID)){
                     count = 4;
                 }

            }else{
                    count =  2;
            }

         }else if(entity instanceof Tank && entity1 instanceof Wall){
                Tank.pushTankAway((Tank) entity, (Wall) entity1);

        }else if(entity instanceof Shell && entity1 instanceof Tank){

             gameWorld.removeEntity(entity);
             checkHealth(entity1);

            if(entity1.currentHealth < 1){
                count = 3;
                gameWorld.removeEntity(entity1);

                if(entity1.getId().equals(Constants.PLAYER_TANK_ID)){
                    count = 4;
                }
            }else {
                count = 1;
            }

         }else if(entity instanceof Shell && entity1 instanceof Shell){
             gameWorld.removeEntity(entity1);
             gameWorld.removeEntity(entity);

             count = 3;

         }else if(entity instanceof Shell && entity1 instanceof Wall) {
             gameWorld.removeEntity(entity);
             gameWorld.removeEntity(entity1);

             count = 3;
         }
        return count;
    }


    public double getExtraLifeX(){
        return Constants.EXTRA_LIFE_INITIAL_X;
    }
    public double getExtraLifeY(){
        return Constants.EXTRA_LIFE_INITIAL_Y;
    }

    // The following methods will be useful for determining where a shell should be spawned when it
    // is created by this tank. It needs a slight offset so it appears from the front of the tank,
    // even if the tank is rotated. The shell should have the same angle as the tank.

    private double getShellX() {
        return getX() + Constants.TANK_WIDTH / 2 + 45.0 * Math.cos(getAngle()) - Constants.SHELL_WIDTH / 2;
    }

    private double getShellY() {
        return getY() + Constants.TANK_HEIGHT / 2 + 45.0 * Math.sin(getAngle()) - Constants.SHELL_HEIGHT / 2;
    }

    private double getShellAngle() {
        return getAngle();
    }

    protected static void checkHealth(Entity entity) {

        entity.currentHealth = entity.currentHealth - 5;
    }

    //makes sure that entinty does not go off screen if a tank and removes object if a shell goe off screen
    public abstract void checkBounds(GameWorld gameWorld);

    //Brain for moving object
    public abstract boolean move(GameWorld gameWorld);

    protected abstract void turnRight(double tankTurnSpeed);

    protected abstract void turnLeft(double tankTurnSpeed);

    //bound the entity by adding the width of an entity
    public abstract double getXBounds();

    public abstract double getYBounds();

}
