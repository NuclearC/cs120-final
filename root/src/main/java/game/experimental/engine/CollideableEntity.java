package game.experimental.engine;

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


    public Vector2F calculateImpulse(Movable other){

        Vector2F line = this.getCenter().subtract(((CollideableEntity)other).getCenter());
        Movable obj = (Movable) this;
        Vector2F vec = obj.getVelocity();
        if (vec.length() < 0.01){                // equals 0
            return new Vector2F();
        }
        vec.subtract(((Movable) other).getVelocity());
        int direction = 1;
        if (Math.cos(line.getAngle(vec)) < 0){
            direction = -1;
        }
        line = line.getNormalized();
        return line.multiply((float)Math.cos(line.getAngle(vec))* vec.length()*direction);
    }
    public Vector2F calculateImpulse(CollectableEntity other){
        Vector2F line = this.getCenter().subtract(other.getCenter());
        Movable obj = (Movable) this;
        Vector2F vec = obj.getVelocity();
        if (vec.length() < 0.01){                // equals 0
            return new Vector2F();
        }
        vec.subtract(other.getPosition());
        int direction = 1;
        if (Math.cos(line.getAngle(vec)) < 0){
            direction = -1;
        }
        line = line.getNormalized();
        return line.multiply((float)Math.cos(line.getAngle(vec))* vec.length()*direction);
    }

}
