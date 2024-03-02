package game.experimental.engine;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;
import game.experimental.utils.Vector4F;

public class Main {
    public static void main(String[] args) {
        int firstClient = 1;
        int secondClient = 2;
        int thirdClient = 3;

        World myWorld = new World();             // a new world is created with an empty room

        myWorld.addPlayer(firstClient, 0);  // adds a player for first client to room 0.
        myWorld.addPlayer(secondClient, 0);  // adds a player for second client to room 0.
        myWorld.addPlayer(thirdClient, 0);  // adds a player for third client to room 0.

        myWorld.simulate();
    }
}