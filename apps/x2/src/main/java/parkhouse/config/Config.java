package parkhouse.config;

public class Config {

    /*
    Author: TEAM
     */

    private Config() {}

    public static final int SIMULATION_SPEED = 100;
    public static final int TIME_SHIFT = 0;
    public static final int WAIT_REDLIGHT_SHIFT = 10;

    protected static final String[] CLIENT_CATEGORIES = new String[] {"Default", "Women", "Business", "Family", "Disability"};
    protected static final String[] VEHICLE_TYPES = new String[] {"PKW", "SUV", "QUAD", "TRIKE", "PICKUP"};
    protected static final String[] PRICE_FACTOR = new String[] {
            "PICKUP:2.1",
            "SUV:2",
            "QUAD:0.8",
            "TRIKE:0.9",
            "Family:0.5",
            "Business:0.4",
            "Family.SUV:1.2",
            "Family.PICKUP:1.3",
            "Business.SUV:1.1",
            "Business.PICKUP:1.2"
    };

    public static final int DEFAULT_MAX_CARS = 16;
    public static final int DEFAULT_OPEN_FROM = 0;
    public static final int DEFAULT_OPEN_TO = 0;

    public static String[] clientCategories() {
        return CLIENT_CATEGORIES;
    }

    public static String[] vehicleTypes() {
        return VEHICLE_TYPES;
    }

    public static String[] priceFactor() {
        return PRICE_FACTOR;
    }

}
