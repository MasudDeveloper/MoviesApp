package com.mrdeveloper.nestedjsonapipractice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView transparentImage, moviePoster;
    TextView movieTitle, movieRuntime, movieRating, movieReleased, movieGenre, moviePlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        transparentImage = findViewById(R.id.transparentPoster);
        moviePoster = findViewById(R.id.moviePoster);
        movieTitle = findViewById(R.id.movieTitle);
        movieRuntime = findViewById(R.id.movieRuntime);
        movieRating = findViewById(R.id.movieRating);
        movieReleased = findViewById(R.id.movieReleased);
        movieGenre = findViewById(R.id.movieGenre);
        moviePlot = findViewById(R.id.moviePlot);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String released = intent.getStringExtra("released");
        String runtime = intent.getStringExtra("runtime");
        String genre = intent.getStringExtra("genre");
        String plot = intent.getStringExtra("plot");
        String imdbRating = intent.getStringExtra("imdbRating");

        byte[] imageBytes = intent.getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);

        transparentImage.setImageBitmap(bitmap);
        moviePoster.setImageBitmap(bitmap);

        movieTitle.setText(title);
        movieReleased.setText(released);
        movieRuntime.setText(runtime);
        movieRating.setText(imdbRating+" (IMDB)");
        movieGenre.setText(genre);
        moviePlot.setText(plot);

    }
}