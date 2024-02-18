package game.experimental.utils;

/**
 * Implements a 4 dimensional vector of floats.
 * More docs needed                                                       //TODO
 */
public class Vector4F implements Vector, Cloneable {
    // this is kind of stupid but whatever
    private float[] values;

    /**
     * Default constructor. Creates a zero vector.
     */
    public Vector4F() {
        values = new float[4];
    }

    /**
     * Creates a vector from the specified array.
     * No implicit copy is performed.
     */
    public Vector4F(float[] v) {
        values = v;
    }

    /**
     * Creates a vector from another vector.
     * All the other elements are zero-initialized.
     */
    public Vector4F(Vector v) {
        values = new float[] { v.get(0), v.get(1), v.get(2), v.get(3) };
    }

    /**
     * Creates a vector from the specified values.
     * All the other elements are zero-initialized.
     */
    public Vector4F(float x, float y) {
        values = new float[] { x, y, 0.f, 0.f };
    }

    /**
     * Creates a vector from the specified values.
     * All the other elements are zero-initialized.
     */
    public Vector4F(float x, float y, float z) {
        values = new float[] { x, y, z, 0.f };
    }

    /**
     * Creates a vector from the specified values.
     */
    public Vector4F(float x, float y, float z, float w) {
        values = new float[] { x, y, z, w };
    }

    /**
     * Get the array of floats representing this vector.
     * 
     * @return reference to the array
     */
    public float[] getRaw() {
        return values;
    }

    /**
     * Get an entry at the specified index
     * 
     * @return the value of the entry
     */
    @Override
    public float get(int r) {
        if (r >= 0 && r < 4)
            return values[r];
        return 0;
    }

    // stupid shorthand functions
    public float getX() {
        return values[0];
    }

    public float getY() {
        return values[1];
    }

    public float getZ() {
        return values[2];
    }

    public float getW() {
        return values[3];
    }

    /**
     * Returns the squared length of this vector.
     * 
     * @return the squared length
     */
    public float lengthSq() {
        return values[0] * values[0] +
                values[1] * values[1] +
                values[2] * values[2] +
                values[3] * values[3];
    }

    /**
     * Returns the length of this vector.
     * 
     * @return the length
     */
    @Override
    public float length() {
        return (float) Math.sqrt(this.lengthSq());
    }

    /**
     * Perform vector addition. Does not change the callee vector.
     * 
     * @param other The vector to be added.
     * @return The newly resulting vector.
     */
    @Override
    public Vector4F add(Vector other) {
        Vector4F other4F = (Vector4F)other;
        return new Vector4F(getX() + other4F.getX(), getY() + other4F.getY(), getZ() + other4F.getZ(), getW() + other4F.getW());
    }

    /**
     * Perform vector subtraction. Does not change the callee vector.
     * 
     * @param other The vector to be added.
     * @return The newly resulting vector.
     */
    @Override
    public Vector4F subtract(Vector other) {
        Vector4F other4F = (Vector4F)other;
        return new Vector4F(getX() - other4F.getX(), getY() - other4F.getY(), getZ() - other4F.getZ(), getW() - other4F.getW());
    }

    /**
     * Perform vector and scalar multiplication. Does not change the callee vector.
     * 
     * @param scalar the scalar to multiply the vector with.
     * @return The newly resulting vector.
     */
    @Override
    public Vector4F multiply(float scalar) {
        return new Vector4F(getX() * scalar, getY() * scalar, getZ() * scalar, getW() * scalar);
    }

    /**
     * Compute the dot product of the callee vector and the specified vector.
     * 
     * @param other the other vector.
     * @return the scalar value of the dot product.
     */
    @Override
    public float dotProduct(Vector other) {
        Vector4F other4F = (Vector4F)other;
        return getX() * other4F.getX() + getY() * other4F.getY() + getZ() * other4F.getZ() + getW() * other4F.getW();
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
     * needs to be chekced
     * @return the clone of the vector
     */
    @Override
    public Vector4F clone() {
        Vector4F vector = new Vector4F(values[0], values[1], values[2], values[3]);
        return vector;
    }
}
