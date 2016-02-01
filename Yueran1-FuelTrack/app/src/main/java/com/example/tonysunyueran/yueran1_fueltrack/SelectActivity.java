package com.example.tonysunyueran.yueran1_fueltrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by tonysunyueran on 2016/1/27.
 */
public class SelectActivity extends AppCompatActivity {


    //This class is working for select any entry that user input before
    //We use radio button to allow user to select one of the entry
    protected ArrayList<FuelTrack_log> logs = new ArrayList<FuelTrack_log>();
    protected FuelTrack_log Flog;
    protected int NumberFuelLogs;
    protected static final String FILENAME = "file.sav";
    public static int selection_identifier;
    private RadioButton[] radioGroup;

    //load the entries
    protected void onStart() {
        super.onStart();
        loadFromFile();

    }

    //This is a dynamical way to add radio button
    //We use count number to determine how many radio button we need to create
    // And take the date and station information of every entry to be the radio button title
    public void RadioSelect(int count) {


        for (int i = 1; i <= count; i++) {
            Flog=logs.get(i-1);

            //create a new radio button for every loop
            RadioButton button = new RadioButton(this);
            button.setId(1*i);
            //show date and station information on raidion button
            button.setText("Select Log: " + Flog.getDate()+" "+Flog.getStation());
            radioGroup[i-1] = button;
            ((ViewGroup) findViewById(R.id.radiogroup)).addView(radioGroup[i- 1]);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectactivity);
        loadFromFile();
        Button finishbutton = (Button) findViewById(R.id.finishbutton);

        //add here we initialize an selection identifier to check does user has selected one of
        //the radio button
        selection_identifier = -1;
        NumberFuelLogs = logs.size();
        radioGroup = new RadioButton[NumberFuelLogs];
        RadioSelect(NumberFuelLogs);
        RadioGroup radio = (RadioGroup) findViewById(R.id.radiogroup);

        //After user select one of the radio button, selection identifier will changed to the selected
        //radio button index
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < NumberFuelLogs; i++) {
                    if (checkedId == radioGroup[i].getId()) {
                        selection_identifier = i;
                    }
                }

            }
        });

        finishbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    //we use the proper selection identifier to check qualifications of ViewActivity and EditActivity
    //At here i have changed the onclick method for ViewButton and EditButton to the
    //getinto method
    //if button text is "view_log" , we start ViewActivity
    //if button text is "Edit_log", we start EditActivity
    public void getinto(View view){
        String button_text;
        button_text= ((Button) view).getText().toString();
        if (selection_identifier != -1) {
                if (button_text.equals("view_log")) {
                    Intent intent = new Intent(SelectActivity.this, ViewActivity.class);
                    startActivity(intent);

                } else if (button_text.equals("Edit_Log")) {

                    Intent intent = new Intent(this, EditActivity.class);
                    startActivity(intent);
                }
        }

        //Remind user to select a log before View or Edit
        else {
            Toast.makeText(SelectActivity.this, "You need to select a log firstly", Toast.LENGTH_SHORT).show();
        }
    }

    //load the file
    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            //
            Type listType = new TypeToken<ArrayList<FuelTrack_log>>() {
            }.getType();
            logs = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block

            logs = new ArrayList<>();
        } catch (IOException e) {
            // TODO Auto-generated catch block

            throw new RuntimeException();

        }

    }




}
