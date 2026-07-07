package parkhouse.calculations;

import parkhouse.car.ICar;
import parkhouse.controller.IParkingController;

import java.util.List;
import java.util.stream.Collectors;

public class Locator {

    /*
    Author: jstueh2s & docsof2s
     */

    private Locator() {}

    public static int locate(IParkingController controller, int max) {
        List<Integer> occupied = controller.getCars()
                .stream().mapToInt(ICar::space)
                .boxed().collect(Collectors.toList());

        for (int s = 1; s <= max; s++) {
            if (!occupied.contains(s)) {
                return s;
            }
        }
        return -1;
    }

}
