package com.echat.arkarcy.laundry;

/**
 * Created by NIRAV on 3/21/2018.
 */

public class income {
    String month;
    long monthincome;

    public income()
    {

    }

    public income(String month, long monthincome) {
        this.month = month;
        this.monthincome = monthincome;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public long getMonthincome() {
        return monthincome;
    }

    public void setMonthincome(long monthincome) {
        this.monthincome = monthincome;
    }
}
