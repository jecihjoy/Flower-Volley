package com.example.jecihjoy.androidrestapp.utilities;

import com.example.jecihjoy.androidrestapp.model.Flower;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jecihjoy on 5/22/2018.
 */

public class JsonParser {
    public static List<Flower> parseFeed(String content){

        try {
            JSONArray array = new JSONArray(content);
            List<Flower> flowerList = new ArrayList<>();

            //objects and arrays. json array does not implement the iterable interface
            //hence foreach cannot be used

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Flower flower = new Flower();
                flower.setProductId(object.getInt("productId"));
                flower.setName(object.getString("name"));
                flower.setCategory(object.getString("category"));
                flower.setInstructions(object.getString("instructions"));
                flower.setPrice(object.getDouble("price"));
                flower.setPhoto(object.getString("photo"));

                flowerList.add(flower);
            }
            return flowerList;
        }catch (JSONException e){
            e.printStackTrace();
            return  null;
        }

    }
}
