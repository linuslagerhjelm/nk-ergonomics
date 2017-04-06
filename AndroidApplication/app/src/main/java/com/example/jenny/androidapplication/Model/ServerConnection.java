package com.example.jenny.androidapplication.Model;

/* LocalHost:4567; */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Jenny on 2017-04-06.
 */

public class ServerConnection {
        public static void main(String[] args) throws Exception {
            URL content = new URL("localhost:4567");
            URLConnection mURLConnection = content.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(mURLConnection.getInputStream()));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            System.out.println(out.toString()); //Prints the string content read from input stream
            reader.close();
        }
}

