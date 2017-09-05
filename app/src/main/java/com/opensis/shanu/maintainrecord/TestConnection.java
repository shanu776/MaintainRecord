package com.opensis.shanu.maintainrecord;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Shanu on 8/24/2017.
 */

public class TestConnection {
    public boolean isConnectedToServer() {
        try{
            URL myUrl = new URL("https://www.google.co.in");
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(2000);
            connection.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
