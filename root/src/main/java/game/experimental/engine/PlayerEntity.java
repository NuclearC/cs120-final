package game.experimental.engine;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

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

    private static final float PLAYER_DEFAULT_SIZE = 50.f;           // TODO  not the best place to keep it

    public PlayerEntity(Vector2F position, int id, int ownerID){
        super(position,id,ownerID);

        this.size = new Vector2F(PLAYER_DEFAULT_SIZE, PLAYER_DEFAULT_SIZE);

        this.velocity = new Vector2F();
        this.impulse = new Vector2F();
        this.deltaVelocity = new Vector2F();
        this.boundingBox = new BoundingBox(this.position, this.size);

    }
    public PlayerEntity(){
        super();
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
        System.out.println("\t\tPlayer " + this.id + " simulated.");
        System.out.print("\t\t From "+ this.position.toString() + "      To ");

        this.processActions();
        this.processVelocity();

//        float a = Settings.PLAYER_MAX_VELOCITY / Settings.ENGINE_FRAMERATE;

        this.move();
        System.out.println(this.position.toString());
    }

    @Override
    public void setVelocity(Vector2F velocity) {
        this.velocity = velocity;
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
        this.position = this.position.add(this.velocity);
        this.position = this.position.add(this.impulse);
        this.impulse = new Vector2F();
        updateBoundingBox();
    }

    @Override
    public boolean checkBoundaries() {
        return false;
    }

    private void processActions() {

        for(PlayerCommand command: PlayerCommand.values()){
            if(command.isSet(this.userCommandKey)){
                deltaVelocity = deltaVelocity.add(command.deltaVector);
            }
        }

    }
    private void processVelocity() {
        this.setVelocity(velocity.multiply(0.8f));                          // multiple cloning is performed
        this.setVelocity(velocity.add(deltaVelocity));                          // multiple cloning is performed TODO
    }

    private void shoot(){
        System.out.println("Shot");
    }

    @Override
    public void onCollision(CollideableEntity collided){
        if(collided.getClass() == PlayerEntity.class){
            System.out.println("collided with player");
        }
        else if(collided.getClass() == MovingCollectableEntity.class){
            collided.onCollision(this);
            System.out.println("collided with moving collectable");
        }
        else if(collided.getClass() == StaticCollectableEntity.class){
            collided.onCollision(this);
            System.out.println("collided with static collectable");

        }
    }

}
