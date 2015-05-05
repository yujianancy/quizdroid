package edu.washington.yujia1.quizdroid2;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jia on 2015/5/4.
 */


public class Topic extends Fragment {
    TopicClass topic;
   // QuestionActivity hostActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //QuestionActivity qa = (QuestionActivity)activity;
       // hostActivity = qa;
    }

   public Topic(TopicClass topic){
        this.topic = topic;
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("QuestionFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_topic, container, false);

        TextView topicName = (TextView) view.findViewById(R.id.TopicName);
        TextView topicDes = (TextView) view.findViewById(R.id.TopicDescription);
        topicName.setText(topic.getName());
        topicDes.setText(topic.getDescription());
        return view;
    }
}
