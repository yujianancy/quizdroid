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
    private long enqueue;
    public static final int ALARM = 123;
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
        // Hooray! This method is called where the AlarmManager shouldve started the download service and we just received it here!

        // Specify the url you want to download here

        URL = (String) workIntent.getExtras().get("URL");
        time = (int) workIntent.getExtras().get("time");

        if (URL == null){URL = "http://tednewardsandbox.site44.com/questions.json";}
       // if (time == 0) {time = 3;}
        Log.i("DownloadService", "should be downloading here");

        // Start the download
        dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri Download_Uri = Uri.parse(URL);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);


        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        enqueue = dm.enqueue(request);

        Toast.makeText(this, "Getting data from Server, Please WAIT...", Toast.LENGTH_SHORT).show();

    }

    public void startOrStopAlarm(Context context, boolean on) {
        Log.i("DownloadService", "startOrStopAlarm on = " + on);

        Intent alarmReceiverIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM, alarmReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //if (on) {
        int refreshInterval = time * 60000; // 5 min x 60,000 milliseconds = total ms in 5 min

        Log.i("DownloadService", "setting alarm to " + refreshInterval);

        // Start the alarm manager to repeat
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), refreshInterval, pendingIntent);
        // }
        /*else {
            manager.cancel(pendingIntent);
            pendingIntent.cancel();

            Log.i("DownloadService", "Stopping alarm");
        }*/

    }
}
