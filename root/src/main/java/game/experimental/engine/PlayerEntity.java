package game.experimental.engine;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;
import game.experimental.engine.Settings;

/**
 * Represents a player in the game.
 */
public class PlayerEntity extends CollideableEntity implements Movable{

    private Vector2F velocity;
    private Vector2F deltaVelocity;
    private Vector2F impulse;
    private int userCommandKey;
    private int userInputKey;
    private float userInputAngle;

    private int life = 0;
    public static final float PLAYER_DEFAULT_SIZE = 50.f;           // TODO  not the best place to keep it
    private static final float PLAYER_MAX_VELOCITY = 10.0f;
    private static final float PLAYER_MOVE_VELOCITY = 1.0f;

    public PlayerEntity(long beginTick, Vector2F position, int id, int ownerID){
        super(beginTick, position,id,ownerID);

        this.size = new Vector2F(PLAYER_DEFAULT_SIZE, PLAYER_DEFAULT_SIZE);

        this.velocity = new Vector2F();
        this.impulse = new Vector2F();
        this.deltaVelocity = new Vector2F();
        this.boundingBox = new BoundingBox(this.position, this.size);

    }

    public PlayerEntity(long beginTick){
        super(beginTick);
    }
    public void setUserInputKey(int inputKey) {
        this.userInputKey = inputKey;
    }

    public void setUserCommandKey(int commandKey){
        this.userCommandKey = commandKey;
    }
    public void setUserInputAngle(float angle){
        this.userInputAngle = angle;
    }

    @Override
    public void simulate() {
        // System.out.println("\t\tPlayer " + this.id + " simulated.");
        // System.out.print("\t\t From "+ this.position.toString() + "      To ");

        this.processActions();
        this.processVelocity();

//        float a = Settings.PLAYER_MAX_VELOCITY / Settings.ENGINE_FRAMERATE;

        this.move();
        // System.out.println(this.position.toString());
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

    @Override
    public void move() {
        Vector2F newPosition = this.position.add(this.velocity);
        newPosition = newPosition.add(this.impulse);

        if (remainsWithinBoundary(newPosition)){
            this.position = newPosition;
            this.impulse = new Vector2F();
        }else {
            float x = Math.min(Math.max(newPosition.getX(),0),Settings.MAP_WIDTH - size.getX());
            float y = Math.min(Math.max(newPosition.getY(),0),Settings.MAP_HEIGHT - size.getY());
            this.position = new Vector2F(x, y);
            System.out.println(this.position);
            System.out.println("this is the porblem");
        }

        updateBoundingBox();
    }

    @Override
    public boolean remainsWithinBoundary(Vector2F newPosition) {
        BoundingBox roomBox = new BoundingBox(new Vector2F(),new Vector2F(Settings.MAP_WIDTH, Settings.MAP_HEIGHT));
        BoundingBox playerBox = new BoundingBox(newPosition, size);
        return roomBox.contains(playerBox);
    }

    private void processActions() {

        deltaVelocity = new Vector2F(0, 0);
        for(PlayerCommand command: PlayerCommand.values()){
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
        System.out.println("Shot");
    }

    @Override
    public void onCollision(CollideableEntity collided){
        if (this == collided){
            return;
        }
        if(collided.getClass() == PlayerEntity.class) {
            System.out.println("collided with player");
            this.setImpulse(calculateImpulse((PlayerEntity)collided));

            float SMOOTHNESS_FACTOR = 0.5f;    // Try 0, 1 ,2, 3
            this.velocity = this.velocity.add(this.impulse.multiply(SMOOTHNESS_FACTOR));
        }
        else if(collided instanceof CollectableEntity){
            takeCollectible((CollectableEntity)collided);
            collided.onCollision(this);
        }
    }

    private void takeCollectible(CollectableEntity collectible){
        System.out.println(collectible.getClass() +  " is eaten");
        collectible.setLife(0);
        this.life += collectible.getValue();
    }

}
