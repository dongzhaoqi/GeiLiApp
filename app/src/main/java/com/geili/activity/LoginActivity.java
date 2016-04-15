package com.geili.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.geili.R;
import com.geili.bean.User;
import com.geili.util.ContantValues;
import com.geili.util.SharedPreferencesUtil;
import com.geili.view.CustomApplication;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.xwray.passwordview.PasswordView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dong on 2016/3/27.
 */
public class LoginActivity extends BaseActivity implements OnClickListener{

    private Toolbar toolbar;
    private Button btn_sign_in;
    private EditText et_email;
    private PasswordView et_password;
    private String str_email,str_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(getString(R.string.nav_login));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (PasswordView) findViewById(R.id.et_password);
        btn_sign_in = (Button) findViewById(R.id.btn_sign_in);
        btn_sign_in.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            case R.id.action_register:
                toRegister();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_sign_in){
            attemptLogin();
        }
    }

    private void attemptLogin() {

        str_email = et_email.getText().toString();
        str_password = et_password.getText().toString();

        if(TextUtils.isEmpty(str_email)) {
            showToast("用户名不能为空");
        }else if(TextUtils.isEmpty(str_password)){
            showToast("密码不能为空");
        }else{
            toLogin();
        }
    }

    private void toLogin() {
        String api = "api/user/login";
        String url = ContantValues.urlRoot + api;

        //请求成功
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject arg0) {
                Log.d("onResponse", arg0.toString());
                handleLogin(arg0);
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

        JSONObject object = new JSONObject();
        try {
            object.put("userName", str_email);
            object.put("password", str_password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(url, object, listener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        CustomApplication.getInstance().addToRequestQueue(request);      //加入请求队列
    }

    private void handleLogin(JSONObject arg0){
        JSONObject result = arg0.optJSONObject("result");
        int code = arg0.optInt("errorCode");
        if(code == 0){
            User user = new User();
            user.setUserName(result.optString("userName"));
            ((CustomApplication)getApplication()).setmUser(user);
            startAnimActivity(MainActivity.class);
            SharedPreferencesUtil.writeString(SharedPreferencesUtil.getSharedPreference(this,"login"),
                    "userName", user.getUserName());
            showToast("登录成功");
        }

    }

    private void toRegister(){
        DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentHolder(new ViewHolder(R.layout.activity_register))
                .setGravity(Gravity.CENTER)
                .create();
        dialog.show();
    }
}
