package com.example.jecihjoy.androidrestapp.utilities;

/**
 * Created by Jecihjoy on 5/21/2018.
 */


import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {
    public static String getData(String uri){
        BufferedReader reader = null;
        try{
            URL url = new URL(uri);
            //OkHttpClient httpClient = new OkHttpClient();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();//new OkUrlFactory(httpClient).open(uri);
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            return  sb.toString();
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return  null;
                }
            }
        }
    }

    //method overloading is sooo nice
    public static String getData(String uri, String username, String pass){
        BufferedReader reader = null;
        HttpURLConnection con = null;
        byte[] loginByte = (username + ":" + pass).getBytes();
        StringBuilder loginBuilder = new StringBuilder()
                .append("Basic ")
                .append(Base64.encodeToString(loginByte, Base64.DEFAULT));

        try{
            URL url = new URL(uri);
            con = (HttpURLConnection) url.openConnection();

            //request object for authorization
            con.addRequestProperty("Authorization", loginBuilder.toString());

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            return  sb.toString();
        }catch (Exception e){
            e.printStackTrace();
            try {
                int status = con.getResponseCode(); //PRINT THIS STATUS TO USER
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return  null;
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return  null;
                }
            }
        }
    }
}
