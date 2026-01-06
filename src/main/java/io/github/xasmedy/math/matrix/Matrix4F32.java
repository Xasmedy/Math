package io.github.xasmedy.math.matrix;

import io.github.xasmedy.math.rotation.Quaternion;
import io.github.xasmedy.math.rotation.Radians;
import io.github.xasmedy.math.vector.v2.Vector2F32;
import io.github.xasmedy.math.vector.v3.Vector3F32;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import static io.github.xasmedy.math.FloatingPointUtil.EPSILON;
import static io.github.xasmedy.math.vector.Vectors.v3;

/// Matrix4x4 stored in column-major order.
@SuppressWarnings("unused")
public value record Matrix4F32(
        // I'm not using an array because it's an identity object, and this reads and feels better to work with.
        float m00, float m01, float m02, float m03,
        float m10, float m11, float m12, float m13,
        float m20, float m21, float m22, float m23,
        float m30, float m31, float m32, float m33
) implements Matrix4<Matrix4F32, Float, Vector3F32> {

    public static final int M00 = 0, M01 = 4, M02 =  8, M03 = 12;
    public static final int M10 = 1, M11 = 5, M12 =  9, M13 = 13;
    public static final int M20 = 2, M21 = 6, M22 = 10, M23 = 14;
    public static final int M30 = 3, M31 = 7, M32 = 11, M33 = 15;

    public static Matrix4F32 identity() {
        return new Matrix4F32(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        );
    }

    /// Creates a matrix4 array from the provided matrices array.
    /// @param matrices4 a flat array of matrices.
    /// @apiNote The matrices array length must be a multiple of 16.
    public static Matrix4F32[] fromMatricesArray(float[] matrices4) {

        if (matrices4.length % 16 != 0) throw new IllegalArgumentException(String.format(
                "The matrices array provided is not big enough to be a 4x4 matrix. [Current: %d]",
                matrices4.length)
        );

        final Matrix4F32[] matrices = new Matrix4F32[matrices4.length / 16];
        for (int i = 0; i < matrices.length; i++) {
            final int p = i * 16;
            matrices[i] = new Matrix4F32(
                    matrices4[p + M00], matrices4[p + M01], matrices4[p + M02], matrices4[p + M03],
                    matrices4[p + M10], matrices4[p + M11], matrices4[p + M12], matrices4[p + M13],
                    matrices4[p + M20], matrices4[p + M21], matrices4[p + M22], matrices4[p + M23],
                    matrices4[p + M30], matrices4[p + M31], matrices4[p + M32], matrices4[p + M33]
            );
        }
        return matrices;
    }

    /// Creates a matrix4 from the provided matrix array.
    /// @param matrix4 the matrix to copy from.
    /// @return the copied matrix.
    /// @apiNote The matrix array must be at least 16 in length, if longer, the first 16 elements are used.
    public static Matrix4F32 fromArray(float[] matrix4) {

        if (matrix4.length < 16) throw new IllegalArgumentException(String.format(
                "The matrix array provided is not big enough to be a 4x4 matrix. [Current: %d]",
                matrix4.length)
        );
        return new Matrix4F32(
                matrix4[M00], matrix4[M01], matrix4[M02], matrix4[M03],
                matrix4[M10], matrix4[M11], matrix4[M12], matrix4[M13],
                matrix4[M20], matrix4[M21], matrix4[M22], matrix4[M23],
                matrix4[M30], matrix4[M31], matrix4[M32], matrix4[M33]
        );
    }

    /// Creates a transformation matrix from a translation and rotation.
    /// @return The transformation matrix.
    /// @apiNote The rotation quaternion is normalized internally.
    public static Matrix4F32 fromTR(Vector3F32 translation, Quaternion rotation) {

        final var rot = rotation.normalize();

        final float xs = rot.x() * 2f, ys = rot.y() * 2f, zs = rot.z() * 2f;
        final float wx = rot.w() * xs, wy = rot.w() * ys, wz = rot.w() * zs;
        final float xx = rot.x() * xs, xy = rot.x() * ys, xz = rot.x() * zs;
        final float yy = rot.y() * ys, yz = rot.y() * zs, zz = rot.z() * zs;

        final float m00 = 1f - (yy + zz), m01 = xy - wz       , m02 = xz + wy       , m03 = translation.x();
        final float m10 = xy + wz       , m11 = 1f - (xx + zz), m12 = yz - wx       , m13 = translation.y();
        final float m20 = xz - wy       , m21 = yz + wx       , m22 = 1f - (xx + yy), m23 = translation.z();
        final float m30 = 0             , m31 = 0             , m32 = 0             , m33 = 1;
        return new Matrix4F32(
                m00, m01, m02, m03,
                m10, m11, m12, m13,
                m20, m21, m22, m23,
                m30, m31, m32, m33
        );
    }

    /// @return a new rotation matrix around the given axis.
    public static Matrix4F32 fromAxisAngle(Vector3F32 axis, Radians angle) {
        if (angle.value() == 0) return identity();
        final var quat = Quaternion.fromAxisAngle(axis, angle);
        return fromRotation(quat);
    }

    /// @return a pure rotation matrix from the quaternion.
    public static Matrix4F32 fromRotation(Quaternion quaternion) {
        return fromTR(v3(0f, 0f, 0f), quaternion);
    }

    /// @return a new rotation matrix that aligns `v1` direction with `v2` direction.
    public static Matrix4F32 fromRotationBetween(Vector3F32 v1, Vector3F32 v2) {
        final var quat = Quaternion.fromRotationBetween(v1, v2);
        return fromRotation(quat);
    }

    /// @return a new rotation matrix from the given Euler angles.
    public static Matrix4F32 fromEulerAngles(Radians yaw, Radians pitch, Radians roll) {
        final var quat = Quaternion.fromEulerAngles(yaw, pitch, roll);
        return fromRotation(quat);
    }

    /// Creates a transformation matrix from translation, rotation, and scale.
    /// @return The transformation matrix.
    /// @apiNote The rotation quaternion is normalized internally.
    public static Matrix4F32 fromTRS(Vector3F32 translation, Quaternion rotation, Vector3F32 scale) {
        return fromTR(translation, rotation).scale(scale);
    }

    /// Creates a matrix from three axes and a translation vector.
    /// @return a matrix representing the given axes and translation.
    /// @apiNote
    /// |   |   |   |             |
    /// |:-:|:-:|:-:|:-----------:|
    /// | x | x | x | x-translation |
    /// | y | y | y | y-translation |
    /// | z | z | z | z-translation |
    /// | 0 | 0 | 0 |      1       |
    public static Matrix4F32 fromAxes(Vector3F32 xAxis, Vector3F32 yAxis, Vector3F32 zAxis, Vector3F32 translation) {
        return new Matrix4F32(
                xAxis.x(), xAxis.y(), xAxis.z(), translation.x(),
                yAxis.x(), yAxis.y(), yAxis.z(), translation.y(),
                zAxis.x(), zAxis.y(), zAxis.z(), translation.z(),
                0, 0, 0, 1
        );
    }

    /// Creates a projection matrix with a near and far plane, a field of view, and an aspect ratio.
    /// @param near The near plane.
    /// @param far The far plane.
    /// @param fovY The field of view of the height.
    /// @param aspectRatio The aspect ratio.
    /// @apiNote Only the vertical FOV is specified, the horizontal FOV is derived from the aspect ratio.
    public static Matrix4F32 fromProjection(float near, float far, Radians fovY, float aspectRatio) {
        final float focalLen = (float) (1f / Math.tan(fovY.value() / 2f));
        final float m00 = focalLen / aspectRatio;
        final float m22 = (far + near) / (near - far);
        final float m33 = (2f * far * near) / (near - far);
        return new Matrix4F32(
                m00, 0, 0, 0,
                0, focalLen, 0, 0,
                0, 0, m22, m33,
                0, 0, -1, 0
        );
    }

    /// Creates an off-center perspective projection matrix.\
    /// Useful for asymmetric frustums (off-center projections), e.g., stereo rendering or shadows.
    /// @param left The X coordinate on the near plane that maps to the left of the viewport.
    /// @param right The X coordinate on the near plane that maps to the right of the viewport.
    /// @param bottom The Y coordinate on the near plane that maps to the bottom of the viewport.
    /// @param top The Y coordinate on the near plane that maps to the top of the viewport.
    /// @param near The distance to the near clipping plane (must be positive).
    /// @param far The distance to the far clipping plane (must be positive and greater than near).
    /// @return the projection matrix that maps the specified frustum to normalized device coordinates.
    public static Matrix4F32 fromProjection(float left, float right, float bottom, float top, float near, float far) {
        float m00 = 2f * near / (right - left); // X offset.
        float m11 = 2f * near / (top - bottom); // Y offset.
        float m02 = (right + left) / (right - left);
        float m12 = (top + bottom) / (top - bottom);
        float m22 = (far + near) / (near - far);
        float m23 = (2f * far * near) / (near - far);
        return new Matrix4F32(
                m00, 0, m02, 0,
                0, m11, m12, 0,
                0, 0, m22, m23,
                0, 0, -1, 0
        );
    }

    /// Creates an orthographic projection matrix, equivalent to OpenGL's glOrtho.
    /// @param left   The left clipping plane (x-coordinate)
    /// @param right  The right clipping plane (x-coordinate)
    /// @param bottom The bottom clipping plane (y-coordinate)
    /// @param top    The top clipping plane (y-coordinate)
    /// @param near   The near clipping plane (z-coordinate, must be less than far)
    /// @param far    The far clipping plane (z-coordinate, must be greater than near)
    /// @return       the new matrix representing the orthographic projection.
    /// @see <a href="https://registry.khronos.org/OpenGL-Refpages/gl2.1/xhtml/glOrtho.xml">glOrtho documentation</a>
    public static Matrix4F32 fromOrtho(float left, float right, float bottom, float top, float near, float far) {

        final float xOrtho =  2f / (right - left);
        final float yOrtho =  2f / (top - bottom);
        final float zOrtho = -2f / (far - near);

        final float tx = -(right + left) / (right - left);
        final float ty = -(top + bottom) / (top - bottom);
        final float tz = -(far + near) / (far - near);

        return new Matrix4F32(
                xOrtho, 0, 0, tx,
                0, yOrtho, 0, ty,
                0, 0, zOrtho, tz,
                0, 0, 0, 1
        );
    }

    /// Creates an orthographic projection matrix whose lower‑left corner is {@code origin},
    /// extending {@code width} horizontally and {@code height} vertically.
    /// @param width   horizontal size (must be positive)
    /// @param height  vertical size (must be positive)
    /// @param near   The near clipping plane (z-coordinate, must be less than far)
    /// @param far    The far clipping plane (z-coordinate, must be greater than near)
    /// @return       the new matrix representing the 2D orthographic projection.
    public static Matrix4F32 fromOrtho2D(Vector2F32 origin, float width, float height, float near, float far) {
        return fromOrtho(origin.x(), origin.x() + width, origin.y(), origin.y() + height, near, far);
    }

    /// Creates an orthographic projection matrix whose lower‑left corner is {@code origin},
    /// extending {@code width} horizontally and {@code height} vertically.
    ///
    /// The near plane is set to 0, and the far plane is set to 1.
    /// @param width   horizontal size (must be positive)
    /// @param height  vertical size (must be positive)
    /// @return       the new matrix representing the 2D orthographic projection.
    public static Matrix4F32 fromOrtho2D(Vector2F32 origin, float width, float height) {
        return fromOrtho(origin.x(), origin.x() + width, origin.y(), origin.y() + height, 0, 1);
    }

    /// @return creates an identity matrix having the 4th column set to the translation vector.
    public static Matrix4F32 fromTranslation(Vector3F32 translation) {
        return new Matrix4F32(
                1, 0, 0, translation.x(),
                0, 1, 0, translation.y(),
                0, 0, 1, translation.z(),
                0, 0, 0, 1
        );
    }

    /// @return creates an identity matrix having the 4th column set to the translation vector and the scaling vector in the diagonal.
    public static Matrix4F32 fromTranslation(Vector3F32 translation, Vector3F32 scaling) {
        final Matrix4F32 m = fromTranslation(translation);
        final float m00 = scaling.x();
        final float m11 = scaling.y();
        final float m22 = scaling.z();
        return new Matrix4F32(
                m00  , m.m01, m.m02, m.m03,
                m.m10, m11  , m.m12, m.m13,
                m.m20, m.m21, m22  , m.m23,
                m.m30, m.m31, m.m32, m.m33
        );
    }

    /// @return a new pure scaling matrix.
    public static Matrix4F32 fromScale(Vector3F32 scale) {
        final Matrix4F32 i = identity();
        return new Matrix4F32(
                scale.x(), i.m01      , i.m02      , i.m03,
                i.m10      , scale.y(), i.m12      , i.m13,
                i.m20      , i.m21      , scale.z(), i.m23,
                i.m30      , i.m31      , i.m32      , i.m33
        );
    }

    /// Creates a view rotation matrix from a view direction and an up vector.
    /// This matrix contains rotation only; combine with a translation to form a full view matrix.
    public static Matrix4F32 fromLookRotation(Vector3F32 direction, Vector3F32 up) {

        final var f = direction.normalize();   // forward
        final var r = f.cross(up).normalize(); // right
        final var u = r.cross(f).normalize();  // true up

        final Matrix4F32 i = identity();
        return new Matrix4F32(
                 r.x(),  r.y(),  r.z(), i.m03,
                 u.x(),  u.y(),  u.z(), i.m13,
                -f.x(), -f.y(), -f.z(), i.m23,
                 i.m30,  i.m31,  i.m32, i.m33
        );
    }

    /// Creates a view (camera) matrix that looks from `position` towards `target`, using `up` as the up direction.
    ///
    /// The resulting matrix transforms world-space coordinates into view space.
    public static Matrix4F32 fromLookAt(Vector3F32 position, Vector3F32 target, Vector3F32 up) {
        final var direction = target.sub(position);
        final Matrix4F32 rotation = fromLookRotation(direction, up);
        final Matrix4F32 translation = fromTranslation(position.mul(-1f));
        return rotation.mul(translation);
    }

    public static Matrix4F32 fromWorld(Vector3F32 position, Vector3F32 forward, Vector3F32 up) {
        final var f = forward.normalize();     // forward
        final var r = f.cross(up).normalize(); // right
        final var u = r.cross(f).normalize();  // true Up
        return fromAxes(r, u, f.mul(-1f), position);
    }

    /** Sets this matrix to the given 3x3 matrix. The third column of this matrix is set to (0,0,1,0).
     * @param matrix the matrix */
    public static Matrix4F32 fromMatrix3(Matrix3F32 matrix) {
        return new Matrix4F32(
                matrix.m00(), matrix.m01(), matrix.m02(), 0,
                matrix.m10(), matrix.m11(), matrix.m12(), 0,
                matrix.m20(), matrix.m21(), matrix.m22(), 0,
                0, 0, 0, 1
        );
    }

    /// @return The array presentation of this matrix.
    /// @apiNote The returned array uses the [column-major](https://en.wikipedia.org/wiki/Row-_and_column-major_order) order.
    /// @implNote Arrays are identity objects, meaning they are heavy for the garbage collector.
    @Override
    public Float[] asArray() {
        return new Float[] {
                m00, m10, m20, m30, // Column 0
                m01, m11, m21, m31, // Column 1
                m02, m12, m22, m32, // Column 2
                m03, m13, m23, m33  // Column 3
        };
    }

    /// Copies this matrix onto the provided array.
    /// @param out The array to copy the values onto.
    /// @apiNote The matrix is copied using the [column-major](https://en.wikipedia.org/wiki/Row-_and_column-major_order) order.
    /// The array must be at least 16 in length, if longer, the first 16 elements are used.
    public void toArray(float[] out) {
        if (out.length < 16) throw new IllegalArgumentException("The matrix array provided is not a 4x4 matrix.");
        out[M00] = m00; out[M10] = m10; out[M20] = m20; out[M30] = m30;
        out[M01] = m01; out[M11] = m11; out[M21] = m21; out[M31] = m31;
        out[M02] = m02; out[M12] = m12; out[M22] = m22; out[M32] = m32;
        out[M03] = m03; out[M13] = m13; out[M23] = m23; out[M33] = m33;
    }

    /// Copies this matrix onto a new {@link MemorySegment} allocated from the provided {@link Arena}.
    /// @apiNote The matrix is copied using the [column-major](https://en.wikipedia.org/wiki/Row-_and_column-major_order) order.
    @Override
    public MemorySegment asMemorySegment(Arena arena) {

        final var layout = ValueLayout.JAVA_FLOAT;
        final var segment = arena.allocate(16 * layout.byteSize());

        segment.setAtIndex(layout, M00, m00);
        segment.setAtIndex(layout, M01, m01);
        segment.setAtIndex(layout, M02, m02);
        segment.setAtIndex(layout, M03, m03);

        segment.setAtIndex(layout, M10, m10);
        segment.setAtIndex(layout, M11, m11);
        segment.setAtIndex(layout, M12, m12);
        segment.setAtIndex(layout, M13, m13);

        segment.setAtIndex(layout, M20, m20);
        segment.setAtIndex(layout, M21, m21);
        segment.setAtIndex(layout, M22, m22);
        segment.setAtIndex(layout, M23, m23);

        segment.setAtIndex(layout, M30, m30);
        segment.setAtIndex(layout, M31, m31);
        segment.setAtIndex(layout, M32, m32);
        segment.setAtIndex(layout, M33, m33);

        return segment;
    }

    @Override
    public Matrix4F32 add(Matrix4F32 other) {
        return new Matrix4F32(
                m00 + other.m00, m01 + other.m01, m02 + other.m02, m03 + other.m03,
                m10 + other.m10, m11 + other.m11, m12 + other.m12, m13 + other.m13,
                m20 + other.m20, m21 + other.m21, m22 + other.m22, m23 + other.m23,
                m30 + other.m30, m31 + other.m31, m32 + other.m32, m33 + other.m33
        );
    }

    @Override
    public Matrix4F32 sub(Matrix4F32 other) {
        return new Matrix4F32(
                m00 - other.m00, m01 - other.m01, m02 - other.m02, m03 - other.m03,
                m10 - other.m10, m11 - other.m11, m12 - other.m12, m13 - other.m13,
                m20 - other.m20, m21 - other.m21, m22 - other.m22, m23 - other.m23,
                m30 - other.m30, m31 - other.m31, m32 - other.m32, m33 - other.m33
        );
    }

    /// Multiples `this` matrix with the `other` matrix.
    /// @return the multiplied matrix.
    /// @apiNote Order is important! `this * other != other * this`
    @Override
    public Matrix4F32 mul(Matrix4F32 other) {
        final float n00 = m00 * other.m00 + m01 * other.m10 + m02 * other.m20 + m03 * other.m30;
        final float n01 = m00 * other.m01 + m01 * other.m11 + m02 * other.m21 + m03 * other.m31;
        final float n02 = m00 * other.m02 + m01 * other.m12 + m02 * other.m22 + m03 * other.m32;
        final float n03 = m00 * other.m03 + m01 * other.m13 + m02 * other.m23 + m03 * other.m33;
        final float n10 = m10 * other.m00 + m11 * other.m10 + m12 * other.m20 + m13 * other.m30;
        final float n11 = m10 * other.m01 + m11 * other.m11 + m12 * other.m21 + m13 * other.m31;
        final float n12 = m10 * other.m02 + m11 * other.m12 + m12 * other.m22 + m13 * other.m32;
        final float n13 = m10 * other.m03 + m11 * other.m13 + m12 * other.m23 + m13 * other.m33;
        final float n20 = m20 * other.m00 + m21 * other.m10 + m22 * other.m20 + m23 * other.m30;
        final float n21 = m20 * other.m01 + m21 * other.m11 + m22 * other.m21 + m23 * other.m31;
        final float n22 = m20 * other.m02 + m21 * other.m12 + m22 * other.m22 + m23 * other.m32;
        final float n23 = m20 * other.m03 + m21 * other.m13 + m22 * other.m23 + m23 * other.m33;
        final float n30 = m30 * other.m00 + m31 * other.m10 + m32 * other.m20 + m33 * other.m30;
        final float n31 = m30 * other.m01 + m31 * other.m11 + m32 * other.m21 + m33 * other.m31;
        final float n32 = m30 * other.m02 + m31 * other.m12 + m32 * other.m22 + m33 * other.m32;
        final float n33 = m30 * other.m03 + m31 * other.m13 + m32 * other.m23 + m33 * other.m33;
        return new Matrix4F32(
                n00, n01, n02, n03,
                n10, n11, n12, n13,
                n20, n21, n22, n23,
                n30, n31, n32, n33
        );
    }

    /// Multiples the `other` matrix with `this` matrix.
    /// @return the multiplied matrix.
    /// @apiNote Order is important! `other * this != this * other`
    @Override
    public Matrix4F32 preMul(Matrix4F32 matrix) {
        return matrix.mul(this);
    }

    /// @return the transposed version of this matrix.
    @Override
    public Matrix4F32 transpose() {
        return new Matrix4F32(
                m00, m10, m20, m30,
                m01, m11, m21, m31,
                m02, m12, m22, m32,
                m03, m13, m23, m33
        );
    }

    /// @return The determinant of this matrix.
    @Override
    public Float determinant() {
        return m30 * m21 * m12 * m03 - m20 * m31 * m12 * m03
             - m30 * m11 * m22 * m03 + m10 * m31 * m22 * m03
             + m20 * m11 * m32 * m03 - m10 * m21 * m32 * m03
             - m30 * m21 * m02 * m13 + m20 * m31 * m02 * m13
             + m30 * m01 * m22 * m13 - m00 * m31 * m22 * m13
             - m20 * m01 * m32 * m13 + m00 * m21 * m32 * m13
             + m30 * m11 * m02 * m23 - m10 * m31 * m02 * m23
             - m30 * m01 * m12 * m23 + m00 * m31 * m12 * m23
             + m10 * m01 * m32 * m23 - m00 * m11 * m32 * m23
             - m20 * m11 * m02 * m33 + m10 * m21 * m02 * m33
             + m20 * m01 * m12 * m33 - m00 * m21 * m12 * m33
             - m10 * m01 * m22 * m33 + m00 * m11 * m22 * m33;
    }

    /// @return true if the matrix is a singular matrix.
    @Override
    public boolean isSingular() {
        return Math.abs(determinant()) < EPSILON;
    }

    /// Inverts the matrix.
    /// @throws ArithmeticException if the matrix cannot be inverted because it is singular.
    @Override
    public Matrix4F32 invert() {

        final float det = determinant();
        if (Math.abs(det) < EPSILON) throw new ArithmeticException("The matrix cannot be inverted since singular.");

        final float invDet = 1f / det;

        // I honestly can't imagine how people figure out these things... (Hardly I can, still, how much time did it take?)
        final float n00 = (m12 * m23 * m31 - m13 * m22 * m31 + m13 * m21 * m32 - m11 * m23 * m32 - m12 * m21 * m33 + m11 * m22 * m33) * invDet;
        final float n01 = (m03 * m22 * m31 - m02 * m23 * m31 - m03 * m21 * m32 + m01 * m23 * m32 + m02 * m21 * m33 - m01 * m22 * m33) * invDet;
        final float n02 = (m02 * m13 * m31 - m03 * m12 * m31 + m03 * m11 * m32 - m01 * m13 * m32 - m02 * m11 * m33 + m01 * m12 * m33) * invDet;
        final float n03 = (m03 * m12 * m21 - m02 * m13 * m21 - m03 * m11 * m22 + m01 * m13 * m22 + m02 * m11 * m23 - m01 * m12 * m23) * invDet;
        final float n10 = (m13 * m22 * m30 - m12 * m23 * m30 - m13 * m20 * m32 + m10 * m23 * m32 + m12 * m20 * m33 - m10 * m22 * m33) * invDet;
        final float n11 = (m02 * m23 * m30 - m03 * m22 * m30 + m03 * m20 * m32 - m00 * m23 * m32 - m02 * m20 * m33 + m00 * m22 * m33) * invDet;
        final float n12 = (m03 * m12 * m30 - m02 * m13 * m30 - m03 * m10 * m32 + m00 * m13 * m32 + m02 * m10 * m33 - m00 * m12 * m33) * invDet;
        final float n13 = (m02 * m13 * m20 - m03 * m12 * m20 + m03 * m10 * m22 - m00 * m13 * m22 - m02 * m10 * m23 + m00 * m12 * m23) * invDet;
        final float n20 = (m11 * m23 * m30 - m13 * m21 * m30 + m13 * m20 * m31 - m10 * m23 * m31 - m11 * m20 * m33 + m10 * m21 * m33) * invDet;
        final float n21 = (m03 * m21 * m30 - m01 * m23 * m30 - m03 * m20 * m31 + m00 * m23 * m31 + m01 * m20 * m33 - m00 * m21 * m33) * invDet;
        final float n22 = (m01 * m13 * m30 - m03 * m11 * m30 + m03 * m10 * m31 - m00 * m13 * m31 - m01 * m10 * m33 + m00 * m11 * m33) * invDet;
        final float n23 = (m03 * m11 * m20 - m01 * m13 * m20 - m03 * m10 * m21 + m00 * m13 * m21 + m01 * m10 * m23 - m00 * m11 * m23) * invDet;
        final float n30 = (m12 * m21 * m30 - m11 * m22 * m30 - m12 * m20 * m31 + m10 * m22 * m31 + m11 * m20 * m32 - m10 * m21 * m32) * invDet;
        final float n31 = (m01 * m22 * m30 - m02 * m21 * m30 + m02 * m20 * m31 - m00 * m22 * m31 - m01 * m20 * m32 + m00 * m21 * m32) * invDet;
        final float n32 = (m02 * m11 * m30 - m01 * m12 * m30 - m02 * m10 * m31 + m00 * m12 * m31 + m01 * m10 * m32 - m00 * m11 * m32) * invDet;
        final float n33 = (m01 * m12 * m20 - m02 * m11 * m20 + m02 * m10 * m21 - m00 * m12 * m21 - m01 * m10 * m22 + m00 * m11 * m22) * invDet;
        return new Matrix4F32(
                n00, n01, n02, n03,
                n10, n11, n12, n13,
                n20, n21, n22, n23,
                n30, n31, n32, n33
        );
    }

    /// Linearly interpolates between this matrix and the other matrix mixing by alpha.
    /// @param alpha the alpha value in the range `[0,1]`.
    @Override
    public Matrix4F32 lerp(Matrix4F32 other, Float alpha) {
        final float invAlpha = 1f - alpha;
        final float n00 = m00 * invAlpha + other.m00 * alpha;
        final float n01 = m01 * invAlpha + other.m01 * alpha;
        final float n02 = m02 * invAlpha + other.m02 * alpha;
        final float n03 = m03 * invAlpha + other.m03 * alpha;
        final float n10 = m10 * invAlpha + other.m10 * alpha;
        final float n11 = m11 * invAlpha + other.m11 * alpha;
        final float n12 = m12 * invAlpha + other.m12 * alpha;
        final float n13 = m13 * invAlpha + other.m13 * alpha;
        final float n20 = m20 * invAlpha + other.m20 * alpha;
        final float n21 = m21 * invAlpha + other.m21 * alpha;
        final float n22 = m22 * invAlpha + other.m22 * alpha;
        final float n23 = m23 * invAlpha + other.m23 * alpha;
        final float n30 = m30 * invAlpha + other.m30 * alpha;
        final float n31 = m31 * invAlpha + other.m31 * alpha;
        final float n32 = m32 * invAlpha + other.m32 * alpha;
        final float n33 = m33 * invAlpha + other.m33 * alpha;
        return new Matrix4F32(
                n00, n01, n02, n03,
                n10, n11, n12, n13,
                n20, n21, n22, n23,
                n30, n31, n32, n33
        );
    }

    /// Averages this matrix with another, using lerp for translation/scale and slerp for rotation.
    /// @param other The other matrix.
    /// @param weight Weight for this transform (other's weight is `1 - weight`)
    @Override
    public Matrix4F32 average(Matrix4F32 other, Float weight) {

        final float otherWeight = 1 - weight;
        final Vector3F32 scaling = scale().lerp(other.scale(), otherWeight);
        final Quaternion rotation = rotation().slerp(other.rotation(), otherWeight);
        final Vector3F32 translation = translation().lerp(other.translation(), otherWeight);

        return fromTRS(translation, rotation, scaling);
    }

    /// Averages an array of matrices using the same weight.
    /// @return A new matrix representing the average transform of the input matrices.
    @Override
    public Matrix4F32 average(Matrix4F32[] matrices) {

        final float weight = 1f / matrices.length;

        var scale = matrices[0].scale().mul(weight);
        var rot = matrices[0].rotation().pow(weight);
        var tran = matrices[0].translation().mul(weight);

        for (int i = 1; i < matrices.length; i++) {

            final var matrix = matrices[i];

            scale = scale.add(matrix.scale().mul(weight));
            rot = rot.mul(matrix.rotation().pow(weight));
            tran = tran.add(matrix.translation().mul(weight));
        }
        return fromTRS(tran, rot, scale); // The rotations gets normalized internally.
    }

    /// Averages an array of matrices using the provided weights.
    /// @return A new matrix representing the average transform of the input matrices.
    @Override
    public Matrix4F32 average(Matrix4F32[] matrices, Float[] weights) {

        if (matrices.length != weights.length) throw new IllegalArgumentException("The matrices and weights must have the same length.");

        var scale = matrices[0].scale().mul(weights[0]);
        var rot = matrices[0].rotation().pow(weights[0]);
        var tran = matrices[0].translation().mul(weights[0]);

        for (int i = 1; i < matrices.length; i++) {

            final var matrix = matrices[i];

            scale = scale.add(matrix.scale().mul(weights[i]));
            rot = rot.mul(matrix.rotation().pow(weights[i]));
            tran = tran.add(matrix.translation().mul(weights[i]));
        }
        return fromTRS(tran, rot, scale); // The rotations gets normalized internally.
    }

    @Override
    public Vector3F32 translation() {
        return v3(m03, m13, m23);
    }

    /// @return The rotation of this matrix.
    @Override
    public Quaternion rotation() {
        return Quaternion.fromMatrix4(this);
    }

    /// @return the vector which will receive the (non-negative) scale components on each axis.
    @Override
    public Vector3F32 scale() {
        final float x = (float) Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02);
        final float y = (float) Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12);
        final float z = (float) Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22);
        return new Vector3F32(x, y, z);
    }

    /// @return a matrix with the translational part removed (set to 0) and transposed.
    @Override
    public Matrix4F32 toNormalMatrix() {
        return new Matrix4F32(
                m00, m01, m02, 0f,
                m10, m11, m12, 0f,
                m20, m21, m22, 0f,
                m30, m31, m32, m33
        ).invert().transpose();
    }

    @Override
    public Matrix3F32 asMatrix3() {
        return Matrix3F32.fromMatrix4(this);
    }

    // TODO Consider SIMD versions of mulVec(), project(), and rotateVec()?

    /** Multiplies the vector with the given matrix.
     * @param vector the vector. */
    @Override
    public Vector3F32 transform(Vector3F32 vector) {
        return asMatrix3()
                .transform(vector)
                .add(v3(m03, m13, m23));
    }

    /** Postmultiplies this matrix by a translation matrix. Postmultiplication is also used by OpenGL ES' 1.x
     * glTranslate/glRotate/glScale.
     * @return This matrix for the purpose of chaining methods together. */
    @Override
    public Matrix4F32 translate(Vector3F32 translation) {
        return mul(fromTranslation(translation));
    }

    /** Postmultiplies this matrix with a (counter-clockwise) rotation matrix. Postmultiplication is also used by OpenGL ES' 1.x
     * glTranslate/glRotate/glScale.
     * @param axis The vector axis to rotate around.
     * @param angle The angle in radians.
     * @return This matrix for the purpose of chaining methods together. */
    @Override
    public Matrix4F32 rotateAround(Vector3F32 axis, Radians angle) {
        return mul(fromAxisAngle(axis, angle));
    }

    /** Postmultiplies this matrix with a (counter-clockwise) rotation matrix. Postmultiplication is also used by OpenGL ES' 1.x
     * glTranslate/glRotate/glScale.
     * @return This matrix for the purpose of chaining methods together. */
    @Override
    public Matrix4F32 rotate(Quaternion rotation) {
        return mul(fromRotation(rotation)); // TODO This can be expressed by multiplying a 4x4 matrix with a 3x3 one.
    }

    /** Postmultiplies this matrix by the rotation between two vectors.
     * @param v1 The base vector
     * @param v2 The target vector
     * @return This matrix for the purpose of chaining methods together */
    @Override
    public Matrix4F32 rotateBetween(Vector3F32 v1, Vector3F32 v2) {
        return mul(fromRotationBetween(v1, v2));
    }

    /** Post-multiplies this matrix by a rotation toward a direction.
     * @param direction direction to rotate toward
     * @param up up vector
     * @return This matrix for chaining */
    @Override
    public Matrix4F32 rotateToDirection(Vector3F32 direction, Vector3F32 up) {
        return mul(fromLookRotation(direction, up));
    }

    /** Postmultiplies this matrix with a scale matrix. Postmultiplication is also used by OpenGL ES' 1.x
     * glTranslate/glRotate/glScale.
     * @return This matrix for the purpose of chaining methods together. */
    @Override
    public Matrix4F32 scale(Vector3F32 scale) {
        return mul(fromScale(scale));
    }

    @Override
    public Vector3F32 project(Vector3F32 vector) {
        final float invW = 1f / (vector.x() * m30() + vector.y() * m31() + vector.z() * m32() + m33());
        return transform(vector).mul(invW);
    }

    @Override
    public Vector3F32 rotate(Vector3F32 vector) {
        return asMatrix3().transform(vector);
    }

    @Override
    public Vector3F32 unrotate(Vector3F32 vector) {
        return asMatrix3().unrotate(vector);
    }

    @Override
    public Vector3F32 untransform(Vector3F32 vector) {
        return asMatrix3().unrotate(vector.sub(v3(m03, m13, m23)));
    }

    /** Copies the 4x3 upper-left sub-matrix into float array. The destination array is supposed to be a column major matrix.
     * @param out the destination matrix */
    @Override
    public void toMatrix4x3Array(Float[] out) {
        if (out.length < 12) throw new IllegalArgumentException("The matrix array provided is not a 4x3 matrix.");
        out[0] = m00; out[3] = m01; out[6] = m02; out[9] = m03;
        out[1] = m10; out[4] = m11; out[7] = m12; out[10] = m13;
        out[2] = m20; out[5] = m21; out[8] = m22; out[11] = m23;
    }
}
