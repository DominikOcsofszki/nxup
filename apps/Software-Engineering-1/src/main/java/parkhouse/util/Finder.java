package parkhouse.util;

import parkhouse.car.ICar;

import java.util.function.Function;
import java.util.stream.StreamSupport;

public class Finder {

    /*
    Author: jstueh2s & docsof2s
     */

    private Finder() {}

    public static ICar findCar(Iterable<ICar> iter, Function<ICar, Object> loc, Object id) {
        return StreamSupport.stream(iter.spliterator(), false)
                .filter(c -> (loc.apply(c).equals(id)))
                .findFirst().orElseThrow();
    }

}
