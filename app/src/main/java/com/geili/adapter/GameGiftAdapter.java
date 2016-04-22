package com.geili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.geili.R;
import com.geili.bean.GameGift;
import com.geili.util.ContantValues;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Dong on 2016/3/30.
 */
public class GameGiftAdapter extends RecyclerView.Adapter<GameGiftAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<GameGift> mGiftList = new ArrayList<>();
    private HashMap<String, Object> libao_code;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.gift_img) ImageView mGameImg;
        @Bind(R.id.gift_title) TextView mGameTitle;
        @Bind(R.id.gift_info) TextView mGameInfo;
        @Bind(R.id.progressBar_id) ProgressBar mProgress;
        @Bind(R.id.game_gift_get) Button game_gift_get;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public GameGiftAdapter(Context context, ArrayList<GameGift> giftList) {
        this.mContext = context;
        this.mGiftList = giftList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_gift, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GameGift gift = mGiftList.get(position);
        Picasso.with(mContext)
                .load(ContantValues.picRoot + gift.getGameImg())
                .into(holder.mGameImg);
        holder.mGameInfo.setText(gift.getGameInfo());
        holder.mGameTitle.setText(gift.getGameTitle());
        holder.mProgress.setProgress(gift.getProgress());
        holder.game_gift_get.setTag(position);
        holder.game_gift_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(v.getTag().toString());
                String appId = mGiftList.get(number).getAppid();
                Toast.makeText(mContext, appId, Toast.LENGTH_SHORT).show();
                /*try {
                    libao_code = saveGift(appId);
                    String code = libao_code.get("code").toString();
                    Toast.makeText(mContext, code.equals("-1") ? "已经失效" : code, Toast.LENGTH_LONG)
                            .show();

                } catch (Exception e) {
                    e.printStackTrace();
                }*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return mGiftList == null ? 0 : mGiftList.size();
    }

    /*private HashMap<String, Object> saveGift(String appid) throws Exception {
        Log.d("id","id:" + appid);
        final HashMap<String, Object> map = new HashMap<String, Object>();
        String api = "api/content/libao/save";
        String url = ContantValues.urlRoot + api;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", appid);
        jsonObject.put("userId", "1");

        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("onResponse", "response:" + response);
                try {
                    if(response.getString("errorCode").equals("1")){
                        map.put("code","-1");
                    }else {
                        JSONObject result = response.getJSONObject("result");
                        map.put("code", result.getString("code"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.getMessage());
            }
        });
        CustomApplication.getInstance().addToRequestQueue(request);      //加入请求队列
        return map;
    }*/
}