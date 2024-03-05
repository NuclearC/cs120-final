package game.experimental.engine;

/**
 * So far just a placeholder for Client class.
 */
public class Client {
    public final int id;
    public int type;
    private ClientChannel channelInstance;
    private ConnectionMode connectionMode;

    public Client(int id){
        this.id = id;
        this.type = 0;    // not that it decides in which room the player will be placed.
    }

    private void makeLocalChannel() {
        if (channelInstance == null) {
            channelInstance = new LocalClientChannel(id);
        }
    }
    private void makeRemoteChannel(){
        if (channelInstance == null){
//            channelInstance = new RemoteClientChannel(id);
        }
    }

    /**
     * Sets the connection mode to Local or Remote.
     * @param mode ConnectionMode.LOCAL or ConnectionMode.REMOTE
     */
    public void setConnectionMode(ConnectionMode mode){
        this.connectionMode = mode;
    }

    /**
     * Returns the ClientChannel of the client.
     * Pre-condition: ConnectionMode is set
     * Post-condition: Channel between client and server is established
     * @return the channel of the client
     */
    public ClientChannel getChannelInstance(){
        if(channelInstance == null){
            if (connectionMode == ConnectionMode.REMOTE){
                System.out.println("Remote Connection Not Implemented");
                System.exit(10);
            }
            else if(connectionMode == ConnectionMode.LOCALE){
                makeLocalChannel();
            }
        }

        return channelInstance;
    }

    public enum ConnectionMode{
        LOCALE,
        REMOTE;
    }
}
