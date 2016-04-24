package com.geili.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.geili.R;
import com.geili.activity.GameInfoActivity;
import com.geili.bean.Game;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.aigestudio.downloader.bizs.DLManager;
import cn.aigestudio.downloader.interfaces.SimpleDListener;

/**
 * Created by Dong on 4/4/2016.
 */
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private static Context mContext;
    private static ArrayList<Game> mGameList = new ArrayList<>();
    private String saveDir = Environment.getExternalStorageDirectory() + "/AAA/";

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.game_img)
        ImageView mGameImg;
        @Bind(R.id.game_title)
        TextView mGameTitle;
        @Bind(R.id.game_info)
        TextView mGameInfo;
        @Bind(R.id.game_download)
        Button game_download;
        @Bind(R.id.progressBar)
        ProgressBar progressBar;
        @Bind(R.id.tv_progress)
        TextView tvProgress;
        @Bind(R.id.layout_progress)
        LinearLayout layoutProgress;

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
        DLManager.getInstance(mContext).setMaxTask(5);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Game game = mGameList.get(position);

        Picasso.with(mContext)
                .load(game.getImage())
                .into(holder.mGameImg);
        holder.itemView.setTag(position);
        holder.mGameTitle.setText(game.getTitle());
        holder.mGameInfo.setText("下载量:" + game.getDownloadNumber());
        holder.game_download.setTag(game.getUrl());
        holder.game_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = v.getTag().toString();
                final int[] len = new int[1];
                if("下载".equals(holder.game_download.getText()) || "继续".equals(holder.game_download.getText())) {
                    holder.game_download.setText("暂停");
                    holder.layoutProgress.setVisibility(View.VISIBLE);

                    DLManager.getInstance(mContext).dlStart(url, saveDir,
                            game.getTitle(), null, new SimpleDListener() {
                                @Override
                                public void onStart(String fileName, String realUrl, int fileLength) {
                                    holder.progressBar.setMax(fileLength);
                                    len[0] = fileLength;
                                    Log.d("FILELEN", fileLength + "");
                                }

                                @Override
                                public void onProgress(final int progress) {
                                    holder.progressBar.setProgress(progress);

                                    if (progress == 100){
                                        holder.game_download.setText("安装");
                                    }
                                    holder.tvProgress.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            String p = (100 * progress) / len[0] + "";
                                            holder.tvProgress.setText(p + "%");
                                        }
                                    });
                                }
                            });
                }else if("暂停".equals(holder.game_download.getText())){
                    holder.game_download.setText("继续");
                    DLManager.getInstance(mContext).dlStop(url);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGameList == null ? 0 : mGameList.size();
    }

}