package game.experimental.engine;

public class Main {
    public static void main(String[] args) {
        Engine myEngine = Engine.getInstance();             // An Engine is created.
        Client firstClient = new Client(1);
        Client secondClient = new Client(1);
        Client thirdClient = new Client(1);
        myEngine.addClientChannel(firstClient.getChannelInstance());  // adds a player for first client to room 0.
        myEngine.addClientChannel(secondClient.getChannelInstance());  // adds a player for first client to room 0.
        myEngine.addClientChannel(thirdClient.getChannelInstance());  // adds a player for first client to room 0.
        myEngine.runEngineFrame();
    }
}