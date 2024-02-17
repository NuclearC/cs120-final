package game.experimental.engine;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

public class Main {
    public static void main(String[] args) {
        /*
        Vector2F a = new Vector2F(1,0);
        Vector2F b = new Vector2F(0,1);
        System.out.println(a.getAngle(b));

        World myWorld = new World();
        myWorld.simulate();


         */

        QuadTree<Integer> qt = new QuadTree<Integer>(null, new BoundingBox(new Vector2F(0.f, 0.f), new Vector2F(100.f, 100.f)));

        for (int i = 0; i< 100; i++) {
            Random r = new Random();
            if (false == qt.insert(i, new BoundingBox(new Vector2F(i ,i), new Vector2F(2.f, 2.f)))) {
                System.out.println("failed to insert " + i);
            }
        }
        ArrayList<Integer> xd = new ArrayList<>();
        qt.query(new BoundingBox(40.f, 40.f, 20.f, 20.f), xd);
        System.out.println(xd.size());


    }
    final int a=0;
}