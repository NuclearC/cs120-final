/**
 * Represents collectable objects.
 */
public class CollectableEntity extends ColladableEntity {

    private int value;
    private int life;

    /**
     * Creates a collectable object.
     * @param positionX X position of the entity
     * @param positionY y position of the entity
     * @param id id of the entity
     * @param ownerID ID of the owner entity
     * @param value value of the object
     * @param life life of the object
     */
    public CollectableEntity(float positionX, float positionY, int id, int ownerID, int value, int life) {
        super(positionX, positionY, id, ownerID);
    }

    /**
     * Returns the value of the collectable.
     * @return value of the collectable object.
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets a new value to the collectable.
     * @param value new value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Returns the life level of the entity.
     * @return life level of the entity.
     */
    public int getLife() {
        return life;
    }

    /**
     * Sets the new life level of the entity.
     * @param life new life level of the entity.
     */
    public void setLife(int life) {
        this.life = life;
    }
}
