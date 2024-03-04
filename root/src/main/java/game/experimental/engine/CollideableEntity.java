package game.experimental.engine;

import game.experimental.utils.*;

/**
 * Represents Collideable Entities, extends Entity class.
 */
abstract class CollideableEntity extends Entity {
    /**
     * Creates a new colladable entity.
     * @param position adkjfbadkjgnla
     * @param id id of the entity
     * @param ownerID id of the owner
     */
    public CollideableEntity(Vector2F position, int id, int ownerID){
        super(position, id, ownerID);
    }


    public void onCollisison(CollideableEntity entity){
    }

}
