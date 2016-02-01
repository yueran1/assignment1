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
    protected ArrayList<FuelTrack_log> logs = new ArrayList<FuelTrack_log>();
    protected FuelTrack_log Flog;
    protected int NumberFuelLogs;
    protected static final String FILENAME = "file.sav";
    public static int selection_identifier;
    private RadioButton[] radioGroup;

    protected void onStart() {
        super.onStart();
        loadFromFile();

    }


    public void RadioSelect(int count) {


        for (int i = 1; i <= count; i++) {
            Flog=logs.get(i-1);
            RadioButton button = new RadioButton(this);
            button.setId(1*i);
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

        selection_identifier = -1;
        NumberFuelLogs = logs.size();
        radioGroup = new RadioButton[NumberFuelLogs];
        RadioSelect(NumberFuelLogs);
        RadioGroup radio = (RadioGroup) findViewById(R.id.radiogroup);

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
        else {
            Toast.makeText(SelectActivity.this, "You need to select a log firstly", Toast.LENGTH_SHORT).show();
        }
    }


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

            logs = new ArrayList<>();//might be error
        } catch (IOException e) {
            // TODO Auto-generated catch block

            throw new RuntimeException();

        }

    }




}
