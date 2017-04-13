package com.example.videoapp.models.data;

import android.support.annotation.WorkerThread;

import com.example.videoapp.models.rest.VideoAPI;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class RestCall {
    private Gson gson;
    private VideoAPI videoAPI;

    @Inject
    public RestCall(Gson gson, VideoAPI videoAPI) {
        this.gson = gson;
        this.videoAPI = videoAPI;
    }

    @WorkerThread
    public JsonObject call(String url) throws IOException {
        return videoAPI.getCategories(url).execute().body();
    }

    public Gson getGson() {
        return gson;
    }
}
