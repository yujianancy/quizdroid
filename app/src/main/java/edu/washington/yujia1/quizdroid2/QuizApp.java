package edu.washington.yujia1.quizdroid2;

import android.app.Application;
import android.content.Intent;
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
        try
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
        }




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


