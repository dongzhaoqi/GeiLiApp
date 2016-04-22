package com.geili.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geili.R;
import com.geili.activity.GameInfoActivity;
import com.geili.bean.Game;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dong on 4/4/2016.
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private static Context mContext;
    private static ArrayList<Game> mGameList = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.game_img) ImageView mGameImg;
        @Bind(R.id.game_title) TextView mGameTitle;
        @Bind(R.id.game_info) TextView mGameInfo;
        @Bind(R.id.game_download) Button game_download;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    Game game = mGameList.get(Integer.parseInt(v.getTag().toString()));
                    bundle.putSerializable("game", game);
                    mContext.startActivity(new Intent(mContext, GameInfoActivity.class).putExtras(bundle));
                }
            });
        }
    }

    public GameAdapter(Context context, ArrayList<Game> gameList) {
        this.mContext = context;
        this.mGameList = gameList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Game game = mGameList.get(position);
        Picasso.with(mContext)
                .load(game.getImage())
                .into(holder.mGameImg);
        holder.itemView.setTag(position);
        holder.mGameTitle.setText(game.getTitle());
        holder.mGameInfo.setText("下载量:" + game.getDownloadNumber());
        holder.game_download.setTag(position);
        holder.game_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(v.getTag().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGameList == null ? 0 : mGameList.size();
    }

}