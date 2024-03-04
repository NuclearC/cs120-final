package game.experimental.engine;

import game.experimental.utils.Vector2F;

import java.util.ArrayList;


public interface ClientChannel {
    ArrayList<Entity> getViewBoxData();
    void setPlayerId(int playerId);
    int getWorldId();
    int getPlayerId();
    int getRoomId();
    void setWorldId(int worldId);
    void setRoomId(int roomId);
    int getId();
    void unsetPlayer();

//    public final int clientId;
//    public int channelId;
//    private BoundingBox userViewBox;
//    private PlayerEntity player;
//
//    /**
//     * Representation for client in the Engine
//     * @param clientId id of client
//     */
//    public ClientChannel(int clientId){
//        this.clientId = clientId;
//    }
//
//    public void setPlayer(PlayerEntity player){
//        this.player = player;
//    }
//
//    public void unsetPlayer(){
//        this.player = null;
//    }
//
//    public Vector2F getDrawableData(){
//        return player.position;
//    }

}
