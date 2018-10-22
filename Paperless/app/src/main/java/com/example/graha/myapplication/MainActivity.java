package com.example.graha.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult, searchTextView;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.text_view_result);
        searchTextView = findViewById(R.id.editText);
        Button buttonParse = findViewById(R.id.button_parse);


        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
                
            }
        });

    }

    private void jsonParse(){


        String url = "http://api.walmartlabs.com/v1/search?query=" + searchTextView.getText()+ "&format=json&apiKey=6vcmy7qjr95srak5ed8aj6bu";



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");

//                    for(int i =0; i<jsonArray.length(); i++){            //********Commented out for loop to only get first element searched
                        JSONObject item = jsonArray.getJSONObject(0);

                        String productName = item.getString("name") + "\n";
                        int price = item.getInt("salePrice");

                        mTextViewResult.append(productName + "\n" + " € " +String.valueOf(price) + "\n");

//                    }                 //for loop closing brace
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
