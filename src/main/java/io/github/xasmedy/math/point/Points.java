package io.github.xasmedy.math.point;

/// A class to import statically to have a short way to initialize all the library points.
public value class Points {

    private Points() {}

    /* -- Point1D -- */

    public Point1.I32 p1(int x) {
        return new Point1.I32(x);
    }

    public Point1.I64 p1(long x) {
        return new Point1.I64(x);
    }

    public Point1.F32 p1(float x) {
        return new Point1.F32(x);
    }

    public Point1.F64 p1(double x) {
        return new Point1.F64(x);
    }

    /* -- Point2D -- */

    public Point2.I32 p2(int x, int y) {
        return new Point2.I32(x, y);
    }

    public Point2.I64 p2(long x, long y) {
        return new Point2.I64(x, y);
    }

    public Point2.F32 p2(float x, float y) {
        return new Point2.F32(x, y);
    }

    public Point2.F64 p2(double x, double y) {
        return new Point2.F64(x, y);
    }

    /* -- Point3D -- */

    public Point3.I32 p3(int x, int y, int z) {
        return new Point3.I32(x, y, z);
    }

    public Point3.I64 p3(long x, long y, long z) {
        return new Point3.I64(x, y, z);
    }

    public Point3.F32 p3(float x, float y, float z) {
        return new Point3.F32(x, y, z);
    }

    public Point3.F64 p3(double x, double y, double z) {
        return new Point3.F64(x, y, z);
    }

    /* -- Point4D -- */

    public Point4.I32 p4(int x, int y, int z, int w) {
        return new Point4.I32(x, y, z, w);
    }

    public Point4.I64 p4(long x, long y, long z, long w) {
        return new Point4.I64(x, y, z, w);
    }

    public Point4.F32 p4(float x, float y, float z, float w) {
        return new Point4.F32(x, y, z, w);
    }

    public Point4.F64 p4(double x, double y, double z, double w) {
        return new Point4.F64(x, y, z, w);
    }
}
