package io.github.xasmedy.math.matrix;

import io.github.xasmedy.math.rotation.Quaternion;
import io.github.xasmedy.math.rotation.Radians;
import io.github.xasmedy.math.vector.v3.Vector3;

/// Immutable Matrix4x4 always using post-multiplication.
/// Internal indexing is row-major, while external raw output is column-major.
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
    /// @return a new matrix representing the average transform of the input matrices.
    T average(T[] matrices);

    /// Averages an array of matrices using the provided weights.
    /// @return a new matrix representing the average transform of the input matrices.
    T average(T[] matrices, N[] weights);

    /// @return the translation part of this matrix.
    V translation();

    /// @return the rotation part of this matrix.
    Quaternion rotation();

    /// @return the scale components along each axis.
    V scale();

    /// @return the 3x3 part of this matrix as a matrix3.
    Matrix3<?, N, ?, V> asMatrix3();

    // TODO Consider SIMD versions of mulVec(), project(), and rotateVec()?

    /// Transforms a 3D position vector using the affine part of this matrix.
    /// @return the transformed vector.
    V transform(V vector);

    /// @return a new matrix with the translation applied.
    T translate(V translation);

    /// @return a new matrix with the rotation around the given axis applied.
    T rotateAround(V axis, Radians angle);

    /// @return a new matrix with the given rotation applied.
    T rotate(Quaternion rotation);

    /// @return a new matrix that rotates the direction of `v1` to align with `v2`.
    T rotateBetween(V v1, V v2);

    /// @return a new matrix that rotates to align the forward direction with `direction` and up vector with `up`.
    T rotateToDirection(V direction, V up);

    /// @return a new matrix with the given scale applied.
    T scale(V scale);

    /// Projects a 3D position vector using this matrix and performs a perspective divide.
    /// @apiNote Includes rotation, translation, scale, and perspective; output is divided by W.
    V project(V vector);

    /// Rotates a 3D direction vector using the 3×3 part of this matrix.
    /// @apiNote Translation and perspective components are ignored.
    V rotate(V vector);

    /// Applies the inverse rotation of this matrix to a 3D vector, undoing {@link #rotate(Vector3)}.
    /// @apiNote Translation and perspective components are ignored. Non-uniform scale in the 3×3 submatrix may affect results.
    V unrotate(V vector);

    /// Applies the inverse affine transformation of this matrix to a 3D vector.
    V untransform(V vector);

    /// Copies the 4x3 upper-left matrix into the array.
    /// @apiNote The layout is column-major order.
    void toMatrix4x3Array(N[] out);
}
