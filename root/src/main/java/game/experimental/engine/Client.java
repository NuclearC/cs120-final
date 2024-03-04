package game.experimental.engine;

import java.rmi.Remote;

/**
 * So far just a placeholder for Client class.
 */
public class Client {
    public final int id;
    public int type;
    private ClientChannel channelInstance;

    public Client(int id){
        this.id = id;
        this.type = 0;    // not that it decides in which room the player will be placed.
    }

    private ClientChannel createLocalChannel(){

        return new ClientChannel(id);
    };
    private ClientChannel createRemoteChannel(){
        return new ClientChannel(id);
    };

    public ClientChannel getChannelInstance(){
        return channelInstance;
    }
}
