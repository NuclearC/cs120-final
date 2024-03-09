package game.experimental.utils;

/**
 * Represents the vectors' functionality.
 */
public interface Vector {
    /**
     * @param anotherVector is the second operand
     * @return the sum of two vectors
     */
    Vector add(Vector anotherVector);
    
    /**
     * @param anotherVector is the second operand
     * @return the difference of two vectors
     */
    Vector subtract(Vector anotherVector);

    /**
     *
     * @param anotherVector is the second operand
     * @return the dot product of two vectors
     */
    float dotProduct(Vector anotherVector);

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
     * get the specified component
     * @param index the index of the component
     * @return the component of vector at index
     */
    float get(int index);

    /**
     * calculates the length of the vector
     * @return the length of the vector
     */
    float length();

    /**
     * Normalizes the vector in-place.
     */
    void normalize();

    /**
     * Gets a normalized vector from this one.
     */
    Vector getNormalized();

    /**
     * Returns the string representation for the Vector.
     * @return string representation.
     */
    String toString();




}
