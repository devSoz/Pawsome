package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Filterable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Database.databaseInterface;
import com.example.myapplication.Model.Result;
import com.example.myapplication.Model.favDoge;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class adapterDoge extends RecyclerView.Adapter<adapterDoge.DogeViewHolder> implements Filterable {

    private static List<Result> dogeList = new ArrayList<>();
    private List<Result> dogeListFull;
    private Integer rowLayout;
    Context context;
    private createDatabase createDb;
    private databaseInterface dao;
    private List<String> imageIdList = new ArrayList<>();
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private String filterPattern="";
    private List<favDoge> favDogeList;

    public adapterDoge(Integer rowLayout, Context context)
    {
        Log.d("testinside", String.valueOf(dogeList.size()));
        //this.dogeList = dogeList;
        this.rowLayout = rowLayout;
        this.context = context;
        dogeListFull = new ArrayList<>(dogeList);

        createDb = createDatabase.getInstance(context);
        dao = createDb.Dao();
        favDogeList =  dao.getFavDoge();

        for (favDoge i : favDogeList) {
            imageIdList.add(i.getImageId());
        }
    }
    public static class DogeViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout layoutDoge;
        TextView bredFor;
        TextView name, temperament, height, weight, lifeSpan;
        TextView breedGroup;
        TextView origin;
        ImageView imageView;
        ImageButton ibtnFavDoge, ibtnLikeDoge;
        CardView cardView;
        LinearLayout linearLayoutBack, linearLayoutFront ;

        public DogeViewHolder(View view)
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            layoutDoge = (LinearLayout) view.findViewById(R.id.layoutDoge);
            bredFor = (TextView) view.findViewById(R.id.BredFor);
            name = (TextView) view.findViewById(R.id.Name);
             breedGroup = (TextView) view.findViewById(R.id.BreedGroup);
            origin = (TextView) view.findViewById(R.id.Origin);
            ibtnFavDoge = (ImageButton) view.findViewById(R.id.btnFavDoge);
            cardView = (CardView) view.findViewById(R.id.cardViewDoge);
            lifeSpan = (TextView) view.findViewById(R.id.Lifespan);
            height = (TextView) view.findViewById(R.id.Height);
            weight = (TextView) view.findViewById(R.id.Weight);
            linearLayoutBack = (LinearLayout) view.findViewById(R.id.layoutBack);
            linearLayoutFront = (LinearLayout) view.findViewById(R.id.layoutFront);
            temperament = (TextView) view.findViewById(R.id.Temperament);
            ibtnLikeDoge = (ImageButton) view.findViewById(R.id.btnFavDoge);
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
        try {
            //position = holder.getAdapterPosition();
            Picasso.get()
                    .load(dogeList.get(position).getImage().getUrl())
                    .placeholder(R.color.white)
                    .into(holder.imageView);
            holder.name.setText(dogeList.get(position).getName());

            holder.breedGroup.setText("Breed group: "+ emptyifNull( dogeList.get(position).getBreedGroup()));
            holder.origin.setText("Origin: "+emptyifNull( dogeList.get(position).getOrigin()));;
            holder.temperament.setText("Temperament: "+emptyifNull( dogeList.get(position).getTemperament()));
            holder.lifeSpan.setText("Life Span: "+emptyifNull( dogeList.get(position).getLifeSpan()));
            holder.origin.setText("Origin: "+emptyifNull( dogeList.get(position).getOrigin()));
            holder.weight.setText("Weight:\n   Metric: "+dogeList.get(position).getWeight().getMetric()+"\n   Imperial: " +dogeList.get(position).getWeight().getImperial());
            holder.height.setText("Height:\n   Metric: "+dogeList.get(position).getHeight().getMetric()+"\n   Imperial: " +dogeList.get(position).getHeight().getImperial());
            holder.linearLayoutBack.setVisibility(View.GONE);
            holder.linearLayoutFront.setVisibility(View.VISIBLE);




            if(imageIdList.contains(dogeList.get(position).getImage().getId()))
            {
                holder.ibtnLikeDoge.setImageResource(R.drawable.like);
            }
            else
            {
                holder.ibtnLikeDoge.setImageResource(R.drawable.unlike);
            }

            holder.linearLayoutFront.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  /*  holder.linearLayoutFront.animate()
                            .translationY(holder.linearLayoutFront.getHeight())
                            .alpha(0.0f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    holder.linearLayoutFront.setVisibility(View.GONE);
                                    holder.linearLayoutBack.setVisibility(View.VISIBLE);

                                }
                            });*/


                    holder.linearLayoutFront.setVisibility(View.GONE);
                    holder.linearLayoutBack.setVisibility(View.VISIBLE);

                }
            });

            holder.linearLayoutBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        holder.linearLayoutBack.setVisibility(View.GONE);
                        holder.linearLayoutFront.setVisibility(View.VISIBLE);
                  /*  holder.linearLayoutBack.animate()
                            .translationY(holder.linearLayoutBack.getHeight())
                            .alpha(0.0f)
                            .setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    holder.linearLayoutBack.setVisibility(View.GONE);
                                    holder.linearLayoutFront.setVisibility(View.VISIBLE);

                                }
                            });*/
                }
            });

            holder.ibtnFavDoge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer pos = holder.getAdapterPosition();
                    String imageUrl = dogeList.get(pos).getImage().getUrl();
                    String imageId = dogeList.get(pos).getImage().getId();

                    if(imageIdList.contains(imageId))
                    {
                        holder.ibtnLikeDoge.setImageResource(R.drawable.unlike);
                        deleteData(imageId, imageUrl);
                        imageIdList.remove(imageId);
                    }
                    else {
                        holder.ibtnLikeDoge.setImageResource(R.drawable.like);
                        imageIdList.add(imageId);
                        insertData(imageId, imageUrl);
                    }
                }
            });
        }
        catch (Exception e)
        {
            Log.d("Mmkayy", e.getMessage());
        }
    }

    public String emptyifNull(String s)
    {
        if (s==null)
            return "";
        else
            return s;
    }

    public void insertData(String imageId, String imageUrl)
    {
        favDoge myDataList = new favDoge(imageId, imageUrl);
        createDb = createDatabase.getInstance(context);
        dao = createDb.Dao();
        dao.insert(myDataList);

        Toast.makeText(context,"Added to favourites!",Toast.LENGTH_LONG).show();
    }

    public void deleteData(String imageId, String imageUrl)
    {
        favDoge myDataList = new favDoge(imageId, imageUrl);
        createDb = createDatabase.getInstance(context);
        dao = createDb.Dao();
        dao.deleteByImageId(imageId);

        Toast.makeText(context,"Removed from favourites!",Toast.LENGTH_LONG).show();
    }

    @Override
    public Filter getFilter() {
        return searchDogeFilter;
    }
    private Filter searchDogeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Result> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(dogeListFull);
            } else {
                filterPattern = constraint.toString().toLowerCase().trim();
                for (Result item : dogeList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dogeList.clear();
            dogeList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void getFilter2() {
        List<Result> filteredList = new ArrayList<>();

        for (Result item : dogeList) {
            if (item.getName().toLowerCase().contains(filterPattern)) {
                filteredList.add(item);
            }
        }

        dogeList.clear();
        dogeList.addAll(filteredList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dogeList.size();
    }


    public void add(Result r) {
        if(!dogeListFull.contains(r))
            dogeListFull.add(r);
        if(!dogeList.contains(r))
            dogeList.add(r);
        notifyItemInserted(dogeList.size() - 1);
    }


    public void addAll(List<Result> tempResults) {
        for (Result result : tempResults) {
            add(result);
        }

        if(filterPattern!=null)
        {
            getFilter2();
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
    }

    /*public void remove(Result r) {
        int position = dogeList.indexOf(r);
        if (position > -1) {
            dogeList.remove(position);
            notifyItemRemoved(position);
        }
    }*/

    public void clear() {
        isLoadingAdded = false;
        Integer count = dogeList.size();
        dogeList.removeAll(dogeList);
        notifyItemRangeRemoved(0, count);
        notifyDataSetChanged();
        //notifyDataSetChanged();
    }

}
