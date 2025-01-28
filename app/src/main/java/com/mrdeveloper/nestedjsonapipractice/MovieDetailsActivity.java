package com.mrdeveloper.nestedjsonapipractice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mrdeveloper.nestedjsonapipractice.Model.MainModel;
import com.mrdeveloper.nestedjsonapipractice.Model.Movies;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView transparentImage, moviePoster;
    TextView movieTitle, movieRuntime, movieRating, movieReleased, movieGenre, moviePlot, similarMovies;
    RecyclerView recyclerView;

    CustomAdapter2 adapter;

    ApiInterface apiInterface;
    List<Movies> moviesList;

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
        recyclerView = findViewById(R.id.recyclerView);
        similarMovies = findViewById(R.id.similarMovies);

        loadMovies();

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

        similarMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterMovies(genre);
            }
        });


    }

    private void loadMovies() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://mrdeveloper.xyz/").addConverterFactory(GsonConverterFactory.create()).build();


        apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getResponse().enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                if (response.isSuccessful()) {
                    MainModel model = response.body();
                    moviesList = model.getMovies();
                    adapter = new CustomAdapter2(MovieDetailsActivity.this,moviesList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailsActivity.this,LinearLayoutManager.HORIZONTAL,false));

                } else {
                    Toast.makeText(MovieDetailsActivity.this, "No Load Data", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {
                Toast.makeText(MovieDetailsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void filterMovies(String genre) {
        List<Movies> filteredList = new ArrayList<>();
        for (Movies movie : moviesList) {
            if (movie.getGenre().equalsIgnoreCase(genre)) {
                filteredList.add(movie); // যেটা মিলবে, সেটা লিস্টে যোগ করুন
            }
        }
        adapter.filterList(filteredList); // নতুন লিস্ট Adapter-এ পাঠান
    }




}