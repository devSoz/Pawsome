package com.example.myapplication;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class scrollListener extends RecyclerView.OnScrollListener {

        LinearLayoutManager layoutManager;

        public scrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            Log.d("chumo: last", String.valueOf(layoutManager.findLastVisibleItemPosition()));
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

          /*  Log.d("chumo: visibleItemCount", String.valueOf(visibleItemCount));
            Log.d("chumo: totalItemCount", String.valueOf(totalItemCount));
            Log.d("chumo: firstVisiblePos", String.valueOf(firstVisibleItemPosition));
            Log.d("chumo: firstVisiblePos", String.valueOf(firstVisibleItemPosition));
*/

            if (!isLoading() && !isLastPage()) {
                if (((visibleItemCount + firstVisibleItemPosition) >= totalItemCount)) {
                    if (firstVisibleItemPosition >= 0) {
                        //if (totalItemCount >= getTotalPageCount()) {
                            loadMoreItems();
                        //}
                    }
                }
            }
        }
        protected abstract void loadMoreItems();

        public abstract int getTotalPageCount();

        public abstract boolean isLastPage();

        public abstract boolean isLoading();
    }
