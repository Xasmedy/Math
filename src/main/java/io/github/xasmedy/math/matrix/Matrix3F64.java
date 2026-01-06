package io.github.xasmedy.math.matrix;

import io.github.xasmedy.math.rotation.Quaternion;
import io.github.xasmedy.math.rotation.Radians;
import io.github.xasmedy.math.vector.v2.Vector2F64;
import io.github.xasmedy.math.vector.v3.Vector3F64;
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
public value record Matrix3F64(
        @NullRestricted Double m00, @NullRestricted Double m01, @NullRestricted Double m02,
        @NullRestricted Double m10, @NullRestricted Double m11, @NullRestricted Double m12,
        @NullRestricted Double m20, @NullRestricted Double m21, @NullRestricted Double m22
) implements Matrix3<Matrix3F64, Double, Vector2F64, Vector3F64> {

    /** Sets this matrix to the identity matrix
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F64 identity() {
        return new Matrix3F64(
                1d, 0d, 0d,
                0d, 1d, 0d,
                0d, 0d, 1d
        );
    }

    /** Constructs a matrix from the given double array. The array must have at least 9 elements; the first 9 will be copied.
     * @param matrix The double array to copy. Remember that this matrix is in
     *           <a href="http://en.wikipedia.org/wiki/Row-major_order#Column-major_order">column major</a> order. (The double array
     *           is not modified.) */
    public static Matrix3F64 fromArray(double[] matrix) {
        return new Matrix3F64(
                matrix[M00], matrix[M01], matrix[M02],
                matrix[M10], matrix[M11], matrix[M12],
                matrix[M20], matrix[M21], matrix[M22]
        );
    }

    /** Sets this matrix to a translation matrix.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F64 fromTranslation(Vector2F64 translation) {
        final double x = translation.x();
        final double y = translation.y();
        return new Matrix3F64(
                1d, 0d, x,
                0d, 1d, y,
                0d, 0d, 1d
        );
    }

    /** Sets this matrix to a rotation matrix that will rotate any vector in counter-clockwise direction around the z-axis.
     * @param angle the angle in radians.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F64 fromAffineRotation(Radians angle) {
        final double cos = Math.cos(angle.value());
        final double sin = Math.sin(angle.value());
        return new Matrix3F64(
                cos, -sin, 0d,
                sin, cos, 0d,
                0d, 0d, 1d
        );
    }

    public static Matrix3F64 fromRotation(Quaternion rotation) {

        final var rot = rotation.normalize();

        final double xs = rot.x() * 2d, ys = rot.y() * 2d, zs = rot.z() * 2d;
        final double wx = rot.w() * xs, wy = rot.w() * ys, wz = rot.w() * zs;
        final double xx = rot.x() * xs, xy = rot.x() * ys, xz = rot.x() * zs;
        final double yy = rot.y() * ys, yz = rot.y() * zs, zz = rot.z() * zs;

        final double m00 = 1d - (yy + zz), m01 = xy - wz       , m02 = xz + wy;
        final double m10 = xy + wz       , m11 = 1d - (xx + zz), m12 = yz - wx;
        final double m20 = xz - wy       , m21 = yz + wx       , m22 = 1d - (xx + yy);
        return new Matrix3F64(
                m00, m01, m02,
                m10, m11, m12,
                m20, m21, m22
        );
    }

    /** Sets this matrix to a scaling matrix.
     * @param scale The scale vector.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F64 fromScale(Vector2F64 scale) {
        final double x = scale.x();
        final double y = scale.y();
        return new Matrix3F64(
                x, 0d, 0d,
                0d, y, 0d,
                0d, 0d, 1d
        );
    }

    /** Sets this matrix to a concatenation of translation and scale. It is a more efficient form for:
     * <code>idt().translate(x, y).scale(scaleX, scaleY)</code>
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F64 fromST(Vector2F64 translation, Vector2F64 scale) {
        return new Matrix3F64(
                scale.x(), 0d, translation.x(),
                0d, scale.y(), translation.y(),
                0d, 0d, 1d
        );
    }

    /** Sets this matrix to a concatenation of translation, rotation and scale. It is a more efficient form for:
     * <code>idt().translate(x, y).rotateRad(radians).scale(scaleX, scaleY)</code>
     * @param rotation The angle in radians.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F64 fromAffineTRS(Vector2F64 translation, Radians rotation, Vector2F64 scale) {
        final var rot = fromAffineRotation(rotation);
        final var scl = fromScale(scale);
        final var rotScl = rot.affineMul(scl);
        return fromTranslation(translation).affineMul(rotScl);
    }

    /** Sets this matrix to a concatenation of translation, rotation and scale. It is a more efficient form for:
     * <code>idt().translate(x, y).rotateRad(radians).scale(scaleX, scaleY)</code>
     * @param rotation The angle in radians.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F64 fromTRS(Vector2F64 translation, Quaternion rotation, Vector2F64 scale) {
        final var rot = fromRotation(rotation);
        final var scl = fromScale(scale);
        final var rotScl = rot.mul(scl);
        return fromTranslation(translation).mul(rotScl);
    }

    /** Sets this 3x3 matrix to the top left 3x3 corner of the provided 4x4 matrix.
     * @param matrix The matrix whose top left corner will be copied. This matrix will not be modified.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F64 fromMatrix4(Matrix4F32 matrix) {
        // TODO Replace with Matrix4F64 version.
        return new Matrix3F64(
                (double) matrix.m00(), (double) matrix.m01(), (double) matrix.m02(),
                (double) matrix.m10(), (double) matrix.m11(), (double) matrix.m12(),
                (double) matrix.m20(), (double) matrix.m21(), (double) matrix.m22()
        );
    }

    /** Sets this matrix to a shearing matrix.
     * @return This matrix for the purpose of chaining operations. */
    public static Matrix3F64 fromShear(Vector2F64 shear) {
        final double x = shear.x(), y = shear.y();
        return new Matrix3F64(
                1d, x,  0d,
                y,  1d, 0d,
                0d, 0d, 1d
        );
    }

    @Override
    public Matrix3F64 add(Matrix3F64 other) {
        return new Matrix3F64(
                m00 + other.m00(), m01 + other.m01(), m02 + other.m02(),
                m10 + other.m10(), m11 + other.m11(), m12 + other.m12(),
                m20 + other.m20(), m21 + other.m21(), m22 + other.m22()
        );
    }

    @Override
    public Matrix3F64 sub(Matrix3F64 other) {
        return new Matrix3F64(
                m00 - other.m00(), m01 - other.m01(), m02 - other.m02(),
                m10 - other.m10(), m11 - other.m11(), m12 - other.m12(),
                m20 - other.m20(), m21 - other.m21(), m22 - other.m22()
        );
    }

    @Override
    public Matrix3F64 mul(Matrix3F64 other) {
        final double n00 = m00 * other.m00 + m01 * other.m10 + m02 * other.m20;
        final double n01 = m00 * other.m01 + m01 * other.m11 + m02 * other.m21;
        final double n02 = m00 * other.m02 + m01 * other.m12 + m02 * other.m22;
        final double n10 = m10 * other.m00 + m11 * other.m10 + m12 * other.m20;
        final double n11 = m10 * other.m01 + m11 * other.m11 + m12 * other.m21;
        final double n12 = m10 * other.m02 + m11 * other.m12 + m12 * other.m22;
        final double n20 = m20 * other.m00 + m21 * other.m10 + m22 * other.m20;
        final double n21 = m20 * other.m01 + m21 * other.m11 + m22 * other.m21;
        final double n22 = m20 * other.m02 + m21 * other.m12 + m22 * other.m22;
        return new Matrix3F64(
                n00, n01, n02,
                n10, n11, n12,
                n20, n21, n22
        );
    }

    @Override
    public Matrix3F64 preMul(Matrix3F64 other) {
        return other.mul(this);
    }

    @Override
    public Vector3F64 transform(Vector3F64 vector) {
        final double x = vector.x() * m00 + vector.y() * m01 + vector.z() * m02;
        final double y = vector.x() * m10 + vector.y() * m11 + vector.z() * m12;
        final double z = vector.x() * m20 + vector.y() * m21 + vector.z() * m22;
        return v3(x, y, z);
    }

    @Override
    public Vector2F64 transform(Vector2F64 vector) {
        final double x = vector.x(), y = vector.y();
        return new Vector2F64(
                m00 * x + m01 * y + m02,
                m10 * x + m11 * y + m12
        );
    }

    @Override
    public Double determinant() {
        return m00 * m11 * m22 + m01 * m12 * m20 + m02 * m10 * m21
                -m00 * m12 * m21 - m01 * m10 * m22 - m02 * m11 * m20;
    }

    @Override
    public boolean isSingular() {
        return Math.abs(determinant()) < EPSILON;
    }

    @Override
    public Matrix3F64 invert() {

        final double det = determinant();
        if (Math.abs(det) < EPSILON) throw new ArithmeticException("The matrix cannot be inverted since singular.");

        final double inv_det = 1d / det;

        final double n00 = (m11 * m22 - m21 * m12) * inv_det;
        final double n01 = (m21 * m02 - m01 * m22) * inv_det;
        final double n02 = (m01 * m12 - m11 * m02) * inv_det;
        final double n10 = (m20 * m12 - m10 * m22) * inv_det;
        final double n11 = (m00 * m22 - m20 * m02) * inv_det;
        final double n12 = (m10 * m02 - m00 * m12) * inv_det;
        final double n20 = (m10 * m21 - m20 * m11) * inv_det;
        final double n21 = (m20 * m01 - m00 * m21) * inv_det;
        final double n22 = (m00 * m11 - m10 * m01) * inv_det;
        return new Matrix3F64(
                n00, n01, n02,
                n10, n11, n12,
                n20, n21, n22
        );
    }

    @Override
    public Matrix3F64 transpose() {
        return new Matrix3F64(
                m00, m10, m20,
                m01, m11, m21,
                m02, m12, m22
        );
    }

    @Override
    public Matrix3F64 rotate(Quaternion quaternion) {
        return mul(fromRotation(quaternion));
    }

    @Override
    public Vector3F64 unrotate(Vector3F64 vector) {
        return transpose().transform(vector);
    }

    @Override
    public Matrix3F64 affineMul(Matrix3F64 other) {
        final double n00 = m00 * other.m00 + m01 * other.m10;
        final double n01 = m00 * other.m01 + m01 * other.m11;
        final double n02 = m00 * other.m02 + m01 * other.m12 + m02;
        final double n10 = m10 * other.m00 + m11 * other.m10;
        final double n11 = m10 * other.m01 + m11 * other.m11;
        final double n12 = m10 * other.m02 + m11 * other.m12 + m12;
        return new Matrix3F64(
                n00, n01, n02,
                n10, n11, n12,
                0d, 0d, 1d
        );
    }

    @Override
    public Double affineDeterminant() {
        return m00 * m11 - m01 * m10;
    }

    @Override
    public Matrix3F64 affineInvert() {

        final double det = affineDeterminant();
        if (Math.abs(det) < EPSILON) throw new ArithmeticException("The matrix cannot be inverted since singular.");

        final double invDet = 1d / det;

        final double n00 = invDet * m11;
        final double n01 = invDet * -m01;
        final double n02 = invDet * (m01 * m12 - m11 * m02);
        final double n10 = invDet * -m10;
        final double n11 = invDet * m00;
        final double n12 = invDet * (m10 * m02 - m00 * m12);
        return new Matrix3F64(
                n00, n01, n02,
                n10, n11, n12,
                0d, 0d, 1d
        );
    }

    @Override
    public boolean isAffineSingular() {
        return Math.abs(affineDeterminant()) < EPSILON;
    }

    @Override
    public Matrix3F64 affineTranslate(Vector2F64 translation) {
        return affineMul(fromTranslation(translation));
    }

    @Override
    public Matrix3F64 affineRotate(Radians angle) {
        return affineMul(fromAffineRotation(angle));
    }

    @Override
    public Matrix3F64 affineShear(Vector2F64 shear) {
        return affineMul(fromShear(shear));
    }

    @Override
    public Matrix3F64 affineScale(Vector2F64 scale) {
        return affineMul(fromScale(scale));
    }

    @Override
    public Vector2F64 affineScale() {
        final double x = Math.sqrt(m00 * m00 + m01 * m01);
        final double y = Math.sqrt(m10 * m10 + m11 * m11);
        return new Vector2F64(x, y);
    }

    @Override
    public Vector2F64 translation() {
        return new Vector2F64(m02, m12);
    }

    @Override
    public Radians affineRotation() {
        return Radians.radians(Math.atan2(m10, m00));
    }

    @Override
    public Double[] asArray() {
        return new Double[] {
                m00, m10, m20,
                m01, m11, m21,
                m02, m12, m22
        };
    }

    @Override
    public MemorySegment asMemorySegment(Arena arena) {

        final var layout = ValueLayout.JAVA_DOUBLE;
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
