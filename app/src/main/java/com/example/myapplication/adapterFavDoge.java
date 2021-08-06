package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.databaseInterface;
import com.example.myapplication.Model.Result;
import com.example.myapplication.Model.favDoge;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterFavDoge extends RecyclerView.Adapter<adapterFavDoge.favDogeViewHolder> {

    private List<favDoge> faveDogeList;
    private Integer rowLayout;
    Context context;
    private createDatabase createDb;
    private databaseInterface dao;

    public adapterFavDoge(List<favDoge> faveDogeList, Integer rowLayout, Context context)
    {
        this.faveDogeList = faveDogeList;
        this.rowLayout = rowLayout;
        this.context = context;
    }
    public static class favDogeViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout layoutFavDoge;
        TextView id;
        TextView imageId;
        ImageView imageViewFav;

        public favDogeViewHolder(View view)
        {
            super(view);
            imageViewFav = (ImageView) view.findViewById(R.id.imageViewFav);
            layoutFavDoge = (LinearLayout) view.findViewById(R.id.layoutFavDoge);
            imageId = (TextView) view.findViewById(R.id.txtViewImageId);
        }
    }

    @Override
    public adapterFavDoge.favDogeViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new favDogeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(favDogeViewHolder holder, final int position) {

        Picasso.get()
                .load(faveDogeList.get(position).getUrl())
                .placeholder(R.color.white)
                .into(holder.imageViewFav);

        //holder.id.setText(String.valueOf(faveDogeList.get(position).getId()));
        holder.imageId.setText(faveDogeList.get(position).getImageId());

    }
        @Override
        public int getItemCount () {
            return faveDogeList.size();
        }
}
