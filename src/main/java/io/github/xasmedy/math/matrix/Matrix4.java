package io.github.xasmedy.math.matrix;

import io.github.xasmedy.math.rotation.Quaternion;
import io.github.xasmedy.math.rotation.Radians;
import io.github.xasmedy.math.vector.v3.Vector3;

/// Matrix4x4 stored in column-major order.
@SuppressWarnings("unused")
public interface Matrix4<T extends Matrix4<T, N, V>, N, V extends Vector3<V, N>> extends Matrix<T, N> {

    int M00 = 0, M01 = 4, M02 =  8, M03 = 12;
    int M10 = 1, M11 = 5, M12 =  9, M13 = 13;
    int M20 = 2, M21 = 6, M22 = 10, M23 = 14;
    int M30 = 3, M31 = 7, M32 = 11, M33 = 15;

    @Override
    default int size() {
        return 16;
    }

    @Override
    default int rows() {
        return 4;
    }

    @Override
    default int columns() {
        return 4;
    }

    /// Linearly interpolates between this matrix and the other matrix mixing by alpha.
    /// @param alpha the alpha value in the range `[0,1]`.
    T lerp(T other, N alpha);

    /// Averages this matrix with another, using lerp for translation/scale and slerp for rotation.
    /// @param other The other matrix.
    /// @param weight Weight for this transform (other's weight is `1 - weight`)
    T average(T other, N weight);

    /// Averages an array of matrices using the same weight.
    /// @return A new matrix representing the average transform of the input matrices.
    T average(T[] matrices);

    /// Averages an array of matrices using the provided weights.
    /// @return A new matrix representing the average transform of the input matrices.
    T average(T[] matrices, N[] weights);

    /// @return The translation part of this matrix.
    V translation();

    /// @return The rotation part of this matrix.
    Quaternion rotation();

    /// @return the vector which will receive the (non-negative) scale components on each axis.
    V scale();

    /// @return the 3x3 part of this matrix as a matrix3.
    Matrix3<?, N, ?, V> asMatrix3();

    // TODO Consider SIMD versions of mulVec(), project(), and rotateVec()?

    /// @return the post-multiplied vector with this matrix.
    V transform(V vector);

    /** Postmultiplies this matrix by a translation matrix. Postmultiplication is also used by OpenGL ES' 1.x
     * glTranslate/glRotate/glScale.
     * @return This matrix for the purpose of chaining methods together. */

    T translate(V translation);

    /** Postmultiplies this matrix with a (counter-clockwise) rotation matrix. Postmultiplication is also used by OpenGL ES' 1.x
     * glTranslate/glRotate/glScale.
     * @param axis The vector axis to rotate around.
     * @param angle The angle in radians.
     * @return This matrix for the purpose of chaining methods together. */
    T rotateAround(V axis, Radians angle);

    /** Postmultiplies this matrix with a (counter-clockwise) rotation matrix. Postmultiplication is also used by OpenGL ES' 1.x
     * glTranslate/glRotate/glScale.
     * @return This matrix for the purpose of chaining methods together. */
    T rotate(Quaternion rotation);

    /** Postmultiplies this matrix by the rotation between two vectors.
     * @param v1 The base vector
     * @param v2 The target vector
     * @return This matrix for the purpose of chaining methods together */
    T rotateBetween(V v1, V v2);

    /** Post-multiplies this matrix by a rotation toward a direction.
     * @param direction direction to rotate toward
     * @param up up vector
     * @return This matrix for chaining */
    T rotateToDirection(V direction, V up);

    /** Postmultiplies this matrix with a scale matrix. Postmultiplication is also used by OpenGL ES' 1.x
     * glTranslate/glRotate/glScale.
     * @return This matrix for the purpose of chaining methods together. */
    T scale(V scale);

    V project(V vector);

    V rotate(V vector);

    V unrotate(V vector);

    V untransform(V vector);

    /** Copies the 4x3 upper-left sub-matrix into float array. The destination array is supposed to be a column major matrix.
     * @param out the destination matrix */
    void toMatrix4x3Array(N[] out);
}
