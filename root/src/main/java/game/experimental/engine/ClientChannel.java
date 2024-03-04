package game.experimental.engine;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

public class ClientChannel {

    public final int clientId;
    public int channelId;
    private BoundingBox userViewBox;

    /**
     * Representation for client in the Engine
     * @param clientId id of client
     */
    public ClientChannel(int clientId){
        this.clientId = clientId;
    }

}
