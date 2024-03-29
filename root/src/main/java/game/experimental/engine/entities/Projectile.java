package game.experimental.engine.entities;

import game.experimental.engine.Settings;
import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

public class Projectile extends CollideableEntity implements Movable {

    private Vector2F velocity;
    private Vector2F impulse;
    public final int FORCE = 1;
    int SOME_FACTOR = 25;
    Boolean tobeRemoved;

    private static final float PROJECTILE_SIZE = 10.f;
    private static final float PI = (float)Math.PI;

    public Projectile(long beginTick,float angle, Vector2F position, int id, int ownerID) {

        super(beginTick, position.subtract(new Vector2F(
                        PROJECTILE_SIZE * 0.5f,
                        PROJECTILE_SIZE * 0.5f).subtract(new Vector2F((float) Math.cos(angle), (float) Math.sin(-angle)).multiply(22))),
                id, ownerID);

        this.velocity = new Vector2F((float) Math.cos(angle), (float) Math.sin(-angle)).multiply(SOME_FACTOR);
        this.impulse = new Vector2F();
        this.angle = angle;
        this.size = new Vector2F(PROJECTILE_SIZE, PROJECTILE_SIZE);
        this.tobeRemoved = false;

    }

    @Override
    public void simulate() {
        move();
        updateBoundingBox();
    }

    @Override
    public void setVelocity(Vector2F velocity) {
        this.velocity = velocity;
    }

    @Override
    public Vector2F getVelocity() {
        return this.velocity;
    }

    @Override
    public void setImpulse(Vector2F impulse) {
            this.impulse = impulse;
    }

    @Override
    public void move() {
        Vector2F newPosition = this.position.add(this.velocity);
        newPosition = newPosition.add(this.impulse);

        if (remainsWithinBoundary(newPosition)) {
            this.position = newPosition;
            this.impulse = new Vector2F();
        }
        else tobeRemoved = true;
    }

    @Override
    public boolean remainsWithinBoundary(Vector2F newPosition) {
        BoundingBox roomBox = new BoundingBox(new Vector2F(), new Vector2F(Settings.MAP_WIDTH, Settings.MAP_HEIGHT));
        BoundingBox projectile = new BoundingBox(newPosition, size);
        return roomBox.contains(projectile);
    }

    @Override
    public void onCollision(CollideableEntity collided) {
        System.out.println("PROJECTILE COLISION!!!!!!!!!!!!!!!!!!!! " + this.getClass());
        if(collided.getClass() == CollectableEntity.class){
            System.out.println("Entered");
            setImpulse(calculateImpulse(collided));
            return;
        }
        if(collided.getId() != this.ownerID) {
            tobeRemoved = true;
        }
    }


    public Boolean toBeRemoved(){
        return tobeRemoved;
    }
}
