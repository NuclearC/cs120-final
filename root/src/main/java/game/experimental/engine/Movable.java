package game.experimental.engine;

/**
 * Represents the API for all movable objects.
 */
public interface Movable {
    void updatePosition(float newX, float newY);
    void setSpeed();
    float getSpeed();

    void move();

}
