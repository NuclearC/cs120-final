package game.experimental.engine.entities;

import game.experimental.engine.Settings;
import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

/**
 * Represents a Moving Collectable object in the game. Extends StaticCollectableEntity
 */
public class MovingCollectableEntity extends CollectableEntity implements Movable {
    private Vector2F velocity = new Vector2F();
    private Vector2F destination = new Vector2F();
    private Vector2F impulse = new Vector2F();     // initilized to o

    public MovingCollectableEntity(long beginTick, Vector2F position, int id, int ownerID, int type){
        super(beginTick, position, id, ownerID, type);
        destination = position;
    }

    public MovingCollectableEntity(long beginTick) {
        super(beginTick);
    }

    @Override
    public void setVelocity(Vector2F velocity) {
    }

    @Override
    public void setImpulse(Vector2F impulse) {
        this.impulse = impulse;
    }

    @Override
    public Vector2F getVelocity() {
        return velocity;
    }

    public Vector2F getDestination(){return this.destination;}

    public void setDestination(Vector2F destination){
        this.destination = destination;
    }
    private void changeDestination(){

        Vector2F topCorner = new Vector2F(Math.max(destination.getX() - Settings.MAP_WIDTH / 8, 0),Math.max(destination.getY() - Settings.MAP_HEIGHT / 8,0));
        Vector2F bottomCorner = new Vector2F(Math.min(destination.getX() + Settings.MAP_WIDTH / 8, Settings.MAP_WIDTH),Math.min(destination.getY() + Settings.MAP_HEIGHT / 8,Settings.MAP_HEIGHT));
        //the boundaries of the random x,y can be changed and added to the settings
        destination = Vector2F.randomVector(topCorner.getX(), bottomCorner.getX(), topCorner.getY(), bottomCorner.getY());
//        System.out.println("CHANGE...................Destination" + destination);
        processVelocity();
    }

    @Override
    public void move() {
        if(position.subtract(destination).length() < 1)
            changeDestination();
        Vector2F newPosition = this.position.add(this.velocity);
        if (remainsWithinBoundary(newPosition)) {
            this.position = newPosition;
           // this.impulse = new Vector2F();
        } else {
            changeDestination();
        }
        updateBoundingBox();
    }

    private void processVelocity(){
        Vector2F line = destination.subtract(position);
        if (line.length() < 0.01){                // equals 0
            velocity = new Vector2F();
        }
        line = line.getNormalized();
        velocity = line.multiply((float) Math.random()*2 + 1f);//random should have range

    }

    @Override
    public boolean remainsWithinBoundary(Vector2F newPosition) {
        BoundingBox roomBox = new BoundingBox(new Vector2F(), new Vector2F(Settings.MAP_WIDTH, Settings.MAP_HEIGHT));
        BoundingBox collectableBox = new BoundingBox(newPosition, size);
        return roomBox.contains(collectableBox);
    }

    @Override
    public void simulate(){
       // this.processVelocity();
        this.move();
    }
    public void onCollision(CollideableEntity collided){
        if(this == collided)
            return;
        if(collided.getClass() == Projectile.class){
            setLife(getLife() - ((Projectile)collided).FORCE);//damage sould be added TODO
            collided.onCollision(this);
        }
        else
            this.setImpulse(calculateImpulse(collided));

        float SMOOTHNESS_FACTOR = 0.5f;    // i coppied from player need to be chaked
        this.velocity = this.velocity.add(this.impulse.multiply(SMOOTHNESS_FACTOR));
        this.setImpulse(new Vector2F());

        //System.out.println("collided with moving collectable");
        /*
        if(collided.getClass() == PlayerEntity.class){

        }
         else if(collided.getClass() == MovingCollectableEntity.class){
            this.setImpulse(calculateImpulse((MovingCollectableEntity)collided));
            float SMOOTHNESS_FACTOR = 0.5f;    // i coppied from player need to be chaked
            this.velocity = this.velocity.add(this.impulse.multiply(SMOOTHNESS_FACTOR));
            System.out.println("collided with moving collectable");
        }
        else if(collided.getClass() == CollectableEntity.class){
            System.out.println("collided with static collectable");

            setImpulse(new Vector2F());//TODO

        }*/
    }

}
