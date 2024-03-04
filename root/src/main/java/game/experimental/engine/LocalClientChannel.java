package game.experimental.engine;

import game.experimental.utils.Vector2F;

import java.util.ArrayList;

public class LocalClientChannel implements ClientChannel{
    private final Engine localServer;
    private final int id;
    private int worldId;
    private int roomId;
    private int playerId;

    /**
     * Representation for client in the Engine
     * @param id id of the channel
     */
    public LocalClientChannel(int id) {
        this.id = id;
        localServer = Engine.getInstance();
        localServer.addClientChannel(this);
    }

    public ArrayList<Entity> getViewBoxData() {
        localServer.runEngineFrame();
        return localServer.getViewBoxData(this);
    }

    @Override
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public int getWorldId() {
        return worldId;
    }

    @Override
    public int getPlayerId() {
        return playerId;
    }

    @Override
    public int getRoomId() {
        return roomId;
    }

    @Override
    public void setWorldId(int worldId) {
        this.worldId = worldId;
    }

    @Override
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void unsetPlayer() {

    }
}
