package com.arianasp.finaltask.model;

import java.sql.Timestamp;

/**
 * Created by Ariana on 9/30/2016.
 */

public class TransactionIncomeData {
    private String idIncome;
    public String descriptionIncome;
    public String amountIncome;
    public Timestamp timeInc;

    public TransactionIncomeData(String idIncome, String descriptionIncome, String amountIncome) {
        this.idIncome = idIncome;
        this.descriptionIncome = descriptionIncome;
        this.amountIncome = amountIncome;
    }

    public String getIdIncome() {
        return idIncome;
    }

    public void setIdIncome(String idIncome) {
        this.idIncome = idIncome;
    }

    public String getDescriptionIncome() {
        return descriptionIncome;
    }

    public void setDescriptionIncome(String descriptionIncome) {
        this.descriptionIncome = descriptionIncome;
    }

    public String getAmountIncome() {
        return amountIncome;
    }

    public void setAmountIncome(String amountIncome) {
        this.amountIncome = amountIncome;
    }

    public Timestamp getTimeInc() {
        return timeInc;
    }

    public void setTimeInc(Timestamp timeInc) {
        this.timeInc = timeInc;
    }

}
