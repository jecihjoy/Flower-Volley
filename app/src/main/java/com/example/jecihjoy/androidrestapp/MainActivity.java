package com.example.jecihjoy.androidrestapp;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jecihjoy.androidrestapp.adapters.FlowerAdapter;
import com.example.jecihjoy.androidrestapp.model.Flower;
import com.example.jecihjoy.androidrestapp.utilities.HttpManager;
import com.example.jecihjoy.androidrestapp.utilities.JsonParser;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    ProgressBar mLoadProgress;
    FlowerAdapter adapter;
    List<Flower> flowerList;
    public static final String PHOTOS_BASE_URL = "http://services.hanselandpetal.com/photos/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadProgress = (ProgressBar)findViewById(R.id.progressBar);
        mLoadProgress.setVisibility(View.INVISIBLE);
        rv = (RecyclerView) findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action){
            if(isOnline()){
                requestData("http://services.hanselandpetal.com/feeds/flowers.json");
            }else {
                Toast.makeText(this,"Internet not available", Toast.LENGTH_SHORT).show();
            }

        }
        return true;
    }

    private void requestData(String uri) {
        StringRequest request = new StringRequest(uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        flowerList = JsonParser.parseFeed(response);
                        updateDisplay();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void updateDisplay(){
        adapter = new FlowerAdapter(MainActivity.this, flowerList);
        if(!(flowerList.size() < 1)){
            rv.setAdapter(adapter);
        }else {
            Toast.makeText(this, "flowers not available", Toast.LENGTH_SHORT).show();
        }
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
    }

    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ninfo = cm.getActiveNetworkInfo();
        if(ninfo != null && ninfo.isConnectedOrConnecting()){
            //checking for wifi
            /*if(ninfo.getType() != ConnectivityManager.TYPE_WIFI){
                Toast.makeText(this, "This app doesn't work without WIFI", Toast.LENGTH_LONG).show();
                return false;
            }*/
            return true;
        }else {
            return false;
        }

    }

}
