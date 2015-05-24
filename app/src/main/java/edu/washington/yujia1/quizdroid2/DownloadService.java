package edu.washington.yujia1.quizdroid2;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;

/**
 * Created by jia on 2015/5/18.
 */
public class DownloadService extends IntentService {
    private DownloadManager dm;
    String URL = null;
    int time = 3;



    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Log.i("DownloadService", "entered onHandleIntent()");

        URL = (String) workIntent.getExtras().get("URL");
        time = (int) workIntent.getExtras().get("time");
        Log.i("DownloadService", "should be downloading here");

        // Start the download
        dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri Download_Uri = Uri.parse(URL);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);


        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        dm.enqueue(request);

        Toast.makeText(this, "Getting data from Server, Please WAIT...", Toast.LENGTH_SHORT).show();

    }


}
