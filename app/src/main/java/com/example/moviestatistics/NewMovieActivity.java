package com.example.moviestatistics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import static com.example.moviestatistics.MainActivity.EXTRA_DATA_ID;
import static com.example.moviestatistics.MainActivity.EXTRA_DATA_UPDATE_ASPECT_RATIO;
import static com.example.moviestatistics.MainActivity.EXTRA_DATA_UPDATE_COLOR;
import static com.example.moviestatistics.MainActivity.EXTRA_DATA_UPDATE_COMMENT;
import static com.example.moviestatistics.MainActivity.EXTRA_DATA_UPDATE_RUNTIME;
import static com.example.moviestatistics.MainActivity.EXTRA_DATA_UPDATE_TITLE;
import static com.example.moviestatistics.MainActivity.EXTRA_DATA_UPDATE_WATCHED;
import static com.example.moviestatistics.MainActivity.EXTRA_DATA_UPDATE_YEAR;

public class NewMovieActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_TITLE = "com.example.moviestatistics.REPLY_TITLE";
    public static final String EXTRA_REPLY_ID = "com.example.moviestatistics.REPLY_ID";
    public static final String EXTRA_REPLY_YEAR = "com.example.moviestatistics.REPLY_YEAR";
    public static final String EXTRA_REPLY_RUNTIME = "com.example.moviestatistics.REPLY_RUNTIME";
    public static final String EXTRA_REPLY_ASPECT_RATIO = "com.example.moviestatistics.REPLY_ASPECT_RATIO";
    public static final String EXTRA_REPLY_COLOR = "com.example.moviestatistics.REPLY_COLOR";
    public static final String EXTRA_REPLY_WATCHED = "com.example.moviestatistics.REPLY_WATCHED";
    public static final String EXTRA_REPLY_COMMENT = "com.example.moviestatistics.REPLY_COMMENT";

    private EditText mEditTitleView;
    private EditText mEditReleaseYearView;
    private EditText mEditRuntimeView;
    private EditText mEditAspectRatioView;

    private RadioButton mYesColorButton;
    private RadioButton mNoColorButton;
    private RadioButton mYesWatchedButton;
    private RadioButton mNoWatchedButton;

    private EditText mEditCommentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_movie);

        mEditTitleView = findViewById(R.id.edit_title);
        mEditReleaseYearView = findViewById(R.id.edit_releaseYear);
        mEditRuntimeView = findViewById(R.id.edit_runtime);
        mEditAspectRatioView = findViewById(R.id.edit_aspectRatio);

        mYesColorButton = findViewById(R.id.yes_color);
        mNoColorButton = findViewById(R.id.no_color);
        mYesWatchedButton = findViewById(R.id.yes_watched);
        mNoWatchedButton = findViewById(R.id.no_watched);

        mEditCommentView = findViewById(R.id.edit_comment);

        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String title = extras.getString(EXTRA_DATA_UPDATE_TITLE, "");
            int releaseYear = extras.getInt(EXTRA_DATA_UPDATE_YEAR);
            int runtime = extras.getInt(EXTRA_DATA_UPDATE_RUNTIME);
            double aspectRatio = extras.getDouble(EXTRA_DATA_UPDATE_ASPECT_RATIO);
            boolean color = extras.getBoolean(EXTRA_DATA_UPDATE_COLOR);
            boolean watched = extras.getBoolean(EXTRA_DATA_UPDATE_WATCHED);
            String comment = extras.getString(EXTRA_DATA_UPDATE_COMMENT);

            if (!title.isEmpty()) {
                mEditTitleView.setText(title);
                mEditReleaseYearView.setText(Integer.toString(releaseYear));
                mEditRuntimeView.setText(Integer.toString(runtime));
                mEditAspectRatioView.setText(Double.toString(aspectRatio));

                mYesColorButton.setChecked(color);
                mNoColorButton.setChecked(!color);
                mYesWatchedButton.setChecked(watched);
                mNoWatchedButton.setChecked(!watched);

                mEditCommentView.setText(comment);

                mEditTitleView.setSelection(title.length());
                mEditTitleView.requestFocus();
            }
        } // Otherwise, start with empty fields.

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                if (TextUtils.isEmpty(mEditTitleView.getText())) {
                    mEditTitleView.setText("No title");
                }
                if (TextUtils.isEmpty(mEditReleaseYearView.getText())) {
                    mEditReleaseYearView.setText("0");
                }
                if (TextUtils.isEmpty(mEditRuntimeView.getText())) {
                    mEditRuntimeView.setText("0");
                }
                if (TextUtils.isEmpty(mEditAspectRatioView.getText())) {
                    mEditAspectRatioView.setText("0.0");
                }
                if (TextUtils.isEmpty(mEditCommentView.getText())) {
                    mEditCommentView.setText("No comment");
                }

                String title = mEditTitleView.getText().toString();
                String releaseYear = mEditReleaseYearView.getText().toString();
                String runtime = mEditRuntimeView.getText().toString();
                String aspectRatio = mEditAspectRatioView.getText().toString();

                String color = Boolean.toString(mYesColorButton.isChecked());
                String watched = Boolean.toString(mYesWatchedButton.isChecked());

                String comment = mEditCommentView.getText().toString();

                replyIntent.putExtra(EXTRA_REPLY_TITLE, title);
                replyIntent.putExtra(EXTRA_REPLY_YEAR, releaseYear);
                replyIntent.putExtra(EXTRA_REPLY_RUNTIME, runtime);
                replyIntent.putExtra(EXTRA_REPLY_ASPECT_RATIO, aspectRatio);

                replyIntent.putExtra(EXTRA_REPLY_COLOR, color);
                replyIntent.putExtra(EXTRA_REPLY_WATCHED, watched);

                replyIntent.putExtra(EXTRA_REPLY_COMMENT, comment);

                if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                    int id = extras.getInt(EXTRA_DATA_ID, -1);
                    if (id != -1) {
                        replyIntent.putExtra(EXTRA_REPLY_ID, id);
                    }
                }
                setResult(RESULT_OK, replyIntent);

                finish();
            }
        });
    }
}
