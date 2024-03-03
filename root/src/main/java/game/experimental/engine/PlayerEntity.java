package game.experimental.engine;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;
public class PlayerEntity extends MovableEntity {

    private Vector2F velocity;
    private Vector2F deltaVelocity;
    private Vector2F impulse;
    private int userInputKey;
    private float userInputAngle;

    private static final float PLAYER_DEFAULT_SIZE = 10.f;
    public PlayerEntity(Vector2F position, int id, int ownerID){
        super(position,id,ownerID);

        this.size = new Vector2F(PLAYER_DEFAULT_SIZE, PLAYER_DEFAULT_SIZE);

        this.velocity = new Vector2F();
        this.impulse = new Vector2F();
        this.deltaVelocity = new Vector2F();
    }

    public void setUserInputKey(int inputKey){
        this.userInputKey = inputKey;
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
    public BoundingBox getBoundingBox() {
        return new BoundingBox(this.position, this.size);
    }

    @Override
    public void setImpulse(Vector2F impulse) {
        this.impulse = impulse;
    }

    private void processActions() {

        if (PlayerCommand.SHOOT.isSet(this.userInputKey)) {
            shoot();
            for(PlayerCommand command: PlayerCommand.values()){
                if(command.isSet(this.userInputKey)){
                    deltaVelocity = deltaVelocity.add(command.deltaVector);
                }
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
}
