package com.arianasp.finaltask.model;

/**
 * Created by Ariana on 9/30/2016.
 */

public class TransactionIncomeData {
    private int idIncome;
    public String descriptionIncome;
    public String amountIncome;

    public TransactionIncomeData() {

    }

    public TransactionIncomeData(int idIncome, String descriptionIncome, String amountIncome) {
        this.idIncome = idIncome;
        this.descriptionIncome = descriptionIncome;
        this.amountIncome = amountIncome;
    }

    public TransactionIncomeData(String descriptionIncome, String amountIncome) {
        this.descriptionIncome = descriptionIncome;
        this.amountIncome = amountIncome;
    }

    public int getIdIncome() {
        return idIncome;
    }

    public void setIdIncome(int idIncome) {
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


}
