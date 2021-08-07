package com.example.myapplication;
import com.example.myapplication.API.dogeAPI;
import com.example.myapplication.scrollListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
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
    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1000, limit=5;
    private int currentPage = PAGE_START;
    private dogeService dogeservice;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private adapterDoge adapterDoge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapterDoge = new adapterDoge(R.layout.list_doge, this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_doge);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dogeservice = dogeAPI.getClient().create(dogeService.class);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterDoge);

        recyclerView.addOnScrollListener(new scrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                Log.d("chumo: Page number", String.valueOf(currentPage));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        btnClick();
        loadFirstPage();
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

    private void loadFirstPage()
    {
        Call<List<Result>> call = dogeservice.getDoge(apiKey, currentPage, limit);

        call.enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response <List<Result>> response) {
                List<Result> dogeList = response.body();
                //recyclerView.setAdapter(new adapterDoge(dogeList, R.layout.list_doge, getApplicationContext()));
                adapterDoge.addAll(dogeList);
                if (currentPage <= TOTAL_PAGES) {
                adapterDoge.addLoadingFooter(); 
                }
                else
                    {isLastPage = true; }
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
    }

    private void loadNextPage()
    {
        Call<List<Result>> call = dogeservice.getDoge(apiKey, currentPage, limit);

        call.enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response <List<Result>> response) {

                adapterDoge.removeLoadingFooter();
                isLoading = false;
                List<Result> dogeList = response.body();

                adapterDoge.addAll(dogeList);
                if (currentPage != TOTAL_PAGES)
                {    adapterDoge.addLoadingFooter(); }
                else
                {    isLastPage = true;  }
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
    }
}