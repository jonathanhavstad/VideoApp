package com.example.videoapp.models.rest;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public interface VideoAPI {
    @GET
    Call<JsonObject> getCategories(@Url String url);
}
