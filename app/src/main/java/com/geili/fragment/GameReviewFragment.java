package com.geili.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.geili.R;
import com.geili.adapter.GameReviewAdapter;
import com.geili.bean.GameReview;
import com.geili.util.ContantValues;
import com.geili.view.CustomApplication;
import com.geili.view.ListViewForScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dong on 4/9/2016.
 */
public class GameReviewFragment extends Fragment {

    @Bind(R.id.review_list) RecyclerView reviewList;
    private View view;
    private List<GameReview> reviews = new ArrayList<>();
    private GameReviewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game_review, container, false);
        ButterKnife.bind(this, view);
        layoutManager = new LinearLayoutManager(getActivity());
        reviewList.setLayoutManager(layoutManager);
        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void initData() throws JSONException {

        String api = "api/content/comments/get";
        String url = ContantValues.urlRoot + api;
        //String id = (String) getArguments().get("id");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", "15");

        //请求成功
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("onResponse", response.toString());
                try {
                    getData(response);
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
    }

    public void getData(JSONObject response) throws JSONException {
        GameReview review = null;
        JSONArray result = response.optJSONArray("result");
        if(result.length() == 0){
            review = new GameReview();
            review.setUser("");
            review.setContent("暂无评论");
            review.setTime("");
            reviews.add(review);
        }else {
            for (int i = 0; i < result.length(); i++) {
                review = new GameReview();
                review.setUser(result.getJSONObject(i).optString("username"));
                review.setContent(result.getJSONObject(i).optString("msg"));
                review.setTime(result.getJSONObject(i).optString("dtime"));
                System.out.println("dtime:" + review.getTime());
                reviews.add(review);
            }
        }
        mAdapter = new GameReviewAdapter(getActivity(), reviews);
        reviewList.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
