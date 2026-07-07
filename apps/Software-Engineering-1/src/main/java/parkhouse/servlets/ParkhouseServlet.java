package parkhouse.servlets;

import parkhouse.calculations.Locator;
import parkhouse.calculations.Price;
import parkhouse.calculations.Stats;
import parkhouse.car.Car;
import parkhouse.car.ICar;
import parkhouse.commands.CarEnterCommand;
import parkhouse.commands.CarLeaveCommand;
import parkhouse.config.Config;
import parkhouse.controller.IParkingController;
import parkhouse.controller.ParkingController;
import parkhouse.security.SanitizedCar;
import parkhouse.util.Finder;
import parkhouse.util.Saver;
import parkhouse.util.Time;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class ParkhouseServlet extends HttpServlet {

    abstract String name();

    abstract int max();

    abstract String config();

    private static final String RELOAD = "<img src='x' onerror=location.reload();>";
    private static final String PARKING_CONTROLLER = "parkingController";
    private static final String SAVER = "saver";

    private static final Logger LOGGER = Logger.getLogger(ParkhouseServlet.class.getName());

    protected int[] config = new int[] {Config.DEFAULT_MAX_CARS, Config.DEFAULT_OPEN_FROM, Config.DEFAULT_OPEN_TO};

    /**
     * HTTP GET
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String cmd = request.getParameter("cmd");
        LOGGER.info(cmd + "requested: " + request.getQueryString());

        switch (cmd) {
            /*
            Author: docsof2s
            */
            case "config":
                out.println(config());
                if (saver().init()) {
                    saver().loadCars(parkingController());
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        saver().saveConfig(config);
                        saver().saveCars(parkingController());
                    }));
                    out.println(RELOAD);
                }
                break;
            /*
            Author: jstueh2s
            */
            case "cars":
                out.print(
                        parkingController().getAllCars()
                                .stream().map(c -> c.toString() + "/" + name())
                                .collect(Collectors.joining(","))
                );
                break;
            /*
            Author: docsof2s
            */
            case "Sum":
                out.println(String.format(
                        "Total income: %s | (Live): %s",
                        Price.format(Stats.sumCars(parkingController().getRemovedCars())),
                        Price.format(Stats.sumCars(parkingController().getAllCars()))
                ));
                break;
            /*
            Author: docsof2s
            */
            case "Avg":
                out.println(String.format(
                        "Average income per customer: %s | (Live): %s",
                        Price.format(Stats.avgCars(parkingController().getRemovedCars())),
                        Price.format(Stats.avgCars(parkingController().getAllCars()))
                ));
                break;
            /*
            Author: jstueh2s
            */
            case "Min":
                out.println(String.format(
                        "Lowest income from a customer: %s | (Live): %s",
                        Price.format(Stats.minCars(parkingController().getRemovedCars())),
                        Price.format(Stats.minCars(parkingController().getAllCars()))
                ));
                break;
            /*
            Author: jstueh2s
            */
            case "Max":
                out.println(String.format(
                        "Highest income from a customer: %s | (Live): %s",
                        Price.format(Stats.maxCars(parkingController().getRemovedCars())),
                        Price.format(Stats.maxCars(parkingController().getAllCars()))
                ));
                break;
            /*
            Author: jstueh2s
            */
            case "Types":
                out.println(
                        parkingController().vehicleTypeView()
                );
                break;
            /*
            Author: jstueh2s
            */
            case "Categories":
                out.println(
                        parkingController().clientCategoriesView()
                );
                break;
            /*
            Author: jstueh2s
            */
            case "Durations":
                out.println(
                        parkingController().durationView()
                );
                break;
            /*
            Author: jstueh2s
            */
            case "Daily-Earnings":
                out.println(
                        parkingController().dailyEarningsView()
                );
                break;
            /*
            Author: jstueh2s
            */
            case "Weekly-Earnings":
                out.println(
                        parkingController().weeklyEarningsView()
                );
                break;
            /*
            Author: jstueh2s
            */
            case "Current-Cost":
                out.println(
                        parkingController().currentCostView()
                );
                break;
            /*
            Author: jstueh2s
            */
            case "Earnings-Categories":
                out.println(
                        parkingController().earningsByCategoriesView()
                );
                break;
            /*
            Author: staher2s
            */
            case "Time":
                out.println(String.format(
                        "Real time: %d / Sim time: %d / Diff: %d",
                        Time.now(), Time.simNow(),
                        Time.simNow() - Time.now())
                );
                break;
            /*
            Author: jstueh2s
            */
            case "Reset":
                getServletContext().setAttribute(PARKING_CONTROLLER + name(), null);
                out.println(RELOAD);
                break;
            /*
            Author: tpapen2s
            */
            case "Undo":
                parkingController().commander().undo();
                out.println(RELOAD);
                break;
            /*
            Author: tpapen2s
            */
            default:
                LOGGER.warning("Invalid Command: " + request.getQueryString());
        }
    }


    /**
     * HTTP POST
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String body = getBody(request);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        LOGGER.info(body);
        String[] params = body.split(",");
        String event = params[0];
        String[] restParams = Arrays.copyOfRange(params, 1, params.length);

        switch (event) {
            /*
            Author: jstueh2s & docsof2s
            */
            case "change_max":
                config[0] = Integer.parseInt(restParams[0]);
                break;
            /*
            Author: jstueh2s & docsof2s
            */
            case "change_open_from":
                config[1] = Integer.parseInt(restParams[0]);
                break;
            /*
            Author: jstueh2s & docsof2s
            */
            case "change_open_to":
                config[2] = Integer.parseInt(restParams[0]);
                break;
            /*
            Author: jstueh2s & docsof2s
            */
            case "enter":
                int space = Locator.locate(parkingController(), config[0]);
                if (space != -1) {
                    ICar car = new SanitizedCar(new Car(restParams));
                    car.setSpace(space);
                    CarEnterCommand cmd = new CarEnterCommand(car, parkingController());
                    parkingController().commander().queue(cmd);
                    parkingController().commander().activate();
                    out.println(space);
                }
                break;
            /*
            Author: jstueh2s & docsof2s
            */
            case "leave":
                ICar car = Finder.findCar(parkingController().getCars(), ICar::ticket, restParams[4]);
                car.updateParams(restParams);
                CarLeaveCommand cmd = new CarLeaveCommand(car, parkingController());
                parkingController().commander().queue(cmd);
                parkingController().commander().activate();
                out.println(restParams[3]);
                break;
            /*
            Author: tpapen2s
            */
            case "invalid":
            case "occupied":
                LOGGER.info(() -> "occupied: " + body);
                break;
            /*
            Author: mkaul2s
            */
            case "tomcat":
                out.println(getServletConfig().getServletContext().getServerInfo()
                        + getServletConfig().getServletContext().getMajorVersion()
                        + getServletConfig().getServletContext().getMinorVersion());
                break;
            /*
            Author: tpapen2s
            */
            default:
                LOGGER.info(() -> "Invalid Command: " + body);
        }

    }

    /*
    Author: mkaul2s
    */
    public ServletContext getContext() {
        return getServletConfig().getServletContext();
    }

    /*
    Author: docsof2s
    */
    public IParkingController parkingController() {
        if (getContext().getAttribute(PARKING_CONTROLLER + name()) == null) {
            getContext().setAttribute(PARKING_CONTROLLER + name(), new ParkingController());
        }
        return (IParkingController) getContext().getAttribute(PARKING_CONTROLLER + name());
    }

    /*
    Author: jstueh2s
    */
    public Saver saver() {
        if (getContext().getAttribute(SAVER + name()) == null) {
            getContext().setAttribute(SAVER + name(), new Saver(name()));
        }
        return (Saver) getContext().getAttribute(SAVER + name());
    }

    /*
    Author: mkaul2s
    */
    public String getBody(HttpServletRequest request) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try (InputStream inputStream = request.getInputStream()) {
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return stringBuilder.toString();
    }

    /*
    Author: tpapen2s
    */
    @Override
    public void destroy() {
        LOGGER.info("Servlet destroyed");
    }
}