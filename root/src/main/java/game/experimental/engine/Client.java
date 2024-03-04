package game.experimental.engine;

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

    public void makeLocalChannel() {
        if (channelInstance == null) {
            channelInstance = new LocalClientChannel(id);
        }
    }
    public void makeRemoteChannel(){
        if (channelInstance == null){
//            channelInstance = new RemoteClientChannel(id);
        }
    }

    public ClientChannel getChannelInstance(){
        return channelInstance;
    }
}
