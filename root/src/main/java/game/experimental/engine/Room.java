package game.experimental.engine;

import game.experimental.gl.Gizmos;
import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

import java.util.ArrayList;

/**
 * Represents a Room in the game.
 */
public class Room implements Settings{
    private int id;
    private Level level;
    private QuadTree<Entity> quadTree;

    private final PlayerEntity[] playerEntities;
    private final StaticCollectableEntity[] staticCollectables;
    private final MovingCollectableEntity[] movingCollectables;

    /**
     * Creates a new instance of the Room.
     */
    public Room(int id, int level){
        this.id = id;
        this.level = Level.getLevel(level);

        playerEntities = new PlayerEntity[this.level.MAX_NUMBER_OF_PLAYERS];
        staticCollectables = new StaticCollectableEntity[this.level.MAX_NUMBER_OF_STATIC_COLLECTABLES];
        movingCollectables = new MovingCollectableEntity[this.level.MAX_NUMBER_OF_MOVING_COLLECTABLES];

        createQuadTree();
        fillMapWithCollectable(); // TODO
    }

    /**
     * Performs collision checks between entities in the game. 
     */
    private void checkCollisions() {
        for (int i = 0; i < level.MAX_NUMBER_OF_PLAYERS; i++){
            if (playerEntities[i] != null) {
                ArrayList<Entity> collidedEntities = new ArrayList<>();
                quadTree.query(playerEntities[i].getBoundingBox(), collidedEntities);
                for(Entity collided : collidedEntities){
                    playerEntities[i].onCollision((CollideableEntity) collided);
                }
            }
        }
        for (int i = 0; i < level.MAX_NUMBER_OF_MOVING_COLLECTABLES; i++){
            if (movingCollectables[i] != null) {
                ArrayList<Entity> collidedEntities = new ArrayList<>();
                quadTree.query(movingCollectables[i].getBoundingBox(), collidedEntities);
                for(Entity collided : collidedEntities){
                    movingCollectables[i].onCollision((CollideableEntity) collided);
                }
            }
        }
    }

    /**
     * Simulates the game inside one room.
     */
    public void simulate(){
        Gizmos.drawBoundingBox(quadTree.getRange(), new float[]{1.f, 0.f, 1.f, 1.f});
        // checkCollide(); TODO;
        
        // System.out.println("\tRoom simulated "+ this.getId());

        for (int i = 0; i < level.MAX_NUMBER_OF_PLAYERS; i++){
            if (playerEntities[i] != null) {
                PlayerEntity player = playerEntities[i];
                quadTree.remove(player, player.getBoundingBox());
                player.simulate();
                quadTree.insert(player, player.getBoundingBox());
            }
        }

        for (int i = 0; i < level.MAX_NUMBER_OF_MOVING_COLLECTABLES; i++){
            if (movingCollectables[i] != null) {
                MovingCollectableEntity collectable = movingCollectables[i];
                quadTree.remove(collectable, collectable.getBoundingBox());
                collectable.simulate();
                if(collectable.getLife() > 0)
                    quadTree.insert(collectable, collectable.getBoundingBox());
                else collectable = null;
            }
        }
        for (int i = 0; i < level.MAX_NUMBER_OF_STATIC_COLLECTABLES; i++){
            if (staticCollectables[i] != null) {
                StaticCollectableEntity collectable = staticCollectables[i];
                quadTree.remove(collectable, collectable.getBoundingBox());
                collectable.simulate();
                if(collectable.getLife() > 0)
                    quadTree.insert(collectable, collectable.getBoundingBox());
                else collectable = null;
            }
        }
        Engine engine = Engine.getInstance();
        // generates DrawData for each player ViewBox
        //
        for (int i = 0; i < level.MAX_NUMBER_OF_PLAYERS; i++){
            if (playerEntities[i] != null) {
                PlayerEntity player = playerEntities[i];

                ClientChannel channel = engine.getClientChannel(player.getOwnerID());
                if (channel == null) continue;

                channel.updateViewport();

                ArrayList<Entity> visibleEntities = retrieveEntitiesInRange(channel.getViewport());

                channel.setViewBoxData(visibleEntities);
            }
        }

    }

    /**
     * Generates a ViewBox data for each player.  I don't know what this data should look like.
     * Now it returns only the player in an array. But quadtree queries should be done and an array of entities should be returned.
     * @param playerId the id of the player for which data is being generated.
     */
    public ArrayList<Entity> retrieveEntitiesInRange(BoundingBox range){
        ArrayList<Entity> foundObjects = new ArrayList<>();
        
        quadTree.query(range, foundObjects);

        return foundObjects;
    }

    /**
     * Adds a new player to the Room for the given Client id.
     * @param ownerID id of the Client with whom the player will be associated.
     */
    public PlayerEntity addPlayer(int ownerID){

        for(int i = 0; i < playerEntities.length; i++)
            if(playerEntities[i] == null){
                PlayerEntity player = createPlayer(i, ownerID);
                // insert the player into the quadtree for collision checks
                quadTree.insert(player, player.getBoundingBox());
                playerEntities[i] = player;
                System.out.println("\tPlayer Added to " + id + " Room");
                return player;
            }
        System.out.println("No free place to add player");   //  a better ways needs to be implemented TODO
        System.exit(0);
        return null;      // not reached
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
                // shouldn't we remove it also from tree??????     // TODO
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
    private MovingCollectableEntity createMovingCollectable(int id, int type){
        Vector2F position = Vector2F.randomVector(0,MAP_HEIGHT,0, MAP_HEIGHT); // TODO
        return new MovingCollectableEntity(position,id,-1,1);                       //TODO
    }

    /**
     * adds moving collectable to the room
     */
    public void addMovingCollectable(){
        for(int i = 0; i < movingCollectables.length; i++){
            if(movingCollectables[i] == null){
                MovingCollectableEntity collectables = createMovingCollectable(i, 0);//change the type TODO
                quadTree.insert(collectables, collectables.getBoundingBox());
                movingCollectables[i] = collectables;
                break;
            }
        }
    }
    /**
     * adds moving collectable to the room in the given index
     */
    public void addMovingCollectable(int ind){
        MovingCollectableEntity collectables = createMovingCollectable(ind, 0);//change the type TODO
        quadTree.insert(collectables, collectables.getBoundingBox());
        movingCollectables[ind] = collectables;
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
     * adds static collectable to the room in the give index
     */
    public void addStaticCollectable(int ind){
        StaticCollectableEntity collectables = createStaticCollectable(ind, 0);//change the type TODO
        quadTree.insert(collectables, collectables.getBoundingBox());
        staticCollectables[ind] = collectables;
    }


    public PlayerEntity getPlayer(int id) {
        return playerEntities[id];
    }

    /**
     * Fills the game collectable entities.Changes the
     */
    public void fillMapWithCollectable(){
        for(int i = 0; i < this.level.MAX_NUMBER_OF_MOVING_COLLECTABLES; i++)
            addMovingCollectable(i);
        for(int i = 0; i < this.level.MAX_NUMBER_OF_STATIC_COLLECTABLES; i++)
            addStaticCollectable(i);
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
