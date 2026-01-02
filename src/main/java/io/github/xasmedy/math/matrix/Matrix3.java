package io.github.xasmedy.math.matrix;

import io.github.xasmedy.math.complex.Quaternion;
import io.github.xasmedy.math.unit.Radians;
import io.github.xasmedy.math.vector.v2.Vector2F32;
import io.github.xasmedy.math.vector.v3.Vector3F32;
import static io.github.xasmedy.math.FloatingPointUtil.EPSILON;

/// A Matrix3x3 stored in major-order.
public value record Matrix3(
        float m00, float m01, float m02,
        float m10, float m11, float m12,
        float m20, float m21, float m22
) {

    // TODO fromTRS are missing, implement them.

	public static final int M00 = 0, M01 = 3, M02 = 6;
	public static final int M10 = 1, M11 = 4, M12 = 7;
	public static final int M20 = 2, M21 = 5, M22 = 8;

	/** Constructs a matrix from the given float array. The array must have at least 9 elements; the first 9 will be copied.
	 * @param matrix The float array to copy. Remember that this matrix is in
	 *           <a href="http://en.wikipedia.org/wiki/Row-major_order#Column-major_order">column major</a> order. (The float array
	 *           is not modified.) */
	public static Matrix3 fromArray(float[] matrix) {
        return new Matrix3(
                matrix[M00], matrix[M01], matrix[M02],
                matrix[M10], matrix[M11], matrix[M12],
                matrix[M20], matrix[M21], matrix[M22]
        );
	}

	/** Sets this matrix to the identity matrix
	 * @return This matrix for the purpose of chaining operations. */
	public static Matrix3 identity() {
        return new Matrix3(
                1, 0, 0,
                0, 1, 0,
                0, 0, 1
        );
	}

    /** Sets this matrix to a rotation matrix that will rotate any vector in counter-clockwise direction around the z-axis.
     * @param angle the angle in radians.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3 fromRotation(Radians angle) {
        final float cos = (float) Math.cos(angle.value());
        final float sin = (float) Math.sin(angle.value());
        return new Matrix3(
                cos, -sin, 0,
                sin, cos, 0,
                0, 0, 1
        );
    }

    public static Matrix3 fromRotation(Quaternion rotation) {

        final var rot = rotation.normalize();

        final float xs = rot.x() * 2f, ys = rot.y() * 2f, zs = rot.z() * 2f;
        final float wx = rot.w() * xs, wy = rot.w() * ys, wz = rot.w() * zs;
        final float xx = rot.x() * xs, xy = rot.x() * ys, xz = rot.x() * zs;
        final float yy = rot.y() * ys, yz = rot.y() * zs, zz = rot.z() * zs;

        final float m00 = 1f - (yy + zz), m01 = xy - wz       , m02 = xz + wy;
        final float m10 = xy + wz       , m11 = 1f - (xx + zz), m12 = yz - wx;
        final float m20 = xz - wy       , m21 = yz + wx       , m22 = 1f - (xx + yy);
        return new Matrix3(
                m00, m01, m02,
                m10, m11, m12,
                m20, m21, m22
        );
    }

    /** Sets this matrix to a translation matrix.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3 fromTranslation(Vector2F32 translation) {
        final float x = translation.x();
        final float y = translation.y();
        return new Matrix3(
                1, 0, x,
                0, 1, y,
                0, 0, 1
        );
    }

    /** Sets this matrix to a scaling matrix.
     * @param scale The scale vector.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3 fromScale(Vector2F32 scale) {
        final float x = scale.x();
        final float y = scale.y();
        return new Matrix3(
                x, 0, 0,
                0, y, 0,
                0, 0, 1
        );
    }

    public static Matrix3 fromAxisAngle(Vector3F32 axis, Radians angle) {
        return fromRotation(Quaternion.fromAxisAngle(axis, angle));
    }

    /** Sets this 3x3 matrix to the top left 3x3 corner of the provided 4x4 matrix.
     * @param matrix The matrix whose top left corner will be copied. This matrix will not be modified.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3 fromMatrix4(Matrix4 matrix) {
        return new Matrix3(
                matrix.m00(), matrix.m01(), matrix.m02(),
                matrix.m10(), matrix.m11(), matrix.m12(),
                matrix.m20(), matrix.m21(), matrix.m22()
        );
    }

    /** Copies the values from the provided affine matrix to this matrix. The last row is set to (0, 0, 1).
     * @param affine The affine matrix to copy.
     * @return This matrix for the purposes of chaining. */
    public static Matrix3 fromAffine2(Affine2 affine) {
        return new Matrix3(
                affine.m00(), affine.m01(), affine.m02(),
                affine.m10(), affine.m11(), affine.m12(),
                affine.m20(), affine.m21(), affine.m22()
        );
    }

	/** Postmultiplies this matrix with the provided matrix and stores the result in this matrix. For example:
	 *
	 * <pre>
	 * A.mul(B) results in A := AB
	 * </pre>
	 *
	 * @param other Matrix to multiply by.
	 * @return This matrix for the purpose of chaining operations together. */
	public Matrix3 mul(Matrix3 other) {
		final float n00 = m00 * other.m00 + m01 * other.m10 + m02 * other.m20;
		final float n01 = m00 * other.m01 + m01 * other.m11 + m02 * other.m21;
		final float n02 = m00 * other.m02 + m01 * other.m12 + m02 * other.m22;
		final float n10 = m10 * other.m00 + m11 * other.m10 + m12 * other.m20;
		final float n11 = m10 * other.m01 + m11 * other.m11 + m12 * other.m21;
		final float n12 = m10 * other.m02 + m11 * other.m12 + m12 * other.m22;
		final float n20 = m20 * other.m00 + m21 * other.m10 + m22 * other.m20;
		final float n21 = m20 * other.m01 + m21 * other.m11 + m22 * other.m21;
		final float n22 = m20 * other.m02 + m21 * other.m12 + m22 * other.m22;
        return new Matrix3(
                n00, n01, n02,
                n10, n11, n12,
                n20, n21, n22
        );
	}

	/** Premultiplies this matrix with the provided matrix and stores the result in this matrix. For example:
	 *
	 * <pre>
	 * A.mulLeft(B) results in A := BA
	 * </pre>
	 *
	 * @param other The other Matrix to multiply by
	 * @return This matrix for the purpose of chaining operations. */
	public Matrix3 preMul(Matrix3 other) {
        return other.mul(this);
	}

	/** @return The determinant of this matrix */
	public float determinant() {
		return m00 * m11 * m22 + m01 * m12 * m20 + m02 * m10 * m21
			  -m00 * m12 * m21 - m01 * m10 * m22 - m02 * m11 * m20;
	}

    /// @return true if the matrix is a singular matrix.
    public boolean isSingular() {
        return Math.abs(determinant()) < EPSILON;
    }

	/// Inverts this matrix given that the determinant is != 0.
	/// @return This matrix for the purpose of chaining operations.
    /// @throws ArithmeticException if the matrix cannot be inverted because it is singular.
	public Matrix3 invert() {

		float det = determinant();
        if (Math.abs(det) < EPSILON) throw new ArithmeticException("The matrix cannot be inverted since singular.");

		final float inv_det = 1f / det;

		final float n00 = (m11 * m22 - m21 * m12) * inv_det;
        final float n01 = (m21 * m02 - m01 * m22) * inv_det;
        final float n02 = (m01 * m12 - m11 * m02) * inv_det;
        final float n10 = (m20 * m12 - m10 * m22) * inv_det;
        final float n11 = (m00 * m22 - m20 * m02) * inv_det;
        final float n12 = (m10 * m02 - m00 * m12) * inv_det;
        final float n20 = (m10 * m21 - m20 * m11) * inv_det;
        final float n21 = (m20 * m01 - m00 * m21) * inv_det;
		final float n22 = (m00 * m11 - m10 * m01) * inv_det;
        return new Matrix3(
                n00, n01, n02,
                n10, n11, n12,
                n20, n21, n22
        );
	}

	/** Postmultiplies this matrix by a translation matrix. Postmultiplication is also used by OpenGL ES' 1.x
	 * glTranslate/glRotate/glScale.
	 * @param translation The translation vector.
	 * @return This matrix for the purpose of chaining. */
	public Matrix3 translate(Vector2F32 translation) {
        return mul(fromTranslation(translation));
	}

    /** Adds a translational component to the matrix in the 3rd column. The other columns are untouched.
     * @param scale The translation vector.
     * @return This matrix for the purpose of chaining. */
    public Matrix3 preTranslate(Vector2F32 scale) {
        final float n02 = m02 + scale.x();
        final float n12 = m12 + scale.y();
        return new Matrix3(
                m00, m01, n02,
                m10, m11, n12,
                m20, m21, m22
        );
    }

	/** Postmultiplies this matrix with a (counter-clockwise) rotation matrix. Postmultiplication is also used by OpenGL ES' 1.x
	 * glTranslate/glRotate/glScale.
	 * @param angle The angle in radians
	 * @return This matrix for the purpose of chaining. */
	public Matrix3 rotate(Radians angle) {
        return mul(fromRotation(angle));
	}

    public Matrix3 preRotate(Radians angle) {
        return fromRotation(angle).mul(this);
    }

    /** Postmultiplies this matrix with a scale matrix. Postmultiplication is also used by OpenGL ES' 1.x
	 * glTranslate/glRotate/glScale.
	 * @param scale The vector to scale the matrix by.
	 * @return This matrix for the purpose of chaining. */
	public Matrix3 scale(Vector2F32 scale) {
        return mul(fromScale(scale));
	}

    public Matrix3 preScale(Vector2F32 scale) {
        return fromScale(scale).mul(this);
    }

    public Vector2F32 scale() {
        final float x = (float) Math.sqrt(m00 * m00 + m01 * m01);
        final float y = (float) Math.sqrt(m10 * m10 + m11 * m11);
        return new Vector2F32(x, y);
    }

    /** Transposes the current matrix.
     * @return This matrix for the purpose of chaining methods together. */
    public Matrix3 transpose() {
        return new Matrix3(
                m00, m10, m20,
                m01, m11, m21,
                m02, m12, m22
        );
    }

	public Vector2F32 translation(Vector2F32 position) {
        return new Vector2F32(m02, m12);
	}

	public Radians rotation() {
		return Radians.radians(Math.atan2(m10, m00));
	}

    public Affine2 asAffine2() {
        return Affine2.fromMatrix3(this);
    }
}
