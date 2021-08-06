package com.example.myapplication;
import com.example.myapplication.API.dogeAPI;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.API.dogeService;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final static String apiKey = "0bee7108-b9af-414c-8a97-2f803b14ca45";
    private final static String TAG = "Ok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_doge);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dogeService dogeservice = dogeAPI.getClient().create(dogeService.class);
        Call<List<Result>> call = dogeservice.getDoge(apiKey);

        call.enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response <List<Result>> response) {
                List<Result> dogeList = response.body();
                recyclerView.setAdapter(new adapterDoge(dogeList, R.layout.list_doge, getApplicationContext()));
                Log.d(TAG, String.valueOf(dogeList.size()));
            }

            @Override
            public void onFailure(Call<List<Result>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                Log.d(TAG, String.valueOf(t));
            }
        });

        btnClick();
    }

    public void btnClick()
    {
        ImageButton btnUpload = (ImageButton) findViewById(R.id.uploadButton);
        ImageButton btnFav = (ImageButton) findViewById(R.id.favButton);
        ImageButton btnSearch = (ImageButton) findViewById(R.id.searchButton);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, upload.class);
                startActivity(intent);
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, favDogeDisplay.class);
                startActivity(intent);
            }
        });
    }
}