package game.experimental.engine;

import java.util.ArrayList;

/**
 * Main Environment of the game. Contains Rooms.
 */
public class World {

    private ArrayList<Room> rooms;
    private final int DELAY = 5000;                      // Needs to be stored with other constants.

    /**
     * Creates a new instance of World.
     * Implicitly creates a Room.
     */
    public World(){
        rooms = new ArrayList<>();
        this.addRoom();
    }

    /**
     * Creates a room and stores it in the list of the World.
     */
    public void addRoom(){

        rooms.add(new Room(getNewRoomID()));
    }

    /**
     * Starts the simulation of the game.
     * Calls the simulate() methods of all rooms.
     */
    public void simulate(){
        rooms.get(0).addPlayer(1);
        rooms.get(0).addPlayer(2);
        while (true) {
            try {
                Thread.sleep((long)(1.0f / Settings.ENGINE_FRAMERATE));

            } catch (InterruptedException e) {
                System.out.println("couldnt sleep....");
            }
            for (Room room : rooms) {
                room.simulate();
            }
        }
    }

    public int getNewRoomID(){
        return rooms.size();
    }
}
