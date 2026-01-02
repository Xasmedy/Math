package io.github.xasmedy.math.matrix;

import io.github.xasmedy.math.unit.Radians;
import io.github.xasmedy.math.vector.v2.Vector2F32;
import static io.github.xasmedy.math.FloatingPointUtil.EPSILON;

/** A specialized 3x3 matrix that can represent sequences of 2D translations, scales, flips, rotations, and shears.
 * <a href="http://en.wikipedia.org/wiki/Affine_transformation">Affine transformations</a> preserve straight lines, and parallel
 * lines remain parallel after the transformation. Operations on affine matrices are faster because the last row can always be
 * assumed (0, 0, 1).
 *
 * @author vmilea */
public value record Affine2(
        float m00, float m01, float m02,
        float m10, float m11, float m12
) {

	/** Sets this matrix to the identity matrix
	 * @return This matrix for the purpose of chaining operations. */
	public Affine2 identity() {
        return new Affine2(
                1, 0, 0,
                0, 1, 0
        );
	}

    public float m20() {
        return 0;
    }

    public float m21() {
        return 0;
    }

    public float m22() {
        return 1;
    }

	/** Copies the values from the provided matrix to this matrix.
	 * @param matrix The matrix to copy, assumed to be an affine transformation.
	 * @return This matrix for the purposes of chaining. */
	public static Affine2 fromMatrix3(Matrix3 matrix) {
        return new Affine2(
                matrix.m00(), matrix.m01(), matrix.m02(),
                matrix.m10(), matrix.m11(), matrix.m12()
        );
	}

	/** Copies the 2D transformation components from the provided 4x4 matrix. The values are mapped as follows:
	 *
	 * <pre>
	 *      [  M00  M01  M03  ]
	 *      [  M10  M11  M13  ]
	 *      [   0    0    1   ]
	 * </pre>
	 *
	 * @param matrix The source matrix, assumed to be an affine transformation within XY plane. This matrix will not be modified.
	 * @return This matrix for the purpose of chaining operations. */
	public static Affine2 fromMatrix4(Matrix4 matrix) {
		return new Affine2(
				matrix.m00(), matrix.m01(), matrix.m02(),
				matrix.m10(), matrix.m11(), matrix.m12()
		);
	}

	/** Sets this matrix to a translation matrix.
	 * @return This matrix for the purpose of chaining operations. */
	public static Affine2 fromTranslation(Vector2F32 translation) {
        return new Affine2(
                1, 0, translation.x(),
                0, 1, translation.y()
        );
	}

	/** Sets this matrix to a scaling matrix.
	 * @return This matrix for the purpose of chaining operations. */
	public static Affine2 fromScale(Vector2F32 scale) {
        final float x = scale.x(), y = scale.y();
        return new Affine2(
                x, 0, 0,
                0, y, 0
        );
	}

	/** Sets this matrix to a rotation matrix that will rotate any vector in counter-clockwise direction around the z-axis.
	 * @param angle The angle in degrees.
	 * @return This matrix for the purpose of chaining operations. */
	public static Affine2 fromRotation(Radians angle) {
        final float cos = (float) Math.cos(angle.value());
        final float sin = (float) Math.sin(angle.value());
        return new Affine2(
                cos, -sin, 0,
                sin, cos, 0
        );
    }

	/** Sets this matrix to a shearing matrix.
	 * @return This matrix for the purpose of chaining operations. */
	public static Affine2 fromShear(Vector2F32 shear) {
        final float x = shear.x(), y = shear.y();
        return new Affine2(
                1, x, 0,
                y, 1, 0
        );
	}

	/** Sets this matrix to a concatenation of translation, rotation and scale. It is a more efficient form for:
	 * <code>idt().translate(x, y).rotateRad(radians).scale(scaleX, scaleY)</code>
	 * @param rotation The angle in radians.
	 * @return This matrix for the purpose of chaining operations. */
	public Affine2 fromTRS(Vector2F32 translation, Radians rotation, Vector2F32 scale) {

        final float sin = (float) Math.sin(rotation.value());
        final float cos = (float) Math.cos(rotation.value());

        // TODO Generalise this use a fromRS or something.

        final float n00 = cos * scale.x();
        final float n01 = -sin * scale.y();
        final float n02 = translation.x();
        final float n10 = sin * scale.x();
        final float n11 = cos * scale.y();
        final float n12 = translation.y();
        return new Affine2(
                n00, n01, n02,
                n10, n11, n12
        );
	}

	/** Sets this matrix to a concatenation of translation and scale. It is a more efficient form for:
	 * <code>idt().translate(x, y).scale(scaleX, scaleY)</code>
	 * @return This matrix for the purpose of chaining operations. */
	public static Affine2 fromTS(Vector2F32 translation, Vector2F32 scale) {
        return new Affine2(
                scale.x(), 0, translation.x(),
                0, scale.y(), translation.y()
        );
	}

	/** Sets this matrix to the product of two matrices.
	 * @param other Right matrix.
	 * @return This matrix for the purpose of chaining operations. */
	public Affine2 product(Affine2 other) {
		final float n00 = m00 * other.m00 + m01 * other.m10;
		final float n01 = m00 * other.m01 + m01 * other.m11;
		final float n02 = m00 * other.m02 + m01 * other.m12 + m02;
		final float n10 = m10 * other.m00 + m11 * other.m10;
		final float n11 = m10 * other.m01 + m11 * other.m11;
		final float n12 = m10 * other.m02 + m11 * other.m12 + m12;
		return new Affine2(
                n00, n01, n02,
                n10, n11, n12
        );
	}

	/** Inverts this matrix given that the determinant is != 0.
	 * @return This matrix for the purpose of chaining operations.
	 * @throws ArithmeticException if the matrix is singular (not invertible) */
	public Affine2 invert() {

        float det = determinant();
        if (Math.abs(det) < EPSILON) throw new ArithmeticException("The matrix cannot be inverted since singular.");

		float invDet = 1f / det;

		final float n00 = invDet * m11;
		final float n01 = invDet * -m01;
		final float n02 = invDet * (m01 * m12 - m11 * m02);
		final float n10 = invDet * -m10;
		final float n11 = invDet * m00;
		final float n12 = invDet * (m10 * m02 - m00 * m12);
        return new Affine2(
                n00, n01, n02,
                n10, n11, n12
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
	public Affine2 mul(Affine2 other) {
		final float n00 = m00 * other.m00 + m01 * other.m10;
		final float n01 = m00 * other.m01 + m01 * other.m11;
		final float n02 = m00 * other.m02 + m01 * other.m12 + m02;
		final float n10 = m10 * other.m00 + m11 * other.m10;
		final float n11 = m10 * other.m01 + m11 * other.m11;
		final float n12 = m10 * other.m02 + m11 * other.m12 + m12;
        return new Affine2(
                n00, n01, n02,
                n10, n11, n12
        );
	}

	/** Premultiplies this matrix with the provided matrix and stores the result in this matrix. For example:
	 *
	 * <pre>
	 * A.preMul(B) results in A := BA
	 * </pre>
	 * 
	 * @param other The other Matrix to multiply by
	 * @return This matrix for the purpose of chaining operations. */
	public Affine2 preMul(Affine2 other) {
        return other.mul(this);
	}

	/** Postmultiplies this matrix by a translation matrix.
	 * @return This matrix for the purpose of chaining. */
	public Affine2 translate(Vector2F32 translation) {
        return mul(fromTranslation(translation));
	}

	/** Premultiplies this matrix by a translation matrix.
	 * @return This matrix for the purpose of chaining. */
	public Affine2 preTranslate(Vector2F32 translation) {
        return new Affine2(
                m00, m01, m02 + translation.x(),
                m10, m11, m12 + translation.y()
        );
	}

	/** Postmultiplies this matrix with a scale matrix.
	 * @return This matrix for the purpose of chaining. */
	public Affine2 scale(Vector2F32 scale) {
        return mul(fromScale(scale));
	}

	/** Premultiplies this matrix with a scale matrix.
	 * @return This matrix for the purpose of chaining. */
	public Affine2 preScale(Vector2F32 scale) {
        return fromScale(scale).mul(this);
	}

	/** Postmultiplies this matrix with a (counter-clockwise) rotation matrix.
	 * @param angle The angle in radians
	 * @return This matrix for the purpose of chaining. */
	public Affine2 rotate(Radians angle) {
        return mul(fromRotation(angle));
	}

	/** Premultiplies this matrix with a (counter-clockwise) rotation matrix.
	 * @param angle The angle in radians
	 * @return This matrix for the purpose of chaining. */
	public Affine2 preRotate(Radians angle) {
        return fromRotation(angle).mul(this);
    }

	/** Postmultiplies this matrix by a shear matrix.
	 * @return This matrix for the purpose of chaining. */
	public Affine2 shear(Vector2F32 shear) {
        return mul(fromShear(shear));
	}

	/** Premultiplies this matrix by a shear matrix.
	 * @param shear The shear in x direction.
	 * @return This matrix for the purpose of chaining. */
	public Affine2 preShear(Vector2F32 shear) {
        return fromShear(shear).mul(this);
	}

	/** Calculates the determinant of the matrix.
	 * @return The determinant of this matrix. */
	public float determinant() {
		return m00 * m11 - m01 * m10;
	}

	/** Get the x-y translation component of the matrix.
	 * @return Filled position. */
	public Vector2F32 translation() {
		return new Vector2F32(m02, m12);
	}

	/** Check if this is a plain translation matrix.
	 * @return True if scale is 1 and rotation is 0. */
	public boolean isTranslation () {
		return m00 == 1 && m11 == 1 && m01 == 0 && m10 == 0;
	}

	/** Check if this is an indentity matrix.
	 * @return True if scale is 1 and rotation is 0. */
	public boolean isIdentity() {
		return m00 == 1 && m02 == 0 && m12 == 0 && m11 == 1 && m01 == 0 && m10 == 0;
	}

	/** Applies the affine transformation on a vector. */
	public Vector2F32 applyTo(Vector2F32 point) {
		final float x = point.x(), y = point.y();
        return new Vector2F32(
                m00 * x + m01 * y + m02,
                m10 * x + m11 * y + m12
        );
	}

    public Matrix3 asMatrix3() {
        return Matrix3.fromAffine2(this);
    }
}
