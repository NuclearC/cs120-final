package game.experimental.engine;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

import java.util.ArrayList;

/**
 * Represents a player in the game.
 */
public class PlayerEntity extends CollideableEntity implements Movable {

    private Vector2F velocity;
    private Vector2F deltaVelocity;
    private Vector2F impulse;
    private int userCommandKey;
    private ArrayList<Projectile> projectiles;

    private int life = 0;
    public static final float PLAYER_DEFAULT_SIZE = 50.f; // TODO not the best place to keep it
    private static final float PLAYER_MAX_VELOCITY = 10.0f;
    private static final float PLAYER_MOVE_VELOCITY = 1.0f;

    public PlayerEntity(long beginTick, Vector2F position, int id, int ownerID) {
        super(beginTick, position, id, ownerID);

        this.size = new Vector2F(PLAYER_DEFAULT_SIZE, PLAYER_DEFAULT_SIZE);
        this.life = 100;
        this.velocity = new Vector2F();
        this.impulse = new Vector2F();
        this.deltaVelocity = new Vector2F();
        this.boundingBox = new BoundingBox(this.position, this.size);
        this.projectiles = new ArrayList<>();
    }

    public PlayerEntity(long beginTick) {
        super(beginTick);
    }

    public void setUserCommandKey(int commandKey) {
        this.userCommandKey = commandKey;
    }

    @Override
    public void simulate() {

        this.processActions();
        this.processVelocity();

        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i) != null)
                projectiles.get(i).simulate();
        }

        // float a = Settings.PLAYER_MAX_VELOCITY / Settings.ENGINE_FRAMERATE;

        this.move();
    }

    @Override
    public void setVelocity(Vector2F velocity) {
        this.velocity = velocity;
        if (this.velocity.length() > PLAYER_MAX_VELOCITY) {
            this.velocity = this.deltaVelocity.getNormalized().multiply(PLAYER_MAX_VELOCITY);
        }
    }

    @Override
    public void setImpulse(Vector2F impulse) {
        this.impulse = impulse;
    }

    @Override
    public Vector2F getVelocity() {
        return this.velocity;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    @Override
    public void move() {
        Vector2F newPosition = this.position.add(this.velocity);
        newPosition = newPosition.add(this.impulse);

        if (remainsWithinBoundary(newPosition)) {
            this.position = newPosition;
            this.impulse = new Vector2F();
        } else {
            float x = Math.min(Math.max(newPosition.getX(), 0), Settings.MAP_WIDTH - size.getX());
            float y = Math.min(Math.max(newPosition.getY(), 0), Settings.MAP_HEIGHT - size.getY());
            this.position = new Vector2F(x, y);
        }

        updateBoundingBox();

    }

    @Override
    public boolean remainsWithinBoundary(Vector2F newPosition) {
        BoundingBox roomBox = new BoundingBox(new Vector2F(), new Vector2F(Settings.MAP_WIDTH, Settings.MAP_HEIGHT));
        BoundingBox playerBox = new BoundingBox(newPosition, size);
        return roomBox.contains(playerBox);
    }

    private void processActions() {

        deltaVelocity = new Vector2F(0, 0);
        if (PlayerCommand.SHOOT.isSet(this.userCommandKey)) {
            shoot();
        }
        for(PlayerCommand command : PlayerCommand.values()){
            if (command.isSet(this.userCommandKey)){

                deltaVelocity = deltaVelocity.add(command.deltaVector);
            }
        }
        // deltaVelocity.normalize();

    }

    private void processVelocity() {
        Vector2F playerMoveVector = deltaVelocity.getNormalized().multiply(PLAYER_MOVE_VELOCITY);

        this.setVelocity(velocity.multiply(0.9f).add(playerMoveVector));
    }

    private void shoot(){

        int index = getNextProjectileIndex();
        if (index == projectiles.size())
            projectiles.add(new Projectile(beginTick, angle, getCenter(), index, this.id));
        else
            projectiles.set(index, new Projectile(beginTick, angle, getCenter(), index, this.id));
    }

    @Override
    public void onCollision(CollideableEntity collided) {
        if (this == collided) {
            return;
        }
        if(collided.getClass() == PlayerEntity.class) {
            this.setImpulse(calculateImpulse((PlayerEntity)collided));
            float SMOOTHNESS_FACTOR = 0.5f;    // Try 0, 1 ,2, 3
            this.velocity = this.velocity.add(this.impulse.multiply(SMOOTHNESS_FACTOR));
        }
        else if(collided instanceof Projectile){
            Projectile projectile = (Projectile) collided;
            if(projectile.ownerID == this.id)
                return;
            this.life -= projectile.getForce();
            projectile.onCollision(this);
        }
        else if(collided instanceof CollectableEntity){
            takeCollectible((CollectableEntity)collided);
            collided.onCollision(this);
        }
    }

    private void takeCollectible(CollectableEntity collectible) {
        System.out.println(collectible.getClass() + " is eaten");
        collectible.setLife(0);
        this.life += collectible.getValue();
    }

    public void removeProjectileFromList(int projectileIndex) {
        this.projectiles.remove(null);
    }

    private int getNextProjectileIndex() {
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i) == null) {
                return i;
            }
        }
        return projectiles.size();
    }

    public int getLifeLevel(){
        return this.life;
    }
}
