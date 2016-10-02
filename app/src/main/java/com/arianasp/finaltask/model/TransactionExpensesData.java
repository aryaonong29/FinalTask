package com.arianasp.finaltask.model;

import java.sql.Timestamp;

/**
 * Created by Ariana on 9/30/2016.
 */

public class TransactionExpensesData {
    private String idExpenses;
    public String descriptionExpenses;
    public String amountExpenses;
    public Timestamp timeExp;

    public TransactionExpensesData(String idExpenses, String descriptionExpenses, String amountExpenses) {
        this.idExpenses = idExpenses;
        this.descriptionExpenses = descriptionExpenses;
        this.amountExpenses = amountExpenses;
    }

    public String getIdExpenses() {
        return idExpenses;
    }

    public void setIdExpenses(String idExpenses) {
        this.idExpenses = idExpenses;
    }

    public String getDescriptionExpenses() {
        return descriptionExpenses;
    }

    public void setDescriptionExpenses(String descriptionExpenses) {
        this.descriptionExpenses = descriptionExpenses;
    }

    public String getAmountExpenses() {
        return amountExpenses;
    }

    public void setAmountExpenses(String amountExpenses) {
        this.amountExpenses = amountExpenses;
    }

    public Timestamp getTimeExp() {
        return timeExp;
    }

    public void setTimeExp(Timestamp timeExp) {
        this.timeExp = timeExp;
    }
}
