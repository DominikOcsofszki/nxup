package parkhouse.util;

import parkhouse.config.Config;

public class Time {

    private Time() {}

    public static final long INSTANCE_START_DATE = System.currentTimeMillis();
    public static final long MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000L;
    public static final long MILLISECONDS_PER_WEEK = MILLISECONDS_PER_DAY * 7;

    /*
    Author: tpapen2s
     */
    public static long diff(long a, long b) {
        return Math.abs(a - b);
    }

    /*
    Author: jstueh2s
     */
    public static long now() {
        return System.currentTimeMillis();
    }

    /*
    Author: jstueh2s
     */
    public static long simTime(long realTime) {
        return INSTANCE_START_DATE + (Config.SIMULATION_SPEED * (realTime - INSTANCE_START_DATE));
    }

    /*
    Author: jstueh2s
     */
    public static long simNow() {
        return simTime(System.currentTimeMillis());
    }
}