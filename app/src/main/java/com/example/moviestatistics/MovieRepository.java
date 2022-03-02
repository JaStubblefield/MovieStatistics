package com.example.moviestatistics;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MovieRepository {

    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllMovies;

    MovieRepository(Application application) {
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mAllMovies = mMovieDao.getAllMovies();
    }

    LiveData<List<Movie>> getAllMovies() { return mAllMovies; }

    public void insert(Movie movie) { new insertAsyncTask(mMovieDao).execute(movie); }

    public void update(Movie movie) { new updateMovieAsyncTask(mMovieDao).execute(movie); }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao mAsyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllMoviesAsyncTask extends AsyncTask<Void, Void, Void> {
        private MovieDao mAsyncTaskDao;

        deleteAllMoviesAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void deleteAll() {
        new deleteAllMoviesAsyncTask(mMovieDao).execute();
    }

    private static class deleteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao mAsyncTaskDao;

        deleteMovieAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.deleteMovie(params[0]);
            return null;
        }
    }

    public void deleteMovie(Movie movie)  {
        new deleteMovieAsyncTask(mMovieDao).execute(movie);
    }

    private static class updateMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao mAsyncTaskDao;

        updateMovieAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
