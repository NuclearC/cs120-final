package game.experimental.engine;

/**
 * Collects all the settings needed for the Room class. Is implemented by Room.
 */
public interface Settings {
    float MAP_HEIGHT = 1000;
    float MAP_WIDTH = 1000;
    int MAX_NUMBER_OF_PLAYERS = 30;

    float ENGINE_FRAMERATE = 20.F;

    float PLAYER_MAX_VELOCITY = 100.f;
}
