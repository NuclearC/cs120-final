package game.experimental.engine;
import java.util.ArrayList;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

/**
 * QuadTree data structure implementation.
 *
 * // something about generic                              // TODO
 * @param <T>
 */
public class QuadTree<T> {

    private static final int CAPACITY = 4;         // max number of node to be stored in a single QuadTree
    private int totalObjectCount = 0;              // total number of objects in current and children nodes

    /**
     * Represents a node in the Quadtree.
     * Bounds the object and the bounding box together.
     */
     public class Node {
        private final T object;
        private final BoundingBox boundingBox;

        /**
         * Creates a Node.
         * @param object object to be inserted in the quadtree.
         * @param boundingBox bounding box associated with the object.
         */
        public Node(T object, BoundingBox boundingBox) {
            this.object = object;
            this.boundingBox = boundingBox;
        }

         /**
          * Return the object bounded with the node
          * @return object bounded with the node
          */
        public T getObject() {
            return object;
        }

         /**
          * Return the bounding box associated with the node.
          * @return bounding box associated with the node.
          */
        public BoundingBox getBoundingBox() {
            return boundingBox;
        }
    }


    private ArrayList<Node> objects;
    private BoundingBox range;
    private QuadTree<T>[] children;
    private QuadTree<T> parent;

    /**
     * Default constructor for the QuadTree
     * All fields are initialized to null.
     */
    public QuadTree() {
        parent = null;
        children = null;
        objects = new ArrayList<Node>();
        range = new BoundingBox();
    }

    /**
     * Creates a Quadtree.
     * @param parent parent QuadTree
     * @param range bounding box of the Quadtree
     */
    public QuadTree(QuadTree<T> parent, BoundingBox range) {
        this.range = range.clone();
        this.parent = parent;
        objects = new ArrayList<Node>();
        children = null;
    }

    /**
     * Inserts a new object into the QuadTree.
     * @param object object to be inserted
     * @param boundingBox bounded box associated with the object.
     * @return true if the insertion happened, otherwise false.
     */
    public boolean insert(T object, BoundingBox boundingBox) {
        if (this.range.contains(boundingBox)) {
            if (objects.size() >= CAPACITY) {
                split();                                                // split returns if hasChildren()
            }

            if (hasChildren())
                for (int i = 0; i < 4; i++) {
                    if (children[i].insert(object, boundingBox))
                    {
                        totalObjectCount++;
                        return true;
                    }
                }

            objects.add(new Node(object, boundingBox));
            totalObjectCount++;
            // System.out.println("inserted object " + boundingBox.toString() + " into " + range.toString() + " " + objects.size() + " " + totalObjectCount);
            return true;
        }

        return false;
    }

    /**
     * Inserts a new object into the quadtree.
     * @param object object to be inserted
     * @param x x coordinate of the top-left corner
     * @param y y coordinate of the top-left corner
     * @param width width of the object
     * @param height height of the object
     * @return true if insertion happened, otherwise false
     */
    public boolean insert(T object, float x, float y, float width, float height){
        BoundingBox boundingBox = new BoundingBox(x, y, width, height);
        return this.insert(object, boundingBox);
    }

    /**
     * Splits the current QuadTree into children QuadTrees.
     */
    private void split() {
        if (hasChildren())
            return;
        // System.out.println("---------split");
        children = new QuadTree[4];
        final Vector2F newSize = range.getSize().multiply(0.5f);
        children[0] = new QuadTree<T>(this, new BoundingBox(range.getPosition(), newSize));
        children[1] = new QuadTree<T>(this, new BoundingBox(new Vector2F(range.getPosition().getX() + newSize.getX(), range.getPosition().getY()), newSize));
        children[2] = new QuadTree<T>(this, new BoundingBox(new Vector2F(range.getPosition().getX(), range.getPosition().getY() + newSize.getY()), newSize));
        children[3] = new QuadTree<T>(this, new BoundingBox(range.getPosition().add(newSize), newSize));

        refactor();
    }

    /**
     * Distributes the nodes contained in the current quadtree to the child QuadTrees.
     */
    private void refactor() {
        if (children == null)
            return;
        for (int j = 0; j < objects.size(); j++) {
            for (int i = 0; i < 4; i++)
                if (children[i].insert(objects.get(j).getObject(), objects.get(j).getBoundingBox())) {   // Should we have insert(nodeReference)?
                    objects.remove(j--);
                    break;
                }
        }
    }

    /**
     * Queries the quadtree for some range.
     * Returns all the elements having intersection with the specified range.
     * @param range range as a Bounding Box to be queried about
     * @param foundObjects list of the found objects
     */
    public void query(BoundingBox range, ArrayList<T> foundObjects) {
        if (this.range.intersects(range)) {
            for (Node node : objects) {
                if (node.getBoundingBox().intersects(range)) {
                    foundObjects.add(node.getObject());
                }
            }

            if (hasChildren())
                for (QuadTree<T> child : children)
                    child.query(range, foundObjects);
        }
    }

    /**
     * Checks whether the quadtree has children.
     * @return true, if it has children, false otherwise.
     */
    private boolean hasChildren() {
        return children != null;
    }

    public BoundingBox getRange() {
        return range;
    }

    public QuadTree<T>[] getChildren() {
        return children;
    }

    public ArrayList<Node> getObjects() {
        return objects;
    }

    private void merge() {
        if (hasChildren()) {
            for (int i = 0; i < 4; i++)
            {
                children[i].merge();
                objects.addAll(children[i].getObjects());
            }

            children = null;
        }
    }

    private void cleanup() {
        if (hasChildren()) {
            if (totalObjectCount <= CAPACITY) {
                merge();
            }
        }

        if (parent != null)
            parent.cleanup();
    }

    public boolean remove(T object, BoundingBox boundingBox) {
        if (this.range.contains(boundingBox)) {
            // System.out.println("remove on " + this.range.toString() + " " + boundingBox.toString() + " " + object.toString());
            for (Node node : objects) {
                System.out.println(node.getObject() + " " + node.getBoundingBox().toString());
                if (node.getObject().equals(object)) {
                    // System.out.println("removed");
                    if (!objects.remove(node)){
                        System.out.println("tf is this");
                        System.exit(-1);
                    }
                    totalObjectCount--;
                    if (parent != null)
                        parent.cleanup();

                    return true;
                }
            }

            if (hasChildren())
                for (QuadTree<T> child : children) {
                    if (child.remove(object, boundingBox))
                    {
                        totalObjectCount--;
                        return true;
                    }
                }
        }

        return false;
    }



}
