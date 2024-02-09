/**
 * Cre
 */
public class ColladableEntity extends Entity {
    /**
     * Creates a new colladable entity.
     * @param positionX X position of the entity
     * @param positionY Y position of the entity
     * @param id id of the entity
     * @param ownerID id of the owner
     */
    public ColladableEntity(float positionX, float positionY, int id, int ownerID){
        super(positionX, positionY, id, ownerID);
    }

    // maybe some method to detect collision
}
