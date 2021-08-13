package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.myapplication.Database.databaseInterface;
import com.example.myapplication.Model.favDoge;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class favDogeDisplay extends AppCompatActivity {

    private createDatabase createDb;
    private databaseInterface dao;
    List<favDoge> favDogeList;
    RecyclerView recyclerView;
    adapterFavDoge adapterFavDog;
    Button btnback1;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_fav);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        context = getApplicationContext();

        favDisplay();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // this method is called
                // when the item is moved.
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                favDoge deletedDoge = favDogeList.get(viewHolder.getAdapterPosition());

                favDoge myDataList = new favDoge(deletedDoge.getImageId(), deletedDoge.getUrl());

                dao.deleteByImageId(deletedDoge.getImageId());

                int position = viewHolder.getAdapterPosition();

                adapterFavDog.notifyDel(viewHolder.getAdapterPosition(), deletedDoge.getImageId());

                /*Snackbar.make(recyclerView, "Removed from the favorites", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        favDogeList.add(position, deletedDoge);


                        adapterFavDog.notifyItemInserted(position);
                    }
                }).show();*/
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(recyclerView);

    }

    public void onBackPressed(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void favDisplay()
    {
        //favDoge myDataList=new favDoge(imageId, imageUrl);
        createDb = createDatabase.getInstance(context);
        dao = createDb.Dao();
        favDogeList =  dao.getFavDoge();
        adapterFavDog = new adapterFavDoge(favDogeList, R.layout.fave_doge, context);
        //recyclerView.setAdapter(new adapterFavDoge(favDogeList, R.layout.fave_doge, this));
        recyclerView.setAdapter(adapterFavDog);
    }


}