package com.geili.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.geili.dialog.CustomProgressDialog;

/**
 * Created by Dong on 2016/3/25.
 */
public class BaseActivity extends AppCompatActivity {

    private Toast mToast;
    public CustomProgressDialog customProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        customProgressDialog = new CustomProgressDialog(BaseActivity.this, "loading..");
        customProgressDialog.setCancelable(true);
        customProgressDialog.setCanceledOnTouchOutside(true);
    }

    public void showToast(final String str) {
        if (!TextUtils.isEmpty(str)) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), str,
                                Toast.LENGTH_LONG);
                    } else {
                        mToast.setText(str);
                    }
                    mToast.show();
                }
            });
        }
    }

    public void showToast(int resId) {
        final String str = getResources().getString(resId);
        if (!TextUtils.isEmpty(str)) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), str,
                                Toast.LENGTH_LONG);
                    } else {
                        mToast.setText(str);
                    }
                    mToast.show();
                }
            });
        }
    }

    public void startAnimActivity(Class<?> cla) {
        this.startActivity(new Intent(this, cla));
    }

    public void startAnimActivity(Intent intent) {
        this.startActivity(intent);
    }

    public void showProcessDialog() {
        if (customProgressDialog == null){
            customProgressDialog = new CustomProgressDialog(this,"loading...");
            customProgressDialog.show();
            customProgressDialog.setCanceledOnTouchOutside(true);
        }
        if (customProgressDialog.isShowing() == false)
            customProgressDialog.show();
    }

    public void dismissProcessDialog() {

        if (customProgressDialog != null)
            customProgressDialog.dismiss();
    }
}
