package com.example.videoapp.models.bg.tasks;

import android.os.AsyncTask;

import com.example.videoapp.models.components.DaggerNetComponent;
import com.example.videoapp.models.data.Category;
import com.example.videoapp.models.data.RestCall;
import com.example.videoapp.models.modules.NetModule;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by jonathanhavstad on 4/13/17.
 */

public class RestTask extends AsyncTask<String, Integer, Category> {
    private static final String CATEGORIES_LABEL = "categories";

    private static final String REST_URL = "https://commondatastorage.googleapis.com";
    private static final String HOSTNAME = "commondatastorage.googleapis.com";
    private static final String PIN1 = "sha256/1JSe/nkH5Im7nI6OfWax+PQwhdd/z7ntE9YiFHGEFGs=";
    private static final String PIN2 = "sha256/7HIpactkIAq2Y49orFOOQKurWxmmSFZhBCoQYcRhJ3Y=";
    private static final String PIN3 = "sha256/h6801m+z8v3zbgkRHpq6L29Esgfzhj89C1SyUCOQmqU=";

    public interface OnFinished {
        void finished(Category category);
    }

    private final OnFinished onFinished;

    private RestCall restCall;

    public RestTask(OnFinished onFinished) {
        this.onFinished = onFinished;

        restCall = DaggerNetComponent.builder()
                .netModule(new NetModule(REST_URL, HOSTNAME, PIN1, PIN2, PIN3))
                .build()
                .restCall();
    }

    @Override
    protected Category doInBackground(String... params) {
        Category firstCategory = null;

        for (String url : params) {
            try {
                JsonObject restResult = restCall.call(url);
                JsonArray categories = restResult.getAsJsonArray(CATEGORIES_LABEL);
                firstCategory = restCall.getGson().fromJson(categories.get(0), Category.class);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return firstCategory;
    }

    @Override
    protected void onPostExecute(Category category) {
        onFinished.finished(category);
    }
}
