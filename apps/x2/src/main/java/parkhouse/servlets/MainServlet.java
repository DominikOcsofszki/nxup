package parkhouse.servlets;

import parkhouse.config.Config;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "MainServlet", value = "/main-servlet")
public class MainServlet extends ParkhouseServlet {

    /*
    Author: TEAM
     */

    @Override
    String name() {
        return "MainServlet";
    }

    @Override
    int max() {
        return config[0];
    }

    public String config() {
        if (saver().initConfig()) {
            config = saver().loadConfig();
        }
        return String.format(
                "%d,%d,%d,%d,%d,%d",
                config[0],
                config[1],
                config[2],
                Config.SIMULATION_SPEED,
                Config.WAIT_REDLIGHT_SHIFT,
                Config.TIME_SHIFT
        );
    }

}
