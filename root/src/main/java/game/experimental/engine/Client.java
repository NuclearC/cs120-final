package game.experimental.engine;

/**
 * So far just a placeholder for Client class.
 */
public class Client {
    public final int id;
    public int type;
    public Client(int id){
        this.id = id;
        this.type = 0;    // not that it decides in which room the player will be placed.
    }
}
