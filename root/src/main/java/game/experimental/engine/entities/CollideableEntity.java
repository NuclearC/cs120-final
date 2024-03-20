package game.experimental.engine.entities;

import game.experimental.utils.*;

/**
 * Represents Collideable Entities, extends Entity class.
 */
abstract public class CollideableEntity extends Entity {
    /**
     * Creates a new colladable entity.
     * @param position adkjfbadkjgnla
     * @param id id of the entity
     * @param ownerID id of the owner
     */
    public CollideableEntity(long beginTick, Vector2F position, int id, int ownerID){
        super(beginTick, position, id, ownerID);
    }

    public CollideableEntity(long beginTick) {
        super(beginTick);
    }

    public void onCollision(CollideableEntity entity){
    }

    public Vector2F calculateImpulse(CollideableEntity other){

//        if (!(this instanceof Movable)){
//            return new Vector2F();   // Not good
//        }
        Vector2F line = this.getCenter().subtract(other.getCenter());
        Movable obj = (Movable) this;

        Vector2F vec = obj.getVelocity();
        if (vec.length() < 0.01){
            System.out.println("velocity 00000000000000");
            return new Vector2F();
        }

        if (other instanceof Movable){
            vec = vec.subtract(((Movable) other).getVelocity());
        }

        int direction = 1;
        if (Math.cos(line.getAngle(vec)) < 0){
            direction = -1;
        }
        line = line.getNormalized();
        return line.multiply((float)Math.cos(line.getAngle(vec))* vec.length()*direction);
    }

}
