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
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.videoapp", appContext.getPackageName());
    }
}
