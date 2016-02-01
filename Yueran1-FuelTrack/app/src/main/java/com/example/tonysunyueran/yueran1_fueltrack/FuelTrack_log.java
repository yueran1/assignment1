package com.example.tonysunyueran.yueran1_fueltrack;

import java.util.Date;

/**
 * Created by tonysunyueran on 2016/1/27.
 */
public class FuelTrack_log {
    protected String date;
    protected String station;
    protected Double odometer;
    protected String fuel_grade;
    protected Double fuel_amount;
    protected Double fuel_unit_cost;
    protected Double fuel_cost;


    public FuelTrack_log(String date, String station, double odometer, String fuel_grade, double fuel_amount, double fuel_unit_cost, double fuel_cost) {
        this.date = date;
        this.station = station;
        this.odometer = odometer;
        this.fuel_grade = fuel_grade;
        this.fuel_amount = fuel_amount;
        this.fuel_unit_cost = fuel_unit_cost;

    }
    ////////////////////check
   public double getTotalCost(){
       return this.fuel_amount*(this.fuel_unit_cost/100);
   }

    public void setFuel_cost(Double fuel_cost) {
        this.fuel_cost = fuel_cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public double getOdometer() {
        return odometer;
    }

    public void setOdometer(double odometer) {
        this.odometer = odometer;
    }

    public String getFuel_grade() {
        return fuel_grade;
    }

    public void setFuel_grade(String fuel_grade) {
        this.fuel_grade = fuel_grade;
    }

    public double getFuel_amount() {
        return fuel_amount;
    }

    public void setFuel_amount(double fuel_amount) {
        this.fuel_amount = fuel_amount;
    }

    public double getFuel_unit_cost() {
        return fuel_unit_cost;
    }

    public void setFuel_unit_cost(double fuel_unit_cost) {
        this.fuel_unit_cost = fuel_unit_cost;


    }



}
