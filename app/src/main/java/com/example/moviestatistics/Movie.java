package com.example.moviestatistics;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "movie_table")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo
    private String title;

    @ColumnInfo
    private int releaseYear;

    @ColumnInfo
    private int runtime;

    @ColumnInfo
    private double aspectRatio;

    @ColumnInfo
    private boolean color;

    @ColumnInfo
    private boolean watched;

    @NonNull
    @ColumnInfo
    private String comment;

    public Movie(@NonNull String title) {
        this.title = title;
        releaseYear = 0;
        runtime = 0;
        aspectRatio = 0.0;
        color = true;
        watched = true;
        comment = "Comment";
    }

    @Ignore
    public Movie(@NonNull String title, int releaseYear, int runtime, double aspectRatio, boolean color, boolean watched, @NonNull String comment) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.runtime = runtime;
        this.aspectRatio = aspectRatio;
        this.color = color;
        this.watched = watched;
        this.comment = comment;
    }

    @Ignore
    public Movie(int id, @NonNull String title) {
        this.id = id;
        this.title = title;
        releaseYear = 0;
        runtime = 0;
        aspectRatio = 0.0;
        color = true;
        watched = true;
        comment = "Comment";
    }

    @Ignore
    public Movie(int id, @NonNull String title, int releaseYear, int runtime, double aspectRatio, boolean color, boolean watched, @NonNull String comment) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.runtime = runtime;
        this.aspectRatio = aspectRatio;
        this.color = color;
        this.watched = watched;
        this.comment = comment;
    }

    public int getId() { return id; }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getRuntime() {
        return runtime;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public boolean isColor() {
        return color;
    }

    public boolean isWatched() {
        return watched;
    }

    public String getComment() {
        return comment;
    }

    public void setId(int id) { this.id = id; }

    public void setTitle(@NonNull String title) { this.title = title; }

    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public void setRuntime(int runtime) { this.runtime = runtime; }

    public void setAspectRatio(double aspectRatio) { this.aspectRatio = aspectRatio; }

    public void setColor(boolean color) { this.color = color; }

    public void setWatched(boolean watched) { this.watched = watched; }

    public void setComment(@NonNull String comment) { this.comment = comment; }
}
