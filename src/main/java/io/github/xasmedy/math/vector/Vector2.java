package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.arithmetic.*;
import io.github.xasmedy.math.point.Point2;
import io.github.xasmedy.math.unit.Radians;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;
import static io.github.xasmedy.math.vector.Vectors.*;
import static io.github.xasmedy.math.arithmetic.Arithmetics.*;

public interface Vector2<T extends Vector2<T, N>, N extends Number> extends Vector<T, N>, Point2<N> {

    static Vector2.F64 rotate(Vector2.F64 vector, Radians radians) {

        final double cos = Math.cos(radians.value());
        final double sin = Math.sin(radians.value());

        final double newX = vector.x() * cos - vector.y() * sin;
        final double newY = vector.x() * sin + vector.y() * cos;

        return v2(newX, newY);
    }

    static Radians angle(Vector2.F64 vector) {
        return Radians.radians(Math.atan2(vector.y(), vector.x()));
    }

    T new_(N x, N y);

    Vector3<?, N> withZ(N z);

    Vector1<?, N> withoutY();

    @Override
    default int dimension() {
        return 2;
    }

    @Override
    default N component(int index) throws IndexOutOfBoundsException {
        return switch (index) {
            case 0 -> x();
            case 1 -> y();
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    default N sum() {
        return arithmetic().add(x(), y());
    }

    @Override
    default T filled(N value) {
        return new_(value, value);
    }

    @Override
    default T operation(T other, Operation<N> operation) {
        final N newX = operation.calculate(arithmetic(), x(), other.x());
        final N newY = operation.calculate(arithmetic(), y(), other.y());
        return new_(newX, newY);
    }

    @Override
    default T operation(Transformation<N> operation) {
        final N newX = operation.calculate(arithmetic(), x());
        final N newY = operation.calculate(arithmetic(), y());
        return new_(newX, newY);
    }

    @Override
    default boolean condition(T other, Predicate<N> predicate) {
        return predicate.test(arithmetic(), x(), other.x()) &&
               predicate.test(arithmetic(), y(), other.y());
    }

    default N cross(T vector) {
        final var ath = arithmetic();
        // x * vec.y - y * vec.x
        final N newX = ath.mul(x(), vector.y());
        final N newY = ath.mul(y(), vector.x());
        return ath.sub(newX, newY);
    }

    @LooselyConsistentValue
    value record F32(@NullRestricted Float x,
                     @NullRestricted Float y) implements Vector2<F32, Float>, RealVector<F32, Float> {

        public Vector2.F32 rotate(Radians radians) {
            final var copy = new Vector2.F64((double) x(), (double) y());
            final var rotated = Vector2.rotate(copy, radians);
            return new Vector2.F32((float) (double) rotated.x(), (float) (double) rotated.y());
        }

        public Radians angle() {
            return Vector2.angle(new Vector2.F64((double) x(), (double) y()));
        }

        @Override
        public Vector2.F32 new_(Float x, Float y) {
            return v2(x, y);
        }

        @Override
        public Vector3.F32 withZ(Float z) {
            return v3(x(), y(), z);
        }

        @Override
        public Vector1.F32 withoutY() {
            return v1(x());
        }

        @Override
        public Vector2.I32 ceilAsInt() {
            return v2((int) Math.ceil(x()), (int) Math.ceil(y()));
        }

        @Override
        public Vector2.I32 floorAsInt() {
            return v2((int) Math.floor(x()), (int) Math.floor(y()));
        }

        @Override
        public ArithmeticF32 arithmetic() {
            return ATH_F32;
        }

        @Override
        public Vector2.F32 value() {
            return this;
        }
    }

    @LooselyConsistentValue
    value record F64(@NullRestricted Double x,
                     @NullRestricted Double y) implements Vector2<F64, Double>, RealVector<F64, Double> {

        public Vector2.F64 rotate(Radians radians) {
            return Vector2.rotate(this, radians);
        }

        public Radians angle() {
            return Vector2.angle(this);
        }

        @Override
        public Vector2.F64 new_(Double x, Double y) {
            return new Vector2.F64(x, y);
        }

        @Override
        public Vector3.F64 withZ(Double z) {
            return v3(x(), y(), z);
        }

        @Override
        public Vector1.F64 withoutY() {
            return v1(x());
        }

        @Override
        public Vector2.I64 ceilAsInt() {
            return new Vector2.I64((long) Math.ceil(x()), (long) Math.ceil(y()));
        }

        @Override
        public Vector2.I64 floorAsInt() {
            return new Vector2.I64((long) Math.floor(x()), (long) Math.floor(y()));
        }

        @Override
        public ArithmeticF64 arithmetic() {
            return ATH_F64;
        }

        @Override
        public Vector2.F64 value() {
            return this;
        }
    }

    @LooselyConsistentValue
    value record I32(@NullRestricted Integer x,
                     @NullRestricted Integer y) implements Vector2<I32, Integer>, IntegerVector<I32, Integer> {

        @Override
        public Vector2.I32 new_(Integer x, Integer y) {
            return new Vector2.I32(x, y);
        }

        @Override
        public Vector3.I32 withZ(Integer z) {
            return v3(x(), y(), z);
        }

        @Override
        public Vector1.I32 withoutY() {
            return v1(x());
        }

        @Override
        public Vector2.F32 asReal() {
            return new Vector2.F32((float) x(), (float) y());
        }

        @Override
        public ArithmeticI32 arithmetic() {
            return ATH_I32;
        }

        @Override
        public Vector2.I32 value() {
            return this;
        }
    }

    @LooselyConsistentValue
    value record I64(@NullRestricted Long x,
                     @NullRestricted Long y) implements Vector2<I64, Long>, IntegerVector<I64, Long> {

        @Override
        public Vector2.I64 new_(Long x, Long y) {
            return new Vector2.I64(x, y);
        }

        @Override
        public Vector3.I64 withZ(Long z) {
            return v3(x(), y(), z);
        }

        @Override
        public Vector1.I64 withoutY() {
            return v1(x());
        }

        @Override
        public Vector2.F64 asReal() {
            return new Vector2.F64((double) x(), (double) y());
        }

        @Override
        public ArithmeticI64 arithmetic() {
            return ATH_I64;
        }

        @Override
        public Vector2.I64 value() {
            return this;
        }
    }
}
