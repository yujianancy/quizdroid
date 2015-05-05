package edu.washington.yujia1.quizdroid2;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.FragmentTransaction;


public class QuestionFragment extends android.support.v4.app.Fragment {

    private QuestionClass question;
    private QuestionActivity hostActivity;
    int answerNo ;
    int count = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("count")) {
            count = getArguments().getInt("count");
        }
    }

    public QuestionFragment(QuestionClass question)
    {
        this.question = question;
    }


    /*public void getIndex(answerCallBack ansCallBack){
        int index = answerNo;
        ansCallBack.getIndex(index);
    }*/



    public interface answerCallBack{
        public void getIndex(int index, int count);
    }

answerCallBack ansCallBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ansCallBack = (QuestionActivity) activity;
        QuestionActivity qa = (QuestionActivity)activity;
        hostActivity = qa;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("QuestionFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        TextView txtText = (TextView) view.findViewById(R.id.txtQues);
        txtText.setText(question.getQuestionText());

        final RadioGroup rG = (RadioGroup) view.findViewById(R.id.radioGroup);

        final RadioButton radAnswer1 = (RadioButton) view.findViewById(R.id.radioButton1);
        radAnswer1.setText(question.getAnswer1());

        final RadioButton radAnswer2 = (RadioButton) view.findViewById(R.id.radioButton2);
        radAnswer2.setText(question.getAnswer2());

        final RadioButton radAnswer3 = (RadioButton) view.findViewById(R.id.radioButton3);
        radAnswer3.setText(question.getAnswer3());

        final RadioButton radAnswer4 = (RadioButton) view.findViewById(R.id.radioButton4);
        radAnswer4.setText(question.getAnswer4());

        final Button submit = (Button) view.findViewById(R.id.submit);

        rG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                submit.setVisibility(View.VISIBLE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (question.getCorrectAnswer())
                {
                    case 1:
                        if (radAnswer1.isChecked()){
                           answerNo = 1;
                            count++;
                        } else if (radAnswer2.isChecked()){
                            answerNo = 2;
                        } else if (radAnswer3.isChecked()) {
                            answerNo = 3;
                        } else {
                            answerNo = 4;
                        }
                        break;
                    case 2:
                        if (radAnswer2.isChecked()){
                            answerNo = 2;
                            count++;
                        }else if (radAnswer1.isChecked()){
                            answerNo = 1;
                        } else if (radAnswer3.isChecked()) {
                            answerNo = 3;
                        } else {
                            answerNo = 4;
                        }
                        break;
                    case 3:
                        if (radAnswer3.isChecked()){
                            answerNo = 3;
                            count++;
                        }else if (radAnswer2.isChecked()){
                            answerNo = 2;
                        } else if (radAnswer1.isChecked()) {
                            answerNo = 1;
                        } else {
                            answerNo = 4;
                        }
                        break;
                    case 4:
                        if (radAnswer4.isChecked()){
                            answerNo = 4;
                            count++;
                        }else if (radAnswer2.isChecked()){
                            answerNo = 2;
                        } else if (radAnswer3.isChecked()) {
                            answerNo = 3;
                        } else {
                            answerNo = 1;
                        }
                        break;
                }
                ansCallBack.getIndex(answerNo,count);
                hostActivity.ans();




            }

        });

        return view;
    }



}