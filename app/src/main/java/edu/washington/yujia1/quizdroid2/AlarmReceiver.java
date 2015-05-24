package edu.washington.yujia1.quizdroid2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by jia on 2015/5/18.
 */
public class AlarmReceiver extends BroadcastReceiver {

    public AlarmReceiver() {}


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmReceiver", "entered onReceive() from AlarmReceiver");


        // This is where we start our DownloadService class! aka tell our IntentService to start the download!
       String URL = intent.getStringExtra("URL");
        int time = intent.getIntExtra("time",3);
        Intent downloadServiceIntent = new Intent(context, DownloadService.class);
        downloadServiceIntent.putExtra("URL",URL);
        downloadServiceIntent.putExtra("time",time);
        context.startService(downloadServiceIntent);
    }
}
