package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point2D;
import io.github.xasmedy.math.point.Point3D;
import java.util.function.Function;
import static io.github.xasmedy.math.util.MathUtil.sqrt;

public interface Vector<T extends Vector<T, P>, P> {

    static Vector2 vector2(float x, float y) {
        return new Vector2(x, y);
    }

    static Vector2 vector2(Point2D point) {
        return vector2(point.x(), point.y());
    }

    static Vector3 vector3(float x, float y, float z) {
        return new Vector3(x, y, z);
    }

    static Vector3 vector3(Point3D point) {
        return vector3(point.x(), point.y(), point.z());
    }

    /**
     * Adds the given vector to this vector
     *
     * @param vector The vector
     * @return This vector for chaining
     */
    T add(P vector);

    /**
     * Subtracts the given vector from this vector.
     *
     * @param vector The vector
     * @return This vector for chaining
     */
    T sub(P vector);

    /**
     * Scales this vector by another vector
     *
     * @return This vector for chaining
     */
    T scale(P vector);

    /**
     * Scales this vector by a scalar
     *
     * @param scalar The scalar
     * @return This vector for chaining
     */
    T scale(float scalar);

    /**
     * @return The Euclidean length
     */
    default float length() {
        return sqrt(length2());
    }

    /**
     * This method is faster than {@link Vector#length()} because it avoids calculating a square root. It is useful for comparisons,
     * but not for getting exact lengths, as the return value is the square of the actual length.
     *
     * @return The squared Euclidean length
     */
    float length2();

    /**
     * Sets the length of this vector. Does nothing if this vector is zero.
     *
     * @param length desired length for this vector
     * @return this vector for chaining
     */
    default T withLength(float length) {
        return withLength2(length * length);
    }

    /**
     * Sets the length of this vector, based on the square of the desired length. Does nothing if this vector is zero.
     * <p/>
     * This method is slightly faster than setLength().
     *
     * @param length2 desired square of the length for this vector
     * @return this vector for chaining
     * @see #length2()
     */
    default T withLength2(float length2) {
        final float current = length2();
        if (current == 0 || current == length2) return this_(); // No changes done.
        return scale(sqrt(length2 / current));
    }

    /**
     * Limits the length of this vector, based on the desired maximum length.
     *
     * @param limit desired maximum length for this vector
     * @return this vector for chaining
     */
    default T limit(float limit) {
        return limit2(limit * limit);
    }

    /**
     * Limits the length of this vector, based on the desired maximum length squared.
     * <p/>
     * This method is slightly faster than limit().
     *
     * @param limit2 squared desired maximum length for this vector
     * @return this vector for chaining
     * @see #length2()
     */
    default T limit2(float limit2) {
        final float current = length2();
        if (limit2 <= current) return this_(); // No changes done.
        return scale(sqrt(limit2 / current));
    }

    /**
     * Clamps this vector's length to given min and max values
     *
     * @param min Min length
     * @param max Max length
     * @return This vector for chaining
     */
    default T clamp(float min, float max) {

        final float len2 = length2();
        if (len2 == 0) return this_();

        final float max2 = max * max;
        if (len2 > max2) return scale(sqrt(max2 / len2));

        final float min2 = min * min;
        if (len2 < min2) return scale(sqrt(min2 / len2));
        return this_();
    }

    /**
     * Normalizes this vector. Does nothing if it is zero.
     *
     * @return This vector for chaining
     */
    T normalize();

    /**
     * @param vector The other vector
     * @return The dot product between this and the other vector
     */
    float dot(P vector);

    /**
     * @param vector The other vector
     * @return the distance between this and the other vector
     */
    default float distance(P vector) {
        return sqrt(distance2(vector));
    }

    /**
     * This method is faster than {@link Vector#distance(Vector)} because it avoids calculating a square root. It is useful for
     * comparisons, but not for getting accurate distances, as the return value is the square of the actual distance.
     *
     * @param vector The other vector
     * @return the squared distance between this and the other vector
     */
    float distance2(P vector);

    /**
     * Linearly interpolates between this vector and the targeT vectorector by alpha which is in the range [0,1]. The result is stored
     * in this vector.
     *
     * @param target The targeT vectorector
     * @param alpha  The interpolation coefficient
     * @return This vector for chaining.
     */
    T lerp(P target, float alpha);

    /**
     * Interpolates between this vector and the given targeT vectorector by alpha (within range [0,1]) using the given Interpolation
     * method. the result is stored in this vector.
     *
     * @param target       The targeT vectorector
     * @param alpha        The interpolation coefficient
     * @param interpolator An Interpolation object describing the used interpolation method
     * @return This vector for chaining.
     */
    default T interpolate(P target, float alpha, Function<Float, Float> interpolator) {
        return lerp(target, interpolator.apply(alpha));
    }

    /**
     * @return Whether this vector is a unit length vector within the given margin.
     */
    default boolean isUnit(float margin) {
        return Math.abs(length2() - 1f) < margin;
    }

    /**
     * @return Whether this vector is a unit length vector
     */
    default boolean isUnit() {
        return isUnit(0.000000001f);
    }

    /**
     * @return Whether this vector is a zero vector
     */
    boolean isZero();

    /**
     * @return Whether the length of this vector is smaller than the given margin
     */
    default boolean isLengthZero(float margin) {
        return length2() < margin;
    }

    /**
     * @return true if this vector is in line with the other vector (either in the same or the opposite direction)
     */
    boolean isParallel(P vector, float epsilon);

    /**
     * @return true if this vector is collinear with the other vector ({@link #isParallelTo(Vector, float)} &&
     * {@link #hasSameDirection(Vector)}).
     */
    default boolean isCollinear(P vector, float epsilon) {
        return isParallel(vector, epsilon) && hasSameDirection(vector);
    }

    /**
     * @return true if this vector is opposite collinear with the other vector ({@link #isParallelTo(Vector, float)} &&
     * {@link #hasOppositeDirection(Vector)}).
     */
    default boolean isCollinearOpposite(P vector, float epsilon) {
        return isParallel(vector, epsilon) && hasOppositeDirection(vector);
    }

    /**
     * @param epsilon a positive small number close to zero
     * @return Whether this vector is perpendicular with the other vector. True if the dot product is 0.
     */
    default boolean isPerpendicular(P vector, float epsilon) {
        return Math.abs(dot(vector)) <= epsilon;
    }

    /**
     * @return Whether this vector has similar direction compared to the other vector. True if the normalized dot product is >
     * 0.
     */
    default boolean hasSameDirection(P vector) {
        return dot(vector) > 0;
    }

    /**
     * @return Whether this vector has opposite direction compared to the other vector. True if the normalized dot product is <
     * 0.
     */
    default boolean hasOppositeDirection(P vector) {
        return dot(vector) < 0;
    }

    /**
     * Compares this vector with the other vector, using the supplied epsilon for fuzzy equality testing.
     *
     * @param vector
     * @param epsilon
     * @return whether the vectors have fuzzy equality.
     */
    boolean epsilonEquals(P vector, float epsilon);

    /// Utility method to convert {@link Vector} to {@link T}.\
    /// This allows the Vector interface to generalize some code.
    T this_();
}
