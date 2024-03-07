package game.experimental.engine;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

/**
 * Represents the Base class object for all other objects in the project.
 * Implements the main methods and instance variables that are common in all objects.
 */
public abstract class Entity {
    protected Vector2F size;
    protected Vector2F position;
    protected int id;
    protected int ownerID;
    protected BoundingBox boundingBox;
    protected float angle;

    protected long beginTick;

    public Entity(long beginTick) {
        this.beginTick = beginTick;
    }

    /**
     * Constructor class of the Entity object.
     *
     * @param position coordinate of the Entity object
     * @param id unique ID of the object
     * @param ownerID ID of the owner
     */
    public Entity(long beginTick, Vector2F position, int id, int ownerID){
        this(beginTick);

        this.position = position;
        this.id = id;
        this.ownerID = ownerID;
        this.angle = 0;
    }

    /**
     * Retrieve the tick count when this entity was created. 
     * @return the tick count (synchronized with Room's tickCount)
     */
    public long getBeginTick() {
        return this.beginTick;
    }

    /**
     * Gets the player's position
     * @return the position
     */
    public Vector2F getPosition() {
        return position.clone();
    }
    public Vector2F getSize() {
        return size.clone();
    }

    /**
     * Retrieve the position of the center for this entity. 
     * @return the Vector2F of center
     */
    public Vector2F getCenter() {
        return position.add(size.multiply(0.5f));
    }


    /**
     * Set the player's position
     * @param position the new position of the player
     */
    public void setPosition(Vector2F position) {
        this.position = position;
    }

    /**
     * Simulate the entity.
     */
    abstract public void simulate();


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

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return this.angle;
    }

    public BoundingBox getBoundingBox() {
        if (boundingBox == null)
            updateBoundingBox();
        return boundingBox;
    }

    public void updateBoundingBox(){
        this.boundingBox = new BoundingBox(this.position,this.size);
    }

//    /**
//     * Needs to formulated into what is going to be transferred and how
//     * @return Vector[] so far size and position vectors
//     */
//    public Vector2F[] getDrawableData(){
//        return new Vector2F[]{position, size};
//    }
}
