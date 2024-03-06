package game.experimental.engine;

import game.experimental.utils.Vector2F;

/**
 * Represents a Moving Collectable object in the game. Extends StaticCollectableEntity
 */
public class MovingCollectableEntity extends StaticCollectableEntity implements Movable{
    private Vector2F velocity;
    private final Vector2F impulse = new Vector2F();     // initilized to o

    public MovingCollectableEntity(Vector2F position, int id, int ownerID, int type){
        super(position, id, ownerID, type);
    }

    public MovingCollectableEntity() {
        super();
    }

    @Override
    public void setVelocity(Vector2F velocity) {
    }

    @Override
    public void setImpulse(Vector2F impulse) {}

    @Override
    public Vector2F getVelocity() {
        return velocity;
    }

    @Override
    public void move() {
        this.position = this.position.add(this.velocity);
        checkBoundaries();
        this.updateBoundingBox();
    }

    @Override
    public boolean checkBoundaries() {
        return false;
    }

    @Override
    public void simulate(){
        if(this.getLife() < 0){
            //it should be erased
        }
    }
    public void onCollision(CollideableEntity collided){
        if(collided.getClass() == PlayerEntity.class){
            System.out.println("collided with player");
            //it should ignore it, cause it will be handeled by the player
        }
        else if(collided.getClass() == MovingCollectableEntity.class){
            System.out.println("collided with moving collectable");
            setImpulse(new Vector2F());//TODO
            ((MovingCollectableEntity) collided).setImpulse(new Vector2F());//TODO
        }
        else if(collided.getClass() == StaticCollectableEntity.class){
            System.out.println("collided with static collectable");

            setImpulse(new Vector2F());//TODO

        }
    }
}
