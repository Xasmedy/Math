package io.github.xasmedy.math.complex;

import io.github.xasmedy.math.unit.Radians;
import io.github.xasmedy.math.vector.v3.Vector3F32;
import io.github.xasmedy.math.vector.v4.Vector4F32;
import static io.github.xasmedy.math.vector.Vectors.v3;

public value record Quaternion(float x, float y, float z, float w) {

    private static final float TOLERANCE = 1e-6f;

    public Quaternion(Vector4F32 v4) {
        this(v4.x(), v4.y(), v4.z(), v4.w());
    }

    /// @return a new identity quaternion representing no rotation.
    public static Quaternion identity() {
        return new Quaternion(0, 0, 0, 1);
    }

    /// Creates a quaternion from an axis and a rotation angle in radians.
    /// @param axis the rotation axis.
    /// @param angle the rotation angle in radians.
    /// @return a new quaternion representing the rotation.
    /// @apiNote The axis is normalized automatically.
    public static Quaternion fromAxisAngle(Vector3F32 axis, Radians angle) {
        final var nor = axis.normalize();
        final double half = angle.value() * 0.5;
        final double sin = Math.sin(half);
        final double cos = Math.cos(half);
        return new Quaternion(
                (float) (nor.x() * sin),
                (float) (nor.y() * sin),
                (float) (nor.z() * sin),
                (float) cos
        );
    }

    /// Creates a new quaternion from the given Euler angles in radians.
    ///
    /// @param yaw   rotation around the y-axis in radians.
    /// @param pitch rotation around the x-axis in radians.
    /// @param roll  rotation around the z-axis in radians.
    /// @return a new quaternion representing the rotation.
    /// @apiNote Euler angles are applied in y (yaw), x (pitch), z (roll) order.
    public static Quaternion fromEulerAngles(Radians yaw, Radians pitch, Radians roll) {

        final double hr = roll.value() * 0.5f;
        final float shr = (float) Math.sin(hr);
        final float chr = (float) Math.cos(hr);

        final double hp = pitch.value() * 0.5f;
        final float shp = (float) Math.sin(hp);
        final float chp = (float) Math.cos(hp);

        final double hy = yaw.value() * 0.5f;
        final float shy = (float) Math.sin(hy);
        final float chy = (float) Math.cos(hy);

        final float chyShp = chy * shp;
        final float shyChp = shy * chp;
        final float chyChp = chy * chp;
        final float shyShp = shy * shp;

        final float newX = (chyShp * chr) + (shyChp * shr); // cos(yaw/2) * sin(pitch/2) * cos(roll/2) + sin(yaw/2) * cos(pitch/2) * sin(roll/2)
        final float newY = (shyChp * chr) - (chyShp * shr); // sin(yaw/2) * cos(pitch/2) * cos(roll/2) - cos(yaw/2) * sin(pitch/2) * sin(roll/2)
        final float newZ = (chyChp * shr) - (shyShp * chr); // cos(yaw/2) * cos(pitch/2) * sin(roll/2) - sin(yaw/2) * sin(pitch/2) * cos(roll/2)
        final float newW = (chyChp * chr) + (shyShp * shr); // cos(yaw/2) * cos(pitch/2) * cos(roll/2) + sin(yaw/2) * sin(pitch/2) * sin(roll/2)
        return new Quaternion(newX, newY, newZ, newW);
    }

    /// Creates a quaternion representing the rotation from one vector to another.
    ///
    /// @param v1 the starting normalized vector.
    /// @param v2 the target normalized vector.
    /// @return a new quaternion that rotates v1 onto v2.
    /// @apiNote If the vectors are already parallel, the identity quaternion is returned.
    ///          If the vectors are antiparallel, the rotation is 180° around any axis
    ///          perpendicular to the vectors.
    public static Quaternion fromRotationBetween(Vector3F32 v1, Vector3F32 v2) {

        final float dot = Math.clamp(v1.dot(v2), -1f, 1f);
        final var cross = v1.cross(v2);

        // I check if the vectors are not parallel.
        if (cross.length2() >= TOLERANCE) {
            final Radians angle = Radians.radians(Math.acos(dot));
            return fromAxisAngle(cross, angle);
        }

        // When the vectors are parallel.
        if (dot > 0f) return identity();
        // In case the vectors are antiparallel, I need to rotate 180 degrees.
        final Vector3F32 perpendicular = Math.abs(v1.x()) < 0.9f ?
                new Vector3F32(1f,0f,0f) :
                new Vector3F32(0f,1f,0f);
        return fromAxisAngle(perpendicular.cross(v1), Radians.radians(Math.PI));
    }

    /// Spherical linearly interpolates multiple quaternions by the given weights.
    /// @param quat the quaternions to interpolate.
    /// @param weights the weight used for each quaternion, the length must be the same as the quaternions array.
    /// @return the interpolated weighted quaternion.
    /// @apiNote
    /// - Both arrays are only read and not modified.
    /// - Multiplication is performed left-to-right.
    public static Quaternion weightedSlerp(Quaternion[] quat, float[] weights) {

        if (quat.length == 0) throw new IllegalArgumentException("Provided empty quaternion array.");
        if (quat.length != weights.length) throw new IllegalArgumentException("The quaternion and weights arrays do not have the same length.");

        Quaternion result = quat[0].pow(weights[0]);

        for (int i = 1; i < quat.length; i++) {
            final Quaternion exp = quat[i].pow(weights[i]);
            result = result.mul(exp);
        }
        return result.normalize();
    }

    /// Spherical linearly interpolates multiple quaternions.
    /// @param quat the quaternions to interpolate.
    /// @return the interpolated weighted quaternion.
    /// @apiNote
    /// - The array is only read and not modified.
    /// - All quaternions have the same weight `(1 / array length)`.
    /// - Multiplication is performed left-to-right.
    public static Quaternion weightedSlerp(Quaternion[] quat) {

        if (quat.length == 0) throw new IllegalArgumentException("Provided empty quaternion array.");

        final float weight = 1f / quat.length;
        Quaternion result = quat[0].pow(weight);

        for (int i = 1; i < quat.length; i++) {
            final Quaternion exp = quat[i].pow(weight);
            result = result.mul(exp);
        }
        return result.normalize();
    }

    public Vector4F32 v4() {
        return new Vector4F32(x(), y(), z(), w());
    }

    /**
     * @return the Euclidean length of this quaternion.
     */
    public float length() {
        return v4().length();
    }

    /**
     * Gets the pole of the gimbal lock, if any.
     *
     * @return {@link GimbalPole#NORTH}, {@link GimbalPole#SOUTH}, or {@link GimbalPole#NONE}
     */
    public GimbalPole gimbalPole() {
        final float t = y * x + z * w;
        if (t > 0.499f) return GimbalPole.NORTH;
        if (t < -0.499f) return GimbalPole.SOUTH;
        return GimbalPole.NONE;
    }

    /// Returns the roll (rotation around the z-axis) in radians.
    ///
    /// @return the roll in radians, between -π and +π.
    /// @apiNote The quaternion should be normalized for correct results.
    public Radians roll() {
        final GimbalPole pole = gimbalPole();
        final double radians = switch (pole) {
            case NORTH, SOUTH -> pole.sign * 2.0 * Math.atan2(y, w);
            case NONE -> Math.atan2(2.0 * (w * z + y * x), 1.0 - 2.0 * (x * x + z * z));
        };
        return Radians.radians(radians);
    }

    /// Returns the pitch (rotation around the x-axis) in radians.
    ///
    /// @return the pitch in radians, between -(π/2) and +(π/2).
    /// @apiNote The quaternion should be normalized for correct results.
    public Radians pitch() {
        final GimbalPole pole = gimbalPole();
        final double radians = switch (pole) {
            case NORTH, SOUTH -> pole.sign * Math.PI * 0.5;
            case NONE -> Math.asin(Math.clamp(2.0 * (w * x - z * y), -1.0, 1.0));
        };
        return Radians.radians(radians);
    }

    /// Returns the yaw (rotation around the y-axis) in radians.
    ///
    /// @return the yaw in radians, between -π and +π.
    /// @apiNote The quaternion should be normalized for correct results.
    ///  When the quaternion is in a gimbal lock configuration, the yaw is set to zero by convention.
    public Radians yaw() {
        final double radians = switch (gimbalPole()) {
            case NORTH, SOUTH -> 0.0;
            case NONE -> Math.atan2(2.0 * (y * w + x * z), 1.0 - 2.0 * (y * y + x * x));
        };
        return Radians.radians(radians);
    }

    /**
     * @return the length of this quaternion without square root
     */
    public float length2() {
        return v4().length2();
    }

    /**
     * Normalizes this quaternion to unit length
     *
     * @return the quaternion for chaining
     */
    public Quaternion normalize() {
        return new Quaternion(v4().normalize());
    }

    /// @return The conjugated quaternion
    public Quaternion conjugate() {
        return new Quaternion(-x(), -y(), -z(), w());
    }

    /// Rotates the given vector using this quaternion.
    ///
    /// @param v3 the vector to rotate
    /// @return a new rotated vector.
    /// @apiNote The quaternion should be normalized for correct results.
    public Vector3F32 rotate(Vector3F32 v3) {
        final var other = new Quaternion(v3.withW(0f));
        return conjugate()
                .preMul(other)
                .preMul(this).v4()
                .withoutW();
    }

    /// Returns the Hamilton product of `this` quaternion and `other`.
    ///
    /// @param other the quaternion to multiply.
    /// @return a new quaternion equal to `this * other`
    /// @apiNote Order is important! `this * other != other * this`
    public Quaternion mul(Quaternion other) {
        final float newX = w() * other.x() + x() * other.w() + y() * other.z() - z() * other.y();
        final float newY = w() * other.y() + y() * other.w() + z() * other.x() - x() * other.z();
        final float newZ = w() * other.z() + z() * other.w() + x() * other.y() - y() * other.x();
        final float newW = w() * other.w() - x() * other.x() - y() * other.y() - z() * other.z();
        return new Quaternion(newX, newY, newZ, newW);
    }

    /// Returns the Hamilton product of `other` and `this` quaternion.
    ///
    /// @param other the quaternion to multiply.
    /// @return a new quaternion equal to `other * this`
    /// @apiNote Order is important! `other * this != this * other`
    public Quaternion preMul(Quaternion other) {
        return other.mul(this);
    }

    /**
     * Add the x,y,z,w components of the passed in quaternion to the ones of this quaternion
     */
    public Quaternion add(Quaternion other) {
        return new Quaternion(v4().add(other.v4()));
    }

    // TODO : the matrix4 set(quaternion) doesnt set the last row+col of the matrix to 0,0,0,1 so... that's why there is this
// method

    // TODO Xas Note: TO IMPLEMENT
    /**
     * Fills a 4x4 matrix with the rotation matrix represented by this quaternion.
     *
     * @param matrix Matrix to fill
     */
//    public void toMatrix(final float[] matrix) {
//        final float xx = x * x;
//        final float xy = x * y;
//        final float xz = x * z;
//        final float xw = x * w;
//        final float yy = y * y;
//        final float yz = y * z;
//        final float yw = y * w;
//        final float zz = z * z;
//        final float zw = z * w;
//        // Set matrix from quaternion
//        matrix[Matrix4.M00] = 1 - 2 * (yy + zz);
//        matrix[Matrix4.M01] = 2 * (xy - zw);
//        matrix[Matrix4.M02] = 2 * (xz + yw);
//        matrix[Matrix4.M03] = 0;
//        matrix[Matrix4.M10] = 2 * (xy + zw);
//        matrix[Matrix4.M11] = 1 - 2 * (xx + zz);
//        matrix[Matrix4.M12] = 2 * (yz - xw);
//        matrix[Matrix4.M13] = 0;
//        matrix[Matrix4.M20] = 2 * (xz - yw);
//        matrix[Matrix4.M21] = 2 * (yz + xw);
//        matrix[Matrix4.M22] = 1 - 2 * (xx + yy);
//        matrix[Matrix4.M23] = 0;
//        matrix[Matrix4.M30] = 0;
//        matrix[Matrix4.M31] = 0;
//        matrix[Matrix4.M32] = 0;
//        matrix[Matrix4.M33] = 1;
//    }

    /**
     * @return If this quaternion is an identity Quaternion
     */
    public boolean isIdentity() {
        return isIdentity(TOLERANCE);
    }

    /**
     * @param tolerance allowed deviation from exact identity
     * @return true if this quaternion is approximately identity
     */
    public boolean isIdentity(float tolerance) {
        return Math.abs(x) < tolerance
                && Math.abs(y) < tolerance
                && Math.abs(z) < tolerance
                && Math.abs(w - 1f) < tolerance;
    }

    // TODO Xas Note: TO IMPLEMENT
    /**
     * Sets the Quaternion from the given matrix, optionally removing any scaling.
     */
//    public Quaternion setFromMatrix(boolean normalizeAxes, Matrix4 matrix) {
//        return setFromAxes(normalizeAxes, matrix.val[Matrix4.M00], matrix.val[Matrix4.M01], matrix.val[Matrix4.M02],
//                matrix.val[Matrix4.M10], matrix.val[Matrix4.M11], matrix.val[Matrix4.M12], matrix.val[Matrix4.M20],
//                matrix.val[Matrix4.M21], matrix.val[Matrix4.M22]);
//    }

    // TODO Xas Note: TO IMPLEMENT
    /**
     * Sets the Quaternion from the given rotation matrix, which must not contain scaling.
     */
//    public Quaternion setFromMatrix(Matrix4 matrix) {
//        return setFromMatrix(false, matrix);
//    }

    // TODO Xas Note: TO IMPLEMENT
    /**
     * Sets the Quaternion from the given matrix, optionally removing any scaling.
     */
//    public Quaternion setFromMatrix(boolean normalizeAxes, Matrix3 matrix) {
//        return setFromAxes(normalizeAxes, matrix.val[Matrix3.M00], matrix.val[Matrix3.M01], matrix.val[Matrix3.M02],
//                matrix.val[Matrix3.M10], matrix.val[Matrix3.M11], matrix.val[Matrix3.M12], matrix.val[Matrix3.M20],
//                matrix.val[Matrix3.M21], matrix.val[Matrix3.M22]);
//    }

    // TODO Xas Note: TO IMPLEMENT
    /**
     * Sets the Quaternion from the given rotation matrix, which must not contain scaling.
     */
//    public Quaternion setFromMatrix(Matrix3 matrix) {
//        return setFromMatrix(false, matrix);
//    }

    // TODO Xas Note: TO IMPLEMENT
    /**
     * <p>
     * Sets the Quaternion from the given x-, y- and z-axis which have to be orthonormal.
     * </p>
     *
     * <p>
     * Taken from Bones framework for JPCT, see http://www.aptalkarga.com/bones/ which in turn took it from Graphics Gem code at
     * ftp://ftp.cis.upenn.edu/pub/graphics/shoemake/quatut.ps.Z.
     * </p>
     *
     * @param xx x-axis x-coordinate
     * @param xy x-axis y-coordinate
     * @param xz x-axis z-coordinate
     * @param yx y-axis x-coordinate
     * @param yy y-axis y-coordinate
     * @param yz y-axis z-coordinate
     * @param zx z-axis x-coordinate
     * @param zy z-axis y-coordinate
     * @param zz z-axis z-coordinate
     */
//    public Quaternion setFromAxes(float xx, float xy, float xz, float yx, float yy, float yz, float zx, float zy, float zz) {
//        return setFromAxes(false, xx, xy, xz, yx, yy, yz, zx, zy, zz);
//    }

    // TODO Xas Note: TO IMPLEMENT
    /**
     * <p>
     * Sets the Quaternion from the given x-, y- and z-axis.
     * </p>
     *
     * <p>
     * Taken from Bones framework for JPCT, see http://www.aptalkarga.com/bones/ which in turn took it from Graphics Gem code at
     * ftp://ftp.cis.upenn.edu/pub/graphics/shoemake/quatut.ps.Z.
     * </p>
     *
     * @param normalizeAxes whether to normalize the axes (necessary when they contain scaling)
     * @param xx            x-axis x-coordinate
     * @param xy            x-axis y-coordinate
     * @param xz            x-axis z-coordinate
     * @param yx            y-axis x-coordinate
     * @param yy            y-axis y-coordinate
     * @param yz            y-axis z-coordinate
     * @param zx            z-axis x-coordinate
     * @param zy            z-axis y-coordinate
     * @param zz            z-axis z-coordinate
     */
//    public Quaternion setFromAxes(boolean normalizeAxes, float xx, float xy, float xz, float yx, float yy, float yz, float zx,
//                                  float zy, float zz) {
//        if (normalizeAxes) {
//            final float lx = 1f / Vector3.len(xx, xy, xz);
//            final float ly = 1f / Vector3.len(yx, yy, yz);
//            final float lz = 1f / Vector3.len(zx, zy, zz);
//            xx *= lx;
//            xy *= lx;
//            xz *= lx;
//            yx *= ly;
//            yy *= ly;
//            yz *= ly;
//            zx *= lz;
//            zy *= lz;
//            zz *= lz;
//        }
//        // the trace is the sum of the diagonal elements; see
//        // http://mathworld.wolfram.com/MatrixTrace.html
//        final float t = xx + yy + zz;
//
//        // we protect the division by s by ensuring that s>=1
//        if (t >= 0) { // |w| >= .5
//            float s = (float) Math.sqrt(t + 1); // |s|>=1 ...
//            w = 0.5f * s;
//            s = 0.5f / s; // so this division isn't bad
//            x = (zy - yz) * s;
//            y = (xz - zx) * s;
//            z = (yx - xy) * s;
//        } else if ((xx > yy) && (xx > zz)) {
//            float s = (float) Math.sqrt(1.0 + xx - yy - zz); // |s|>=1
//            x = s * 0.5f; // |x| >= .5
//            s = 0.5f / s;
//            y = (yx + xy) * s;
//            z = (xz + zx) * s;
//            w = (zy - yz) * s;
//        } else if (yy > zz) {
//            float s = (float) Math.sqrt(1.0 + yy - xx - zz); // |s|>=1
//            y = s * 0.5f; // |y| >= .5
//            s = 0.5f / s;
//            x = (yx + xy) * s;
//            z = (zy + yz) * s;
//            w = (xz - zx) * s;
//        } else {
//            float s = (float) Math.sqrt(1.0 + zz - xx - yy); // |s|>=1
//            z = s * 0.5f; // |z| >= .5
//            s = 0.5f / s;
//            x = (xz + zx) * s;
//            y = (zy + yz) * s;
//            w = (yx - xy) * s;
//        }
//
//        return this;
//    }

    /// Spherical interpolation between this quaternion and the other quaternion.
    /// @param end the other quaternion.
    /// @param alpha value in the range of `[0,1]`.
    /// @param epsilon threshold to switch between lerp and full slerp at small angles.
    /// @return the interpolated quaternion.
    public Quaternion slerp(Quaternion end, float alpha, float epsilon) {

        final float dot = dot(end);
        final float absDot = Math.abs(dot);

        // Set the default values in case the angle is too small to calculate the slerp.
        float scale0 = 1f - alpha;
        float scale1 = alpha;

        // To avoid numerical instability at low angles, I skip the lerp if the angle is small enough.
        if ((1 - absDot) > epsilon) {

            final double angle = Math.acos(absDot);
            final double invSinTheta = 1f / Math.sin(angle);

            scale0 = (float) (Math.sin((1f - alpha) * angle) * invSinTheta);
            scale1 = (float) (Math.sin((alpha * angle)) * invSinTheta);
        }

        if (dot < 0f) scale1 = -scale1;
        return new Quaternion(
                (scale0 * x) + (scale1 * end.x),
                (scale0 * y) + (scale1 * end.y),
                (scale0 * z) + (scale1 * end.z),
                (scale0 * w) + (scale1 * end.w)
        );
    }

    /// Spherical interpolation between this quaternion and the other quaternion.
    /// @param end the other quaternion
    /// @param alpha value in the range of [0,1]
    /// @return the interpolated quaternion.
    public Quaternion slerp(Quaternion end, float alpha) {
        return slerp(end, alpha, 1e-4f);
    }

    /// Returns the power of `quaternion^alpha`.
    /// @param alpha The exponent.
    public Quaternion pow(float alpha) {

        final float norm = length();
        final double normExp = Math.pow(norm, alpha); // |q|^alpha
        final double theta = Math.acos(w / norm);
        final double sinTheta = Math.sin(theta);

        // To avoid numerical instability at low angles, I approximate the coefficient.
        final double coefficient = Math.abs(theta) < TOLERANCE ?
                normExp * alpha / norm :
                normExp * Math.sin(alpha * theta) / (norm * sinTheta);

        final var result = new Quaternion(
                (float) (x * coefficient),
                (float) (y * coefficient),
                (float) (z * coefficient),
                (float) (normExp * Math.cos(alpha * theta))
        );
        return result.normalize(); // Fixes any possible discrepancies.
    }

    /**
     * Get the dot product between this and the other quaternion (commutative).
     *
     * @param other the other quaternion.
     * @return the dot product of this and the other quaternion.
     */
    public float dot(Quaternion other) {
        return v4().dot(other.v4());
    }

    /**
     * Multiplies the components of this quaternion with the given scalar.
     *
     * @param scalar the scalar.
     * @return this quaternion for chaining.
     */
    public Quaternion mul(float scalar) {
        return new Quaternion(v4().mul(scalar));
    }

    /// Returns the axis-angle representation of this quaternion's rotation.
    /// @return {@link AxisAngleF32} containing both the axis (as a unit vector) and the angle.
    /// @apiNote The quaternion is normalized internally.
    public AxisAngleF32 axisAngle() {

        final Quaternion quat = normalize();
        final float sqrt = (float) Math.sqrt(1d - quat.w * quat.w);

        // I avoid dividing by 0 if the sqrt is small enough.
        final Vector3F32 newAxis = sqrt < TOLERANCE ?
                v3(quat.x, quat.y, quat.z).normalize() : // I re-normalize because without w the previous normalization might be off.
                v3(quat.x / sqrt, quat.y / sqrt, quat.z / sqrt);

        final Radians angle = angle();
        return new AxisAngleF32(newAxis, angle);
    }

    /// @return the rotation angle of this quaternion in radians.
    /// @apiNote The quaternion is normalized internally.
    public Radians angle() {
        final float wNorm = normalize().w();
        final float angle = (float) (2d * Math.acos(Math.clamp(wNorm, -1f, 1f)));
        return Radians.radians(angle);
    }

    /// Gets the swing rotation and twist rotation for the specified axis.
    /// - The twist rotation represents the rotation around the specified axis.
    /// - The swing rotation represents the rotation of the specified axis itself, which is the rotation around an axis perpendicular to the specified axis.
    ///
    ///  The swing and twist rotation can be used to reconstruct the original quaternion; `this = swing * twist`.
    ///
    /// @param axis of which to get the swing and twist rotation.
    /// @return the `swing` and `twist` pair.
    /// @apiNote The axis is normalized internally.
    public SwingTwist swingTwist(Vector3F32 axis) {

        final var norm = axis.normalize();
        final float dot = v4().withoutW().dot(norm);

        var twist = new Quaternion(norm.x() * dot, norm.y() * dot, norm.z() * dot, w).normalize();
        if (dot < 0) twist = twist.mul(-1f);

        final var swing = twist.conjugate().preMul(normalize());
        return new SwingTwist(swing, twist);
    }

    /**
     * Get the angle in radians of the rotation around the specified axis. The axis must be normalized.
     *
     * @param axis the normalized axis for which to get the angle
     * @return the angle in radians of the rotation around the specified axis
     */
    public Radians angleAround(Vector3F32 axis) {

        final float dot = v4().withoutW().dot(axis);
        final var qAxis = new Quaternion(axis.x() * dot, axis.y() * dot, axis.z() * dot, w);
        final float l2 = qAxis.length2();

        if (Math.abs(l2) < TOLERANCE) return Radians.radians(0.0);

        final float fixedW = dot < 0 ? -w : w;
        final double clamped = Math.clamp(fixedW / Math.sqrt(l2), -1.0, 1.0);
        final double radians = 2.0 * Math.acos(clamped);

        return Radians.radians(radians);
    }

    public enum GimbalPole {

        NORTH(1),
        SOUTH(-1),
        NONE(0);

        public final int sign;

        GimbalPole(int sign) {
            this.sign = sign;
        }
    }

    public value record AxisAngleF32(Vector3F32 axis, Radians angle) {}

    public value record SwingTwist(Quaternion swing, Quaternion twist) {}
}
