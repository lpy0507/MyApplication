package com.example.lpy.myapplication.bean;

/**
 * 电影排片表
 */
public class MovieSchedule {
    private String movieName;
    private String startTime;
    private String endTime;

    public MovieSchedule(String movieName, String startTime, String endTime) {
        this.movieName = movieName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
