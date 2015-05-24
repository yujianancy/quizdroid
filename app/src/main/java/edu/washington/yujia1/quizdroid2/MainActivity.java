package edu.washington.yujia1.quizdroid2;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;



public class MainActivity extends ActionBarActivity {

    public static final int SETTINGS_RESULT = 1;
    private ListView theList;
    public String[] names = new String[3];
    String json = null;
    String json1 = null;

    public static final int ALARM = 123;


    private DownloadManager dm;
    private long enqueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver, filter);



        for (int i = 0; i < 3; i++) {

            TopicClass topic = QuizApp.getInstance().getAllTopics().get(i);
            String s = topic.getName();
            names[i] = s;
        }

        theList = (ListView) findViewById(R.id.ListOfTopics);
        ArrayAdapter<String> items = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        theList.setAdapter(items);
        theList.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intro = new Intent(MainActivity.this, QuestionActivity.class);
                intro.putExtra("pos", position);
                Log.i("MainActivity", "Firing Intent" + intro);
                startActivity(intro);
            }
        });
        Button settings = (Button) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UserSettingActivity.class);
                startActivityForResult(i, SETTINGS_RESULT);
            }
        });

    }

    final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

            Log.i("MyApp BroadcastReceiver", "onReceive of registered download reciever");

            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                Log.i("MyApp BroadcastReceiver", "download complete!");
                final long downloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                if (downloadID != 0) {

                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadID);
                    Cursor c = dm.query(query);
                    if (c.moveToFirst()) {
                        int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                        Log.d("DM Sample", "Status Check: " + status);
                        switch (status) {
                            case DownloadManager.STATUS_PAUSED:
                            case DownloadManager.STATUS_PENDING:
                            case DownloadManager.STATUS_RUNNING:
                                break;
                            case DownloadManager.STATUS_SUCCESSFUL:
                                writeDownload(downloadID);
                                break;

                            case DownloadManager.STATUS_FAILED:
                                Log.d("My App","download failed.");
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Download Failed")
                                        .setMessage("Do you want to retry or quit?")
                                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                writeDownload(downloadID);
                                            }
                                        })
                                        .setNegativeButton("Quit",new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        })
                                        .show();
                                break;
                        }
                    }
                }
            }
        }
    };

    public void writeDownload(long downloadID){
        ParcelFileDescriptor file;

        try {

            file = dm.openDownloadedFile(downloadID);
            FileInputStream fis = new FileInputStream(file.getFileDescriptor());

            json = readJSONFile(fis);

            try {
                Log.i("MyApp", "writing downloaded to file");

                File file1 = new File(getFilesDir().getAbsolutePath(), "questions.json");
                FileOutputStream fos = new FileOutputStream(file1);
                fos.write(json.getBytes());
                fos.close();
            }

            catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }

            try{
                FileInputStream fis1 = openFileInput("questions.json");
                json1 = readJSONFile(fis1);
            }
            catch (IOException e){
                Log.e("Exception", "File read failed");
            }

            if (json.equals(json1)){
                Toast.makeText(MainActivity.this,"File writing finished!",Toast.LENGTH_SHORT).show();
                Log.i("Writing","Writing finished!");
            } else{
                Log.d("Writing","Writing failed!");
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkNet()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        if (network == null) {
            return false;
        } else
            return true;
    }

    public static boolean getAirplaneMode(Context context){
        int isAirplaneMode = Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) ;
        return (isAirplaneMode == 1)?true:false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SETTINGS_RESULT) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String URL = sharedPrefs.getString("prefURL", "No URL");
            String getTime = sharedPrefs.getString("prefTime", "-1");
            int time = Integer.parseInt(getTime);

            if(checkNet() == false){
                if (getAirplaneMode(this)){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Airplane Mode is on")
                            .setMessage("Do you want to turn it off?")
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No",null)
                            .show();

                } else {
                    Toast.makeText(this, "Network is not available.", Toast.LENGTH_SHORT).show();
                    Log.i("NetworkAccess", "No signal!");
                }
            }
                else{
                Intent alarmReceiverIntent = new Intent(MainActivity.this, AlarmReceiver.class);
                alarmReceiverIntent.putExtra("URL",URL);
                alarmReceiverIntent.putExtra("time",time);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, ALARM, alarmReceiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager manager = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);

                int refreshInterval = time * 60000;

                Log.i("DownloadService", "setting alarm to " + refreshInterval);

                manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), refreshInterval, pendingIntent);

                }
            }




    }

        private String readJSONFile(InputStream fis)
                throws IOException {
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();

            return new String(buffer, "UTF-8");

        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
