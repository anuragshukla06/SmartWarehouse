package com.example.smartwarehouse;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {


    String BASE_URL = "http://192.168.43.235:8000/search/";
    String numberStr;
    ListView resultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String area = intent.getStringExtra("area");
        String fruit = intent.getStringExtra("fruit");
        numberStr = intent.getStringExtra("numberStr");
        resultListView = findViewById(R.id.resultListView);

        Query queryFetch = new Query();
        queryFetch.execute(BASE_URL + area + "/" + fruit + "/" + Integer.parseInt(numberStr));


    }

    class Query extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            StringBuilder response = new StringBuilder();

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader  = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String data = bufferedReader.readLine();

                while (data != null) {
                    response.append(data);
                    data = bufferedReader.readLine();
                }

                return response.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<WarehouseEntry> warehouseEntries = new ArrayList<WarehouseEntry>();
            try {
                JSONArray jsonArray = new JSONArray(s);
                // TODO: Handle empty array case
                if(jsonArray.length() != 0){
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        warehouseEntries.add(new WarehouseEntry(
                                jsonObject.getString("name"),
                                jsonObject.getString("address"),
                                jsonObject.getString("area"),
                                jsonObject.getInt("pricePerHour")
                        ));
                    }
                }

                WarehouseListAdapter adapter = new WarehouseListAdapter(getApplicationContext(), warehouseEntries, Integer.parseInt(numberStr));
                resultListView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
