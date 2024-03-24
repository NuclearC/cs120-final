package game.experimental.engine;

/**
 * Collects all the settings needed for the Room class. Is implemented by Room.
 */
public interface Settings {
    float MAP_HEIGHT = 1000;
    float MAP_WIDTH = 1000;

    float ENGINE_FRAMERATE = 30f;    // to be changed to 20
    int MAX_NUMBER_OF_CLIENTS = 50;

    float PLAYER_MAX_VELOCITY = 2.5f;

    /**
     * Level enum is used to define the level of the room. It has three options LOW, MEDIUM, HIGH,
     * Each of them have the corresponding set of settings.
     */
    public enum Level{
        LOW(1),
        MEDIUM(2),
        HIGH(3);

        public final int MAX_NUMBER_OF_PLAYERS;
        public final int MAX_NUMBER_OF_MOVING_COLLECTABLES;
        public final int MAX_NUMBER_OF_STATIC_COLLECTABLES;

        Level(int value){
            switch (value){

                case 2:
                    MAX_NUMBER_OF_PLAYERS = 20;
                    MAX_NUMBER_OF_MOVING_COLLECTABLES = 1;
                    MAX_NUMBER_OF_STATIC_COLLECTABLES = 10;
                    break;

                case 3:
                    MAX_NUMBER_OF_PLAYERS = 30;
                    MAX_NUMBER_OF_MOVING_COLLECTABLES = 40;
                    MAX_NUMBER_OF_STATIC_COLLECTABLES = 40;
                    break;

                default:  // case of 1 and more
                    MAX_NUMBER_OF_PLAYERS = 10;
                    MAX_NUMBER_OF_MOVING_COLLECTABLES = 100;
                    MAX_NUMBER_OF_STATIC_COLLECTABLES = 100;

            }
        }

        /**
         * Returns the Level corresponding to the provided value
         * @param value integer value of the level
         * @return Level of the room.
         */
        public static Level getLevel(int value){
            if(value == 2)
                return MEDIUM;
            else if (value == 3)
                return HIGH;
            else
                return LOW;
        }
    }
}
