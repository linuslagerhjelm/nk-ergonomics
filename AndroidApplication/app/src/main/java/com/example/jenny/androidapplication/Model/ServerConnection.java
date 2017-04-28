package com.example.jenny.androidapplication.Model;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.annotations.*;


/**
 * Created by Jenny on 2017-04-06.
 */

public class ServerConnection {
    private String userCode = "";
    private User user;
    private Office office;
    private boolean doUserExcist;
    public ServerConnection(String code){
        this.userCode=code;
        try{
            JSONArray userData = getUser(this.userCode);
            if(userData.length()==0){
                doUserExcist=false;
            }
            else{
                JSONObject jObject=userData.getJSONObject(0);
                int id = jObject.getInt("id");
                String firstName = jObject.getString("firstName");
                String lastName = jObject.getString("lastName");
                this.office = Office.valueOf(jObject.getString("office"));
                this.user = new User(id,firstName,lastName,office);
                doUserExcist=true;
            }
        }
        catch(Exception e){
            e.getMessage();
        }

    }
    public ServerConnection(){
        System.out.println("A user can not be found. Please enter code");
    }

    public boolean controlIfUserExcist(){
        return doUserExcist;
    }

    public JSONArray getUser(String userCode) throws Exception{
        URL content = new URL("http://localhost:4567/api/getUsers?id=" + userCode);
        URLConnection mURLConnection = content.openConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(mURLConnection.getInputStream(), "utf-8"));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        JSONArray jArray = new JSONArray(out.toString());
        reader.close();
        return jArray;
    }

    public int postScores(List<Score> highScore) throws Exception{

        String score = new Gson().toJson(highScore);
        //JSONArray score = new JSONArray(highScore);
        //System.out.println(score.toString());
        byte[] postData       = score.getBytes();
        URL content = new URL("http://localhost:4567/api/postScores");
        HttpURLConnection connection = (HttpURLConnection)content.openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream( connection.getOutputStream());
        wr.write( postData);

        int statusCode = connection.getResponseCode();
        return statusCode;

    }
}

