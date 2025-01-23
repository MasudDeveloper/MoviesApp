package com.mrdeveloper.nestedjsonapipractice;

import com.mrdeveloper.nestedjsonapipractice.Model.MainModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("Json/movies-250.json")
    Call<MainModel> getResponse();

}
