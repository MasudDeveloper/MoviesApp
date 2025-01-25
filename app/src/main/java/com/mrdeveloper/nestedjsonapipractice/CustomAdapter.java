package com.mrdeveloper.nestedjsonapipractice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.mrdeveloper.nestedjsonapipractice.Model.Movies;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    LottieAnimationView lottieAnimationView;
    List<Movies> moviesList;
    List<Movies> filteredList;

    public CustomAdapter(Context context, List<Movies> moviesList, LottieAnimationView lottieAnimationView) {
        this.context = context;
        this.lottieAnimationView = lottieAnimationView;
        this.moviesList = moviesList;
        this.filteredList = new ArrayList<>(moviesList);
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myView = LayoutInflater.from(context).inflate(R.layout.movie_item,parent,false);


        return new MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {

        String title = filteredList.get(position).getTitle();
        String released = filteredList.get(position).getReleased();
        String runtime = filteredList.get(position).getRuntime();
        String genre = filteredList.get(position).getGenre();
        String plot = filteredList.get(position).getPlot();
        String imdbRating = filteredList.get(position).getImdbRating();

        Glide.with(context)
                .load(filteredList.get(position).getPoster())
                .into(holder.imageView);

        holder.movieTitle.setText(title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("released",released);
                intent.putExtra("runtime",runtime);
                intent.putExtra("genre",genre);
                intent.putExtra("plot",plot);
                intent.putExtra("imdbRating",imdbRating);
                holder.imageView.setDrawingCacheEnabled(true);
                Bitmap bitmap = holder.imageView.getDrawingCache();

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);

                byte[] imageBytes = outputStream.toByteArray();

                intent.putExtra("image",imageBytes);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void setFilteredList(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(moviesList);
            notifyDataSetChanged();
        } else {
            for (Movies movies : moviesList){
                if (movies.getTitle().toLowerCase().contains(query.toLowerCase())){
                    filteredList.add(movies);
                }
            }
            notifyDataSetChanged();
        }

        if (filteredList.isEmpty()){
            lottieAnimationView.setVisibility(View.VISIBLE);
        } else {
            lottieAnimationView.setVisibility(View.GONE);
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView movieTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            movieTitle = itemView.findViewById(R.id.movieTitle);

        }
    }
}
