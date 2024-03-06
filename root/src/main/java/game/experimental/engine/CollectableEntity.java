package game.experimental.engine;

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
    public CollectableEntity(Vector2F position, int id, int ownerID, int type) {
        super(position, id, ownerID);
        this.size = COLLECTABLE_DEFAULT_SIZE;
        this.type = getType(type);
        this.value = this.type.value;
        this.life = this.type.life;
    }

    public CollectableEntity() {
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
        Triangle(10, 100),
        Square(20, 150),
        Diamond(30, 200);
        public final int value;
        public final int life;
        Type(int value, int life){
            this.value = value;
            this.life = life;
        }
        Type(){
            value = 0;
            life = 0;
        }
    }
    public void onCollision(CollideableEntity collided){
        if(collided.getClass() == new PlayerEntity().getClass()){
            //life should get decreased here
            //should be implemented on how to get removed TODO
            // System.out.println("collided with player");
            //it should ignore this case
        }
        else if(collided.getClass() == new MovingCollectableEntity().getClass()){
            // System.out.println("collided with moving collectable");
            ((MovingCollectableEntity) collided).setImpulse(new Vector2F());//TODO
        }
        else if(collided.getClass() == new CollectableEntity().getClass()){
            // System.out.println("collided with static collectable");

        }
    }
}
