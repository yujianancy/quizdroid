package edu.washington.yujia1.quizdroid2;

import android.app.Activity;
import android.app.Application;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by jia on 2015/5/8.
 */



public class QuizApp extends Application implements TopicRepository{
    private static QuizApp instance = null;
    private DownloadManager dm;
    private long enqueue;

    public QuizApp(){

        if (instance == null){
            instance = this;
        } else {
            throw new RuntimeException("Cannot create more than one QuizApp");
        }
    }
    public static QuizApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("QuizApp", "Constructor fired");
        String json = null;
        /*try
        {
            InputStream inputStream = getAssets().open("questions.json");
            json = readJSONFile(inputStream);
            JSONArray jsonTopics = new JSONArray(json);

            this.topics = new ArrayList<TopicClass>();
            for (int i=0; i<jsonTopics.length(); i++)
            {
                JSONObject topic = jsonTopics.getJSONObject(i);
                topics.add(loadTopic(topic));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


            File myFile = new File(getFilesDir().getAbsolutePath(), "/questions.json");  // this string is where you can specify what file you are looking for inside your data/ directory

            // Let's get the JSON in the files directory! (aka data/data.json which is a hidden folder that you can't access or see unless its from the app itself)
            // check if data.json file exists in files directory
            if (myFile.exists()) {
                Log.i("QuizApp", "questions.json DOES exist");

                try {
                    FileInputStream fis = openFileInput("questions.json");
                    json = readJSONFile(fis);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                // Can't find data.json file in files directory. Fetch data.json in assets
                Log.i("QuizApp", "questions.json DOESN'T exist. Fetch from assets");

                try {
                    InputStream inputStream = getAssets().open("questions.json");
                    json = readJSONFile(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            // DO SOMETHING WITH JSON HERE
            try {
                JSONArray jsonTopics = new JSONArray(json);

                this.topics = new ArrayList<TopicClass>();
                for (int i=0; i<jsonTopics.length(); i++)
                {
                    JSONObject topic = jsonTopics.getJSONObject(i);
                    topics.add(loadTopic(topic));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            // Start the Download service (which is a class that you create. This class extends IntentService)


           /* DownloadService downloadService = new DownloadService();
        downloadService.startOrStopAlarm(this, true);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        // URL = sharedPrefs.getString("prefURL", "No URL");
        String URL = "http://tednewardsandbox.site44.com/questions.json";
        String getTime = sharedPrefs.getString("prefTime", "-1");
        int time = Integer.parseInt(getTime);
        Intent mIntent = new Intent(QuizApp.this, AlarmReceiver.class);
        mIntent.putExtra("URL",URL);
        mIntent.putExtra("time",time);
        this.startService(mIntent);*/





    }

    private String readJSONFile(InputStream fis)
            throws IOException
    {
        int size = fis.available();
        byte[] buffer = new byte[size];
        fis.read(buffer);
        fis.close();

        return new String(buffer, "UTF-8");

    }

    private TopicClass loadTopic(JSONObject topic)
            throws JSONException
    {
        JSONArray qs = topic.getJSONArray("questions");
        Log.d("QuizApp", "Topic " + topic.getString("title") + " has " + qs.length() + " questions.");
        List<QuestionClass> questions = new ArrayList<QuestionClass>();
        for (int j=0; j< qs.length(); j++)
        {
            Log.d("QuizApp", "Adding " + qs.getJSONObject(j).getString("text"));
            questions.add(loadQuestion(qs.getJSONObject(j)));
        }

        return new TopicClass(topic.getString("title"), topic.getString("desc"), questions);
    }

    private QuestionClass loadQuestion(JSONObject q)
            throws JSONException
    {
        return new QuestionClass(q.getString("text"),
                q.getJSONArray("answers").getString(0),
                q.getJSONArray("answers").getString(1),
                q.getJSONArray("answers").getString(2),
                q.getJSONArray("answers").getString(3),
                q.getInt("answer"));
    }


    @Override
    public List<TopicClass> getAllTopics() {
        return topics;
    }

    private List<TopicClass> topics;
}


