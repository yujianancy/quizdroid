package edu.washington.yujia1.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Questions extends ActionBarActivity {
    int pos;
    int questionNO;
    int con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        TextView txtQues = (TextView) findViewById(R.id.txtQues1);
        final RadioGroup rG = (RadioGroup) findViewById(R.id.radioGroup);
        final RadioButton r1 = (RadioButton) findViewById(R.id.radioButton1);
        final RadioButton r2 = (RadioButton) findViewById(R.id.radioButton2);
        final RadioButton r3 = (RadioButton) findViewById(R.id.radioButton3);
        final RadioButton r4 = (RadioButton) findViewById(R.id.radioButton4);
        final Button submit = (Button) findViewById(R.id.submit);

        Intent launchIntent = getIntent();
        pos = launchIntent.getIntExtra("pos", 0);
        questionNO = launchIntent.getIntExtra("questionNO",0);
        con = launchIntent.getIntExtra("con",0);


        switch (questionNO){
           case 0:
                switch (pos){
                    case 0:
                        txtQues.setText("Q1: 1 + 1 = ?");
                        r1.setText("2");
                        r2.setText("3");
                        r3.setText("4");
                        r4.setText("0");
                        break;
                    case 1:
                        txtQues.setText("Q1: A person who studies physics is known as a?");
                        r1.setText("Programmer");
                        r2.setText("Player");
                        r3.setText("Nice Person");
                        r4.setText("Physicist");
                        break;
                    case 2:
                        txtQues.setText("Q1: What  Captain America's shield made out of?");
                        r1.setText("Platinum");
                        r2.setText("Vibranium");
                        r3.setText("Adamantuim");
                        r4.setText("Other");
                        break;
                }
              break;
            case 1:
                switch (pos){
                    case 0:
                        txtQues.setText("Q2: 4 - 1 = ?");
                        r1.setText("2");
                        r2.setText("5");
                        r3.setText("3");
                        r4.setText("0");
                        break;
                    case 1:
                        txtQues.setText("Q2: Physics is all about studying the laws and rules of...");
                        r1.setText("Animals");
                        r2.setText("Atmosphere");
                        r3.setText("Matters");
                        r4.setText("Physicist");
                        break;
                    case 2:
                        txtQues.setText("Q2: The Fantastic Four have the headquarters in what building?");
                        r1.setText("Stark Tower");
                        r2.setText("Fantastic Headquarters");
                        r3.setText("Baxter Building");
                        r4.setText("Xavier Institute");
                        break;
                }
                break;
            case 2:
                switch (pos){
                    case 0:
                        txtQues.setText("Q3: 3 * 4 = ?");
                        r1.setText("12");
                        r2.setText("4");
                        r3.setText("6");
                        r4.setText("2");
                        break;
                    case 1:
                        txtQues.setText("Q3: Which of these physics terms deals with the motion of objects?");
                        r1.setText("Thermodynamics");
                        r2.setText("Classical Mechanics");
                        r3.setText("Electromagnetism");
                        r4.setText("Sound");
                        break;
                    case 2:
                        txtQues.setText("Q3: Peter Parker works as a photographer for:");
                        r1.setText("The Daily Planet");
                        r2.setText("The Daily Bugle");
                        r3.setText("The New York Times");
                        r4.setText("The Rolling Stone");
                        break;
                }
                break;
            case 3:
                switch (pos){
                    case 0:
                        txtQues.setText("Q4: 10 / 2 = ?");
                        r1.setText("2");
                        r2.setText("5");
                        r3.setText("3");
                        r4.setText("4");
                        break;
                    case 1:
                        txtQues.setText("Q4:Which of these terms is NOT associated with classical mechanics?");
                        r1.setText("Force");
                        r2.setText("Momentum");
                        r3.setText("Friction");
                        r4.setText("Conductivity");
                        break;
                    case 2:
                        txtQues.setText("Q4:Thor has two war goats to pull his chariot. They are named:");
                        r1.setText("Balder and Hermod");
                        r2.setText("Thunder and Lightning");
                        r3.setText("Ask and Embla");
                        r4.setText("Toothgrinder and Toothgnasher");
                        break;
                }
                break;
        }
        rG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                submit.setVisibility(View.VISIBLE);
            }
        });
       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent answer = new Intent(Questions.this, Answer.class);

               RadioButton rB = (RadioButton) findViewById(rG.getCheckedRadioButtonId());
               String a = rB.getText().toString();
               answer.putExtra("answer", a);
               answer.putExtra("pos",pos);
               answer.putExtra("questionNO",questionNO);
               switch (pos){
                   case 0:
                       switch (questionNO){
                           case 0:
                           case 2:
                               if (r1.isChecked()){
                                   con++;
                               }
                               break;
                           case 1:
                               if (r3.isChecked()){
                                   con++;
                               }
                               break;
                           case 3:
                               if (r2.isChecked()){
                                   con++;
                               }
                               break;
                       }
                       break;
                   case 1:
                       switch (questionNO){
                           case 0:
                           case 3:
                               if (r4.isChecked()){
                                   con++;
                               }
                               break;
                           case 1:
                               if (r3.isChecked()){
                                   con++;
                               }
                               break;
                           case 2:
                               if (r2.isChecked()){
                                   con++;
                               }
                               break;
                       }
                       break;
                   case 2:
                       switch (questionNO){
                           case 0:
                           case 2:
                               if (r2.isChecked()){
                                   con++;
                               }
                               break;
                           case 1:
                               if (r3.isChecked()){
                                   con++;
                               }
                               break;
                           case 3:
                               if (r4.isChecked()){
                                   con++;
                               }
                               break;
                       }
                       break;
               }
               answer.putExtra("con",con);

               startActivity(answer);


           }
       });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_questions, menu);
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
