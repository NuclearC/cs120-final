package game.experimental.engine;

/**
 * Represents the vectors' functionality.
 */
public interface Vector {
    /**
     * @param anotherVector is the second operand
     * @return the sum of two vectors
     */
    Vector sum(Vector anotherVector);
    /**
     *
     * @param anotherVector is the second operand
     * @return the dot product of two vectors
     */
    float dotProduct(Vector anotherVector);
    /**
     *
     * @param anotherVector is the second operand
     * @return the cross product of two vectors
     */
    Vector crossProduct(Vector anotherVector);

    /**
     *
     * @param scalar the scalar that gets multiplied by the vector
     * @return returns the vector multiplied by the scalar
     */
    Vector multiply(float scalar);

    /**
     *
     * @param anotherVector is the second operand
     * @return the angle between the two vector in radians
     */
    float getAngle(Vector anotherVector);

    /**
     * calculates the length of the vector
     * @return the length of the vector
     */
    float length();

    /**
     * checks the equality between vectors.
     * @param vector the second operand
     * @return true when both vectors are equal, false otherwise.
     */
    boolean equal(Vector vector);


}
