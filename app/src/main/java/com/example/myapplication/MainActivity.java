package com.example.myapplication;
import com.example.myapplication.API.dogeAPI;
import com.example.myapplication.scrollListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Point;
import android.os.Handler;
import com.example.myapplication.Model.*;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.transition.Fade;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.myapplication.API.dogeService;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private final static String apiKey = "0bee7108-b9af-414c-8a97-2f803b14ca45";
    private final static String TAG = "Ok";
    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1000, limit=5;
    private int currentPage = PAGE_START, flagIsSearch=0;
    private dogeService dogeservice;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private adapterDoge adapterDoge;
    private static String searchQuery;
    private PopupWindow popUp;

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
                }, 100);
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
        popup();
        ImageButton btnUpload = (ImageButton) findViewById(R.id.uploadButton);
        ImageButton btnFav = (ImageButton) findViewById(R.id.favButton);
        SearchView searchView = (SearchView) findViewById(R.id.searchView);

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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                adapterDoge.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery = newText;
                adapterDoge.getFilter().filter(newText);
                return false;
            }

        });
    }

    private void loadFirstPage()
    {
      //  adapterDoge.clear();
        Call<List<Result>> call;

        call = dogeservice.getDoge(apiKey, currentPage, limit);
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
        Call<List<Result>> call;
        call = dogeservice.getDoge(apiKey, currentPage, limit);
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

    //for help
    public void popup()
    {
       ConstraintLayout rel = (ConstraintLayout) findViewById(R.id.custlayout) ;
        ImageButton but = (ImageButton) findViewById(R.id.helpButton);
        but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Display display = getWindowManager().getDefaultDisplay();

                // Load the resolution into a Point object
                Point size = new Point();

                display.getSize(size);
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View custview = inflater.inflate(R.layout.popup, null);
                popUp = new PopupWindow(custview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView tv = custview.findViewById(R.id.tv);
                tv.setText(Html.fromHtml("<br><br><br><br><u><b>Rules:</b></u><br><br>" +
                        "You will get one point for stepping into every non-mine tile.<br><br>" +
                        "And will be losing the game when stepped into the mine.<br><br>"  +
                        "In level 3, the number of mines in the neighbouring tiles are revealed on the tile you choose, which can be used to track the mines and play the game.<br><br>" +
                        "<u><b>Number of Mines:</b></u><br>" +
                        "<ol>" +
                        "<li>&nbsp;Level 1: 10 mines</li>" +
                        "<li>&nbsp;Level 2: 13 mines</li>" +
                        "<li>&nbsp;Level 3: 3, 6, 9, 12, 15 mines as per difficulty.</li><br>" ));



                ImageButton btnclose =  custview.findViewById(R.id.btnclose);
                btnclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUp.dismiss();
                    }
                });
                popUp.showAtLocation(rel, Gravity.NO_GRAVITY, 10, 10);
                popUp.update(50, 250, size.x-100, size.y-100);
            }});
    }
}