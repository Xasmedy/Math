package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.arithmetic.*;
import io.github.xasmedy.math.point.Point3;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;
import static io.github.xasmedy.math.vector.Vectors.*;
import static io.github.xasmedy.math.arithmetic.Arithmetics.*;

public interface Vector3<T extends Vector3<T, N>, N extends Number> extends Vector<T, N>, Point3<N> {

    // TODO I need to implement Quaternion for rotation..

    T create(N x, N y, N z);

    Vector4<?, N> withW(N w);

    Vector2<?, N> withoutZ();

    @Override
    default int dimension() {
        return 3;
    }

    @Override
    default N component(int index) throws IndexOutOfBoundsException {
        return switch (index) {
            case 0 -> x();
            case 1 -> y();
            case 2 -> z();
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    default N sum() {
        final var ath = arithmetic();
        final N p1 = ath.add(x(), y());
        return ath.add(p1, z());
    }

    @Override
    default T filled(N value) {
        return create(value, value, value);
    }

    default T cross(T vector) {
        final var ath = arithmetic();
        final N newX = ath.sub(ath.mul(y(), vector.z()), ath.mul(z(), vector.y()));
        final N newY = ath.sub(ath.mul(z(), vector.x()), ath.mul(x(), vector.z()));
        final N newZ = ath.sub(ath.mul(x(), vector.y()), ath.mul(y(), vector.x()));
        return create(newX, newY, newZ);
    }

    @LooselyConsistentValue
    value record F32(@NullRestricted Float x,
                     @NullRestricted Float y,
                     @NullRestricted Float z) implements Vector3<F32, Float>, RealVector<F32, Float> {

        @Override
        public Vector3.F32 create(Float x, Float y, Float z) {
            return v3(x, y, z);
        }

        @Override
        public Vector4.F32 withW(Float w) {
            return v4(x(), y(), z(), w);
        }

        @Override
        public Vector2.F32 withoutZ() {
            return v2(x(), y());
        }

        @Override
        public Vector3.I32 ceilAsInt() {
            return v3((int) Math.ceil(x()),
                      (int) Math.ceil(y()),
                      (int) Math.ceil(z()));
        }

        @Override
        public Vector3.I32 floorAsInt() {
            return v3((int) Math.floor(x()),
                      (int) Math.floor(y()),
                      (int) Math.floor(z()));
        }

        @Override
        public boolean isCollinear(Vector3.F32 vector, Float epsilon) {
            return cross(vector).length2() <= epsilon;
        }

        @Override
        public ArithmeticF32 arithmetic() {
            return ATH_F32;
        }

        @Override
        public Vector3.F32 value() {
            return this;
        }

        // ==== Manual Monomorphization for performance ====
        // The method signature without specialization would collide, not allowing the JIT to inline.
        // Now thanks to specializing `other`, the signature becomes unique and the JIT can inline.

        @Override
        public Vector3.F32 operation(Vector3.F32 other, Operation<Float> operation) {
            final Float newX = operation.calculate(arithmetic(), x(), other.x());
            final Float newY = operation.calculate(arithmetic(), y(), other.y());
            final Float newZ = operation.calculate(arithmetic(), z(), other.z());
            return v3(newX, newY, newZ);
        }

        @Override
        public boolean condition(Vector3.F32 other, Predicate<Float> predicate) {
            return predicate.test(arithmetic(), x(), other.x()) &&
                    predicate.test(arithmetic(), y(), other.y()) &&
                    predicate.test(arithmetic(), z(), other.z());
        }
    }

    @LooselyConsistentValue
    value record F64(@NullRestricted Double x,
                     @NullRestricted Double y,
                     @NullRestricted Double z) implements Vector3<F64, Double>, RealVector<F64, Double> {

        @Override
        public Vector3.F64 create(Double x, Double y, Double z) {
            return v3(x, y, z);
        }

        @Override
        public Vector4.F64 withW(Double w) {
            return v4(x(), y(), z(), w);
        }

        @Override
        public Vector2.F64 withoutZ() {
            return v2(x(), y());
        }

        @Override
        public Vector3.I64 ceilAsInt() {
            return v3((long)Math.ceil(x()),
                      (long)Math.ceil(y()),
                      (long)Math.ceil(z()));
        }

        @Override
        public Vector3.I64 floorAsInt() {
            return v3((long) Math.floor(x()),
                      (long) Math.floor(y()),
                      (long) Math.floor(z()));
        }

        @Override
        public boolean isCollinear(Vector3.F64 vector, Double epsilon) {
            return cross(vector).length2() <= epsilon;
        }

        @Override
        public ArithmeticF64 arithmetic() {
            return ATH_F64;
        }

        @Override
        public Vector3.F64 value() {
            return this;
        }

        // ==== Manual Monomorphization for performance ====
        // The method signature without specialization would collide, not allowing the JIT to inline.
        // Now thanks to specializing `other`, the signature becomes unique and the JIT can inline.

        @Override
        public Vector3.F64 operation(Vector3.F64 other, Operation<Double> operation) {
            final Double newX = operation.calculate(arithmetic(), x(), other.x());
            final Double newY = operation.calculate(arithmetic(), y(), other.y());
            final Double newZ = operation.calculate(arithmetic(), z(), other.z());
            return v3(newX, newY, newZ);
        }

        @Override
        public boolean condition(Vector3.F64 other, Predicate<Double> predicate) {
            return predicate.test(arithmetic(), x(), other.x()) &&
                    predicate.test(arithmetic(), y(), other.y()) &&
                    predicate.test(arithmetic(), z(), other.z());
        }
    }

    @LooselyConsistentValue
    value record I32(@NullRestricted Integer x,
                     @NullRestricted Integer y,
                     @NullRestricted Integer z) implements Vector3<I32, Integer>, IntegerVector<I32, Integer> {

        @Override
        public Vector3.I32 create(Integer x, Integer y, Integer z) {
            return v3(x, y, z);
        }

        @Override
        public Vector4.I32 withW(Integer w) {
            return v4(x(), y(), z(), w);
        }

        @Override
        public Vector2.I32 withoutZ() {
            return v2(x(), y());
        }

        @Override
        public Vector3.F32 asReal() {
            return v3((float) x(), (float) y(), (float) z());
        }

        @Override
        public ArithmeticI32 arithmetic() {
            return ATH_I32;
        }

        @Override
        public Vector3.I32 value() {
            return this;
        }

        // ==== Manual Monomorphization for performance ====
        // The method signature without specialization would collide, not allowing the JIT to inline.
        // Now thanks to specializing `other`, the signature becomes unique and the JIT can inline.

        @Override
        public Vector3.I32 operation(Vector3.I32 other, Operation<Integer> operation) {
            final Integer newX = operation.calculate(arithmetic(), x(), other.x());
            final Integer newY = operation.calculate(arithmetic(), y(), other.y());
            final Integer newZ = operation.calculate(arithmetic(), z(), other.z());
            return v3(newX, newY, newZ);
        }

        @Override
        public boolean condition(Vector3.I32 other, Predicate<Integer> predicate) {
            return predicate.test(arithmetic(), x(), other.x()) &&
                    predicate.test(arithmetic(), y(), other.y()) &&
                    predicate.test(arithmetic(), z(), other.z());
        }
    }

    @LooselyConsistentValue
    value record I64(@NullRestricted Long x,
                     @NullRestricted Long y,
                     @NullRestricted Long z) implements Vector3<I64, Long>, IntegerVector<I64, Long> {

        @Override
        public Vector3.I64 create(Long x, Long y, Long z) {
            return v3(x, y, z);
        }

        @Override
        public Vector4.I64 withW(Long w) {
            return v4(x(), y(), z(), w);
        }

        @Override
        public Vector2.I64 withoutZ() {
            return v2(x(), y());
        }

        @Override
        public Vector3.F64 asReal() {
            return v3((double) x(), (double) y(), (double) z());
        }

        @Override
        public ArithmeticI64 arithmetic() {
            return ATH_I64;
        }

        @Override
        public Vector3.I64 value() {
            return this;
        }

        // ==== Manual Monomorphization for performance ====
        // The method signature without specialization would collide, not allowing the JIT to inline.
        // Now thanks to specializing `other`, the signature becomes unique and the JIT can inline.

        @Override
        public Vector3.I64 operation(Vector3.I64 other, Operation<Long> operation) {
            final Long newX = operation.calculate(arithmetic(), x(), other.x());
            final Long newY = operation.calculate(arithmetic(), y(), other.y());
            final Long newZ = operation.calculate(arithmetic(), z(), other.z());
            return v3(newX, newY, newZ);
        }

        @Override
        public boolean condition(Vector3.I64 other, Predicate<Long> predicate) {
            return predicate.test(arithmetic(), x(), other.x()) &&
                    predicate.test(arithmetic(), y(), other.y()) &&
                    predicate.test(arithmetic(), z(), other.z());
        }
    }
}
