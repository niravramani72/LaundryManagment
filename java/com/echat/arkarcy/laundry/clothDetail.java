package com.echat.arkarcy.laundry;

/**
 * Created by NIRAV on 3/18/2018.
 */

public class clothDetail {
    int totalno,remaining,amount,normal,heavy,normalr,heavyr,ramount;

    public clothDetail(int totalno, int remaining, int amount, int normal, int heavy, int normalr, int heavyr, int ramount) {
        this.totalno = totalno;
        this.remaining = remaining;
        this.amount = amount;
        this.normal = normal;
        this.heavy = heavy;
        this.normalr = normalr;
        this.heavyr = heavyr;
        this.ramount = ramount;
    }

    public int getRamount() {
        return ramount;
    }

    public void setRamount(int ramount) {
        this.ramount = ramount;
    }

    public clothDetail(){


    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public int getHeavy() {
        return heavy;
    }

    public void setHeavy(int heavy) {
        this.heavy = heavy;
    }

    public int getNormalr() {
        return normalr;
    }

    public void setNormalr(int normalr) {
        this.normalr = normalr;
    }

    public int getHeavyr() {
        return heavyr;
    }

    public void setHeavyr(int heavyr) {
        this.heavyr = heavyr;
    }

    public int getTotalno() {
        return totalno;
    }

    public void setTotalno(int totalno) {
        this.totalno = totalno;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
