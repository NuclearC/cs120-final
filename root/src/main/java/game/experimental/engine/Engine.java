package game.experimental.engine;

import game.experimental.utils.Vector2F;

import java.util.ArrayList;

/**
 * Represents the Engine of the game. Starts the running  of the main loop.
 * Keeps track of the Worlds.
 */
public class Engine {
    private final ArrayList<World> worlds = new ArrayList<>();
    private final ClientChannel[] clientChannels = new ClientChannel[Settings.MAX_NUMBER_OF_CLIENTS];
    private static final Engine INSTANCE = new Engine();


    private Engine(){
        System.out.println("Engine created...");
        worlds.add(new World(worlds.size()));
        worlds.get(0).addRoom(1);
    }

    public static Engine getInstance(){
        return INSTANCE;
    }


    public void runEngineFrame(){
        try {
            System.out.println();
            System.out.println("Engine Simulated");
            Thread.sleep((long)(1000.0f / Settings.ENGINE_FRAMERATE));

        } catch (InterruptedException e) {
            System.out.println("couldn't sleep....");
        }
        for (World world : worlds) {
            world.simulate();
        }
    }


    /**
     * Gets an instance of the ClientChannel class
     * and decides to which room connect it to.
     * Prints a message if adding player failed.
     * @param newChannel ClientChannel instance
     */
    public void addClientChannel(ClientChannel newChannel){

        int worldID = decideWorldID(newChannel);
        try {
            System.out.println("ClientChannel connected... ");
            worlds.get(worldID).addPlayer(newChannel);
            newChannel.setWorldId(worldID);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ClientChannel connection failed !!!");
            System.out.println("Number of clients Exceeded");
        }
    }

    public int decideWorldID(ClientChannel newChannel){
        return 0;
    }

    private int getNextClientID() throws ArrayIndexOutOfBoundsException{
        for (int i = 0; i < clientChannels.length; i++){
            if (clientChannels[i] == null){
                return i;
            }
        }
        //we need to change the type of exception
        throw  new ArrayIndexOutOfBoundsException("Number of clients exceeded");     // TODO
    }

    public ArrayList<Entity> getViewBoxData(ClientChannel channel){
        return worlds.get(channel.getWorldId()).getViewBoxData(channel.getRoomId(), channel.getPlayerId());
    }


}
