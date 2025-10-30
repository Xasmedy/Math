package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.point.Point2D;
import java.util.function.Function;

public interface Vector<T extends Vector<T, P>, P> {

    static Vector2 vector2(float x, float y) {
        return new Vector2(x, y);
    }

    static Vector2 vector2(Point2D point) {
        return vector2(point.x(), point.y());
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
    float length();

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
    T length(float length);

    /**
     * Sets the length of this vector, based on the square of the desired length. Does nothing if this vector is zero.
     * <p/>
     * This method is slightly faster than setLength().
     *
     * @param length2 desired square of the length for this vector
     * @return this vector for chaining
     * @see #length2()
     */
    T length2(float length2);
    
    /**
     * Limits the length of this vector, based on the desired maximum length.
     *
     * @param limit desired maximum length for this vector
     * @return this vector for chaining
     */
    T limit(float limit);

    /**
     * Limits the length of this vector, based on the desired maximum length squared.
     * <p/>
     * This method is slightly faster than limit().
     *
     * @param limit2 squared desired maximum length for this vector
     * @return this vector for chaining
     * @see #length2()
     */
    T limit2(float limit2);

    /**
     * Clamps this vector's length to given min and max values
     *
     * @param min Min length
     * @param max Max length
     * @return This vector for chaining
     */
    T clamp(float min, float max);

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
    float distance(P vector);

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
    T interpolate(P target, float alpha, Function<Float, Float> interpolator);

    /**
     * @return Whether this vector is a unit length vector within the given margin.
     */
    boolean isUnit(float margin);

    /**
     * @return Whether this vector is a unit length vector
     */
    boolean isUnit();

    /**
     * @return Whether this vector is a zero vector
     */
    boolean isZero();

    /**
     * @return Whether the length of this vector is smaller than the given margin
     */
    boolean isLengthZero(float margin);

    /**
     * @return true if this vector is in line with the other vector (either in the same or the opposite direction)
     */
    boolean isParallel(P vector, float epsilon);

    /**
     * @return true if this vector is collinear with the other vector ({@link #isParallelTo(Vector, float)} &&
     * {@link #hasSameDirection(Vector)}).
     */
    boolean isCollinear(P vector, float epsilon);

    /**
     * @return true if this vector is opposite collinear with the other vector ({@link #isParallelTo(Vector, float)} &&
     * {@link #hasOppositeDirection(Vector)}).
     */
    boolean isCollinearOpposite(P vector, float epsilon);

    /**
     * @param epsilon a positive small number close to zero
     * @return Whether this vector is perpendicular with the other vector. True if the dot product is 0.
     */
    boolean isPerpendicular(P vector, float epsilon);

    /**
     * @return Whether this vector has similar direction compared to the other vector. True if the normalized dot product is >
     * 0.
     */
    boolean hasSameDirection(P vector);

    /**
     * @return Whether this vector has opposite direction compared to the other vector. True if the normalized dot product is <
     * 0.
     */
    boolean hasOppositeDirection(P vector);

    /**
     * Compares this vector with the other vector, using the supplied epsilon for fuzzy equality testing.
     *
     * @param vector
     * @param epsilon
     * @return whether the vectors have fuzzy equality.
     */
    boolean epsilonEquals(P vector, float epsilon);
}
