package game.experimental.engine;

public class BoundingBox {
    private Vector2F position, size;

    /**
     * creates the initial box with all parameters equal to 0
     */
    public BoundingBox() {
        position = new Vector2F();
        size = new Vector2F();
    }

    /**
     * need to be checked
     * @param position it's the position vector of the top-left corner
     * @param size it's the vector having width and height as coordinates
     */
    public BoundingBox(Vector2F position, Vector2F size) {
        this.position = position.clone();
        this.size = size.clone();
    }

    /**
     * gives the position of the BoundingBox
     * @return the position of the BoundingBox
     */
    public Vector2F getPosition() {
        return position.clone();
    }

    /**
     * gives the size of the BoundingBox
     * @return the size of the BoundingBox
     */
    public Vector2F getSize() {
        return size.clone();
    }

    /**
     * gives the vector indicating the top-right corner of the box
     * @return the vector indicating the top-right corner of the box
     */
    public Vector2F getTopRight() {
        Vector2F vector = new Vector2F(position.getX() + size.getX() , position.getY());
        return vector;
    }
    /**
     * gives the vector indicating the Bottom-left corner of the box
     * @return the vector indicating the Bottom-left corner of the box
     */
    public Vector2F getBottomLeft() {
        Vector2F vector = new Vector2F(position.getX(), position.getY() + size.getY());
        return vector;
    }

    /**
     * gives the vector indicating the Bottom-Right corner of the box
     * @return the vector indicating the Bottom-Right corner of the box
     */
    public Vector2F getBottomRight() {
        Vector2F vector = new Vector2F(position.getX() +  size.getX(), position.getY() + size.getY());
        return vector;
    }
    /**
     * gives the vector indicating the center of the box
     * @return the vector indicating the center of the box
     */
    public Vector2F getCenter() {
        Vector2F vector = new Vector2F((position.getX() + size.getX()) / 2, (position.getY() + size.getY()) / 2);
        return vector;
    }

    /**
     * checks whether the given box is inside it or not
     * @param box the box that is checked
     * @return true when box is inside it or equal it, false otherwise.
     */
    public boolean contains(BoundingBox box) {
        boolean result = (position.getX() <= box.position.getX() && getBottomRight().getX() >= box.getBottomRight().getX());
        result = result && (position.getY() <= box.getPosition().getY() && getBottomRight().getY() >= getBottomRight().getY());
        return result;
    }

    /**
     *  checks whether the given box is intersecting with it or not
     * @param box the box that is checked
     * @return true if the boxes interacts, false otherwise
     */
     public boolean intersects(BoundingBox box) {
        return !(position.getX() >= box.getTopRight().getX() || getTopRight().getX() <= box.getPosition().getX() || getPosition().getY() >= box.getBottomRight().getY() || getBottomRight().getY() <= box.getPosition().getY());
     }

}
