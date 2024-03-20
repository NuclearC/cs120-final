package game.experimental.engine.entities;

import game.experimental.utils.Vector2F;

/**
 * Represents collectable objects.
 */
public class CollectableEntity extends CollideableEntity {
    private static final Vector2F COLLECTABLE_DEFAULT_SIZE = new Vector2F(15.f, 15.f);

    private int life;                   // life will be changed in the process
    public final TokenType TYPE;

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
        this.TYPE = getType(type);
        this.life = this.TYPE.life;
    }

    public CollectableEntity(long beginTick) {
        super(beginTick);
        this.TYPE = getType(0);
        this.life = this.TYPE.life;
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
    public int getValueGain() {
        return TYPE.valueGain;
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
    private TokenType getType(int type){
        switch(type){
            default:
                return TokenType.COIN;
            case 2:
                return  TokenType.LIFE_KIT;
            case 3:
                return TokenType.MINE;
            case 4:
                return  TokenType.MOVING_COIN;
        }
    }



    @Override
    public void onCollision(CollideableEntity collided){

        if(collided.getClass() == Projectile.class){
            collided.onCollision(this);
        }
        /*if(collided.getClass() == PlayerEntity.class || collided.getClass() == Projectile.class){
           //setLife(getLife() - 1);//damage sould be added TODO
            life--;
            if(collided.getClass() == Projectile.class){
                collided.onCollision(this);
            }

        }*/

    }
}
