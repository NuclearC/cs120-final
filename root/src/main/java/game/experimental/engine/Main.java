package game.experimental.engine;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;
import game.experimental.utils.Vector4F;

public class Main {
    public static void main(String[] args) {
        Vector4F a = new Vector4F(1,0);
        Vector4F b = new Vector4F(0,1);
        Vector2F c = new Vector2F(0,1);
//        System.out.println(a.add(b));
        System.out.println(a.add(c));
        System.out.println(a.getAngle(b));

//        World myWorld = new World();
//        myWorld.simulate();


    }
}