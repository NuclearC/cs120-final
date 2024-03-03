package game.experimental.engine;


import java.util.ArrayList;

/**
 * Main Environment of the game. Contains Rooms.
 */
public class World {
    private final int id;
    private ArrayList<Room> rooms;
    private final int DELAY = 5000;                      // Needs to be stored with other constants.

    /**
     * Creates a new instance of World.
     * Implicitly creates an empty Room.
     */
    public World(int id){
        this.id = id;
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

        System.out.println("World "+ id + " Simulated");
        for (Room room : rooms) {
            room.simulate();
        }

    }

    /**
     * Adds a new player to a Room.
     * @param owner Client object with whom player is connected.
     */
    public void addPlayer(Client owner){
        int roomId = decideRoom(owner);
        rooms.get(roomId).addPlayer(owner.id);
    }

    /**
     * NOT IMPLEMENTED
     * Should decide to which room add the new player based on the client characteristics.
     *
     * @param client client for whom the player is added.
     * @return the id of the room to which should be added.
     */
    private int decideRoom(Client client){
        return 0;
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
