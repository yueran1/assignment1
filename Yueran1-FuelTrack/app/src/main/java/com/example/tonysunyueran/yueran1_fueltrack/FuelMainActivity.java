package com.example.tonysunyueran.yueran1_fueltrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FuelMainActivity extends AppCompatActivity {
    //try to create some code to send text and view the log
    private EditText bodyText;
    protected TextView textView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuelmainactivity);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        textView =(TextView) findViewById(R.id.action_settings);

        /*//Button viewButton = (Button) findViewById(R.id.view);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text =  bodyText.getText().toString();
            }
        });*/
    }

    public void getinto(View view){
        String button_text;
        button_text= ((Button) view).getText().toString();
        if(button_text.equals("view the log")){
            Intent intent= new Intent(this,ViewActivity.class);
            startActivity(intent);
        }
        else if(button_text.equals("add new entry")){

            Intent intent= new Intent(this,AddActivity.class);
            startActivity(intent);
        }

        else if(button_text.equals("select and edit an existing entry")){

            Intent intent= new Intent(this,SelectActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_yueran1__fuel_track, menu);
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
