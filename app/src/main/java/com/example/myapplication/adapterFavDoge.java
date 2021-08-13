package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.databaseInterface;
import com.example.myapplication.Model.favDoge;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;


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
        ImageButton imgBtnSend;

        public favDogeViewHolder(View view)
        {
            super(view);
            imageViewFav = (ImageView) view.findViewById(R.id.imageViewFav);
            layoutFavDoge = (LinearLayout) view.findViewById(R.id.layoutFavDoge);
            imgBtnSend = (ImageButton) view.findViewById(R.id.imgBtnShare);
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


        holder.imgBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.imageViewFav.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                shareImageandText(bitmap);
            }
        });

    }
        @Override
        public int getItemCount () {
            return faveDogeList.size();
        }


    private void shareImageandText(Bitmap bitmap) {
        Uri uri = getmageToShare(bitmap);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image from Pawsome");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.setType("image/*");

        context.startActivity(Intent.createChooser(intent, "Share Via"));
    }

    private Uri getmageToShare(Bitmap bitmap) {
        File imagefolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "shared_image.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(context, "com.example.myapplication", file);
        } catch (Exception e) {

            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }

    public void notifyDel(int position, String url)
    {

        faveDogeList.remove(position);

        notifyItemRemoved(position);
    }
}
