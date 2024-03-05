package game.experimental.engine;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

import java.util.ArrayList;


public interface ClientChannel {

    final Vector2F VIEWPORT_BASE = new Vector2F(640.f, 480.f);
    final float VIEWPORT_ZOOM_MIN = 0.5F;
    final float VIEWPORT_ZOOM_MAX = 1.5F; // todo: find proper values via trial-error method

    ArrayList<Entity> getViewBoxData();
    void setViewBoxData(ArrayList<Entity> visibleEntities);

    void setPlayerId(int playerId);
    int getPlayerId();
    void setRoom(Room room);
    Room getRoom();
    int getId();
    void unsetPlayer();

    BoundingBox getViewport();
    float getViewportZoom();

    void updateViewport();

    void update();

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
