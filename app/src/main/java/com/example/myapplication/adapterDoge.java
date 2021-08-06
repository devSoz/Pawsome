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

public class adapterDoge extends RecyclerView.Adapter<adapterDoge.DogeViewHolder> {

    private List<Result> dogeList;
    private Integer rowLayout;
    Context context;
    private createDatabase createDb;
    private databaseInterface dao;

    public adapterDoge(List<Result> dogeList, Integer rowLayout, Context context)
    {
        this.dogeList = dogeList;
        this.rowLayout = rowLayout;
        this.context = context;
    }
    public static class DogeViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout layoutDoge;
        TextView id;
        TextView name;
        TextView breedGroup;
        TextView origin;
        ImageView imageView;
        ImageButton ibtnFavDoge;

        public DogeViewHolder(View view)
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            layoutDoge = (LinearLayout) view.findViewById(R.id.layoutDoge);
            id = (TextView) view.findViewById(R.id.Id);
            name = (TextView) view.findViewById(R.id.Name);
            breedGroup = (TextView) view.findViewById(R.id.BreedGroup);
            origin = (TextView) view.findViewById(R.id.Origin);
            ibtnFavDoge = (ImageButton) view.findViewById(R.id.btnFavDoge);
        }
    }

    @Override
    public adapterDoge.DogeViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new DogeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(DogeViewHolder holder, final int position) {

        Picasso.get()
                .load(dogeList.get(position).getImage().getUrl())
                .placeholder(R.color.white)
                .into(holder.imageView);

        holder.id.setText(String.valueOf(dogeList.get(position).getId()));
        holder.name.setText(dogeList.get(position).getName());
        holder.breedGroup.setText(dogeList.get(position).getBreedGroup());
        holder.origin.setText(dogeList.get(position).getOrigin());

        holder.ibtnFavDoge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Integer pos = holder.getAdapterPosition();
                String imageUrl = dogeList.get(pos).getImage().getUrl();
                String imageId = dogeList.get(pos).getImage().getId();
                insertData(imageId, imageUrl);
            }
        });
    }

    public void insertData(String imageId, String imageUrl)
    {
        favDoge myDataList=new favDoge(imageId, imageUrl);
        createDb = createDatabase.getInstance(context);
        dao = createDb.Dao();
        dao.insert(myDataList);

        Toast.makeText(context,"Data Save",Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        return dogeList.size();
    }
}
