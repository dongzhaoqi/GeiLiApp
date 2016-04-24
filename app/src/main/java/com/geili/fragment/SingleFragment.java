package com.geili.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.baoyz.widget.PullRefreshLayout;
import com.geili.R;
import com.geili.adapter.GameAdapter;
import com.geili.bean.Game;
import com.geili.util.ContantValues;
import com.geili.view.CustomApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dong on 4/3/2016.
 */
public class SingleFragment extends Fragment {

    private View view;
    private PullRefreshLayout layout;
    private RecyclerView recyclerView;
    private ArrayList<Game> gameList;
    private GameAdapter mAdapter;
    String page = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_single,container,false);
        initData();
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View v){

        recyclerView = (RecyclerView) v.findViewById(R.id.rv_games);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        layout = (PullRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        layout.setRefreshStyle(PullRefreshLayout.STYLE_RING);
    }

    private void initData(){
        gameList = new ArrayList<>();

        String api = "api/rank/get";
        String url = ContantValues.urlRoot + api;
        JSONObject jsonObject = new JSONObject();
        Log.d("test", getArguments().get("rankCategory") + getString(R.string.str_single));
        try {
            jsonObject.put("rankCategory", getArguments().get("rankCategory"));
            jsonObject.put("appCategory", getString(R.string.str_single));
            jsonObject.put("page", page);
            jsonObject.put("numPerPage", "8");
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

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,jsonObject, listener, errorListener){

        };
        CustomApplication.getInstance().addToRequestQueue(request);      //加入请求队列
    }

    private void getData(JSONObject arg0) throws JSONException {
        JSONArray result = arg0.optJSONArray("result");
        for(int i = 0; i < result.length(); i++){
            Game game = new Game();
            String picUrl = result.optJSONObject(i).getString("pic").replace("\\", "");
            String downloadUrl = result.optJSONObject(i).getString("url").replace("\\", "");
            if (picUrl.contains("http"))
                game.setImage(picUrl);
            else
                game.setImage(ContantValues.picRoot + picUrl);
            game.setId(result.optJSONObject(i).getString("id"));
            game.setTitle(result.optJSONObject(i).getString("name"));
            game.setUrl(downloadUrl);
            game.setDownloadNumber(result.optJSONObject(i).getString("downloadNumber"));
            game.setSize(result.optJSONObject(i).getString("size"));
            gameList.add(game);
        }
        mAdapter = new GameAdapter(getActivity(), gameList);
        recyclerView.setAdapter(mAdapter);
        Log.d("getData", "size:" + gameList.size());
    }
}
