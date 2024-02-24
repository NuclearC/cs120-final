package game.experimental.engine;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

import java.util.ArrayList;

/**
 * Represents a Room in the game.
 */
public class Room implements Settings{

    private int id;
    private QuadTree<Entity> quadTree;


    private PlayerEntity[] playerEntities = new PlayerEntity[MAX_NUMBER_OF_PLAYERS];
    private PlayerEntity createPlayer(int id, int ownerID){

        Vector2F playerPosition = new Vector2F(100, 100); // TODO

        PlayerEntity player = new PlayerEntity(playerPosition, id, ownerID);//add function to get the position vector

        return player;
    }

    public void addPlayer(int ownerID){
        PlayerEntity player;
        for(int i = 0; i < playerEntities.length; i++)
            if(playerEntities[i] == null){
                player = createPlayer(i, ownerID);
                // insert the player into the quadtree for collision checks
                quadTree.insert(player, player.getBoundingBox());

                playerEntities[i] = player;

                break;
            }


    }

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
        if(quadTree == null)
            quadTree = new QuadTree<>(null, new BoundingBox(new Vector2F(), new Vector2F(MAP_WIDTH,MAP_HEIGHT)));
    }

    /**
     * Generates a new CollidableEntity randomly.
     * @return collidableEntity
     */
    public CollideableEntity generateCollidable(){
        float randomX = (float)Math.random() * Settings.MAP_WIDTH;
        float randomY = (float)Math.random() * Settings.MAP_HEIGHT;
        return new CollideableEntity(new Vector2F(randomX, randomY),this.getNextColliadbleId(),this.id);
    }

    /**
     * Fills the game collidable entities.
     */
    public void fillMapWithCollidables(){
       /* for(int i=0; i < this.NUMBER_OF_COLLIDABLES; i++){
            collidableEntitiesList.add(generateCollidable());
        }*/
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
       /// return collidableEntitiesList.size();
        return -12341234;
    }
}
