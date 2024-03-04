package game.experimental.engine;

import game.experimental.utils.Vector2F;

/**
 * Represents a Moving Collectable object in the game. Extends StaticCollectableEntity
 */
public class MovingCollectableEntity extends StaticCollectableEntity implements Movable{
    private Vector2F velocity;
    private final Vector2F impulse = new Vector2F();     // initilized to o

    public MovingCollectableEntity(Vector2F position, int id, int ownerID, int type){
        super(position, id, ownerID, type);
    }

    @Override
    public void setVelocity(Vector2F velocity) {
    }

    @Override
    public void setImpulse(Vector2F impulse) {}

    @Override
    public Vector2F getVelocity() {
        return velocity;
    }

    @Override
    public void move() {
        this.position = this.position.add(this.velocity);
        checkBoundaries();
    }

    @Override
    public boolean checkBoundaries() {
        return false;
    }
}
