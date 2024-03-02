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
     * Implicitly creates an empty Room.
     */
    public World(){
        rooms = new ArrayList<>();
        this.addRoom();
    }

    /**
     * Creates a room and stores it in the list of the World.
     */
    public void addRoom(){
        rooms.add(new Room(getNewRoomID(), 1));
    }

    /**
     * Starts the simulation of the game.
     * Calls the simulate() methods of all rooms.
     */
    public void simulate(){
        while (true) {
            try {
                System.out.println();
                System.out.println("World Simulated");
                Thread.sleep((long)(1000.0f / Settings.ENGINE_FRAMERATE));

            } catch (InterruptedException e) {
                System.out.println("couldnt sleep....");
            }
            for (Room room : rooms) {
                room.simulate();
            }
        }
    }

    /**
     * Adds a new player to a Room.
     * @param ownerId Id of the Client with whom player is connected.
     * @param roomId Id of the room where the new player is to be put.
     */
    public void addPlayer(int ownerId, int roomId){
        rooms.get(roomId).addPlayer(ownerId);
    }

    /**
     * Removes a player from a room.
     * @param ownerId id of the Client with whom the player is associated
     * @param roomId id of the room from where the player should be removed.
     */
    public void removePlayer(int ownerId, int roomId){
        rooms.get(roomId).removePlayer(ownerId);
    }

    /**
     * Returns ID for a new Room.
     * @return id for the new room.
     */
    private int getNewRoomID(){
        return rooms.size();
    }
}
