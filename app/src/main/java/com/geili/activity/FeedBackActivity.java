package com.geili.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.geili.R;
import com.geili.util.ContantValues;
import com.geili.view.CustomApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Dong on 3/27/2016.
 */
public class FeedBackActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.edit_nick_name) EditText editNickName;
    @Bind(R.id.et_advice) EditText etAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (toolbar != null) {
            toolbar.setTitle(getString(R.string.nav_feedback));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.action_send:
                sendFeedback();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sendFeedback() {
        String api = "api/feedBack/send";
        String url = ContantValues.urlRoot + api;

        //请求成功
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject arg0) {
                Log.d("onResponse", arg0.toString());
                editNickName.setText("");
                etAdvice.setText("");
                showToast("提交成功");
            }

        };

        //请求失败
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.getMessage(), error);
                byte[] htmlBodyBytes = error.networkResponse.data;
                Log.e("onErrorResponse", new String(htmlBodyBytes), error);
                showToast("提交失败");
            }
        };

        JSONObject object = new JSONObject();
        try {
            object.put("mobilePhoneNumber", editNickName.getText());
            object.put("advice", etAdvice.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(url, object, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        CustomApplication.getInstance().addToRequestQueue(request);      //加入请求队列
    }

}
