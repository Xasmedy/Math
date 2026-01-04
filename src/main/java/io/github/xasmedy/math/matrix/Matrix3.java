package io.github.xasmedy.math.matrix;

import io.github.xasmedy.math.rotation.Quaternion;
import io.github.xasmedy.math.rotation.Radians;
import io.github.xasmedy.math.vector.v2.Vector2;
import io.github.xasmedy.math.vector.v3.Vector3;

/// Matrix3x3 stored in column-major order.
///
/// Methods starting with `affine` provide optimization for Affine-Matrix3, an affine matrix3 has constant values `[0, 0, 1]` in the last row:
///
/// |   |   |    |
/// |:-:|:-:|:--:|
/// | a | b | tx |
/// | c | d | ty |
/// | 0 | 0 |  1 |
public interface Matrix3<M extends Matrix3<M, N, V2, V3>, N, V2 extends Vector2<V2, N>, V3 extends Vector3<V3, N>> extends Matrix<M, N> {

    int M00 = 0, M01 = 3, M02 = 6;
    int M10 = 1, M11 = 4, M12 = 7;
    int M20 = 2, M21 = 5, M22 = 8;

    @Override
    default int size() {
        return 9;
    }

    @Override
    default int rows() {
        return 3;
    }

    @Override
    default int columns() {
        return 3;
    }

    N m00();
    N m01();
    N m02();

    N m10();
    N m11();
    N m12();

    N m20();
    N m21();
    N m22();

    V3 transform(V3 vector);

    V2 transform(V2 vector);

    /// Applies a 3D rotation.
    M rotate(Quaternion quaternion);

    /// Removes rotation from this matrix.
    /// @apiNote The matrix is assumed to be a rotation matrix.
    V3 unrotate(V3 vector);

    /// Optimized post-multiplication for affine matrices.
    /// @apiNote Both this and the {@code other} matrix are assumed to be affine.
    M affineMul(M other);

    /** Calculates the determinant of the matrix.
     * @return The determinant of this matrix. */
    N affineDeterminant();

    /** Inverts this matrix given that the determinant is != 0.
     * @return This matrix for the purpose of chaining operations.
     * @throws ArithmeticException if the matrix is singular (not invertible) */
    M affineInvert();

    boolean isAffineSingular();

    /** Postmultiplies this matrix by a translation matrix. Postmultiplication is also used by OpenGL ES' 1.x
     * glTranslate/glRotate/glScale.
     * @param translation The translation vector.
     * @return This matrix for the purpose of chaining. */
    M affineTranslate(V2 translation);

    /** Postmultiplies this matrix with a (counter-clockwise) rotation matrix. Postmultiplication is also used by OpenGL ES' 1.x
     * glTranslate/glRotate/glScale.
     * @param angle The angle in radians
     * @return This matrix for the purpose of chaining. */
    M affineRotate(Radians angle);

    /** Postmultiplies this matrix by a shear matrix.
     * @return This matrix for the purpose of chaining. */
    M affineShear(V2 shear);

    /** Postmultiplies this matrix with a scale matrix. Postmultiplication is also used by OpenGL ES' 1.x
     * glTranslate/glRotate/glScale.
     * @param scale The vector to scale the matrix by.
     * @return This matrix for the purpose of chaining. */
    M affineScale(V2 scale);

    V2 affineScale();

    Radians affineRotation();

    V2 translation();
}
