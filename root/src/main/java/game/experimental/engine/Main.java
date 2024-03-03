package game.experimental.engine;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;
import game.experimental.utils.Vector4F;

public class Main {
    public static void main(String[] args) {
        Engine myEngine = Engine.createEngine();             // An Engine is created.

        myEngine.connectClient(0);  // adds a player for first client to room 0.
        myEngine.connectClient(0);  // adds a player for first client to room 0.
        myEngine.connectClient(0);  // adds a player for first client to room 0.
        myEngine.simulate();
    }
}