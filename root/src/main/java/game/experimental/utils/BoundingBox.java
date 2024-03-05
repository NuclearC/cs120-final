package game.experimental.utils;

/**
 * Box to represent entities inside the QuadTree.
 * Keeps the position and size of the object as 2D vectors.
 * Position is the vector with the coordinates of the top-left corner.
 * Size is the diagonal vector having width and height as coordinates.
 */
public class BoundingBox implements Cloneable {
    private Vector2F position, size;

    /**
     * Creates a default box.
     * Position and size are set to 0 vectors.
     */
    public BoundingBox() {
        position = new Vector2F();
        size = new Vector2F();
    }

    /**
     * need to be checked
     * 
     * @param position position vector of the box
     * @param size     size vector of the box
     */
    public BoundingBox(Vector2F position, Vector2F size) {
        this.position = position.clone();
        this.size = size.clone();
    }

    /**
     * Creates a bounding box based on the position coordinates, width and height values.
     *
     * @param x x coordinate of the position vector
     * @param y y coordinate of the position vector
     * @param width width component of the size vector
     * @param height height component of the size vector
     */
    public BoundingBox(float x, float y, float width, float height) {
        this.position = new Vector2F(x, y);
        this.size = new Vector2F(width, height);
    }

    /**
     * Gives the position vector of the BoundingBox.
     * 
     * @return the position vector of the BoundingBox
     */
    public Vector2F getPosition() {
        return position.clone();
    }

    /**
     * Gives the size of the BoundingBox
     * 
     * @return size vector of the BoundingBox
     */
    public Vector2F getSize() {
        return size.clone();
    }

    /**
     * Gives the vector indicating the top-right corner of the box
     * 
     * @return vector of the top-right corner of the box
     */
    public Vector2F getTopRight() {
        Vector2F vector = new Vector2F(position.getX() + size.getX(), position.getY());
        return vector;
    }

    /**
     * Gives the vector indicating the Bottom-left corner of the box
     * 
     * @return vector of the bottom-left corner of the box
     */
    public Vector2F getBottomLeft() {
        Vector2F vector = new Vector2F(position.getX(), position.getY() + size.getY());
        return vector;
    }

    /**
     * Gives the vector indicating the Bottom-Right corner of the box
     * 
     * @return vector of the Bottom-Right corner of the box
     */
    public Vector2F getBottomRight() {
        Vector2F vector = new Vector2F(position.getX() + size.getX(), position.getY() + size.getY());
        return vector;
    }

    /**
     * gives the vector indicating the center of the box
     * 
     * @return the vector indicating the center of the box
     */
    public Vector2F getCenter() {
        return position.add(size.multiply(0.5f));
    }

    /**
     * Checks whether the provided BoundingBox is inside the callee box.
     * 
     * @param box the box that is checked
     * @return true when box is inside it or equal it, false otherwise.
     */
    public boolean contains(BoundingBox box) {
        boolean result = (position.getX() <= box.position.getX()
                && getBottomRight().getX() >= box.getBottomRight().getX());
        result = result && (position.getY() <= box.getPosition().getY()
                && getBottomRight().getY() >= box.getBottomRight().getY());
        return result;
    }

    /**
     * checks whether the given point is inside or not
     * 
     * @param vector the position of the point to check
     * @return true when point is inside it, false otherwise.
     */
    public boolean contains(Vector2F vector) {
        return (vector.getX() >= position.getX() && vector.getX() < getBottomRight().getX()) &&
                (vector.getY() >= position.getY() && vector.getY() < getBottomRight().getY());
    }

    /**
     * checks whether the given box is intersecting with it or not
     * 
     * @param box the box that is checked
     * @return true if the boxes interacts, false otherwise
     */
    public boolean intersects(BoundingBox box) {
        return !(position.getX() > box.getTopRight().getX() 
                || getTopRight().getX() < box.getPosition().getX()
                || getPosition().getY() > box.getBottomRight().getY()
                || getBottomRight().getY() < box.getPosition().getY());
    }

    public BoundingBox clone() {
        BoundingBox newBox = new BoundingBox(this.position, this.size);
        return newBox;
    }

    public String toString() {
        return position.getX() + " " + position.getY() + " " + size.getX() + " " + size.getY();
    }

}
