package game.experimental.engine.entities;

public enum TokenTypes {
    COIN(10, 0, 5),
    LIFE_KIT(0, 10, 5),
    MINE(0, -1, 5),
    MOVING_COIN(30, 0, 5);
    public final int valueGain;
    public final int lifeGain;
    public final int life;
        TokenTypes(int valueGain,int lifeGain, int life) {
            this.valueGain = valueGain;
            this.lifeGain = lifeGain;
            this.life = life;
        }
}
