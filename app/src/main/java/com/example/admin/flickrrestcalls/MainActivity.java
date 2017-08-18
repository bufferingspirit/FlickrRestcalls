package com.example.admin.flickrrestcalls;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.admin.flickrrestcalls.Data.Item;
import com.example.admin.flickrrestcalls.Data.Media;
import com.example.admin.flickrrestcalls.Data.Pictures;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne?tag=kitten&format=json&nojsoncallback=1";
    public static final String TAG = "MainActivity";

    final OkHttpClient client = new OkHttpClient();

    final Request request = new Request.Builder()
            .url(BASE_URL)
            .build();

    String resultResponse = "";

    ArrayList<Entry> entryList = new ArrayList<>();

    Pictures pictures;
    ArrayList<Item> itemList = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    EntryAdapter entryAdapter;

    boolean done_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rView);
        layoutManager = new LinearLayoutManager(this);
        itemAnimator = new DefaultItemAnimator();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);

        entryAdapter = new EntryAdapter(entryList);
        recyclerView.setAdapter(entryAdapter);


        client.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                resultResponse = response.body().string();
                Log.d(TAG, "onResponse: " + resultResponse);

                Gson gson = new Gson();
                pictures = gson.fromJson(resultResponse, Pictures.class);
                itemList.addAll(pictures.getItems());
                Log.d(TAG, "onResponse: " + itemList.size());
                done_flag = true;
            }
        });

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < itemList.size(); i++){
            Entry foo = new Entry(itemList.get(i).getAuthor(), itemList.get(i).getDateTaken(), itemList.get(i).getMedia().getM());
            entryList.add(foo);
        }

        entryAdapter.notifyDataSetChanged();

    }

}
