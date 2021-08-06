package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.myapplication.Database.databaseInterface;
import com.example.myapplication.Model.favDoge;

import java.util.List;

public class favDogeDisplay extends AppCompatActivity {

    private createDatabase createDb;
    private databaseInterface dao;
    RecyclerView recyclerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_fav);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        context = getApplicationContext();
        favDisplay();
    }

    public void favDisplay()
    {
        //favDoge myDataList=new favDoge(imageId, imageUrl);
        createDb = createDatabase.getInstance(context);
        dao = createDb.Dao();
        List<favDoge> favDogeList =  dao.getFavDoge();
        recyclerView.setAdapter(new adapterFavDoge(favDogeList, R.layout.fave_doge, getApplicationContext()));
    }
}