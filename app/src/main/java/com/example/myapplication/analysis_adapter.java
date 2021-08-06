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
    private List<AnalysisDoge> analysisDoge;
    private Integer rowLayout;
    Context context;
    String strLabel=new String("");

    public analysis_adapter(List<AnalysisDoge> analysisDoge, Integer rowLayout, Context context)
    {
        this.analysisDoge = analysisDoge;
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
            analysisLayout = (LinearLayout) view.findViewById(R.id.layoutAnalysis);
            status = (TextView) view.findViewById(R.id.Status);
            analysisName = (TextView) view.findViewById(R.id.analysisName);
            vendor = (TextView) view.findViewById(R.id.Vendor);
            imageId = (TextView) view.findViewById(R.id.ImageId);
        }
    }

    @Override
    public analysis_adapter.DogeViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new analysis_adapter.DogeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(analysis_adapter.DogeViewHolder holder, final int position) {

        holder.status.setText("Hooray, it is a dog xD");
        strLabel   ="";
        for(int i=0;i<analysisDoge.get(position).getLabels().size();i++)
            strLabel += analysisDoge.get(position).getLabels().get(i).getName() + " ";
        holder.analysisName.setText(strLabel);
        holder.vendor.setText(analysisDoge.get(position).getVendor());
        holder.imageId.setText(analysisDoge.get(position).getImageId());
        //holder.origin.setText(analysisDoge.);
    }

    @Override
    public int getItemCount() {
        return analysisDoge.size();
    }
}