package com.geili.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.geili.R;
import com.geili.adapter.HSVAdapter;
import com.geili.util.ContantValues;
import com.geili.view.CustomApplication;
import com.geili.view.HSVLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.hkm.soltag.TagContainerLayout;
import co.hkm.soltag.TagView;

/**
 * Created by Dong on 4/9/2016.
 */
public class GameIntroFragment extends Fragment {

    private View view;
    private HSVLayout movieLayout = null;
    private HSVAdapter adapter = null;
    private HashMap<String, Object> mData;
    private List<HashMap<String, Object>> images;
    private static JSONArray imagesArray;
    private TextView game_comment,game_introduction;
    private String[] labels;
    private TagContainerLayout mTagContainerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_game_intro,container,false);

        initView(view);
        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void initView(View v){
        movieLayout = (HSVLayout) v.findViewById(R.id.movieLayout);
        adapter = new HSVAdapter(getActivity());
        game_comment = (TextView) v.findViewById(R.id.game_comment);
        game_introduction = (TextView) v.findViewById(R.id.game_intro_text);
        mTagContainerLayout = (TagContainerLayout) v.findViewById(R.id.tagcontainerLayout);
    }

    private void initData() throws JSONException {

        String api = "api/content/introduce/get";
        String url = ContantValues.urlRoot + api;
        String id = (String) getArguments().get("id");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);

        //请求成功
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("onResponse", response.toString());
                getData(response);
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

    public void getData(JSONObject response){
        JSONObject result = response.optJSONObject("result");
        imagesArray = result.optJSONArray("mulitimgs");

        game_comment.setText("小编广播:" + Html.fromHtml(result.optString("comment")));
        game_introduction.setText(Html.fromHtml(result.optString("introduce")));
        labels = result.optString("labels").split(",|，");

        mTagContainerLayout.setTags(labels);
        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Toast.makeText(getActivity(), "aaaaaa", Toast.LENGTH_SHORT).show();
                try {
                    search(labels[position]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onTagLongClick(int position, String text) {

            }
        });
        getImages();
    }

    private List<HashMap<String, Object>> getImages(){

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map;
        if(imagesArray != null){
            for(int i = 0; i < imagesArray.length(); i++){
                map = new HashMap<>();
                try {
                    map.put("id", imagesArray.optJSONObject(i).get("id"));
                    map.put("url", imagesArray.optJSONObject(i).get("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list.add(map);
            }
        }
        String[] imgUrl = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map2 = new HashMap<>();
            String urlString = list.get(i).get("url").toString();
            if(urlString.contains("http"))
                imgUrl[i] = urlString;
            else
                imgUrl[i] = ContantValues.picRoot + urlString;
            map2.put("image", imgUrl[i]);
            adapter.addObject(map2);
        }
        movieLayout.setAdapter(adapter);
        return list;
    }


    public void search(String keywords) throws Exception {

        Bundle bundle = new Bundle();
        bundle.putString("keywords", keywords);
        bundle.putString("category", "");
        //Intent intent = new Intent(getActivity(),AppListActivity.class).putExtras(bundle);
        //startActivity(intent);
        Toast.makeText(getActivity(), keywords, Toast.LENGTH_SHORT).show();
    }
}
