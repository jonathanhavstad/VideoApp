package com.example.videoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.videoapp.models.data.VideoData;
import com.example.videoapp.models.data.VideoSource;
import com.example.videoapp.presenters.AppPresenter;
import com.example.videoapp.views.activities.VideoDetailsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.videoapp.models.utils.TimeUtils.formatTimeFromSeconds;

/**
 * Created by jonathanhavstad on 4/14/17.
 */

@RunWith(AndroidJUnit4.class)
public class MockDataDetailTest {
    private VideoSource videoSource;
    private VideoData videoData;

    @Rule
    public ActivityTestRule<VideoDetailsActivity> videoDetailsActivityActivityTestRule = new ActivityTestRule<VideoDetailsActivity>(
            VideoDetailsActivity.class,
            true,
            false);

    private static final String MOCK_VIDEO_TITLE = "Designing For Google Cast";
    private static final String MOCK_VIDEO_DURATION = "333";
    private static final String MOCK_VIDEO_480_270 = "480x270/DesigningForGoogleCast2-480x270.jpg";
    private static final String MOCK_VIDEO_780_1200 = "780x1200/DesigningForGoogleCast-887x1200.jpg";
    private static final String MOCK_VIDEO_STUDIO = "Google IO - 2014";
    private static final String MOCK_VIDEO_SUBTITLE = "Fusce id nisi turpis. Praesent viverra bibendum semper. Donec tristique, orci sed semper lacinia, quam erat rhoncus massa, non congue tellus est quis tellus. Sed mollis orci venenatis quam scelerisque accumsan. Curabitur a massa sit amet mi accumsan mollis sed et magna. Vivamus sed aliquam risus. Nulla eget dolor in elit facilisis mattis. Ut aliquet luctus lacus. Phasellus nec commodo erat. Praesent tempus id lectus ac scelerisque. Maecenas pretium cursus lectus id volutpat.";
    private static final String MOCK_VIDEO_SOURCE_URL = "2017/04/JIBON%20NIYE%20KHELA/JIBON%20NIYE%20KHELA_master.m3u8";
    private static final String MOCK_IMAGES_URL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/images/";
    private static final String MOCK_DASH_URL = "http://vsvf.viewlift.com/Renditions/";

    @Before
    public void setupMockData() {
        videoSource = VideoSource.CREATOR.createFromParcel(Parcel.obtain());
        videoSource.setUrl(MOCK_VIDEO_SOURCE_URL);

        videoData = VideoData.CREATOR.createFromParcel(Parcel.obtain());
        videoData.setSubtitle(MOCK_VIDEO_SUBTITLE);
        videoData.setSources(Arrays.asList(videoSource));
        videoData.setImage_480_270(MOCK_VIDEO_480_270);
        videoData.setImage_780_1200(MOCK_VIDEO_780_1200);
        videoData.setTitle(MOCK_VIDEO_TITLE);
        videoData.setStudio(MOCK_VIDEO_STUDIO);
        videoData.setDuration(MOCK_VIDEO_DURATION);

        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent videoDetailsIntent = new Intent();
        videoDetailsIntent.putExtra(appContext.getString(R.string.images_url_key), MOCK_IMAGES_URL);
        videoDetailsIntent.putExtra(appContext.getString(R.string.dash_url_key), MOCK_DASH_URL);
        videoDetailsIntent.putExtra(appContext.getString(R.string.video_data_key), videoData);
        videoDetailsIntent.putExtra(appContext.getString(R.string.player_type_key), AppPresenter.PLAYER_TYPE.EXO_PLAYER.toString());
        videoDetailsActivityActivityTestRule.launchActivity(videoDetailsIntent);
    }

    @Test
    public void testVideoDetailDuration() {
        onView(withId(R.id.video_duration)).check(matches(withText(formatTimeFromSeconds(Long.valueOf(MOCK_VIDEO_DURATION)))));
    }

    @Test
    public void testVideoDetailStudio() {
        onView(withId(R.id.video_studio)).check(matches(withText(MOCK_VIDEO_STUDIO)));
    }

    @Test
    public void testVideoDetailSubtitle() {
        onView(withId(R.id.video_subtitle)).check(matches(withText(MOCK_VIDEO_SUBTITLE)));
    }
}
