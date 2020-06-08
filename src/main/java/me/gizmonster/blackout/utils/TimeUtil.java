package me.gizmonster.blackout.utils;

import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static String toHMS(long millis) {
        String hms = String.format("%02d Hours, %02d Minutes, and %02d Seconds", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        System.out.println(hms);
        return hms;
    }
}

