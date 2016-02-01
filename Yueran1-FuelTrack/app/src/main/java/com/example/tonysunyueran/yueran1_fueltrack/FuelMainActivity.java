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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FuelMainActivity extends AppCompatActivity {
    //try to create some code to send text and view the log
    private EditText bodyText;
    protected TextView textView;
    private static final String FILENAME = "file.sav";
    private ArrayList<FuelTrack_log> entries = new ArrayList<FuelTrack_log>();
    private Double totalcost = 0.0;
    private FuelTrack_log entry;

    protected void onStart() {
        super.onStart();
        loadFromFile();
        TextView totalFuelAmount = (TextView) findViewById(R.id.mainviewtotalcost);
        totalcost = 0.0;
        for (int i = 0; i < entries.size(); i++) {
            entry = entries.get(i);
            totalcost = totalcost + entry.getTotalCost();
        }
        DecimalFormat fuelCostFormat = new DecimalFormat("###,###.##");
        totalFuelAmount.setText("$"+String.valueOf(fuelCostFormat.format(totalcost)));

    }




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

        else if(button_text.equals("view/edit an existing entry")){

            Intent intent= new Intent(this,SelectActivity.class);
            startActivity(intent);
        }
    }

    private void loadFromFile() {
        try {

            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FuelTrack_log>>() {}.getType();
            entries = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            entries = new ArrayList<FuelTrack_log>();
            //throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }



}
