package game.experimental.utils;

/**
 * Represents a 2D vector with floating point coordinates.
 * Information should be added to how the vector is represented    // TODO
 */
public class Vector2F implements Vector, Cloneable {
    private float x, y;

    /**
     * crates default vector
     * Crates a default 2D vector.
     * Initializes x = 0 and y = 0
     */
    public Vector2F(){
        x = 0;
        y = 0;
    }

    /**
     * creates vector with coordinates of (x,y)
     * @param x x coordinate of the vector
     * @param y y coordinate of the vector
     */
    public Vector2F(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Get the coordinate at the specified index.
     * For performance reasons, no checks for bounds are done.
     *
     * @param coordinateIndex the index of the coordinate to be returned.
     * @return the value of the entry
     */
    @Override
    public float get(int coordinateIndex) {
        if (coordinateIndex == 0) return x;
        else if (coordinateIndex == 1) return y;
        else return 0;
    }

    /**
     * gets the x position of the vector
     * @return x position of the vector
     */
    public float getX(){
        return x;
    }
    /**
     * gets the y position of the vector
     * @return y position of the vector
     */
    public float getY(){
        return y;
    }

    /**
     * x sets the x position
     * @param x sets the x position
     */
    public void setX(float x){
        this.x = x;
    }

    /**
     * y sets the x position
     * @param y sets the y position
     */
    public void setY(float y){
        this.y = y;
    }

    /**
     * Adds a 2D vector to the calling vector.
     * Returns the result as a new vector.
     * @param anotherVector summand vector.
     * @return sum Vector of the two vectors
     */
    @Override
    public Vector2F add(Vector anotherVector) {
        Vector2F anotherVector2F = (Vector2F) anotherVector;
        Vector2F resultVector = new Vector2F(x + anotherVector2F.getX(), y + anotherVector2F.getY());
        return resultVector;
    }

    /**
     * Subtracts a vector from the calling vector.
     * Returns the result as a new Vector.
     * @param anotherVector subtrahend vector
     *
     * @return difference Vector of the two vectors
     */
    @Override
    public Vector2F subtract(Vector anotherVector) {
        Vector2F anotherVector2F = (Vector2F) anotherVector;
        Vector2F resultVector = new Vector2F(x - anotherVector2F.getX(), y - anotherVector2F.getY());
        return resultVector;
    }

    /**
     * the dot product of the two vectors
     * @param anotherVector is the second operand
     * @return the dot product of the two vectors
     */
    @Override
    public float dotProduct(Vector anotherVector) {
        Vector2F anotherVector2F = (Vector2F) anotherVector;
        float result = x * anotherVector2F.getX() + y * anotherVector2F.getY();
        return result;
    }

    /**
     * Multiply a Vector by a scalar.
     * Returns a new vector.
     * @param scalar the scalar that gets multiplied to the vector
     * @return the vector that gets multiplied by Scalar
     */
    @Override
    public Vector2F multiply(float scalar) {
        Vector2F resultVector = new Vector2F(x * scalar, y * scalar);
        return resultVector;
    }

    /**
     * Gives the angel between two vectors(should be revised about the double)
     * @param anotherVector is the second operand
     * @return the angel between two vectors in radians as float
     */
    @Override
    public float getAngle(Vector anotherVector) {
        double angle = this.dotProduct(anotherVector) / (this.length() * anotherVector.length());
        return (float) Math.acos((double)angle);
    }

    /**
     * calculates the length of the vector
     * @return the length of the vector
     */
    @Override
    public float length(){
        float length = (float) Math.sqrt(x * x + y * y);
        return length;
    }

    /**
     * needs to be chekced
     * @return the clone of the vector
     */
    @Override
    public Vector2F clone() {
        Vector2F vector = new Vector2F(this.x, this.y);
        return vector;
    }

    
    /**
     * Gets a normalized vector from this one. 
     * @return the normalized vector
     */
    @Override
    public Vector2F getNormalized() {
        Vector2F vector = new Vector2F(this.x / this.length(), this.y / this.length());
        return vector;
    }

    /**
     * Normalize vector in-place.
     */
    @Override
    public void normalize() {
        float vectorLength = this.length();
        this.x /= vectorLength;
        this.y /= vectorLength;
    }

    @Override
    public String toString(){
        return "x: " + this.x + " y: " + this.y;
    }
}
