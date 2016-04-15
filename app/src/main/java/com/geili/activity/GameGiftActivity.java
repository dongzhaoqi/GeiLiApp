package com.geili.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.geili.R;
import com.geili.adapter.GameGiftAdapter;
import com.geili.bean.GameGift;
import com.geili.util.ContantValues;
import com.geili.view.CustomApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GameGiftActivity extends BaseActivity {

    private Toolbar toolbar;
    private RecyclerView rv_game_gift;
    private RecyclerView.LayoutManager layoutManager;
    private GameGiftAdapter mAdapter;
    private ArrayList<GameGift> giftList = new ArrayList<>();

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_game_gift);

        initData();
        Log.d("initData", "length:" + giftList.size());
        initView();
    }

    private void initView(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(getString(R.string.txt_gift));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        rv_game_gift = (RecyclerView) findViewById(R.id.rv_game_gift);
        layoutManager = new LinearLayoutManager(this);
        rv_game_gift.setLayoutManager(layoutManager);

    }

    private ArrayList<GameGift> initData(){

        String userName = ((CustomApplication)getApplication()).getmUser().getUserName();

        String api = "api/content/libao/getList";
        String url = ContantValues.urlRoot + api;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //请求成功
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject arg0) {
                Log.d("onResponse", arg0.toString());
                try {
                    getData(arg0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //请求失败
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.getMessage(), error);
                byte[] htmlBodyBytes = error.networkResponse.data;
                Log.e("onErrorResponse", new String(htmlBodyBytes), error);
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(url, jsonObject, listener, errorListener);
        CustomApplication.getInstance().addToRequestQueue(request);      //加入请求队列
        return giftList;
    }

    private void getData(JSONObject arg0) throws JSONException {
        JSONArray result = arg0.optJSONArray("result");
        for(int i = 0; i < result.length(); i++){
            GameGift gift = new GameGift();
            gift.setGameImg(result.optJSONObject(i).getString("picUrl"));
            gift.setGameInfo(result.optJSONObject(i).getString("libaoTitle"));
            gift.setGameTitle(result.optJSONObject(i).getString("title"));
            gift.setProgress(result.optJSONObject(i).getInt("progress"));
            gift.setAppid(result.optJSONObject(i).getString("appid"));
            giftList.add(gift);
        }
        mAdapter = new GameGiftAdapter(this, giftList);
        rv_game_gift.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}