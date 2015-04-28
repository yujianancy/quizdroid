package edu.washington.yujia1.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Introduction extends ActionBarActivity {
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        Intent launchingIntent = getIntent();
        pos = launchingIntent.getIntExtra("pos", 0);
        TextView text = (TextView) findViewById(R.id.intro);
        Button begin = (Button) findViewById(R.id.begin);

        switch (pos){
            case 0:
                text.setText("This quiz of Math contains three basic Math questions. Are you ready to get on board? Let's get started!");
                break;
            case 1:
                text.setText("This quiz of Physics contains three basic Physics questions. Are you ready to get on board? Let's get started!");
                break;
            case 2:
                text.setText("This quiz of Marvel Super Heroes contains three basic Marvel Super Heroes questions. Are you ready to get on board? Let's get started!");
                break;
        }

        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent question = new Intent(Introduction.this, Questions.class);
                question.putExtra("pos", pos);
                startActivity(question);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_introduction, menu);
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
