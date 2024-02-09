/**
 * Represents the Base class object for all other objects in the project.
 * Implements the main methods and instance variables that are common in all objects.
 */
public class  Entity {

    protected float positionX;
    protected float positionY;
    protected int id;
    protected int ownerID;

    /**
     * Constructor class of the Entity object.
     *
     * @param positionX X coordinate of the Entity object
     * @param positionY X coordinate of the Entity object
     * @param id unique ID of the object
     * @param ownerID ID of the owner
     */
    public Entity(float positionX, float positionY, int id, int ownerID){
        this.positionX = positionX;
        this.positionY = positionY;
        this.id = id;
        this.ownerID = ownerID;
    }

    /**
     * Returns the X position of the Entity type object.
     * @return x coordinate of the Entity.
     */
    public float getPositionX() {
        return positionX;
    }

    /**
     * Returns the Y position of the Entity type object.
     * @return y coordinate of the Entity.
     */
    public float getPositionY() {
        return positionY;
    }


    /**
     * Sets the new Y coordinate of the Entity.
     * @param newPositionY new Y coordinate of the Entity.
     */
    public void setPositionY(float newPositionY){
        this.positionY = newPositionY;
    }


    /**
     * Returns the ID position of the Entity type object.
     * @return x coordinate of the Entity.
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the new ID of the Entity.
     * @param id new id of the Entity.
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Returns the owners ID
     * @return ID of the owner Entity.
     */
    public int getOwnerID() {
        return ownerID;
    }


    /**
     * Sets new ownerID of the Entity.
     * @param ownerID new owner ID for the Entity.
     */
    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

}
