package com.dat250.FeedApp.dweeting;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;

/***
 * This should be a class for collecting dweets from a raspberry pi
 * https://www.journaldev.com/7148/java-httpurlconnection-example-java-http-request-get-post
 * TODO this is definately going to need some refactoring, if we end up using it at all
 */
public class GetDweetTest {

    // private static final String GET_URL = "https://dweet.io/dweet/for/";
    private static final String NAME = "gunnars_raspberry_pi";
    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws IOException {
        final String GET_LATEST_URL = "https://dweet.io/get/latest/dweet/for/";
        final String GET_DWEETS = "https://dweet.io:443/get/dweets/for/";

        String response = sendGetRequest(GET_LATEST_URL);
        ArrayList<Dweet> allDweets = convertToDweets(response);
        //sendGetRequest(GET_DWEETS);
    }

    /**
     * Send a Get request to get the latest dweets from the raspberry pi
     */
    private static String sendGetRequest(String url) throws IOException, JsonSyntaxException {
        URL obj = new URL(url + NAME);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = connection.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());

            return response.toString();

        }
        else {
            System.out.println("GET request error :: " + responseCode);
            return "";
        }


        // TODO Do we want to save all the dweets to some kind of log?
    }

    private static ArrayList<Dweet> convertToDweets(String response) {
        // Output it as Json
        Gson gson = new Gson();
        JsonObject convertedObject = gson.fromJson(response.toString(), JsonObject.class);
        System.out.println(convertedObject);
        JsonArray jsonArray = convertedObject.getAsJsonArray("with");

        ArrayList<Dweet> dweetList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject elem = (JsonObject) jsonArray.get(i);
            Dweet dweet = gson.fromJson(elem, Dweet.class);
            dweetList.add(dweet);
        }

        for (Dweet dweet : dweetList) {
            System.out.println(dweet);
        }

    }

}
