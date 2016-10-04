package com.arianasp.finaltask.serialization;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mycomputer on 04/10/16.
 */

public class TransactionSerializedIncome {
    @SerializedName("income_Trans")
    public List<IncomeItem> incomeItem;
    public List<IncomeItem> getIncomeItem() {
        return incomeItem;
    }
    public void setIncomeItem(List<IncomeItem> incomeItem){
        this.incomeItem = incomeItem;
    }

    public TransactionSerializedIncome(List<IncomeItem> incomeItem){
        this.incomeItem = incomeItem;
    }
    public class IncomeItem{
        private int idIncome;
        private String descriptionIncome;
        private String amountIncome;

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

}
