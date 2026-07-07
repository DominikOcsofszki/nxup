package parkhouse.car;

import parkhouse.calculations.Price;
import parkhouse.config.Config;
import parkhouse.util.Time;

public class Car implements ICar {

    private String[] params;

    /*
    Author: jstueh2s
     */

    public Car(String[] params){
        this.params = params;
    }

    @Override
    public int nr() {
        return Integer.parseInt(params[0]);
    }

    @Override
    public int space() {
        return Integer.parseInt(params[6]);
    }

    @Override
    public long timer() {
        return Long.parseLong(params[1]);
    }

    @Override
    public long begin() {
        return Long.parseLong(params[10]);
    }

    @Override
    public long end() {
        return this.begin() + this.duration();
    }

    @Override
    public long duration() {
        if (params[2].equals("_")) {
            return Math.max(Config.SIMULATION_SPEED, 1) * (Time.now() - timer());
        }
        return Long.parseLong(params[2]);
    }

    @Override
    public long price() {
        if (params[3].equals("_")) {
            return Math.round(Price.priceFactor(this) * this.duration() / Math.max(Config.SIMULATION_SPEED, 1));
        }
        return Long.parseLong(params[3]);
    }

    @Override
    public String ticket() {
        return params[4];
    }

    @Override
    public String color() {
        return params[5];
    }

    @Override
    public String category() {
        return params[7];
    }

    @Override
    public String type() {
        return params[8];
    }

    @Override
    public String license() {
        return params[9];
    }

    @Override
    public boolean gone() {
        return !params[2].equals("_") || !params[3].equals("_");
    }

    @Override
    public void updateParams(String[] params) {
        this.params = params;
    }

    @Override
    public void setSpace(int s) { // add new spaceNr for car, after locator
        params[6] = s+"";
    }

    @Override
    public String toString(){
        if (gone()) {
            return String.format("%d/%d/%d/%d/%s/%s/%d/%s/%s/%s/%d",
                    nr(), timer(), duration(), price(), ticket(),
                    color(), space(), category(), type(), license(), begin());
        }
        return String.format("%d/%d/%s/%s/%s/%s/%d/%s/%s/%s/%d",
                nr(), timer(), "_", "_", ticket(), color(), space(),
                category(), type(), license(), begin());
    }

}
