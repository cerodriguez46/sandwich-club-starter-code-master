package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {





    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwichObject = new JSONObject(json);

            //name is a json object
            JSONObject nameObj = sandwichObject.getJSONObject("name");

            String primaryName = nameObj.optString("mainName");


            //place of origin, description, and image come directly from sandwichObject
            String origin = sandwichObject.optString("placeOfOrigin");

            String description = sandwichObject.optString("description");

            String img = sandwichObject.optString("image");

            //other names and ingredients are JSON Arrrays
            JSONArray otherNames = nameObj.optJSONArray("alsoKnownAs");
            List<String> otherNamesList = new ArrayList<>();

            for (int i = 0; i < otherNames.length(); i++) {
                String alsoKnownAs = otherNames.optString(i);
                otherNamesList.add(alsoKnownAs);

            }

            JSONArray ingredientsArray = sandwichObject.optJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++){
                String ingredients = ingredientsArray.optString(i);
                ingredientsList.add(ingredients.toString());
            }



            //Make sure JSON is being parsed correctly
            Log.v("name_tag", "Primary Name is: " + primaryName);
            Log.v("origin_tag", "Origin is: " + origin);
            Log.v("image_tag", "Image is: " + img);
            Log.v("description_tag", "Description is: " + description);
            Log.v("otherNames_tag", "It is also known as" + otherNamesList);
            Log.v("ingredient_tag", "Ingredients include" + ingredientsList);

            //sandwich object contains all the parsed variables
            Sandwich sandwich = new Sandwich(primaryName, otherNamesList, origin, description, img, ingredientsList);

return sandwich;


        } catch (JSONException e) {
            e.printStackTrace();

        }


        return null;


    }

}
