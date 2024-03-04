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
    private final StaticCollectableEntity[] staticCollectables;
    private final StaticCollectableEntity[] movingCollectables;
    /**
     * Creates a new instance of the Room.
     */
    public Room(int id, int level){
        this.id = id;
        this.level = Level.getLevel(level);

        playerEntities = new PlayerEntity[this.level.MAX_NUMBER_OF_PLAYERS];
        staticCollectables = new StaticCollectableEntity[this.level.MAX_NUMBER_OF_STATIC_COLLECTABLES];
        movingCollectables = new StaticCollectableEntity[this.level.MAX_NUMBER_OF_MOVING_COLLECTABLES];

        createQuadTree();
        fillMapWithCollectable();
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
        for(int i = 0; i < playerEntities.length; i++)
            if(playerEntities[i] == null){
                PlayerEntity player = createPlayer(i, ownerID);
                // insert the player into the quadtree for collision checks
                quadTree.insert(player, player.getBoundingBox());
                playerEntities[i] = player;
                break;
            }
        System.out.println("\tPlayer Added to " + id + " Room");

    }


    /**
     * Creates a new Player with the given id and with the id of the Client with whom the player is associated.
     * @param id id of the player
     * @param ownerID Id of the Client
     * @return created Player entity.
     */
    private PlayerEntity createPlayer(int id, int ownerID){

       Vector2F playerPosition = Vector2F.randomVector(0,MAP_HEIGHT,0, MAP_HEIGHT); // TODO

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
                // shouldn't we remove it also from tree??????
                return;
            }
        }
    }

    /**
     * creates MovingCollectable with the give id and type in random position(we need to change this!!!!!!)
     * @param id the id of the collectable
     * @param type the type of the collectable
     * @return the created collectable
     */
    private StaticCollectableEntity createMovingCollectable(int id, int type){
        Vector2F position = Vector2F.randomVector(0,MAP_HEIGHT,0, MAP_HEIGHT); // TODO
        return new StaticCollectableEntity(position,id,-1,1);                       //TODO
    }

    /**
     * adds moving collectable to the room
     */
    public void addMovingCollectable(){
        for(int i = 0; i < movingCollectables.length; i++){
            if(movingCollectables[i] == null){
                StaticCollectableEntity collectables = createMovingCollectable(i, 0);//change the type TODO
                quadTree.insert(collectables, collectables.getBoundingBox());
                movingCollectables[i] = collectables;
                break;
            }
        }
    }

    /**
     * removed  the moving collectable form the room
     * @param id the id of the moving collectable that needs to be removed
     */
    public void removeMovingCollectable(int id){
        quadTree.remove(movingCollectables[id],movingCollectables[id].getBoundingBox());
        movingCollectables[id] = null;
    }
    /**
     * creates Static Collectable with the give id and type in random position(we need to change this!!!!!!)
     * @param id the id of the collectable
     * @param type the type of the collectable
     * @return the created collectable
     */
    private StaticCollectableEntity createStaticCollectable(int id, int type){
        Vector2F position = Vector2F.randomVector(0,MAP_HEIGHT,0, MAP_HEIGHT); // TODO
        return new StaticCollectableEntity(position,id,-1,1);//TODO
    }

    /**
     * removed  the static collectable form the room
     * @param id the id of the static collectable that needs to be removed
     */
    public void removeStaticCollectable(int id){
        quadTree.remove(staticCollectables[id],staticCollectables[id].getBoundingBox());
        staticCollectables[id] = null;
    }

    /**
     * Creates a new Quadtree.
     */
    public void createQuadTree(){
        if(quadTree == null)
            quadTree = new QuadTree<>(null, new BoundingBox(new Vector2F(), new Vector2F(MAP_WIDTH,MAP_HEIGHT)));
    }
    /**
     * adds static collectable to the room
     */
    public void addStaticCollectable(){
        for(int i = 0; i < staticCollectables.length; i++){
            if(staticCollectables[i] == null){
                StaticCollectableEntity collectables = createStaticCollectable(i, 0);//change the type TODO
                quadTree.insert(collectables, collectables.getBoundingBox());
                staticCollectables[i] = collectables;
                break;
            }
        }
    }


    /**
     * Fills the game collectable entities.Changes the
     */
    public void fillMapWithCollectable(){
        for(int i = 0; i < this.level.MAX_NUMBER_OF_MOVING_COLLECTABLES; i++)
            addMovingCollectable();
        for(int i = 0; i < this.level.MAX_NUMBER_OF_STATIC_COLLECTABLES; i++)
            addStaticCollectable();
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
