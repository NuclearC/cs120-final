package game.experimental.engine;

import game.experimental.engine.connection.ClientChannel;
import game.experimental.engine.entities.Entity;
import game.experimental.engine.entities.PlayerEntity;
import game.experimental.utils.Clock;

import java.util.ArrayList;

/**
 * Represents the Engine of the game. Starts the running  of the main loop.
 * Keeps track of the Worlds.
 */
public class Engine {
    private final ArrayList<World> worlds = new ArrayList<>();
    private final ClientChannel[] clientChannels = new ClientChannel[Settings.MAX_NUMBER_OF_CLIENTS];
    private static final Engine INSTANCE = new Engine();

    private float lastFrameBeginTime;

    private Engine() {
        System.out.println("Engine created...");
        worlds.add(new World(worlds.size()));
        worlds.get(0).addRoom(1);

        lastFrameBeginTime = this.getSimulationTime();
    }

    /**
     * Get a singleton instance of an Engine
     * @return the instance
     */
    public static Engine getInstance(){
        return INSTANCE;
    }

    /**
     * Retrieve monotonic clock's value
     * @return a value in seconds
     */
    public float getSimulationTime() {
        return Clock.now();
    }

    /**
     * Get the time since the last frame was began
     * @return the time in seconds
     */
    public float getTimeSinceLastFrame() {
        return (getSimulationTime() - this.lastFrameBeginTime);
    }

    /**
     * Run a single frame of simulation. 
     */
    public void runEngineFrame() {
        final float currentTime = getSimulationTime();
        final float elapsedTime = currentTime - lastFrameBeginTime;

        final float requiredFrameTime = 1.0f / Settings.ENGINE_FRAMERATE;

        if (elapsedTime > requiredFrameTime) {
            lastFrameBeginTime = currentTime;
            
            for (World world : worlds) {
                world.simulate();
            }

            channelsUpdate();
        }
    }

    private void channelsUpdate(){
        for (ClientChannel channel: clientChannels) {
            if (channel != null) {
                channel.updateViewport();

                ArrayList<Entity> visibleEntities = worlds.get(channel.getWorldId()).getRoom(channel.getRoomId()).getEntitiesInRange(channel.getViewport());

                channel.setViewBoxData(visibleEntities);
            }
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
            // TODO: do proper way
            clientChannels[newChannel.getId()] = newChannel;
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ClientChannel connection failed !!!");
            System.out.println("Number of clients Exceeded");
        }
    }

    public int decideWorldID(ClientChannel newChannel){
        return 0;
    }

    public World getWorld(int id) {
        return worlds.get(id);
    }

    /**
     * Retrieve the ClientChannel with the specified ID
     * @param id the ID of clientChannel
     * @return the client channel with set ID or null if non existant
     */
    public ClientChannel getClientChannel(int id) {
        if (id < 0 || id > clientChannels.length) return null;

        return clientChannels[id];
    }

}
