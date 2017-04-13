package com.example.videoapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.videoapp.models.bg.tasks.RestTask;
import com.example.videoapp.models.components.DaggerNetComponent;
import com.example.videoapp.models.data.Category;
import com.example.videoapp.models.data.RestCall;
import com.example.videoapp.models.modules.NetModule;
import com.example.videoapp.presenters.AppPresenter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String REST_URL = "https://commondatastorage.googleapis.com";
    private static final String HOSTNAME = "commondatastorage.googleapis.com";
    private static final String REST_API_URL = "gtv-videos-bucket/CastVideos/f.json";
    private static final String PIN1 = "sha256/1JSe/nkH5Im7nI6OfWax+PQwhdd/z7ntE9YiFHGEFGs=";
    private static final String PIN2 = "sha256/7HIpactkIAq2Y49orFOOQKurWxmmSFZhBCoQYcRhJ3Y=";
    private static final String PIN3 = "sha256/h6801m+z8v3zbgkRHpq6L29Esgfzhj89C1SyUCOQmqU=";
    private static final String CATEGORIES_LABEL = "categories";
    private static final int EXPECTED_NUMBER_RESULTS = 17;
    private static final String FIRST_VIDEO_TITLE = "Designing For Google Cast";

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.videoapp", appContext.getPackageName());
    }

    @Test
    public void testRestUrl() throws Exception {
        RestCall restCall = DaggerNetComponent.builder()
                .netModule(new NetModule(REST_URL, HOSTNAME, PIN1, PIN2, PIN3))
                .build()
                .restCall();

        Category firstCategory = null;

        try {
            JsonObject restResult = restCall.call(REST_API_URL);
            JsonArray categories = restResult.getAsJsonArray(CATEGORIES_LABEL);
            firstCategory = restCall.getGson().fromJson(categories.get(0), Category.class);

        } catch (IOException e) {
            e.printStackTrace();
            throw new AssertionError(e.getMessage());
        }

        assertNotNull(firstCategory);

        assertEquals(firstCategory.getVideoDataList().size(), EXPECTED_NUMBER_RESULTS);

        assertTrue(firstCategory.getVideoDataList().get(0).getTitle().equals(FIRST_VIDEO_TITLE));
    }
}
