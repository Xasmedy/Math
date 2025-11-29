package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.arithmetic.*;
import io.github.xasmedy.math.point.Point4;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;
import static io.github.xasmedy.math.vector.Vectors.*;
import static io.github.xasmedy.math.arithmetic.Arithmetics.*;

public interface Vector4<T extends Vector4<T, N>, N extends Number> extends Vector<T, N>, Point4<N> {

    T create(N x, N y, N z, N w);

    Vector3<?, N> withoutY();

    @Override
    default int dimension() {
        return 4;
    }

    @Override
    default N component(int index) throws IndexOutOfBoundsException {
        return switch (index) {
            case 0 -> x();
            case 1 -> y();
            case 2 -> z();
            case 3 -> w();
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    default N sum() {
        final var ath = arithmetic();
        final N p1 = ath.add(x(), y());
        final N p2 = ath.add(z(), w());
        return ath.add(p1, p2);
    }

    @Override
    default T filled(N value) {
        return create(value, value, value, value);
    }

    @LooselyConsistentValue
    value record F32(@NullRestricted Float x,
                     @NullRestricted Float y,
                     @NullRestricted Float z,
                     @NullRestricted Float w) implements Vector4<Vector4.F32, Float>, RealVector<F32, Float> {

        @Override
        public Vector4.F32 create(Float x, Float y, Float z, Float w) {
            return v4(x, y, z, w);
        }

        @Override
        public Vector3.F32 withoutY() {
            return v3(x(), y(), z());
        }

        @Override
        public Vector4.I32 ceilAsInt() {
            return v4((int) Math.ceil(x()),
                      (int) Math.ceil(y()),
                      (int) Math.ceil(z()),
                      (int) Math.ceil(w()));
        }

        @Override
        public Vector4.I32 floorAsInt() {
            return v4((int) Math.floor(x()),
                      (int) Math.floor(y()),
                      (int) Math.floor(z()),
                      (int) Math.floor(w()));
        }

        @Override
        public ArithmeticF32 arithmetic() {
            return ATH_F32;
        }

        @Override
        public Vector4.F32 value() {
            return this;
        }

        // ==== Manual Monomorphization for performance ====
        // The method signature without specialization would collide, not allowing the JIT to inline.
        // Now thanks to specializing `other`, the signature becomes unique and the JIT can inline.

        @Override
        public Vector4.F32 operation(Vector4.F32 other, Operation<Float> operation) {
            final Float newX = operation.calculate(arithmetic(), x(), other.x());
            final Float newY = operation.calculate(arithmetic(), y(), other.y());
            final Float newZ = operation.calculate(arithmetic(), z(), other.z());
            final Float newW = operation.calculate(arithmetic(), w(), other.w());
            return v4(newX, newY, newZ, newW);
        }

        @Override
        public boolean condition(Vector4.F32 other, Predicate<Float> predicate) {
            return predicate.test(arithmetic(), x(), other.x()) &&
                    predicate.test(arithmetic(), y(), other.y()) &&
                    predicate.test(arithmetic(), z(), other.z()) &&
                    predicate.test(arithmetic(), w(), other.w());
        }
    }

    @LooselyConsistentValue
    value record F64(@NullRestricted Double x,
                     @NullRestricted Double y,
                     @NullRestricted Double z,
                     @NullRestricted Double w) implements Vector4<Vector4.F64, Double>, RealVector<F64, Double> {

        @Override
        public Vector4.F64 create(Double x, Double y, Double z, Double w) {
            return v4(x, y, z, w);
        }

        @Override
        public Vector3.F64 withoutY() {
            return v3(x(), y(), z());
        }

        @Override
        public Vector4.I64 ceilAsInt() {
            return v4((long) Math.ceil(x()),
                      (long) Math.ceil(y()),
                      (long) Math.ceil(z()),
                      (long) Math.ceil(w()));
        }

        @Override
        public Vector4.I64 floorAsInt() {
            return v4((long) Math.floor(x()),
                      (long) Math.floor(y()),
                      (long) Math.floor(z()),
                      (long) Math.floor(w()));
        }

        @Override
        public ArithmeticF64 arithmetic() {
            return ATH_F64;
        }

        @Override
        public Vector4.F64 value() {
            return this;
        }

        // ==== Manual Monomorphization for performance ====
        // The method signature without specialization would collide, not allowing the JIT to inline.
        // Now thanks to specializing `other`, the signature becomes unique and the JIT can inline.

        @Override
        public Vector4.F64 operation(Vector4.F64 other, Operation<Double> operation) {
            final Double newX = operation.calculate(arithmetic(), x(), other.x());
            final Double newY = operation.calculate(arithmetic(), y(), other.y());
            final Double newZ = operation.calculate(arithmetic(), z(), other.z());
            final Double newW = operation.calculate(arithmetic(), w(), other.w());
            return v4(newX, newY, newZ, newW);
        }

        @Override
        public boolean condition(Vector4.F64 other, Predicate<Double> predicate) {
            return predicate.test(arithmetic(), x(), other.x()) &&
                    predicate.test(arithmetic(), y(), other.y()) &&
                    predicate.test(arithmetic(), z(), other.z()) &&
                    predicate.test(arithmetic(), w(), other.w());
        }
    }

    @LooselyConsistentValue
    value record I32(@NullRestricted Integer x,
                     @NullRestricted Integer y,
                     @NullRestricted Integer z,
                     @NullRestricted Integer w) implements Vector4<Vector4.I32, Integer>, IntegerVector<Vector4.I32, Integer> {

        @Override
        public Vector4.I32 create(Integer x, Integer y, Integer z, Integer w) {
            return v4(x, y, z, w);
        }

        @Override
        public Vector3.I32 withoutY() {
            return v3(x(), y(), z());
        }

        @Override
        public Vector4.F32 asReal() {
            return v4((float) x(), (float) y(), (float) z(), (float) w());
        }

        @Override
        public ArithmeticI32 arithmetic() {
            return ATH_I32;
        }

        @Override
        public Vector4.I32 value() {
            return this;
        }

        // ==== Manual Monomorphization for performance ====
        // The method signature without specialization would collide, not allowing the JIT to inline.
        // Now thanks to specializing `other`, the signature becomes unique and the JIT can inline.

        @Override
        public Vector4.I32 operation(Vector4.I32 other, Operation<Integer> operation) {
            final Integer newX = operation.calculate(arithmetic(), x(), other.x());
            final Integer newY = operation.calculate(arithmetic(), y(), other.y());
            final Integer newZ = operation.calculate(arithmetic(), z(), other.z());
            final Integer newW = operation.calculate(arithmetic(), w(), other.w());
            return v4(newX, newY, newZ, newW);
        }

        @Override
        public boolean condition(Vector4.I32 other, Predicate<Integer> predicate) {
            return predicate.test(arithmetic(), x(), other.x()) &&
                    predicate.test(arithmetic(), y(), other.y()) &&
                    predicate.test(arithmetic(), z(), other.z()) &&
                    predicate.test(arithmetic(), w(), other.w());
        }
    }

    @LooselyConsistentValue
    value record I64(@NullRestricted Long x,
                     @NullRestricted Long y,
                     @NullRestricted Long z,
                     @NullRestricted Long w) implements Vector4<Vector4.I64, Long>, IntegerVector<Vector4.I64, Long> {

        @Override
        public Vector4.I64 create(Long x, Long y, Long z, Long w) {
            return v4(x, y, z, w);
        }

        @Override
        public Vector3.I64 withoutY() {
            return v3(x(), y(), z());
        }

        @Override
        public Vector4.F64 asReal() {
            return v4((double) x(),
                      (double) y(),
                      (double) z(),
                      (double) w());
        }

        @Override
        public ArithmeticI64 arithmetic() {
            return ATH_I64;
        }

        @Override
        public Vector4.I64 value() {
            return this;
        }


        // ==== Manual Monomorphization for performance ====
        // The method signature without specialization would collide, not allowing the JIT to inline.
        // Now thanks to specializing `other`, the signature becomes unique and the JIT can inline.

        @Override
        public Vector4.I64 operation(Vector4.I64 other, Operation<Long> operation) {
            final Long newX = operation.calculate(arithmetic(), x(), other.x());
            final Long newY = operation.calculate(arithmetic(), y(), other.y());
            final Long newZ = operation.calculate(arithmetic(), z(), other.z());
            final Long newW = operation.calculate(arithmetic(), w(), other.w());
            return v4(newX, newY, newZ, newW);
        }

        @Override
        public boolean condition(Vector4.I64 other, Predicate<Long> predicate) {
            return predicate.test(arithmetic(), x(), other.x()) &&
                    predicate.test(arithmetic(), y(), other.y()) &&
                    predicate.test(arithmetic(), z(), other.z()) &&
                    predicate.test(arithmetic(), w(), other.w());
        }
    }
}
