package game.experimental.engine;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

import java.util.ArrayList;

public class LocalClientChannel implements ClientChannel{
    private final Engine localEngine;
    private final int id;
    private Room room;
    private int playerId;
    private BoundingBox viewport;

    private ArrayList<Entity> visibleEntities;

    // center of the camera for this specific client (calculated from PlayerEntities)
    public Vector2F viewportCenter;
    // zoom level of camera for this client
    // >1.0 zoom out
    // <1.0 zoom in
    private float viewportZoom;

    /**
     * Representation for client in the Engine
     * @param id id of the channel
     */
    public LocalClientChannel(int id) {
        this.id = id;

        localEngine = Engine.getInstance();
        localEngine.addClientChannel(this);

        viewport = new BoundingBox();
        viewportCenter = new Vector2F(0, 0);
        viewportZoom = 1.0f;
    }

    @Override
    public float getViewportZoom() {
        return viewportZoom;
    }

    @Override
    public void updateViewport() {
        viewportCenter = room.getPlayer(this.playerId).getCenter();
        viewport = new BoundingBox(viewportCenter.add(VIEWPORT_BASE.multiply(viewportZoom * -0.5f)), VIEWPORT_BASE.multiply(viewportZoom));
    }

    @Override
    public void setViewBoxData(ArrayList<Entity> visibleEntities) {
        this.visibleEntities = visibleEntities;
    }

    /**
     * Returns the entities currently visible on Client's screen.
     */
    public ArrayList<Entity> getViewBoxData() {
        return visibleEntities;
    }

    @Override
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public int getPlayerId() {
        return playerId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void unsetPlayer() {

    }

    @Override 
    public BoundingBox getViewport() {
        return viewport;
    }

    @Override
    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public Room getRoom() {
        return room;
    }
}
