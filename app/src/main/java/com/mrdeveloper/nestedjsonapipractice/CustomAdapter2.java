package com.mrdeveloper.nestedjsonapipractice;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mrdeveloper.nestedjsonapipractice.Model.Movies;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.MyViewHolder>{

    Context context;
    List<Movies> moviesList;
    List<Movies> filteredList;

    public CustomAdapter2(Context context, List<Movies> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
        this.filteredList = new ArrayList<>(moviesList);
    }

    @NonNull
    @Override
    public CustomAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(context).inflate(R.layout.movie_item2,parent,false);


        return new CustomAdapter2.MyViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter2.MyViewHolder holder, int position) {
        String title = filteredList.get(position).getTitle();
        Glide.with(context)
                .load(filteredList.get(position).getPoster())
                .into(holder.imageView);

        holder.movieTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filterList(List<Movies> filteredList) {
        this.filteredList = filteredList; // নতুন লিস্ট সেট করুন
        notifyDataSetChanged(); // RecyclerView-কে জানিয়ে দিন যে ডাটা আপডেট হয়েছে
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
