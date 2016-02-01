package com.example.tonysunyueran.yueran1_fueltrack;


import android.support.v7.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * Created by tonysunyueran on 2016/1/27.
 */
public class AddActivity extends AppCompatActivity {

    //Create variable which allowed user to add
    private static final String FILENAME = "file.sav";
    protected EditText dateText;
    protected EditText stationText;
    protected EditText odometerText;
    protected EditText fuelGradeText;
    protected EditText fuelAmountText;
    protected EditText fuelUnitCostText;
    protected ArrayList<FuelTrack_log> logs = new ArrayList<FuelTrack_log>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);


        //Make user able to add the data to Rdit Text
        dateText = (EditText) findViewById(R.id.enter_date);
        stationText = (EditText) findViewById(R.id.enter_station);
        odometerText = (EditText) findViewById(R.id.enter_odermeter);
        fuelGradeText = (EditText) findViewById(R.id.enter_fuelgrade);
        fuelAmountText = (EditText) findViewById(R.id.enter_fuelamount);
        fuelUnitCostText = (EditText) findViewById(R.id.enter_unitcost);
        Button cancel_button = (Button) findViewById(R.id.cancelbutton);
        Button finish_button = (Button) findViewById(R.id.finishbutton);


        //This is the finish Button for the addActivity, in the onClick method
        //We allow user to input information about date station odometer fuel amount fuel grade
        //and fuel unit cost.
        //We use try and catch to find out the error

        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String date = dateText.getText().toString();
                    String station = stationText.getText().toString();
                    String odometerString = odometerText.getText().toString();
                    String fuelGrade = fuelGradeText.getText().toString();
                    String fuelAmountString = fuelAmountText.getText().toString();
                    String fuelUnitCostString = fuelUnitCostText.getText().toString();
                    double fuelAmount = Double.parseDouble(fuelAmountString);
                    double odometer = Double.parseDouble(odometerString);
                    double fuelUnitCost = Double.parseDouble(fuelUnitCostString);


                    //At here, if user forget to enter some of the data and leave text field empty,
                    // we will remind the user to enter.
                    if (date.equals("")) {
                        Toast.makeText(AddActivity.this, "Do not forget your date :)", Toast.LENGTH_SHORT).show();
                    } else if (station.equals("")) {
                        Toast.makeText(AddActivity.this, "Do not forget your station :)", Toast.LENGTH_SHORT).show();
                    } else if (fuelGrade.equals("")) {
                        Toast.makeText(AddActivity.this, "Do not forget your fuel grade :)", Toast.LENGTH_SHORT).show();
                    } else if (odometerString.equals("")) {
                        Toast.makeText(AddActivity.this, "Do not forget your odometer :)", Toast.LENGTH_SHORT).show();
                    } else if (fuelAmountString.equals("")) {
                        Toast.makeText(AddActivity.this, "Do not forget your fuel_amount :)", Toast.LENGTH_SHORT).show();
                    } else {
                        Double totalcost = fuelAmount * fuelUnitCost;
                        FuelTrack_log newEntry = new FuelTrack_log(date, station, odometer, fuelGrade, fuelAmount, fuelUnitCost, totalcost);
                        loadFromFile();
                        logs.add(newEntry);
                        saveInFile();
                        finish();
                    }

                //If user does not enter the proper data type, we will remind the user
                } catch (NumberFormatException e) {
                    Toast.makeText(AddActivity.this, "Data does not enter correctly", Toast.LENGTH_SHORT).show();

                }
            }

        });
        //Cancel the addActivity and back to main page.
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
    }


    //load the file, allow user to see the entries they added.
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FuelTrack_log>>() {}.getType();
            logs = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            logs = new ArrayList<>();
        }catch (IOException e) {
        // TODO Auto-generated catch block
        //e.printStackTrace();
        throw new RuntimeException();

        }
    }
    //save the file
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(logs, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }



}