package parkhouse.car;

public abstract class CarDecorator implements ICar {

    private final ICar car;

    /*
    Author: jstueh2s
     */

    public CarDecorator(ICar car) {
        this.car = car;
    }

    @Override
    public int nr() {
        return car.nr();
    }

    @Override
    public int space() {
        return car.space();
    }

    @Override
    public long timer() {
        return car.timer();
    }

    @Override
    public long begin() {
        return car.begin();
    }

    @Override
    public long end() {
        return car.end();
    }

    @Override
    public long duration() {
        return car.duration();
    }

    @Override
    public long price() {
        return car.price();
    }

    @Override
    public String ticket() {
        return car.ticket();
    }

    @Override
    public String color() {
        return car.color();
    }

    @Override
    public String category() {
        return car.category();
    }

    @Override
    public String type() {
        return car.type();
    }

    @Override
    public String license() {
        return car.license();
    }

    @Override
    public boolean gone() {
        return car.gone();
    }

    @Override
    public void setSpace(int s) {
        car.setSpace(s);
    }

    @Override
    public void updateParams(String[] params) {
        car.updateParams(params);
    }

    @Override
    public String toString() {
        return car.toString();
    }
}
