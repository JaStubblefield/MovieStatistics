package com.example.moviestatistics;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MovieViewModel mMovieViewModel;

    public static final int NEW_MOVIE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_MOVIE_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_UPDATE_TITLE = "extra_title_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_YEAR = "extra_year_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_RUNTIME = "extra_runtime_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_ASPECT_RATIO = "extra_aspect_ratio_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_COLOR = "extra_color_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_WATCHED = "extra_watched_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_COMMENT = "extra_comment_to_be_updated";

    public static final String EXTRA_DATA_ID = "extra_data_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final MovieListAdapter adapter = new MovieListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mMovieViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable final List<Movie> movies) {
                adapter.setMovies(movies);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewMovieActivity.class);
                startActivityForResult(intent, NEW_MOVIE_ACTIVITY_REQUEST_CODE);
            }
        });

        // Swipe items to delete
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Movie myMovie = adapter.getMovieAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myMovie.getTitle(), Toast.LENGTH_SHORT).show();

                        mMovieViewModel.deleteMovie(myMovie);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new MovieListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Movie movie = adapter.getMovieAtPosition(position);
                launchUpdateMovieActivity(movie);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            Toast.makeText(this, "All movies deleted.",
                    Toast.LENGTH_SHORT).show();
            mMovieViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_MOVIE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(NewMovieActivity.EXTRA_REPLY_TITLE);
            String releaseYear = data.getStringExtra(NewMovieActivity.EXTRA_REPLY_YEAR);
            String runtime = data.getStringExtra(NewMovieActivity.EXTRA_REPLY_RUNTIME);
            String aspectRatio = data.getStringExtra(NewMovieActivity.EXTRA_REPLY_ASPECT_RATIO);
            String color = data.getStringExtra(NewMovieActivity.EXTRA_REPLY_COLOR);
            String watched = data.getStringExtra(NewMovieActivity.EXTRA_REPLY_WATCHED);
            String comment = data.getStringExtra(NewMovieActivity.EXTRA_REPLY_COMMENT);

            Movie movie = new Movie(title, Integer.parseInt(releaseYear), Integer.parseInt(runtime),
                    Double.parseDouble(aspectRatio), Boolean.parseBoolean(color), Boolean.parseBoolean(watched), comment);

            mMovieViewModel.insert(movie);
        } else if (requestCode == UPDATE_MOVIE_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NewMovieActivity.EXTRA_REPLY_ID, -1);

            if (id != -1) {
                String title = data.getStringExtra(NewMovieActivity.EXTRA_REPLY_TITLE);
                String releaseYear = data.getStringExtra(NewMovieActivity.EXTRA_REPLY_YEAR);
                String runtime = data.getStringExtra(NewMovieActivity.EXTRA_REPLY_RUNTIME);
                String aspectRatio = data.getStringExtra(NewMovieActivity.EXTRA_REPLY_ASPECT_RATIO);
                String color = data.getStringExtra(NewMovieActivity.EXTRA_REPLY_COLOR);
                String watched = data.getStringExtra(NewMovieActivity.EXTRA_REPLY_WATCHED);
                String comment = data.getStringExtra(NewMovieActivity.EXTRA_REPLY_COMMENT);

                mMovieViewModel.update(new Movie(id, title, Integer.parseInt(releaseYear), Integer.parseInt(runtime),
                        Double.parseDouble(aspectRatio), Boolean.parseBoolean(color), Boolean.parseBoolean(watched), comment));
            } else {
                Toast.makeText(this, R.string.unable_to_update,
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(
                    this, R.string.empty_not_saved, Toast.LENGTH_SHORT).show();
        }
    }

    public void launchUpdateMovieActivity(Movie movie) {
        Intent intent = new Intent(this, NewMovieActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_TITLE, movie.getTitle());
        intent.putExtra(EXTRA_DATA_ID, movie.getId());
        intent.putExtra(EXTRA_DATA_UPDATE_YEAR, movie.getReleaseYear());
        intent.putExtra(EXTRA_DATA_UPDATE_RUNTIME, movie.getRuntime());
        intent.putExtra(EXTRA_DATA_UPDATE_ASPECT_RATIO, movie.getAspectRatio());
        intent.putExtra(EXTRA_DATA_UPDATE_COLOR, movie.isColor());
        intent.putExtra(EXTRA_DATA_UPDATE_WATCHED, movie.isWatched());
        intent.putExtra(EXTRA_DATA_UPDATE_COMMENT, movie.getComment());
        startActivityForResult(intent, UPDATE_MOVIE_ACTIVITY_REQUEST_CODE);
    }
}
