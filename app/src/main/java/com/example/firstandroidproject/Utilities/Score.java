package com.example.firstandroidproject.Utilities;

public class Score {
    private double lat;
    private double lon;
    private int score;

    public Score(double lat, double lon, int score) {
        this.lat = lat;
        this.lon = lon;
        this.score = score;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getScore() {
        return score;
    }
}
