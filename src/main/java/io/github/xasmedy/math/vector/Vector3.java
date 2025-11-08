package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.arithmetic.*;
import io.github.xasmedy.math.point.Point3;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;

import static io.github.xasmedy.math.vector.Vectors.*;

@LooselyConsistentValue
public interface Vector3<T extends Vector3<T, N>, N extends Number> extends Vector<T, N>, Point3<N> {

    T new_(N x, N y, N z);

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
        return ath.add(x(), ath.add(y(), z()));
    }

    @Override
    default T filled(N value) {
        return new_(value, value, value);
    }

    @Override
    default T operation(T other, Operation<N> operation) {
        final N x = operation.calculate(arithmetic(), x(), other.x());
        final N y = operation.calculate(arithmetic(), y(), other.y());
        final N z = operation.calculate(arithmetic(), z(), other.z());
        return new_(x, y, z);
    }

    @Override
    default T operation(Transformation<N> operation) {
        final N x = operation.calculate(arithmetic(), x());
        final N y = operation.calculate(arithmetic(), y());
        final N z = operation.calculate(arithmetic(), z());
        return new_(x, y, z);
    }

    @Override
    default boolean condition(T other, Predicate<N> predicate) {
        return predicate.test(arithmetic(), x(), other.x()) &&
               predicate.test(arithmetic(), y(), other.y()) &&
               predicate.test(arithmetic(), z(), other.z());
    }

    default T cross(T vector) {
        final var ath = arithmetic();
        final N newX = ath.sub(ath.mul(y(), vector.z()), ath.mul(z(), vector.y()));
        final N newY = ath.sub(ath.mul(z(), vector.x()), ath.mul(x(), vector.z()));
        final N newZ = ath.sub(ath.mul(x(), vector.y()), ath.mul(y(), vector.x()));
        return new_(newX, newY, newZ);
    }

    Vector2<?, N> withoutZ();

    Vector4<?, N> withW(N w);

//    @Override
//    default boolean isCollinear(T vector, N epsilon) {
//        return v3(cross(vector)).length2() <= epsilon;
//    }

    // TODO I need to implement Quaternion for rotation..


    value record F32(@NullRestricted Float x,
                     @NullRestricted Float y,
                     @NullRestricted Float z) implements Vector3<F32, Float>, RealVector<F32, Float> {

        @Override
        public IntegerVector<?, ?> ceilAsInt() {
            return v3((int) Math.ceil(x()),
                    (int) Math.ceil(y()),
                    (int) Math.ceil(z()));
        }

        @Override
        public IntegerVector<?, ?> floorAsInt() {
            return v3((int) Math.floor(x()),
                    (int) Math.floor(y()),
                    (int) Math.floor(z()));
        }

        @Override
        public Vector3.F32 new_(Float x, Float y, Float z) {
            return v3(x, y, z);
        }

        @Override
        public Vector2.F32 withoutZ() {
            return v2(x(), y());
        }

        @Override
        public Vector4.F32 withW(Float w) {
            return v4(x, y, z, w);
        }

        @Override
        public ArithmeticF32 arithmetic() {
            return new ArithmeticF32();
        }

        @Override
        public Vector3.F32 value() {
            return this;
        }
    }

    value record F64(@NullRestricted Double x,
                     @NullRestricted Double y,
                     @NullRestricted Double z) implements Vector3<F64, Double>, RealVector<F64, Double> {

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
        public Vector3.F64 new_(Double x, Double y, Double z) {
            return v3(x, y, z);
        }

        @Override
        public Vector2.F64 withoutZ() {
            return v2(x, y);
        }

        @Override
        public Vector4.F64 withW(Double w) {
            return v4(x, y, z, w);
        }

        @Override
        public ArithmeticF64 arithmetic() {
            return new ArithmeticF64();
        }

        @Override
        public Vector3.F64 value() {
            return this;
        }
    }

    value record I32(@NullRestricted Integer x,
                     @NullRestricted Integer y,
                     @NullRestricted Integer z) implements Vector3<I32, Integer>, IntegerVector<I32, Integer> {

        @Override
        public Vector3.F32 asReal() {
            return v3((float) x(), (float) y(), (float) z());
        }

        @Override
        public Vector3.I32 new_(Integer x, Integer y, Integer z) {
            return v3(x, y, z);
        }

        @Override
        public Vector2.I32 withoutZ() {
            return v2(x(), y());
        }

        @Override
        public Vector4.I32 withW(Integer w) {
            return v4(x, y, z, w);
        }

        @Override
        public ArithmeticI32 arithmetic() {
            return new ArithmeticI32();
        }

        @Override
        public Vector3.I32 value() {
            return this;
        }
    }

    value record I64(@NullRestricted Long x,
                     @NullRestricted Long y,
                     @NullRestricted Long z) implements Vector3<I64, Long>, IntegerVector<I64, Long> {

        @Override
        public Vector3.F64 asReal() {
            return v3((double) x(), (double) y(), (double) z());
        }

        @Override
        public Vector3.I64 new_(Long x, Long y, Long z) {
            return v3(x, y, z);
        }

        @Override
        public Vector2.I64 withoutZ() {
            return v2(x(), y());
        }

        @Override
        public Vector4.I64 withW(Long w) {
            return v4(x, y, z, w);
        }

        @Override
        public ArithmeticI64 arithmetic() {
            return new ArithmeticI64();
        }

        @Override
        public Vector3.I64 value() {
            return this;
        }
    }
}
