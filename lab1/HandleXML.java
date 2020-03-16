package com.example.weatherapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

class HandleXML {
    private static final double ABSOLUTE_ZERO = -273.15;

    private String temperature = "temperature";
    private String clouds = "clouds";
    private String precipitation = "precipitation";
    private String speed = "speed";
    private String direction = "direction";
    private String weather = "weather";
    private String city = "city";
    private String country = "country";

    private final String TEMPERATURE = temperature;
    private final String CLOUDS = clouds;
    private final String PRECIPITATION = precipitation;
    private final String SPEED = speed;
    private final String DIRECTION = direction;
    private final String WEATHER_ICON = weather;
    private final String CITY = city;
    private final String COUNTRY = country;

    private String urlString = null;
    private String imageUrlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    public enum Flags {
        TEMPERATURE, CLOUDS, PRECIPITATION, SPEED, DIRECTION, WEATHER_ICON, CITY, COUNTRY;

        public static final EnumSet<Flags> ALL_OPTS = EnumSet.allOf(Flags.class);
        public static final EnumSet<Flags> USED_OPTS = EnumSet.noneOf(Flags.class);
    }

    public HandleXML(String url) {
        this.urlString = url;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getClouds() {
        return clouds;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public String getSpeed() {
        return speed;
    }

    public String getDirection() {
        return direction;
    }

    public String getWeather() {
        return weather;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }


    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String name = "";
        String text = "";
        DecimalFormat decimalFormatZero = new DecimalFormat("0");
        DecimalFormat decimalFormatOne = new DecimalFormat("0.0");
        DecimalFormat decimalFormatTwo = new DecimalFormat("0.00");
        String[] attributes = new String[]{
                TEMPERATURE, CLOUDS, PRECIPITATION, SPEED,
                DIRECTION, WEATHER_ICON, CITY, COUNTRY};
        List<String> attributesList = Arrays.asList(attributes);

        try {
            event = myParser.getEventType();
            while ((event != XmlPullParser.END_DOCUMENT) && !(Flags.USED_OPTS.containsAll(Flags.ALL_OPTS))) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        name = myParser.getName();
                        if (!attributesList.contains(name)) {
                            event = myParser.next();
                            continue;
                        } else {
                            if (name.equals(TEMPERATURE)) {
                                double kelvin = Double.parseDouble(myParser.getAttributeValue(null, "value"));
                                double celcius = kelvin + ABSOLUTE_ZERO;
                                temperature = "Temperature: " + decimalFormatOne.format(celcius) + " °C";
                                Flags.USED_OPTS.add(Flags.TEMPERATURE);
                            } else if (name.equals(CLOUDS)) {
                                clouds = "Clouds: " + myParser.getAttributeValue(null, "name");
                                Flags.USED_OPTS.add(Flags.CLOUDS);
                            } else if (name.equals(PRECIPITATION)) {
                                precipitation = "Precipitation: " + myParser.getAttributeValue(null, "mode");
                                Flags.USED_OPTS.add(Flags.PRECIPITATION);
                            } else if (name.equals(SPEED)) {
                                double windSpeedValue = Double.parseDouble(myParser.getAttributeValue(null, "value"));
                                speed = "Wind speed: " + decimalFormatZero.format(windSpeedValue) + " m/s";
                                Flags.USED_OPTS.add(Flags.SPEED);
                            } else if (name.equals(DIRECTION)) {
                                direction = "Wind direction: " + myParser.getAttributeValue(null, "name");
                                Flags.USED_OPTS.add(Flags.DIRECTION);
                            } else if (name.equals(WEATHER_ICON)) {
                                weather = myParser.getAttributeValue(null, "icon");
                                Flags.USED_OPTS.add(Flags.WEATHER_ICON);
                            } else if (name.equals(CITY)) {
                                city = myParser.getAttributeValue(null, "name");
                                Flags.USED_OPTS.add(Flags.CITY);
                            }
                            break;
                        }
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        if (name.equals(COUNTRY)) {
                            country = text;
                            Flags.USED_OPTS.add(Flags.COUNTRY);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = myParser.getName();
                        break;
                }
                event = myParser.next();

            }
            parsingComplete = false;
            Flags.USED_OPTS.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                    connect.setReadTimeout(10000);
                    connect.setConnectTimeout(15000);
                    connect.setRequestMethod("GET");
                    connect.setDoInput(true);
                    connect.setDoOutput(true);
                    connect.connect();

                    InputStream stream = connect.getInputStream();
                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = xmlFactoryObject.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(stream, null);
                    parseXMLAndStoreIt(parser);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}


