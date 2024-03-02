package game.experimental.engine;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

/**
 * Represents a Room in the game.
 */
public class Room implements Settings{
    private int id;
    private Level level;
    private QuadTree<Entity> quadTree;

    private final PlayerEntity[] playerEntities;
    private final CollectableEntity[] staticCollectables;
    private final CollectableEntity[] movingCollectables;
    /**
     * Creates a new instance of the Room.
     */
    public Room(int id, int level){
        this.id = id;
        this.level = Level.getLevel(level);

        playerEntities = new PlayerEntity[this.level.MAX_NUMBER_OF_PLAYERS];
        staticCollectables = new CollectableEntity[this.level.MAX_NUMBER_OF_STATIC_COLLECTABLES];
        movingCollectables = new CollectableEntity[this.level.MAX_NUMBER_OF_MOVING_COLLECTABLES];

        createQuadTree();
        fillMapWithCollidables();
    }


    /**
     * Simulates the game inside one room.
     */
    public void simulate(){
        // checkCollide(); TODO;

        System.out.println("\tRoom simulated "+ this.getId());

        for (int i = 0; i < level.MAX_NUMBER_OF_PLAYERS; i++){
            if (playerEntities[i] != null)
                playerEntities[i].simulate();
        }
    }

    /**
     * Adds a new player to the Room for the given Client id.
     * @param ownerID id of the Client with whom the player will be associated.
     */
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
     * Creates a new Player with the given id and with the id of the Client with whom the player is associated.
     * @param id id of the player
     * @param ownerID Id of the Client
     * @return created Player entity.
     */
    private PlayerEntity createPlayer(int id, int ownerID){

        float randomX = (float)Math.random() * Settings.MAP_WIDTH;
        float randomY = (float)Math.random() * Settings.MAP_HEIGHT;

        Vector2F playerPosition = new Vector2F(randomX, randomY); // TODO

        PlayerEntity player = new PlayerEntity(playerPosition, id, ownerID);//add function to get the position vector

        return player;
    }


    /**
     * Removes a player from the room.
     * @param ownerId id of the client whose player is to be removed.
     */
    public void removePlayer(int ownerId){
        for(PlayerEntity player: playerEntities){
            if(player.ownerID == ownerId){
                playerEntities[player.getId()] = null;
                return;
            }
        }
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
