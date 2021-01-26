package com.kgcorner;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 03/04/20
 */

public class JsonProgram {

    public static void main(String[] args) {
        String jsonValue = "{\n" +
            "  \"id\": \"1661-3233-333-ppp\"\n" +
            "}";

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonValue);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String id = jsonObject.get("id").getAsString();
        System.out.println(id);

    }
}