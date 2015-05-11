package edu.washington.yujia1.quizdroid2;

import android.content.Intent;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;



public class QuestionActivity extends ActionBarActivity implements QuestionFragment.answerCallBack {
    int pos;
    int questionNumber = 0;
    int answerNo;
    int count;
    TopicClass topic;
    QuestionClass question;
    String topicName;
    String topicDes;
    List<QuestionClass> questionCur  = new ArrayList<QuestionClass>();


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        QuizApp QuizApp = (QuizApp) getApplication();
        Button next = (Button)findViewById(R.id.next);
        Intent launchingIntent = getIntent();
        pos = launchingIntent.getIntExtra("pos", -1);
        /*QuestionClass question01 = new QuestionClass("Q1: 1 + 1 = ?","2","3","4","0",1);
        QuestionClass question02 = new QuestionClass("Q2: 4 - 1 = ?","2","5","3","0",3);
        QuestionClass question03 = new QuestionClass("Q3: 3 * 4 = ?","12","4","6","2",1);
        QuestionClass question21 = new QuestionClass("Q1: What  Captain America's shield made out of?","Platinum","Vibranium","Adamantuim","Other",2);
        QuestionClass question11 = new QuestionClass("Q1: A person who studies physics is known as a?","Programmer","Player","Nice Person","Physicist",4);
        QuestionClass question12 = new QuestionClass("Q2: Physics is all about studying the laws and rules of...","Animals","Atmosphere","Matters","Physicist",3);
        QuestionClass question22 = new QuestionClass("Q2: The Fantastic Four have the headquarters in what building?","Stark Tower","Fantastic Headquarters","Baxter Building","Xavier Institute",3);
        QuestionClass question13 = new QuestionClass("Q3: Which of these physics terms deals with the motion of objects?","Thermodynamics","Classical Mechanics","Electromagnetism","sound",2);
        QuestionClass question23 = new QuestionClass("Q3: Peter Parker works as a photographer for:","The Daily Planet","The Daily Bugle","The New York Times","The Rolling Stone",4);
        switch (pos){
            case 0:
                topicName = "Math";
                topicDes = "This quiz of Math contains three basic Math questions. Are you ready to get on board? Let's get started!";
                questionCur.add(question01);
                questionCur.add(question02);
                questionCur.add(question03);
                break;
            case 1:
                topicName = "Physics";
                topicDes = "This quiz of Physics contains three basic Physics questions. Are you ready to get on board? Let's get started!";
                questionCur.add(question11);
                questionCur.add(question12);
                questionCur.add(question13);
                break;
            case 2:
                topicName = "Marvel Super Heroes";
                topicDes = "This quiz of Marvel Super Heroes contains three Marvel Super Heroes questions. Are you ready to get on board? Let's get started!";
                questionCur.add(question21);
                questionCur.add(question22);
                questionCur.add(question23);
                break;
        }*/
        topic = QuizApp.getInstance().getAllTopics().get(pos);
        question = topic.getQuestions().get(questionNumber);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                   // .setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out)
                    .add(R.id.container, new Topic(topic))
                    .commit();
        }


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                QuestionFragment qf = new QuestionFragment(question);
                count = getCount();
                Bundle bundle = new Bundle();
                bundle.putInt("count",count);
                qf.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                //fragmentTransaction.setCustomAnimations(R.animator.fragment_slide_left_enter,R.animator.fragment_slide_left_enter);
                fragmentTransaction.replace(R.id.container, qf);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                setQuestionNumber(questionNumber);
            }
        });

        Button previous = (Button) findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                questionNumber = getQuestionNumber();
                if (questionNumber > 1) {
                    questionNumber--;

                    QuestionClass question = topic.getQuestions().get(questionNumber);
                    count = getCount();
                    QuestionFragment q = new QuestionFragment(question);
                    Bundle bundle = new Bundle();
                    bundle.putInt("count", count);
                    q.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()

                            .replace(R.id.container, q)
                            .setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out)
                            .commit();

                }
            }
        });

    }

    @Override
    public void getIndex(int index, int count){
        QuestionClass question = topic.getQuestions().get(questionNumber);
        QuestionFragment f1 = new QuestionFragment(question);
        setAnswerNo(index);
        setCount(count);
    }

    public void setCount(int count){
        this.count = count;
    }

    public int getCount(){
        return count;
    }

    public void setQuestionNumber(int questionNumber){
        this.questionNumber = questionNumber;
    }

    public int getQuestionNumber(){
        return questionNumber;
    }

    public void setAnswerNo(int answerNo){
            this.answerNo = answerNo;
        }

    public int getAnswerNo(){
            return answerNo;
        }

    public void ans() {
            Button next = (Button)findViewById(R.id.next);
            QuestionClass question = topic.getQuestions().get(questionNumber);
            answerNo = getAnswerNo();
            count = getCount();
            answer a = new answer(question);
            Bundle bundle = new Bundle();
            bundle.putInt("answerNo",answerNo);
            bundle.putInt("count",count);
            a.setArguments(bundle);


            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
           // fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
            fragmentTransaction.replace(R.id.container, a);
            fragmentTransaction.commit();
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    nextQuestion();
                }
            });





        }




    public void nextQuestion() {

        if (questionNumber < topic.getQuestions().size() - 1)
        {
            questionNumber++;

            QuestionClass question = topic.getQuestions().get(questionNumber);
            count = getCount();
            QuestionFragment q = new QuestionFragment(question);
            Bundle bundle = new Bundle();
            bundle.putInt("count",count);
            q.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, q)
                    .commit();
            Button previous = (Button) findViewById(R.id.previous);
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (questionNumber > 1) {
                        questionNumber--;

                        QuestionClass question = topic.getQuestions().get(questionNumber);
                        count = getCount();
                        QuestionFragment q = new QuestionFragment(question);
                        Bundle bundle = new Bundle();
                        bundle.putInt("count", count);
                        q.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                               // .setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out)
                                .replace(R.id.container, q)
                                .commit();

                    }
                }
            });
            setQuestionNumber(questionNumber);

        }
        else
        {
            Toast.makeText(QuestionActivity.this,"All done with this topic!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
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
