package com.geili.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.geili.R;
import com.geili.util.ContantValues;
import com.geili.view.CustomApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dong on 4/9/2016.
 */
public class GameStrategyFragment extends Fragment {

    private View view;
    private TextView strategy_titleTextView;
    private static WebView webview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_game_strategy,container,false);

        getData();
        initView(view);
        return view;
    }

    private void initView(View v){
        strategy_titleTextView = (TextView) v.findViewById(R.id.strategy_title);
        webview = (WebView) v.findViewById(R.id.webview);

        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页

        //设置Web视图
        webview.setWebViewClient(new HelloWebViewClient());
    }

    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public  static boolean onKeyDown() {
        if (webview.canGoBack()) {
            webview.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }

    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void getData(){
        String api = "api/content/strategy/get";
        String url = ContantValues.urlRoot + api;
        String id = (String) getArguments().get("id");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //请求成功
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("onResponse", response.toString());
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
}