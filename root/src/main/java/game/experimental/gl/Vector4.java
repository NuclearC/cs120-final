package game.experimental.gl;

/**
 * Implements a 4 dimensional vector of floats.
 */
public class Vector4 {
    // this is kind of stupid but whatever
    private float[] values;

    /**
     * Default constructor. Creates a zero vector.
     */
    public Vector4() {
        values = new float[4];
    }

    /**
     * Creates a vector from the specified array.
     * No implicit copy is performed.
     */
    public Vector4(float[] v) {
        values = v;
    }

    /**
     * Creates a vector from the specified values.
     * All the other elements are zero-initialized.
     */
    public Vector4(float x, float y) {
        values = new float[] { x, y, 0.f, 0.f };
    }

    /**
     * Creates a vector from the specified values.
     * All the other elements are zero-initialized.
     */
    public Vector4(float x, float y, float z) {
        values = new float[] { x, y, z, 0.f };
    }

    /**
     * Creates a vector from the specified values.
     */
    public Vector4(float x, float y, float z, float w) {
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
     * For performance reasons, no checks for bounds are done.
     * 
     * @return the value of the entry
     */
    public float getAt(int r) {
        return values[r];
    }

    // stupid shorthand functions
    public float x() {
        return values[0];
    }

    public float y() {
        return values[1];
    }

    public float z() {
        return values[2];
    }

    public float w() {
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
    public float length() {
        return (float) Math.sqrt(this.lengthSq());
    }

    /**
     * Perform vector addition. Does not change the callee vector.
     * 
     * @param other The vector to be added.
     * @return The newly resulting vector.
     */
    public Vector4 add(Vector4 other) {
        return new Vector4(x() + other.x(), y() + other.y(), z() + other.z(), w() + other.w());
    }

    /**
     * Perform vector subtraction. Does not change the callee vector.
     * 
     * @param other The vector to be added.
     * @return The newly resulting vector.
     */
    public Vector4 sub(Vector4 other) {
        return new Vector4(x() - other.x(), y() - other.y(), z() - other.z(), w() - other.w());
    }

    /**
     * Perform vector and scalar multiplication. Does not change the callee vector.
     * 
     * @param s the scalar to multiply the vector with.
     * @return The newly resulting vector.
     */
    public Vector4 multiply(float s) {
        return new Vector4(x() * s, y() * s, z() * s, w() * s);
    }

    /**
     * Compute the dot product of the callee vector and the specified vector.
     * 
     * @param other the other vector.
     * @return the scalar value of the dot product.
     */
    public float dot(Vector4 other) {
        return x() * other.x() + y() * other.y() + z() * other.z() + w() * other.w();
    }
}
