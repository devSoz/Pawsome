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

    @Override
    protected void onResume() {
        super.onResume();
        //adapterDoge.notifyDataSetChanged();
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
                finish();
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
                tv.setText(Html.fromHtml("<br><br>" +
                        "Click on the top buttons to navigate to upload, favourites and help pages<br><br>" +
                        "<u><b>Main Screen:</b></u> <br> Click on the pictures to get full details<br>"  +
                        "You can search by name<br>" +
                        "Click on the heart symbol to add to favourites.<br><br>" +
                        "<u><b>Favourites section:</b></u> Click on the send button to send details and images of your favourite dogs to other platforms<br>" +
                                "Swipe to remove dogs from favourites<br><br>"+
                                "<u><b>Upload section:</b></u> Click on 'upload your image here' to upload image and get press upload to get the respective analysis<br><br>"));



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