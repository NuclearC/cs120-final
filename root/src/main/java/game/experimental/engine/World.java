package game.experimental.engine;
import java.util.ArrayList;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        System.out.println(rooms);
        Runnable simulationRunnable = new Runnable() {
            public void run() {
                System.out.println(rooms.toString());
                for (Room room: rooms){
                    room.simulate();
                }
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(simulationRunnable, 0, DELAY, TimeUnit.MILLISECONDS);
    }

    public int getNewRoomID(){
        return rooms.size();
    }
}
