package com.example.rruizp.pruebavalid.mainActivity.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rruizp.pruebavalid.R;
import com.example.rruizp.pruebavalid.detailActivity.view.DetailActivity;
import com.example.rruizp.pruebavalid.model.TvShow;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class TvShowSearchAdapter extends RecyclerView.Adapter<TvShowSearchAdapter.TvShowSearchViewHolder> {

    Context context;
    ArrayList<TvShow> tvShows;
    int page;

    private TvShowSearchAdapter.OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager mLinearLayoutManager;

    private boolean isMoreLoading = false;
    private int visibleThreshold = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    public interface OnLoadMoreListener {
        void onLoadMore(int pages);
    }


    public TvShowSearchAdapter(Context context, TvShowSearchAdapter.OnLoadMoreListener onLoadMoreListener, int page) {
        this.context = context;
        this.tvShows = new ArrayList<>();
        this.onLoadMoreListener = onLoadMoreListener;
        this.page = page;
    }


    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    public void setRecyclerView(RecyclerView mView) {
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLinearLayoutManager.getItemCount();
                firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (!isMoreLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore(page);
                    }
                    isMoreLoading = true;
                }
            }
        });
    }

    public void addItemMore(ArrayList<TvShow> tv_new) {
        tvShows.addAll(tv_new);
        notifyItemRangeChanged(0, tvShows.size());
    }


    public void setMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading = isMoreLoading;
    }

    public void setProgressMore(final boolean isProgress) {
        if (isProgress) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    tvShows.add(null);
                    notifyItemInserted(tvShows.size() - 1);
                }
            });
        } else {
            tvShows.remove(tvShows.size() - 1);
            notifyItemRemoved(tvShows.size());
        }
    }


    @Override
    public TvShowSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_result, parent, false);
        return new TvShowSearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TvShowSearchAdapter.TvShowSearchViewHolder holder, int position) {
        TvShow tvShow = tvShows.get(position);
        if (tvShow != null) {

            holder.setData(tvShow.getPoster_path(), tvShow.getName(), tvShow.getFirst_air_date(), tvShow.getVote_average(), tvShow.getId(), tvShow.getBackdrop_path(), tvShow.getOverview());
        }

    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public void addAll(ArrayList<TvShow> tvShows) {
        if (tvShows != null) {
            this.tvShows.addAll(tvShows);
            notifyDataSetChanged();
        }
    }


    public class TvShowSearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img_poster;
        TextView score, title_item, year_item;
        String title_intent, date_intent, average_intent, img_intent, overview_intent;
        int id_intent;

        public TvShowSearchViewHolder(View itemView) {
            super(itemView);

            title_item = (TextView) itemView.findViewById(R.id.title_item);
            score = (TextView) itemView.findViewById(R.id.score);
            year_item = (TextView) itemView.findViewById(R.id.year_item);
            img_poster = (ImageView) itemView.findViewById(R.id.img_poster);
            itemView.setOnClickListener(this);
        }

        public void setData(String poster_path, String title, String release_date, double vote_average, int id, String backdrop_path, String overview) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat output = new SimpleDateFormat("yyyy", Locale.US);
            try {
                Date dateMovie = sdf.parse(release_date);
                String formattedTime = output.format(dateMovie);

                year_item.setText(formattedTime);
                date_intent = formattedTime;

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Glide.with(context).load(Constants.IMG_URL + poster_path).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(img_poster);
            title_item.setText(title);
            score.setText(String.valueOf(vote_average));

            title_intent = title;
            average_intent = String.valueOf(vote_average);
            id_intent = id;
            img_intent = backdrop_path;
            overview_intent = overview;
        }

        @Override
        public void onClick(View view) {
            Constants.SEARCH_TV_PAGE = 1;
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("title", title_intent);
            intent.putExtra("date", date_intent);
            intent.putExtra("img", img_intent);
            intent.putExtra("id", id_intent);
            intent.putExtra("score", average_intent);
            intent.putExtra("overview", overview_intent);
            intent.putExtra("type", Constants.MOVIE);
            context.startActivity(intent);
        }
    }
}