package com.mrdeveloper.nestedjsonapipractice;

import android.graphics.Movie;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.mrdeveloper.nestedjsonapipractice.Model.MainModel;
import com.mrdeveloper.nestedjsonapipractice.Model.Movies;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    AppCompatEditText searchView;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    LottieAnimationView lottieAnimationView;
    ShimmerFrameLayout shimmerFrameLayout;
    ChipGroup chipGroup;

    List<Movies> moviesList;
    CustomAdapter adapter;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        searchView = findViewById(R.id.edSearch);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        lottieAnimationView = findViewById(R.id.animationView);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        chipGroup = findViewById(R.id.chipGroup);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://mrdeveloper.xyz/").addConverterFactory(GsonConverterFactory.create()).build();

        progressBar.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        addChipToGroup();

        apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getResponse().enqueue(new Callback<MainModel>() {
            @Override
            public void onResponse(Call<MainModel> call, Response<MainModel> response) {
                if (response.isSuccessful()) {
                    MainModel model = response.body();
                    moviesList = model.getMovies();
                    adapter = new CustomAdapter(MainActivity.this,moviesList,lottieAnimationView);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    Toast.makeText(MainActivity.this, "Load Data Successful", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                } else {
                    Toast.makeText(MainActivity.this, "No Load Data", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<MainModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });


        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.setFilteredList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    } // ===================== On Create End =================

    private void addChipToGroup() {
        String[] genres = {"Action","Drama","Crime","Horror","Biography","History","Romance","Sci-Fi","Thriller"};
        for (String genre : genres){
            Chip chip = new Chip(MainActivity.this);
            chip.setText(genre);
            chip.setCheckable(true);
            chip.setCheckedIconVisible(true);

            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    if (isChecked) {
                        for (int i = 0; i < chipGroup.getChildCount(); i++) {
                            Chip chip = (Chip) chipGroup.getChildAt(i);
                            if (!chip.getText().toString().equals(genre)) {
                                chip.setChecked(false);  // অন্য চিপগুলো unselect করুন
                            }
                        }
                        adapter.setFilteredList(genre);
                    } else {
                        adapter.setFilteredList("");
                    }

                }
            });

            chipGroup.addView(chip);

        }

    }



}