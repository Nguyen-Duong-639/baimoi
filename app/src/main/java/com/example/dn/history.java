package com.example.dn;

public class history {
    private static String name;
    private String time;
    private String trangthaitt;
    private String dayandmonth;
    private int  id;
    private double temperature;

    public String getTrangthaitt() {
        return trangthaitt;
    }

    public void setTrangthaitt(String trangthaitt) {
        this.trangthaitt = trangthaitt;
    }

    public String getDayandmonth() {
        return dayandmonth;
    }

    public void setDayandmonth(String dayandmonth) {
        this.dayandmonth = dayandmonth;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public history() {
    }

    public history(String name, String time, double temperature, String trangthaitt, String dayandmonth) {
        this.trangthaitt = trangthaitt;
        this.dayandmonth = dayandmonth;
        this.name = name;
        this.time = time;
        this.temperature = temperature;
    }

    public history(String name, String time, double temperature) {

        this.name = name;
        this.time = time;
        this.temperature = temperature;
    }

    public history(String name, String time, double temperature, int id) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.temperature = temperature;
    }

}
