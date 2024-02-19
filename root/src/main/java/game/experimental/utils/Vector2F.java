package game.experimental.utils;

public class Vector2F implements Vector, Cloneable {
    private float x, y;

    /**
     * crates default vector
     * initialize the vectors x and y to zero
     */
    public Vector2F(){
        x = 0;
        y = 0;
    }

    /**
     * creates vector with initial values of (x,y)
     * @param x x position of the vector
     * @param y y position of the vector
     */
    public Vector2F(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Get an entry at the specified index
     * For performance reasons, no checks for bounds are done.
     * 
     * @return the value of the entry
     */
    @Override
    public float get(int r) {
        if (r == 0) return x;
        else if (r == 1) return y;
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
     * sum of the two vectors
     * @param anotherVector is the second operand
     * @return sum of the two vectors
     */
    @Override
    public Vector2F add(Vector anotherVector) {
        Vector2F anotherVector2F = (Vector2F) anotherVector;
        Vector2F resultVector = new Vector2F(x + anotherVector2F.getX(), y + anotherVector2F.getY());
        return resultVector;
    }

    /**
     * difference of the two vectors
     * @param anotherVector is the second operand
     * @return difference of the two vectors
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
     * the vector that gets multiplied by Scalar
     * @param scalar the scalar that gets multiplied by the vector
     * @return the vector that gets multiplied by Scalar
     */
    @Override
    public Vector2F multiply(float scalar) {
        Vector2F resultVector = new Vector2F(x * scalar, y * scalar);
        return resultVector;
    }

    /**
     * gives the angel between two vectors(should be revised about the double)
     * @param anotherVector is the second operand
     * @return the angel between two vectors
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
}
