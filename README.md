# Xasmedy Math Library
Welcome!\
This is a math library made with one of the latest you can get Java, to be more specific, the Valhalla Early Access 2 based on Java 26.\
If you are thinking "Valhalla?!" Yes, I understand, weirdly enough for many, valhalla is coming soon!

# Features
- Point interfaces from 1D to 4D
- Vector interface
- Vector2
- Radians unit class
- Safe nullability API thanks to [JSpecify](https://jspecify.dev/)
- Modularity as a top priority.

# How to Use
I'll try to make a maven release when the library is in a better shape, fow now you need to build from scratch, or use one of the versions I provide.\
You'll need to provide a few runtime flags, since I'm using internal valhalla APIs to provide better performance:
- `--enable-preview`
- `--add-exports=java.base/jdk.internal.value=xasmedy.math`
- `--add-exports=java.base/jdk.internal.vm.annotation=xasmedy.math`

## Why?
I was not satisfied with LibGDX APIs, since outdated; they used Java 6, I was 3 years old when this version was released!\
Either way, I had to abuse primitives to not kill the GC, making both of these things a sh*tty coding experience.

This is why I'm remaking some of the math classes by following modern standards, like immutability, and throwing in the trash inheritance. My objective is to have these math classes be used in a hot-path without any worry, hence why I'm using valhalla.

## Backwards compatibility?
No guarantees. I'm using an Early Access what do you expect?!\
I'll try to be careful, document any breakage, and version appropriately.
But there might be things out of my control, so this is something to keep in mind.\
To make you a bit relieved, I'll be the primary person using this library, and I'm quite lazy, this means I won't have the motivation to change things (unless really needed), meaning breaking changes will probably be kept to a minimum.

## Better name?
One of the two hardest things in programming is figuring out names for things.
I'll keep this name for a long while, probably I'll change it with a proper release, meaning when Valhalla goes in GA.

## Special Thanks
To [LibGDX](https://libgdx.com/), I stole most of the math from their implementation, so if there are bugs, it's their fault! (I'm joking)
Aside that, the implementation is totally different with better naming and (hopefully) usage.

## Building the project
**I'm using [bld](https://rife2.com/bld), a lightweight and easy-to-read build tool that compiles Java using Java.**

Note: You need to change the `bld` script to use the EA Java version, aside that, it's quite straightforward.\
You can easily run in the project root `./bld download` to download the libraries and `./bld jar` to compile the jar.
