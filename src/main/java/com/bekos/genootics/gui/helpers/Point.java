package com.bekos.genootics.gui.helpers;


public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this(point.x, point.y);
    }

    public Point translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        return this;
    }

    public Point translate(Point point) {
        return this.translate(point.x, point.y);
    }

    public Point scale(float scaleFactor) {
        this.x *= scaleFactor;
        this.y *= scaleFactor;
        return this;
    }
}
