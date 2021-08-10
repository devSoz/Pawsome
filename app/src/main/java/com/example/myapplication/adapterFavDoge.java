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
              //  Intent shareIntent = new Intent();
               // shareIntent.setAction(Intent.ACTION_SEND);
               // Integer pos = holder.getAdapterPosition();
               // String urlstr = faveDogeList.get(pos).getUrl();


               // View content = holder.imageViewFav;

                BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.imageViewFav.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                shareImageandText(bitmap);

                //  shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               /* shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(url)));
                Uri photoURI = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", Uri.fromFile(new File(url)));
                shareIntent.setType("image/*");
                Intent shareActivity = new Intent();
                Intent intent = Intent.createChooser(shareIntent, "Complete With");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/
               // context.startActivity(Intent.createChooser(shareIntent, "Ok"));
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

        // putting uri of image to be shared
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        // adding text to share
        intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image from Pawsome");

        // Add subject Here
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");

        // setting type to image
        intent.setType("image/*");

        // calling startactivity() to share
        context.startActivity(Intent.createChooser(intent, "Share Via"));
    }

    // Retrieving the url to share
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
}
