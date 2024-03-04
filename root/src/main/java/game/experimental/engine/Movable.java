package game.experimental.engine;

import game.experimental.utils.Vector2F;

/**
 * Represents the API for all movable objects.
 */
public interface Movable {
    /**
     * Sets the velocity of the moving object,
     * @param velocity new velocity vector
     */
    void setVelocity(Vector2F velocity);

    /**
     * Returns the Velocity of the moving object.
     * @return velocity vector.
     */
    Vector2F getVelocity();

    /**
     * Sets the impulse vector of the moving object.
     * Impulse Vector is generated during collision.
     * @param impulse new impulse Vector
     */
    void setImpulse(Vector2F impulse);

    /**
     * Implements the moving functionality of the object.
     */
    void move();

    /**
     * While moving checks whether the object is still in the boundaries of the map.
     * @return true if the objects is within boundaries, false otherwise
     */
    boolean checkBoundaries();

}


