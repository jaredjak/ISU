package com.example.androidexample;

public class Daily {
    private String name;
    private boolean isCompleted;
    private double progress;
    private int currentProgress;
    private int targetProgess;

    public Daily(String name, boolean isCompleted, double progress, int currentProgress, int targetProgess) {
        this.name = name;
        this.isCompleted = isCompleted;
        this.progress = progress;
        this.currentProgress = currentProgress;
        this.targetProgess = targetProgess;
    }

    public String getName() { return name; }
    public boolean isCompleted() { return isCompleted; }
    public double getProgress() { return progress; }
    public int getCurrentProgress() { return currentProgress; }
    public int getTargetProgess() { return targetProgess; }
}
