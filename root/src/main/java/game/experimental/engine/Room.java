package game.experimental.engine;

import java.util.ArrayList;

/**
 * Represents a Room in the game.
 */
public class Room implements Settings{

    private int id;
    private final int NUMBER_OF_COLLIDABLES = 30;

    private ArrayList<ColladableEntity> collidableEntitiesList = new ArrayList<>();

    /**
     * Creates a new instance of the Room.
     */
    public Room(int id){
        this.id = id;
        createQuadTree();
        fillMapWithCollidables();
    }

    /**
     * Creates a new Quadtree.
     */
    public void createQuadTree(){
    }

    /**
     * Generates a new CollidableEntity randomly.
     * @return collidableEntity
     */
    public  ColladableEntity generateCollidable(){
        float randomX = (float)Math.random() * Settings.MAP_WIDTH;
        float randomY = (float)Math.random() * Settings.MAP_HEIGHT;
        return new ColladableEntity(randomX,randomY,this.getNextColliadbleId(),this.id);
    }

    /**
     * Fills the game collidable entities.
     */
    public void fillMapWithCollidables(){
        for(int i=0; i < this.NUMBER_OF_COLLIDABLES; i++){
            collidableEntitiesList.add(generateCollidable());
        }
    }

    /**
     * Simulates the game inside one room.
     */
    public void simulate(){
        System.out.println("Room simulated "+ this.getId());
    }

    /**
     * Returns the id of the room.
     * @return room id
     */
    public int getId(){
        return this.id;
    }

    /**
     * Returns the next free id for the collidable objects.
     * @return next id for the collidable.
     */
    public int getNextColliadbleId(){
        return collidableEntitiesList.size();
    }
}
