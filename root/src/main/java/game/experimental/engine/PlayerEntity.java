package game.experimental.engine;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector;
import game.experimental.utils.Vector2F;

public class PlayerEntity extends Entity{
    private static final float PLAYER_DEFAULT_SIZE = 10.f;
    public PlayerEntity(Vector2F position, int id, int ownerID){
        super(position,id,ownerID);

        this.size = new Vector2F(PLAYER_DEFAULT_SIZE, PLAYER_DEFAULT_SIZE);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(this.position, this.size);
    }
}
