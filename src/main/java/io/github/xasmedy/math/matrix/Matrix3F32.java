package io.github.xasmedy.math.matrix;

import io.github.xasmedy.math.rotation.Quaternion;
import io.github.xasmedy.math.rotation.Radians;
import io.github.xasmedy.math.vector.v2.Vector2F32;
import io.github.xasmedy.math.vector.v3.Vector3F32;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import static io.github.xasmedy.math.FloatingPointUtil.EPSILON;
import static io.github.xasmedy.math.vector.Vectors.v3;

/// A Matrix3x3, the fields layout and math is row-major for ease of use.\
/// Methods like {@link Matrix#asArray()} and {@link Matrix#asMemorySegment(Arena)} return the column-major representation.
@LooselyConsistentValue
public value record Matrix3F32(
        @NullRestricted Float m00, @NullRestricted Float m01, @NullRestricted Float m02,
        @NullRestricted Float m10, @NullRestricted Float m11, @NullRestricted Float m12,
        @NullRestricted Float m20, @NullRestricted Float m21, @NullRestricted Float m22
) implements Matrix3<Matrix3F32, Float, Vector2F32, Vector3F32> {

    /** Sets this matrix to the identity matrix
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F32 identity() {
        return new Matrix3F32(
                1f, 0f, 0f,
                0f, 1f, 0f,
                0f, 0f, 1f
        );
    }

	/** Constructs a matrix from the given float array. The array must have at least 9 elements; the first 9 will be copied.
	 * @param matrix The float array to copy. Remember that this matrix is in
	 *           <a href="http://en.wikipedia.org/wiki/Row-major_order#Column-major_order">column major</a> order. (The float array
	 *           is not modified.) */
	public static Matrix3F32 fromArray(float[] matrix) {
        return new Matrix3F32(
                matrix[M00], matrix[M01], matrix[M02],
                matrix[M10], matrix[M11], matrix[M12],
                matrix[M20], matrix[M21], matrix[M22]
        );
	}

    /** Sets this matrix to a translation matrix.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F32 fromTranslation(Vector2F32 translation) {
        final float x = translation.x();
        final float y = translation.y();
        return new Matrix3F32(
                1f, 0f, x,
                0f, 1f, y,
                0f, 0f, 1f
        );
    }

    /** Sets this matrix to a rotation matrix that will rotate any vector in counter-clockwise direction around the z-axis.
     * @param angle the angle in radians.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F32 fromAffineRotation(Radians angle) {
        final float cos = (float) Math.cos(angle.value());
        final float sin = (float) Math.sin(angle.value());
        return new Matrix3F32(
                cos, -sin, 0f,
                sin, cos, 0f,
                0f, 0f, 1f
        );
    }

    public static Matrix3F32 fromRotation(Quaternion rotation) {

        final var rot = rotation.normalize();

        final float xs = rot.x() * 2f, ys = rot.y() * 2f, zs = rot.z() * 2f;
        final float wx = rot.w() * xs, wy = rot.w() * ys, wz = rot.w() * zs;
        final float xx = rot.x() * xs, xy = rot.x() * ys, xz = rot.x() * zs;
        final float yy = rot.y() * ys, yz = rot.y() * zs, zz = rot.z() * zs;

        final float m00 = 1f - (yy + zz), m01 = xy - wz       , m02 = xz + wy;
        final float m10 = xy + wz       , m11 = 1f - (xx + zz), m12 = yz - wx;
        final float m20 = xz - wy       , m21 = yz + wx       , m22 = 1f - (xx + yy);
        return new Matrix3F32(
                m00, m01, m02,
                m10, m11, m12,
                m20, m21, m22
        );
    }

    /** Sets this matrix to a scaling matrix.
     * @param scale The scale vector.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F32 fromScale(Vector2F32 scale) {
        final float x = scale.x();
        final float y = scale.y();
        return new Matrix3F32(
                x, 0f, 0f,
                0f, y, 0f,
                0f, 0f, 1f
        );
    }

    /** Sets this matrix to a concatenation of translation and scale. It is a more efficient form for:
     * <code>idt().translate(x, y).scale(scaleX, scaleY)</code>
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F32 fromST(Vector2F32 translation, Vector2F32 scale) {
        return new Matrix3F32(
                scale.x(), 0f, translation.x(),
                0f, scale.y(), translation.y(),
                0f, 0f, 1f
        );
    }

    /** Sets this matrix to a concatenation of translation, rotation and scale. It is a more efficient form for:
     * <code>idt().translate(x, y).rotateRad(radians).scale(scaleX, scaleY)</code>
     * @param rotation The angle in radians.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F32 fromAffineTRS(Vector2F32 translation, Radians rotation, Vector2F32 scale) {
        final var rot = fromAffineRotation(rotation);
        final var scl = fromScale(scale);
        final var rotScl = rot.affineMul(scl);
        return fromTranslation(translation).affineMul(rotScl);
    }

    /** Sets this matrix to a concatenation of translation, rotation and scale. It is a more efficient form for:
     * <code>idt().translate(x, y).rotateRad(radians).scale(scaleX, scaleY)</code>
     * @param rotation The angle in radians.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F32 fromTRS(Vector2F32 translation, Quaternion rotation, Vector2F32 scale) {
        final var rot = fromRotation(rotation);
        final var scl = fromScale(scale);
        final var rotScl = rot.mul(scl);
        return fromTranslation(translation).mul(rotScl);
    }

    /** Sets this 3x3 matrix to the top left 3x3 corner of the provided 4x4 matrix.
     * @param matrix The matrix whose top left corner will be copied. This matrix will not be modified.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F32 fromMatrix4(Matrix4F32 matrix) {
        return new Matrix3F32(
                matrix.m00(), matrix.m01(), matrix.m02(),
                matrix.m10(), matrix.m11(), matrix.m12(),
                matrix.m20(), matrix.m21(), matrix.m22()
        );
    }

    /** Sets this matrix to a shearing matrix.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F32 fromShear(Vector2F32 shear) {
        final float x = shear.x(), y = shear.y();
        return new Matrix3F32(
                1f, x,  0f,
                y,  1f, 0f,
                0f, 0f, 1f
        );
    }

    @Override
    public Matrix3F32 add(Matrix3F32 other) {
        return new Matrix3F32(
                m00 + other.m00(), m01 + other.m01(), m02 + other.m02(),
                m10 + other.m10(), m11 + other.m11(), m12 + other.m12(),
                m20 + other.m20(), m21 + other.m21(), m22 + other.m22()
        );
    }

    @Override
    public Matrix3F32 sub(Matrix3F32 other) {
        return new Matrix3F32(
                m00 - other.m00(), m01 - other.m01(), m02 - other.m02(),
                m10 - other.m10(), m11 - other.m11(), m12 - other.m12(),
                m20 - other.m20(), m21 - other.m21(), m22 - other.m22()
        );
    }

    @Override
    public Matrix3F32 mul(Matrix3F32 other) {
		final float n00 = m00 * other.m00 + m01 * other.m10 + m02 * other.m20;
		final float n01 = m00 * other.m01 + m01 * other.m11 + m02 * other.m21;
		final float n02 = m00 * other.m02 + m01 * other.m12 + m02 * other.m22;
		final float n10 = m10 * other.m00 + m11 * other.m10 + m12 * other.m20;
		final float n11 = m10 * other.m01 + m11 * other.m11 + m12 * other.m21;
		final float n12 = m10 * other.m02 + m11 * other.m12 + m12 * other.m22;
		final float n20 = m20 * other.m00 + m21 * other.m10 + m22 * other.m20;
		final float n21 = m20 * other.m01 + m21 * other.m11 + m22 * other.m21;
		final float n22 = m20 * other.m02 + m21 * other.m12 + m22 * other.m22;
        return new Matrix3F32(
                n00, n01, n02,
                n10, n11, n12,
                n20, n21, n22
        );
	}

    @Override
    public Vector3F32 transform(Vector3F32 vector) {
        final float x = vector.x() * m00 + vector.y() * m01 + vector.z() * m02;
        final float y = vector.x() * m10 + vector.y() * m11 + vector.z() * m12;
        final float z = vector.x() * m20 + vector.y() * m21 + vector.z() * m22;
        return v3(x, y, z);
    }

    @Override
    public Vector2F32 transform(Vector2F32 vector) {
        final float x = vector.x(), y = vector.y();
        return new Vector2F32(
                m00 * x + m01 * y + m02,
                m10 * x + m11 * y + m12
        );
    }

    @Override
	public Float determinant() {
		return m00 * m11 * m22 + m01 * m12 * m20 + m02 * m10 * m21
			  -m00 * m12 * m21 - m01 * m10 * m22 - m02 * m11 * m20;
	}

    @Override
    public boolean isSingular() {
        return Math.abs(determinant()) < EPSILON;
    }

    @Override
    public Matrix3F32 invert() {

		final float det = determinant();
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
        return new Matrix3F32(
                n00, n01, n02,
                n10, n11, n12,
                n20, n21, n22
        );
	}

    @Override
    public Matrix3F32 transpose() {
        return new Matrix3F32(
                m00, m10, m20,
                m01, m11, m21,
                m02, m12, m22
        );
    }

    @Override
    public Matrix3F32 rotate(Quaternion quaternion) {
        return mul(fromRotation(quaternion));
    }

    @Override
    public Vector3F32 unrotate(Vector3F32 vector) {
        return transpose().transform(vector);
    }

    @Override
    public Matrix3F32 affineMul(Matrix3F32 other) {
        final float n00 = m00 * other.m00 + m01 * other.m10;
        final float n01 = m00 * other.m01 + m01 * other.m11;
        final float n02 = m00 * other.m02 + m01 * other.m12 + m02;
        final float n10 = m10 * other.m00 + m11 * other.m10;
        final float n11 = m10 * other.m01 + m11 * other.m11;
        final float n12 = m10 * other.m02 + m11 * other.m12 + m12;
        return new Matrix3F32(
                n00, n01, n02,
                n10, n11, n12,
                0f, 0f, 1f
        );
    }

    @Override
    public Float affineDeterminant() {
        return m00 * m11 - m01 * m10;
    }

    @Override
    public Matrix3F32 affineInvert() {

        final float det = affineDeterminant();
        if (Math.abs(det) < EPSILON) throw new ArithmeticException("The matrix cannot be inverted since singular.");

        final float invDet = 1f / det;

        final float n00 = invDet * m11;
        final float n01 = invDet * -m01;
        final float n02 = invDet * (m01 * m12 - m11 * m02);
        final float n10 = invDet * -m10;
        final float n11 = invDet * m00;
        final float n12 = invDet * (m10 * m02 - m00 * m12);
        return new Matrix3F32(
                n00, n01, n02,
                n10, n11, n12,
                0f, 0f, 1f
        );
    }

    @Override
    public boolean isAffineSingular() {
        return Math.abs(affineDeterminant()) < EPSILON;
    }

    @Override
    public Matrix3F32 affineTranslate(Vector2F32 translation) {
        return affineMul(fromTranslation(translation));
	}

    @Override
    public Matrix3F32 affineRotate(Radians angle) {
        return affineMul(fromAffineRotation(angle));
	}

    @Override
    public Matrix3F32 affineShear(Vector2F32 shear) {
        return affineMul(fromShear(shear));
    }

    @Override
    public Matrix3F32 affineScale(Vector2F32 scale) {
        return affineMul(fromScale(scale));
	}

    @Override
    public Vector2F32 affineScale() {
        final float x = (float) Math.sqrt(m00 * m00 + m01 * m01);
        final float y = (float) Math.sqrt(m10 * m10 + m11 * m11);
        return new Vector2F32(x, y);
    }

    @Override
	public Vector2F32 translation() {
        return new Vector2F32(m02, m12);
	}

    @Override
	public Radians affineRotation() {
		return Radians.radians(Math.atan2(m10, m00));
	}

    @Override
    public Float[] asArray() {
        return new Float[] {
                m00, m10, m20,
                m01, m11, m21,
                m02, m12, m22
        };
    }

    @Override
    public MemorySegment asMemorySegment(Arena arena) {

        final var layout = ValueLayout.JAVA_FLOAT;
        final var segment = arena.allocate(size() * layout.byteSize());

        segment.setAtIndex(layout, M00, m00);
        segment.setAtIndex(layout, M01, m01);
        segment.setAtIndex(layout, M02, m02);

        segment.setAtIndex(layout, M10, m10);
        segment.setAtIndex(layout, M11, m11);
        segment.setAtIndex(layout, M12, m12);

        segment.setAtIndex(layout, M20, m20);
        segment.setAtIndex(layout, M21, m21);
        segment.setAtIndex(layout, M22, m22);

        return segment;
    }
}
