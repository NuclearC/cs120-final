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
    public CollideableEntity(Vector2F position, int id, int ownerID){
        super(position, id, ownerID);
    }

    public CollideableEntity() {
    }

    public void onCollision(CollideableEntity entity){
    }

    public Vector2F calculateImpulse(CollideableEntity other){
        if (!(this instanceof Movable)){
            return new Vector2F();   // Not good
        }
        Vector2F line = this.getCenter().subtract(other.getCenter());
        Movable obj = (Movable) this;
        int direction = 1;
        if (Math.cos(line.getAngle(obj.getVelocity())) < 0){
            direction = -1;
        }
        line = line.getNormalized();
        Vector2F impulse = line.multiply((float)Math.cos(line.getAngle(obj.getVelocity()))* obj.getVelocity().length()*direction);
        System.out.println(1+" " +impulse);
        return impulse;
    }

}
