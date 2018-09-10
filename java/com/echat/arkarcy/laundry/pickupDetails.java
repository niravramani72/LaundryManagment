package com.echat.arkarcy.laundry;

/**
 * Created by NIRAV on 3/21/2018.
 */

public class pickupDetails {
    String date,normalcloth,heavycloth,pickupamount,total;

    public pickupDetails(){

    }

    public pickupDetails(String date, String normalcloth, String heavycloth, String pickupamount, String total) {
        this.date = date;
        this.normalcloth = normalcloth;
        this.heavycloth = heavycloth;
        this.pickupamount = pickupamount;
        this.total = total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNormalcloth() {
        return normalcloth;
    }

    public void setNormalcloth(String normalcloth) {
        this.normalcloth = normalcloth;
    }

    public String getHeavycloth() {
        return heavycloth;
    }

    public void setHeavycloth(String heavycloth) {
        this.heavycloth = heavycloth;
    }

    public String getPickupamount() {
        return pickupamount;
    }

    public void setPickupamount(String pickupamount) {
        this.pickupamount = pickupamount;
    }

}
