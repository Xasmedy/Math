[![Maven](https://maven-badges.sml.io/sonatype-central/io.github.xasmedy/math/badge.svg?style=flat&subject=Maven&color=blue)](https://maven-badges.sml.io/sonatype-central/io.github.xasmedy/math/)
# Xasmedy Math Library
Welcome!\
This is a math library made with one of the latest you can get Java, to be more specific, the [Valhalla Early Access 2](https://jdk.java.net/valhalla/) based on Java 26.\
If you are thinking "Valhalla?!" Yes, I understand, weirdly enough for many, Valhalla is coming soon!

# Features
Most classes that requires numeric have support for `Integer`, `Long`, `Float`, and `Double`, and custom ones can be added easily.
Only the shape classes do not provide a specialization, they use generics directly.

Available:
- Point interfaces/implementations from 1D to 4D
- Vector interfaces/implementations from 1D to 4D
- Quaternion and Radians unit-class for rotation, they always use `double`.
- Modularity as a top priority.
- Shape interface plus `Line`, `Rectangle`, `Cuboid`, and `Sphere` implementations.
- Matrix, Matrix3 and Matrix4 interfaces and implementations using `float` and `double`.
- Safe nullability API thanks to [Jspecify](https://jspecify.dev/)

# How to Use
The library is on Maven Central and can be included with the following.

#### Gradle
```
implementation("io.github.xasmedy:math:0.1.1")
```
#### Bld
```
module("io.github.xasmedy", "math", "0.1.1")
```
To run the application, you'll need to provide a few runtime flags, since I'm using internal Valhalla APIs to provide better performance:
- `--enable-preview`
- `--add-exports=java.base/jdk.internal.value=xasmedy.math`
- `--add-exports=java.base/jdk.internal.vm.annotation=xasmedy.math`

## Why?
I was not satisfied with LibGDX APIs, since outdated; they started with Java 6,
it has almost been 2 decades since this version was released!\
Either way, I had to abuse primitives to not kill the GC, making both of these things a *bad* coding experience.

This is why I'm remaking some of the math classes by following modern standards, like immutability, and for the most part, throwing in the trash inheritance.
My objective is to have these math classes be used in hot-paths without any worry, hence why I'm using Valhalla.

My dream/hope is that this becomes a standard for game related things to make library development easier between different frameworks,
I'll try my best to make this a reality, but it will likely just be one of those nice dreams... we'll see with time!

## Backwards compatibility?
No guarantees. I'm using an Early Access what do you expect?!\
I'll try to be careful, document any breakage, and version appropriately.
But there might be things out of my control, so this is something to keep in mind.\
To make you a bit relieved, I'll be the primary person using this library, and I'm quite lazy,
this means I won't have the motivation to change things (unless really needed), meaning breaking changes will probably be kept to a minimum.

## Better name?
One of the two hardest things in programming is figuring out names for things.
I'll keep this name for a long while, probably I'll change it with a proper release, meaning when Valhalla goes in GA.
Update: The name should be fine, still, I need to change the `github.io` domain, which I might have figured out, I only need to buy it. 

## Special Thanks
To [LibGDX](https://libgdx.com/), I ~~stole~~ used as a reference most of the math from their implementation, so if there are bugs, it's their fault! (I'm joking)
Aside that, the implementation is totally different with better naming, safety, and (hopefully) usage.

## Building the project
**I'm using [bld](https://rife2.com/bld), a lightweight and easy-to-read build tool that compiles Java using Java.**
The build tool is quite easy to use, the only thing is you need to create the `java-version.txt` file and provide the Java directory.
Because of the lack of support, it's quite the pain to use with IntelliJ, hopefully with time this improves,
the good thing is I'm actively discussing with bld creators to improve on this. 

Said this, you can easily do (in the project root) `./bld download` to download the libraries and `./bld jar` to compile the jar.
