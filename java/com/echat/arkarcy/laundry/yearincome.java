package com.echat.arkarcy.laundry;

/**
 * Created by NIRAV on 3/25/2018.
 */

public class yearincome {
    String year;
    long yearincome;

    public yearincome(){

    }
    public yearincome(String year, long yearincome) {
        this.year = year;
        this.yearincome = yearincome;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public long getYearincome() {
        return yearincome;
    }

    public void setYearincome(long yearincome) {
        this.yearincome = yearincome;
    }
}
