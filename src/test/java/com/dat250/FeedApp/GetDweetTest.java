package com.dat250.FeedApp;

import com.dat250.FeedApp.dweeting.Dweet;
import com.dat250.FeedApp.dweeting.DweetLog;
import com.dat250.FeedApp.dweeting.GetDweets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class GetDweetTest {

    private final String NAME = "gunnars_raspberry_pi";
    private final String USER_AGENT = "Mozilla/5.0";
    private final String GET_LATEST_URL = "https://dweet.io/get/latest/dweet/for/";
    private final String GET_DWEETS = "https://dweet.io:443/get/dweets/for/";


//    @Test
//    void sendGetRequestTest() throws IOException {
//        GetDweets getDweets = new GetDweets();
//        DweetLog dweetLog = getDweets.checkForNewDweets();
//
//        System.out.println("Printing new entries");
//        for (Dweet dweet : dweetLog.getNewEntries()) {
//            System.out.println(dweet.getCommand());
//        }

//    }
}
