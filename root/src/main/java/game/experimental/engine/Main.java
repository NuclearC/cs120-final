package game.experimental.engine;

public class Main {
    public static void main(String[] args) {
        Vector2F a = new Vector2F(1,0);
        Vector2F b = new Vector2F(0,1);
        System.out.println(a.getAngle(b));

        World myWorld = new World();
        myWorld.simulate();
    }
    final int a=0;
}