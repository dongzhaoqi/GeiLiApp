package com.geili.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Dong on 2016/3/25.
 */
public class CommonUtils {

    static URL myFileUrl = null;
    static InputStream is;

    /**
     * 获取已安装的应用
     *
     * @param context
     * @return
     */
    public static List getInstalledApp(Context context) {
        return context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
    }

    /**
     * 根据包名,启动应用
     *
     * @param context
     * @param pm
     * @param packageName
     * @return
     */
    public static boolean launchApp(Context context, PackageManager pm, String packageName) {
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            try {
                context.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, "Applicatin not found", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * uninstall apk file
     * @param packageName
     */
    public static void uninstallAPK(Context context, String packageName){
        Uri uri=Uri.parse("package:" + packageName);
        Intent intent=new Intent(Intent.ACTION_DELETE,uri);
        context.startActivity(intent);
    }

    /**
     * 获取SD卡根路径
     * @return
     */
    public static String getFilePath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static InputStream getStream(String url) {

        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;

    }


}