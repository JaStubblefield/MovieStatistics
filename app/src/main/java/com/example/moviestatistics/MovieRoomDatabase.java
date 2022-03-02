package com.example.moviestatistics;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Movie.class}, version = 2, exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static MovieRoomDatabase INSTANCE;

    public static MovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, "movie_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MovieDao mDao;
        String[] titleArr = {"Sunset Boulevard", "Three Billboards Outside Ebbing, Missouri"};
        int[] releaseYearArr = {1950, 2017};
        int[] runtimeArr = {110, 115};
        double[] aspectRatioArr = {1.37, 2.35};
        boolean[] colorArr = {false, true};
        boolean[] watchedArr = {true, true};
        String[] commentArr = {"No comment", "Has a long title."};

        PopulateDbAsync(MovieRoomDatabase db) {
            mDao = db.movieDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            if (mDao.getAnyMovie().length < 1) {
                for (int i = 0; i <= titleArr.length - 1; i++) {
                    Movie movie = new Movie(titleArr[i]);
                    movie.setReleaseYear(releaseYearArr[i]);
                    movie.setRuntime(runtimeArr[i]);
                    movie.setAspectRatio(aspectRatioArr[i]);
                    movie.setColor(colorArr[i]);
                    movie.setWatched(watchedArr[i]);
                    movie.setComment(commentArr[i]);

                    mDao.insert(movie);
                }
            }
            return null;
        }
    }
}
