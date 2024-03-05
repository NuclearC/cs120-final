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

    void setRoomId(int roomId);

    int getRoomId();

    int getId();

    void setUserCommandKey(int commandKey);
    void setCursorPosition(Vector2F cursorPosition);

    void sendControlData();

    BoundingBox getViewport();

    float getViewportZoom();

    void updateViewport();

    void update();

}
