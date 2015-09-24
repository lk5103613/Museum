package com.shmuseum.entity;

public class MapPoint {

    public float x;
    public float y;
    public int id;

    public MapPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public MapPoint(float x, float y, int id) {
        this(x, y);
        this.id = id;
    }

}
