package game.experimental.engine.entities;

import game.experimental.utils.*;

/**
 * Represents collectable objects.
 */
public class CollectableEntity extends CollideableEntity {
    private static final Vector2F COLLECTABLE_DEFAULT_SIZE = new Vector2F(15.f, 15.f);

    private final int value;
    private int life;                   // life will be changed in the process
    private final Type type;

    /**
     * Creates a collectable object.
     * @param position position of the entity
     * @param id id of the entity
     * @param ownerID ID of the owner entity
     * @param type integer to determine the type : 1 - Triangle , 2 - Square, 3- Diamond
     */
    public CollectableEntity(long beginTick, Vector2F position, int id, int ownerID, int type) {
        super(beginTick, position, id, ownerID);
        this.size = COLLECTABLE_DEFAULT_SIZE;
        this.type = getType(type);
        this.value = this.type.value;
        this.life = this.type.life;
    }

    public CollectableEntity(long beginTick) {
        super(beginTick);
        this.type = getType(0);
        this.value = this.type.value;
        this.life = this.type.life;
    }

    @Override
    public void simulate() {
        if(this.life<0){
            //erase it from the screen
        }
    }

    /**
     * Returns the value of the collectable.
     * @return value of the collectable object.
     */
    public int getValue() {
        return value;
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


    /**
     * Sets the type of the Collectable. Should be called in the Constructor.
     * @param type
     */
    private Type getType(int type){
        switch(type){
            default:
                return Type.Triangle;
            case 2:
                return Type.Square;
            case 3:
                return  Type.Diamond;
        }
    }

    /**
     * Defines the possible types of the Collectible objects.
     * Add any specification needed.
     */
    public enum Type {
        Triangle(10, 3),
        Square(20, 150),
        Diamond(30, 200);
        public final int value;
        public final int life;
        Type(int value, int life){
            this.value = value;
            this.life = life;
        }

    }
    @Override
    public void onCollision(CollideableEntity collided){
        if(collided.getClass() == PlayerEntity.class || collided.getClass() == Projectile.class){
           //setLife(getLife() - 1);//damage sould be added TODO
            life--;
            if(collided.getClass() == Projectile.class){
                collided.onCollision(this);
            }

        }

    }
}
