package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Model.*;
import com.squareup.picasso.Picasso;

import java.util.List;

public class analysis_adapter extends RecyclerView.Adapter<analysis_adapter.DogeViewHolder>
{
    private List<AnalysisDoge> dogeList;
    private Integer rowLayout;
    Context context;

    public analysis_adapter(AnalysisDoge analysisDoge, Integer rowLayout, Context context)
    {
        this.dogeList = dogeList;
        this.rowLayout = rowLayout;
        this.context = context;
    }
    public static class DogeViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout analysisLayout;
        TextView status;
        TextView analysisName;
        TextView vendor;
        TextView imageId;

        public DogeViewHolder(View view)
        {
            super(view);
            analysisLayout = (LinearLayout) view.findViewById(R.id.layoutDoge);
            id = (TextView) view.findViewById(R.id.Id);
            name = (TextView) view.findViewById(R.id.Name);
            breedGroup = (TextView) view.findViewById(R.id.BreedGroup);
            origin = (TextView) view.findViewById(R.id.Origin);
        }
    }

    @Override
    public adapterDoge.DogeViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new adapterDoge.DogeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(adapterDoge.DogeViewHolder holder, final int position) {

        Picasso.get()
                .load(dogeList.get(position).getImage().getUrl())
                .placeholder(R.color.white)
                .into(holder.imageView);
        holder.id.setText(String.valueOf(dogeList.get(position).getId()));
        holder.name.setText(dogeList.get(position).getName());
        holder.breedGroup.setText(dogeList.get(position).getBreedGroup());
        holder.origin.setText(dogeList.get(position).getOrigin());
    }

    @Override
    public int getItemCount() {
        return dogeList.size();
    }
}