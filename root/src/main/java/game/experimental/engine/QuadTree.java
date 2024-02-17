package game.experimental.engine;
import java.util.ArrayList;

import game.experimental.utils.BoundingBox;
import game.experimental.utils.Vector2F;

public class QuadTree<T> {

    private static final int CAPACITY = 4;
    private int totalObjectCount = 0;
    public class Node<T> {
        private final T object;
        private final BoundingBox boundingBox;

        public Node(T object, BoundingBox boundingBox) {
            this.object = object;
            this.boundingBox = boundingBox;
        }

        public T getObject() {
            return object;
        }

        public BoundingBox getBoundingBox() {
            return boundingBox;
        }
    }


    private ArrayList<Node<T>> objects;
    private BoundingBox range;
    private QuadTree<T>[] children;
    private QuadTree<T> parent;

    public QuadTree() {
        parent = null;
        children = null;
        objects = new ArrayList<Node<T>>();
        range = new BoundingBox();
    }

    public QuadTree(QuadTree<T> parent, BoundingBox range) {
        this.range = range.clone();
        this.parent = parent;
        objects = new ArrayList<Node<T>>();
        children = null;
    }

    private void refactor() {
        if (children == null)
            return;
        for (int j = 0; j < objects.size(); j++) {
            for (int i = 0; i < 4; i++)
                if (children[i].insert(objects.get(j).getObject(), objects.get(j).getBoundingBox())) {
                    objects.remove(j);
                    totalObjectCount--;
                    j--;
                    break;
                }
        }
    }
    private void split() {
        if (hasChildren())
            return;
        System.out.println("---------split");
        children = new QuadTree[4];
        final Vector2F newSize = range.getSize().multiply(0.5f);
        children[0] = new QuadTree<T>(this, new BoundingBox(range.getPosition(), newSize));
        children[1] = new QuadTree<T>(this, new BoundingBox(new Vector2F(range.getPosition().getX() + newSize.getX(), range.getPosition().getY()), newSize));
        children[2] = new QuadTree<T>(this, new BoundingBox(new Vector2F(range.getPosition().getX(), range.getPosition().getY() + newSize.getY()), newSize));
        children[3] = new QuadTree<T>(this, new BoundingBox(range.getPosition().add(newSize), newSize));

        refactor();
    }

    public BoundingBox getRange() {
        return range;
    }

    public QuadTree<T>[] getChildren() {
        return children;
    }

    public ArrayList<Node<T>> getObjects() {
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

    private boolean hasChildren() {
        return children != null;
    }

    private void cleanup() {
        if (hasChildren()) {
            if (totalObjectCount < CAPACITY) {
                merge();
            }
        }

        if (parent != null)
            parent.cleanup();
    }

    public boolean remove(T object, BoundingBox boundingBox) {
        if (this.range.contains(boundingBox)) {

            for (Node<T> node:  objects) {
                if (node.getObject() == object) {
                    objects.remove(node);
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

    public void query(BoundingBox range, ArrayList<T> result) {
        if (this.range.intersects(range)) {
            for (Node<T> node : objects) {
                if (node.getBoundingBox().intersects(range)) {
                    result.add(node.getObject());
                }
            }

            if (hasChildren())
                for (QuadTree<T> child : children)
                    child.query(range, result);
        }
    }

    public boolean insert(T object, BoundingBox boundingBox) {

        if (this.range.contains(boundingBox)) {
            if (objects.size() > CAPACITY) {
                split();
            }

            if (hasChildren())
                for (int i = 0; i < 4; i++) {
                    if (children[i].insert(object, boundingBox))
                    {
                        totalObjectCount++;
                        return true;
                    }
                }

            System.out.println("inserted object " + boundingBox.toString() + "  into " + range.toString());
            objects.add(new Node<T>(object, boundingBox));
            totalObjectCount++;
            return true;
        }

        return false;
    }



}
