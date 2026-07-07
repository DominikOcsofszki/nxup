<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <meta name="author" content="Team 6 (C) 2021">
    <meta name="description" content="Software Engineering 1 (SE1)">
    <meta name="license" content="The MIT License (MIT)">
    <meta name="theme-color" content="#239BD1"/>
    <meta property="og:title" content="Software Engineering 1 Project(SE1)">
    <meta property="og:description" content="Bachelor Course Software Engineering 1 (SE1), Hochschule Bonn-Rhein-Sieg.">
    <link rel="shortcut icon" href="https://kaul.inf.h-brs.de/favicon.ico" />
    <link rel="stylesheet" href="style.css">
    <title>Tomcat Park house</title>
    <script src="https://kaul.inf.h-brs.de/ccmjs/mkaul-components/parkhaus/versions/ccm.parkhaus-11.0.0.js"></script>
</head>
<body>
<div class="box center grey-background">
    <h1>Park house Team 6</h1>
    <p>Tomcat Version : <%= application.getServerInfo() %></p>
</div>
<div class="box grey">
    <h1>Park house</h1>
    <ccm-parkhaus-11-0-0 server_url="./main-servlet"
                         debug="false"
                         name="Park house"
                         license_min="1"
                         license_max="100"
                         open_from="0"
                         open_to="0"
                         delay="100"
                         simulation_speed="1000"
                         extra_buttons='["Undo","Reset","Sum","Avg","Min","Max","Time","Daily-Earnings","Weekly-Earnings","Earnings-Categories","Current-Cost"]'
                         extra_charts='["Types","Categories","Durations"]'
                         client_categories='["Default", "Women", "Business", "Family", "Disability"]'
                         vehicle_types='["PKW", "SUV", "QUAD", "TRIKE", "PICKUP"]'
                         price_factor='{"PICKUP":2.1,"SUV":2,"QUAD":0.8,"TRIKE":0.9,"Family":0.5,"Business":0.4,"Family.SUV":1.2,"Family.PICKUP":1.3,"Business.SUV":1.1}'
                         space_color='{"1":"black"}'
                         max="32"
                         SALT="456">
    </ccm-parkhaus-11-0-0>
</div>
</body>
</html>