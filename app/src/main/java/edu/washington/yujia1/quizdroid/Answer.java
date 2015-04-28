package edu.washington.yujia1.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Answer extends ActionBarActivity {
    String a;
    int pos;
    int questionNO;
    int con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Button next = (Button) findViewById(R.id.next);
        TextView yourAnswer = (TextView) findViewById(R.id.yourAnswer);
        TextView correctAnswer = (TextView) findViewById(R.id.correctAnswer);
        TextView counter = (TextView) findViewById(R.id.counter);
        Intent launchIntent = getIntent();
        a = launchIntent.getStringExtra("answer");
        pos = launchIntent.getIntExtra("pos", 0);
        con = launchIntent.getIntExtra("con",0);
        questionNO = launchIntent.getIntExtra("questionNO",0);
        yourAnswer.setText("Your answer is: " + a);
        counter.setText("You have " + con + " out of 3 correct");
        switch (questionNO) {
            case 0:
                switch (pos) {
                    case 0:
                        correctAnswer.setText("The correct answer is: 2");
                        break;
                    case 1:
                        correctAnswer.setText("The correct answer is: Physicist");
                        break;
                    case 2:
                        correctAnswer.setText("The correct answer is: Vibranium");
                        break;
                }
              break;
            case 1:
                switch (pos) {
                    case 0:
                        correctAnswer.setText("The correct answer is: 3");
                        break;
                    case 1:
                        correctAnswer.setText("The correct answer is: Matters");
                        break;
                    case 2:
                        correctAnswer.setText("The correct answer is: Baxter Building");
                        break;
                }
                break;
            case 2:
                switch (pos) {
                    case 0:
                        correctAnswer.setText("The correct answer is: 12");
                        break;
                    case 1:
                        correctAnswer.setText("The correct answer is: Classical Mechanics");
                        break;
                    case 2:
                        correctAnswer.setText("The correct answer is: The Daily Bugle");
                        break;
                }
                break;

        }
        if (questionNO == 2){
            next.setText("Finish");
        }




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNO == 2){
                    Intent backToMain = new Intent(Answer.this, MainActivity.class);
                    startActivity(backToMain);
                } else{
                    Intent nQ = new Intent(Answer.this, Questions.class);
                    questionNO++;
                    nQ.putExtra("questionNO",questionNO);
                    nQ.putExtra("pos",pos);
                    nQ.putExtra("con",con);
                    startActivity(nQ);
                }


            }
       });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answer, menu);
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
