package com.geili.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;

import com.geili.R;
import com.geili.bean.User;
import com.geili.util.SharedPreferencesUtil;
import com.geili.view.CustomApplication;

/**
 * Created by Dong on 4/3/2016.
 */
public class SplashActivity extends BaseActivity {

    private static Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SplashActivity.this.handler.sendEmptyMessageAtTime(0, 1000);
            }
        }).start();

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                String userName = SharedPreferencesUtil.readString(
                        SharedPreferencesUtil.getSharedPreference(
                                getApplicationContext(), "login"), "userName");

                if (userName.equals("0") == false) {
                    User user = new User();
                    user.setUserName(userName);
                    ((CustomApplication) getApplication()).setmUser(user);
                    startAnimActivity(MainActivity.class);
                    finish();
                } else {
                    startAnimActivity(LoginActivity.class);
                    finish();
                }
            }
        };
    }
}
