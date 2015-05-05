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

import edu.washington.yujia1.quizdroid2.R;


public class MainActivity extends ActionBarActivity {
    public String[] topics = {"Math", "Physics", "Marvel Super Heroes"};
    private ListView theList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theList = (ListView) findViewById(R.id.ListOfTopics);
        ArrayAdapter<String> items = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,topics);
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
