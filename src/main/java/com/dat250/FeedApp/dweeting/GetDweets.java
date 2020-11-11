package com.dat250.FeedApp.dweeting;

import com.dat250.FeedApp.repository.PollRepository;
import com.dat250.FeedApp.service.PollService;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/***
 * This should be a class for collecting dweets from a raspberry pi
 * https://www.journaldev.com/7148/java-httpurlconnection-example-java-http-request-get-post
 * TODO this is definately going to need some refactoring, if we end up using it at all
 */
@Component
public class GetDweets {

    private static final String USER_AGENT = "Mozilla/5.0";
    private final PollRepository pollRepository;

    private DweetLog dweetLog;

    public GetDweets(PollRepository pollRepository) {
        dweetLog = new DweetLog();
        this.pollRepository = pollRepository;
    }


    @Scheduled(fixedDelay = 5000)
    public DweetLog checkForNewDweets() throws IOException {
        final String GET_DWEETS = "https://dweet.io:443/get/dweets/for/";

        // TODO read unit names from a list?
        ArrayList<String> unitList = new ArrayList<>();
        unitList.add("gunnars_raspberry_pi");
        unitList.add("oyvinds1_raspberry_pi");
        unitList.add("oyvinds2_raspberry_pi");

        ArrayList<Dweet> allDweets = new ArrayList<>();

        for (String unitName : unitList) {
            String response = sendGetRequest(GET_DWEETS, unitName);
            allDweets.addAll(convertToDweets(response));
        }

        // Now check if we have found these dweets before
        allDweets.removeIf(dweet -> dweetLog.isInLog(dweet));

        dweetLog.addToLog(allDweets);

        executeCommands(dweetLog.getNewEntries());


        return dweetLog;
    }

    /**
     * Send a Get request to get the latest dweets from the raspberry pi
     */
    private String sendGetRequest(String url, String unitname) throws IOException, JsonSyntaxException {
        URL obj = new URL(url + unitname);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = connection.getResponseCode();
        // System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();

        }
        else {
            System.out.println("GET request error :: " + responseCode);
            return "";
        }


        // TODO Do we want to save all the dweets to some kind of log?
    }

    private ArrayList<Dweet> convertToDweets(String response) {
        // Output it as Json
        Gson gson = new Gson();
        JsonObject convertedObject = gson.fromJson(response, JsonObject.class);
//        System.out.println(convertedObject);
        if (convertedObject.get("this").toString().equals("\"succeeded\"")) {
            JsonArray jsonArray = convertedObject.getAsJsonArray("with");

            ArrayList<Dweet> dweetList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject elem = (JsonObject) jsonArray.get(i);
                Dweet dweet = gson.fromJson(elem, Dweet.class);
                dweetList.add(dweet);
            }

            return dweetList;
        }
        else {
            // If it failed, return an empty ArrayList
            return new ArrayList<Dweet>();
        }



    }

    private void executeCommands(ArrayList<Dweet> dweets) {

        for (Dweet dweet : dweets) {
            String command = dweet.getCommand();

            // System.out.println(command);
        }

    }
}
