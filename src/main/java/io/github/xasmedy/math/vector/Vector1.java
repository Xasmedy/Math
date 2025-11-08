package io.github.xasmedy.math.vector;

import io.github.xasmedy.math.arithmetic.*;
import io.github.xasmedy.math.point.Point1;
import jdk.internal.vm.annotation.LooselyConsistentValue;
import jdk.internal.vm.annotation.NullRestricted;
import static io.github.xasmedy.math.vector.Vectors.*;
import static io.github.xasmedy.math.arithmetic.Arithmetics.*;

public interface Vector1<T extends Vector1<T, N>, N extends Number> extends Vector<T, N>, Point1<N> {

    T new_(N x);

    Vector2<?, N> withY(N y);

    @Override
    default int dimension() {
        return 1;
    }

    @Override
    default N component(int index) throws IndexOutOfBoundsException {
        if (index != 0) throw new IndexOutOfBoundsException();
        return x();
    }

    @Override
    default N sum() {
        return x();
    }

    @Override
    default T filled(N value) {
        return new_(value);
    }

    @Override
    default T operation(T other, Operation<N> operation) {
        return new_(operation.calculate(arithmetic(), x(), other.x()));
    }

    @Override
    default T operation(Transformation<N> operation) {
        return new_(operation.calculate(arithmetic(), x()));
    }

    @Override
    default boolean condition(T other, Predicate<N> predicate) {
        return predicate.test(arithmetic(), x(), other.x());
    }

    @LooselyConsistentValue
    value record F32(@NullRestricted Float x) implements Vector1<F32, Float>, RealVector<F32, Float> {
        
        @Override
        public Vector1.F32 new_(Float x) {
            return v1(x);
        }

        @Override
        public Vector2.F32 withY(Float y) {
            return v2(x(), y);
        }

        @Override
        public Vector1.I32 ceilAsInt() {
            return new Vector1.I32((int) Math.ceil(x()));
        }

        @Override
        public Vector1.I32 floorAsInt() {
            return new Vector1.I32((int) Math.floor(x()));
        }

        @Override
        public boolean isCollinear(Vector1.F32 vector, Float epsilon) {
            if (x() == 0 || vector.x() == 0) return x().equals(vector.x());
            return true;
        }

        @Override
        public ArithmeticF32 arithmetic() {
            return ATH_F32;
        }

        @Override
        public Vector1.F32 value() {
            return this;
        }
    }

    @LooselyConsistentValue
    value record F64(@NullRestricted Double x) implements Vector1<F64, Double>, RealVector<F64, Double> {

        @Override
        public Vector1.F64 new_(Double x) {
            return v1(x);
        }

        @Override
        public Vector2.F64 withY(Double y) {
            return v2(x(), y);
        }

        @Override
        public Vector1.I64 ceilAsInt() {
            return v1((long) Math.ceil(x()));
        }

        @Override
        public Vector1.I64 floorAsInt() {
            return v1((long) Math.floor(x()));
        }

        @Override
        public boolean isCollinear(Vector1.F64 vector, Double epsilon) {
            if (x() == 0 || vector.x() == 0) return x().equals(vector.x());
            return true;
        }

        @Override
        public ArithmeticF64 arithmetic() {
            return ATH_F64;
        }

        @Override
        public Vector1.F64 value() {
            return this;
        }
    }

    @LooselyConsistentValue
    value record I32(@NullRestricted Integer x) implements Vector1<I32, Integer>, IntegerVector<I32, Integer> {

        @Override
        public Vector1.I32 new_(Integer x) {
            return v1(x);
        }

        @Override
        public Vector2.I32 withY(Integer y) {
            return v2(x(), y);
        }

        @Override
        public Vector1.F32 asReal() {
            return v1((float) x());
        }

        @Override
        public ArithmeticI32 arithmetic() {
            return ATH_I32;
        }

        @Override
        public Vector1.I32 value() {
            return this;
        }
    }

    @LooselyConsistentValue
    value record I64(@NullRestricted Long x) implements Vector1<I64, Long>, IntegerVector<I64, Long> {

        @Override
        public Vector1.I64 new_(Long x) {
            return v1(x);
        }

        @Override
        public Vector2.I64 withY(Long y) {
            return v2(x(), y);
        }

        @Override
        public Vector1.F64 asReal() {
            return v1((double) x());
        }

        @Override
        public ArithmeticI64 arithmetic() {
            return ATH_I64;
        }

        @Override
        public Vector1.I64 value() {
            return this;
        }
    }
}
