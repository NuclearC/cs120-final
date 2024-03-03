package game.experimental.engine;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents the Engine of the game. Starts the running  of the main loop.
 * Keeps track of the Worlds.
 */
public class Engine {
    private final ArrayList<World> worlds = new ArrayList<>();
    private final Client[] clients = new Client[Settings.MAX_NUMBER_OF_CLIENTS];
    private static final Engine SINGLE_ENGINE = new Engine();


    private Engine(){
        System.out.println("Engine created...");
        worlds.add(new World(worlds.size()));
    }

    public static Engine createEngine(){
        return SINGLE_ENGINE;
    }

    public void simulate(){
        System.out.println("Engine simulation started...");
        while (true) {
            try {
                System.out.println();
                System.out.println("Engine Simulated");
                Thread.sleep((long)(1000.0f / Settings.ENGINE_FRAMERATE));

            } catch (InterruptedException e) {
                System.out.println("couldnt sleep....");
            }
            for (World world : worlds) {
                world.simulate();
            }
        }
    }

    /**
     * Connects a client to a world.
     * @param worldID id of the world to be connected.
     */
    public void connectClient(int worldID){
        // new Client object is created.
        // for that particular client a new player is added to a Room.
        try {
            Client newClient = new Client(getNextClientID());
            System.out.println("Client connected... ");
            worlds.get(worldID).addPlayer(newClient);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Client connection failed !!!");
            System.out.println("Number of clients Exceeded");
        }
    }

    public int getNextClientID() throws ArrayIndexOutOfBoundsException{
        for (int i = 0; i < clients.length; i++){
            if (clients[i] == null){
                return i;
            }
        }
        // A better way of handling needs to be implemented
        throw  new ArrayIndexOutOfBoundsException("Number of clients exceeded");     // TODO
    }




}
