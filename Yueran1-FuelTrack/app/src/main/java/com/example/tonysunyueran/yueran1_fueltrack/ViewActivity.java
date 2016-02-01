package com.example.tonysunyueran.yueran1_fueltrack;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
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
public class ViewActivity extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private static final String FILENAME = "file.sav";
    protected ArrayList<FuelTrack_log> logs = new ArrayList<FuelTrack_log>();
    protected FuelTrack_log viewLog;
    protected FuelTrack_log main_viewlog;
    private Double totalcost = 0.0;

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

        DecimalFormat odometerFormat = new DecimalFormat("###,###.#");
        TextView Viewodometer = (TextView) findViewById(R.id.viewodometer);
        Viewodometer.setText(String.valueOf(odometerFormat.format(viewLog.getOdometer()))+"KM");

        TextView Viewfuelgrade = (TextView) findViewById(R.id.viewfuelgrade);
        Viewfuelgrade.setText(viewLog.getFuel_grade());

        DecimalFormat fuelAmountFormat = new DecimalFormat("###,###.###");
        TextView Viewfuelamount = (TextView) findViewById(R.id.viewfuelamount);
        Viewfuelamount.setText(String.valueOf(fuelAmountFormat.format(viewLog.getFuel_amount()))+"L");

        TextView fuelUnitCostInfo = (TextView) findViewById(R.id.viewfuelunitcost);
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
