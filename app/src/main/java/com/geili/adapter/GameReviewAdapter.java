package com.geili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.geili.R;
import com.geili.bean.GameGift;
import com.geili.bean.GameReview;
import com.geili.util.ContantValues;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Dong on 2016/3/30.
 */
public class GameReviewAdapter extends RecyclerView.Adapter<GameReviewAdapter.ViewHolder> {

    private Context mContext;
    private List<GameReview> mReview = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.username) TextView username;
        @Bind(R.id.user_review) TextView userReview;
        @Bind(R.id.review_time) TextView reviewTime;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public GameReviewAdapter(Context context, List<GameReview> reviewList) {
        this.mContext = context;
        this.mReview = reviewList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_review, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GameReview review = mReview.get(position);
        holder.username.setText(review.getUser());
        holder.userReview.setText(Html.fromHtml(review.getContent()));
        holder.reviewTime.setText(review.getTime());
    }

    @Override
    public int getItemCount() {
        return mReview == null ? 0 : mReview.size();
    }

}