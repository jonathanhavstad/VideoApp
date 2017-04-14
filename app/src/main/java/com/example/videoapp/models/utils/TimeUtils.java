package com.example.videoapp.models.utils;

import org.threeten.bp.Duration;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by jonathanhavstad on 4/14/17.
 */

public class TimeUtils {
    private static final String TIME_FORMAT = "%1$02d:%2$02d:%3$02d";

    public static String formatTimeFromSeconds(long totalSeconds) {
        Duration duration = Duration.ofSeconds(totalSeconds);
        long hours = duration.toHours();
        long minutes = (duration.toMinutes()) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = (duration.getSeconds()) % TimeUnit.SECONDS.convert(1L, TimeUnit.MINUTES);
        return String.format(Locale.getDefault(), TIME_FORMAT, hours, minutes, seconds);
    }
}
