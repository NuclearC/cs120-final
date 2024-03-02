package game.experimental.engine;

public enum PlayerCommand {
    SHOOT(1 << 0),
    UP(1 << 1),
    DOWN(1 << 2),
    RIGHT(1 << 3),
    LEFT(1 << 4);

    public final int key;
    private PlayerCommand(int key){
        this.key = key;
    }

    public boolean isSet(int key) {
        return (this.key & key) == this.key;
    }

    public int set(int key) {
        return key | this.key;
    }
}
