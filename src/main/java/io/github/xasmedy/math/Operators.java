package io.github.xasmedy.math;

/// Defines a generic interface for math operators.\
/// This will likely be replaced by Java operator overloading once it comes out.
public interface Operators<I, O> {

    O sum(I input);

    O sub(I input);

    O mul(I input);

    O div(I input);
}
