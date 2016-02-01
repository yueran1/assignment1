package com.example.tonysunyueran.yueran1_fueltrack;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.api.GoogleApiClient;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by tonysunyueran on 2016/1/27.
 */
public class ViewActivity extends AppCompatActivity {

    //This class is allow user to view the log they entered before

    private static final String FILENAME = "file.sav";
    protected ArrayList<FuelTrack_log> logs = new ArrayList<FuelTrack_log>();
    protected FuelTrack_log viewLog;
    protected FuelTrack_log main_viewlog;
    private Double totalcost = 0.0;


    //The onStart method working on load the file and use getTotalcost method from FuelTrack_log to
    // show the total number of fuel cost
    protected void onStart() {
        super.onStart();
        loadFromFile();
        totalcost = 0.0;
        main_viewlog = logs.get(SelectActivity.selection_identifier);
        totalcost =  main_viewlog.getTotalCost();
        DecimalFormat fuelCostFormat = new DecimalFormat("###,###.##");
        TextView WholeCost = (TextView) findViewById(R.id.viewtotalcost);
        WholeCost.setText("$"+String.valueOf(fuelCostFormat.format(totalcost)));
    }



    //In the OnCreate method, we use all getter method in the class FuelTrack_log to get every
    //information that user added, and we use TextView to show user these data.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewactivity);
        Button finishview = (Button) findViewById(R.id.finishview);
        loadFromFile();

        finishview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewLog = logs.get(SelectActivity.selection_identifier);


        TextView Viewdate = (TextView) findViewById(R.id.viewdate);
        Viewdate.setText(viewLog.getDate());

        TextView Viewstation = (TextView) findViewById(R.id.viewstation);
        Viewstation.setText(viewLog.getStation());


        //Show odometer in one decimal format.
        DecimalFormat odometerFormat = new DecimalFormat("###,###.#");
        TextView Viewodometer = (TextView) findViewById(R.id.viewodometer);
        //Add the unit "KM" When show the value of odometer
        Viewodometer.setText(String.valueOf(odometerFormat.format(viewLog.getOdometer()))+"KM");

        TextView Viewfuelgrade = (TextView) findViewById(R.id.viewfuelgrade);
        Viewfuelgrade.setText(viewLog.getFuel_grade());

        //Show fuel amount in three decimal format
        DecimalFormat fuelAmountFormat = new DecimalFormat("###,###.###");
        TextView Viewfuelamount = (TextView) findViewById(R.id.viewfuelamount);
        //Add the unit "L" when show the value of fuel amount
        Viewfuelamount.setText(String.valueOf(fuelAmountFormat.format(viewLog.getFuel_amount()))+"L");

        TextView fuelUnitCostInfo = (TextView) findViewById(R.id.viewfuelunitcost);
        //Add the unit "cents/L" when show the value of fuel unit cost.
        fuelUnitCostInfo.setText(String.valueOf(odometerFormat.format(viewLog.getFuel_unit_cost()))+" cents/L");
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FuelTrack_log>>() {
            }.getType();
            logs = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            logs = new ArrayList<>();
        }

    }
}
