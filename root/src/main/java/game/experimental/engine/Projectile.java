package game.experimental.engine;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

public class Projectile extends CollideableEntity implements Movable{

    private Vector2F velocity;
    int SOME_FACTOR = 25;
    public Projectile(long beginTick,float angle, Vector2F position, int id, int ownerID) {

        super(beginTick, position.add(new Vector2F((float)Math.cos(-angle), (float)Math.sin(-angle)).multiply(20)), id, ownerID);
        this.velocity = new Vector2F((float)Math.cos(-angle), (float)Math.sin(-angle)).multiply(SOME_FACTOR);
        this.size = new Vector2F(10,10);
    }

    @Override
    public void simulate() {
        move();
    }

    @Override
    public void setVelocity(Vector2F velocity) {

    }

    @Override
    public Vector2F getVelocity() {
        return this.velocity;
    }

    @Override
    public void setImpulse(Vector2F impulse) {

    }

    @Override
    public void move() {
        this.position = this.position.add(this.velocity);
    }

    @Override
    public boolean remainsWithinBoundary(Vector2F newPosition) {
        BoundingBox roomBox = new BoundingBox(new Vector2F(),new Vector2F(Settings.MAP_WIDTH, Settings.MAP_HEIGHT));
        BoundingBox projectile = new BoundingBox(newPosition, size);
        return roomBox.contains(projectile);
    }

}
