package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView origin;
    TextView description;
    TextView ingredients;
    TextView otherNames;
    ImageView sandwichImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;



            sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

        //get methods from sandwich object to show results
        description.setText(sandwich.getDescription());
        origin.setText(sandwich.getPlaceOfOrigin());

        //Arrays required a new method to loop through and separate out the strings, otherwise it would return the results still in brackets
        ingredients.setText(loopThroughListForSeparateStrings(sandwich.getIngredients()));
        otherNames.setText(loopThroughListForSeparateStrings(sandwich.getAlsoKnownAs()));

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        origin = (TextView)findViewById(R.id.origin_tv);
        description = (TextView)findViewById(R.id.description_tv);
        ingredients = (TextView)findViewById(R.id.ingredients_tv);
        otherNames = (TextView)findViewById(R.id.also_known_tv);



    }

    //special method to return separate strings instead of the array as one whole string with brackets
    public String loopThroughListForSeparateStrings(List<String> string) {
       StringBuilder builder = new StringBuilder();

       for (String parsedString : string){
           builder.append("-" + parsedString + "\n");
       }
        return builder.toString();

    }



}
