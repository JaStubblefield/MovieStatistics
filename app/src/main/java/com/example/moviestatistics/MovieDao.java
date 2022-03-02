package com.example.moviestatistics;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movie);

    @Query("SELECT * from movie_table ORDER BY title ASC")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * from movie_table LIMIT 1")
    Movie[] getAnyMovie();

    @Query("DELETE FROM movie_table")
    void deleteAll();

    @Delete
    void deleteMovie(Movie movie);

    @Update
    void update(Movie... movie);
}
