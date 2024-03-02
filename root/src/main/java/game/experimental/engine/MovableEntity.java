package game.experimental.engine;

import game.experimental.utils.Vector2F;

/**
 * Represents the API for all movable objects.
 */
abstract public class MovableEntity extends Entity {
    protected Vector2F velocity;
    protected Vector2F impulse;
    public void setImpulse(Vector2F impulse) {
        this.impulse = impulse.clone();
    }
    public void setVelocity(Vector2F velocity) {
        this.velocity = velocity.clone();
    }

    public Vector2F getVelocity() {
        return this.velocity.clone();
    }

    public MovableEntity() {}
    public MovableEntity(Vector2F position, int id, int ownerID) {
        super(position, id, ownerID);
    }

    protected void move() {
        this.position = this.position.add(this.velocity);
        this.position = this.position.add(this.impulse);
        this.impulse = new Vector2F();
        // additional check for out of bounds for position
        // additional check for velocity
    }


}

// { Movable Entity }
