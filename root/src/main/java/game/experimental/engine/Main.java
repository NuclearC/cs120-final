package game.experimental.engine;

public class Main {
    public static void main(String[] args) {
        Engine myEngine = Engine.getInstance();             // An Engine is created.

        myEngine.addClientChannel(0);  // adds a player for first client to room 0.
        myEngine.addClientChannel(0);  // adds a player for first client to room 0.
        myEngine.addClientChannel(0);  // adds a player for first client to room 0.
        myEngine.runEngineFrame();
    }
}