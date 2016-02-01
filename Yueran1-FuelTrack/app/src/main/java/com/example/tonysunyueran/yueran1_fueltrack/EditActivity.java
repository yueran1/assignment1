package com.example.tonysunyueran.yueran1_fueltrack;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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
import java.util.ArrayList;

/**
 * Created by tonysunyueran on 2016/1/31.
 */
public class EditActivity extends Activity {
    private static final String FILENAME = "file.sav";
    private ArrayList<FuelTrack_log> logs = new ArrayList<FuelTrack_log>();
    private FuelTrack_log show_original_data;

    public void onCreate(Bundle savedInstanceState) {
        loadFromFile();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editactivity);
        Button FinishEdit = (Button) findViewById(R.id.finishedit);

        show_original_data = logs.get(SelectActivity.selection_identifier);
        final TextView EditDate = (TextView) findViewById(R.id.editdate);
        final TextView EditStation = (TextView) findViewById(R.id.editstation);
        final TextView EditOdometer = (TextView) findViewById(R.id.editodometer);
        final TextView EditFuelGrade = (TextView) findViewById(R.id.editfuelgrade);
        final TextView EditFuelAmount = (TextView) findViewById(R.id.editfuelamount);
        final TextView EditUnitCost = (TextView) findViewById(R.id.editfuelunitcost);

        EditDate.setText(show_original_data.getDate());
        EditStation.setText(show_original_data.getStation());
        EditOdometer.setText(String.valueOf(show_original_data.getOdometer()));
        EditFuelGrade.setText(show_original_data.getFuel_grade());
        EditFuelAmount.setText(String.valueOf(show_original_data.getFuel_amount()));
        EditUnitCost.setText(String.valueOf(show_original_data.getFuel_unit_cost()));


        FinishEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                try {
                    String date = EditDate.getText().toString();
                    String station = EditStation.getText().toString();
                    String odometerString = EditOdometer.getText().toString();
                    String fuelGrade = EditFuelGrade.getText().toString();
                    String fuelAmountString = EditFuelAmount.getText().toString();
                    String fuelUnitCostString = EditUnitCost.getText().toString();

                    double fuelAmount = Double.parseDouble(fuelAmountString);
                    double odometer = Double.parseDouble(odometerString);
                    double fuelUnitCost = Double.parseDouble(fuelUnitCostString);

                    if (date.equals("")) {
                        Toast.makeText(EditActivity.this, "Do not forget your date :)", Toast.LENGTH_SHORT).show();
                    } else if (station.equals("")) {
                        Toast.makeText(EditActivity.this, "Do not forget your station :)", Toast.LENGTH_SHORT).show();
                    } else if (fuelGrade.equals("")) {
                        Toast.makeText(EditActivity.this, "Do not forget your fuel grade :)", Toast.LENGTH_SHORT).show();
                    } else if (odometerString.equals("")) {
                        Toast.makeText(EditActivity.this, "Do not forget your odometer :)", Toast.LENGTH_SHORT).show();
                    } else if (fuelAmountString.equals("")) {
                        Toast.makeText(EditActivity.this, "Do not forget your fuel_amount :)", Toast.LENGTH_SHORT).show();
                    } else {

                        Double fuelCost = fuelAmount * (fuelUnitCost / 100);

                        show_original_data.setDate(date);
                        show_original_data.setStation(station);
                        show_original_data.setOdometer(odometer);
                        show_original_data.setFuel_grade(fuelGrade);
                        show_original_data.setFuel_amount(fuelAmount);
                        show_original_data.setFuel_unit_cost(fuelUnitCost);
                        show_original_data.setFuel_cost(fuelCost);


                        saveInFile();

                        loadFromFile();
                        finish();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(EditActivity.this, "Data does not enter correctly", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    public void CancelEdit(View view){
        finish();

    }


    private void loadFromFile() {
        //ArrayList<String> tweets = new ArrayList<String>();
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            //String line = in.readLine();
            //while (line != null) {
            //	tweets.add(line);
            //	line = in.readLine();
            //}
            Gson gson= new Gson();

            //
            Type listType = new TypeToken<ArrayList<FuelTrack_log>>() {}.getType();
            logs=gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            logs=new ArrayList<FuelTrack_log>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            throw new RuntimeException();

        }
        //return tweets.toArray(new String[tweets.size()]);
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            //fos.write(new String(date.toString() + " | " + text)
            //.getBytes());

            BufferedWriter out=new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson= new Gson();
            gson.toJson(logs, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
