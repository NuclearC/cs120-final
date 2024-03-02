package game.experimental.engine;

import game.experimental.utils.Vector2F;

public enum PlayerCommand {
    SHOOT(1 << 0, new Vector2F()),
    UP(1 << 1, new Vector2F(5.f,0.f)),
    DOWN(1 << 2, new Vector2F(-5.f, 0.f)),
    RIGHT(1 << 3, new Vector2F(0.f,5.f)),
    LEFT(1 << 4, new Vector2F(0.f,-5.f));

    public final Vector2F deltaVector;

    public final int key;
    private PlayerCommand(int key, Vector2F deltaVector){
        this.key = key;
        this.deltaVector = deltaVector;
    }

    public boolean isSet(int key) {
        return (this.key & key) == this.key;
    }

    public int set(int key) {
        return key | this.key;
    }
}
