package edu.washington.yujia1.quizdroid2;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import edu.washington.yujia1.quizdroid2.R;


public class MainActivity extends ActionBarActivity {


    private ListView theList;
    public String[] names = new String[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QuizApp QuizApp = (QuizApp) getApplication();
        for (int i = 0; i<3; i++){

            TopicClass topic = QuizApp.getInstance().getAllTopics().get(i);
            String s = topic.getName();
            names[i] = s;
        }

        theList = (ListView) findViewById(R.id.ListOfTopics);
        ArrayAdapter<String> items = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        theList.setAdapter(items);
        theList.setOnItemClickListener(new ListView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intro = new Intent(MainActivity.this,QuestionActivity.class);
                intro.putExtra("pos",position);
                Log.i("MainActivity", "Firing Intent" + intro);
                startActivity(intro);
            }
        });




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
