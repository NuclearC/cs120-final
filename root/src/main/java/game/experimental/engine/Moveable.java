package game.experimental.engine;

public interface Moveable {
    void updatePosition(float newX, float newY);
    void setSpeed();
    float getSpeed();

    void move();

}
