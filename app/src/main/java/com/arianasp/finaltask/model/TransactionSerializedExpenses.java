package com.arianasp.finaltask.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mycomputer on 29/09/16.
 */

public class TransactionSerializedExpenses {
    @SerializedName("expenses_Trans")
    public List<ExpenseItem> expenseItem;

    public List<ExpenseItem> getExpensesItem() {
        return expenseItem;
    }
    public void setExpenseItem(List<ExpenseItem> expenseItem){
        this.expenseItem = expenseItem;
    }
    public TransactionSerializedExpenses(List<ExpenseItem> expenseItem){
        this.expenseItem = expenseItem;
    }

    public class ExpenseItem{
        private int idExpense;
        private String descriptionExpense;
        private String amountExpense;

        public int getIdExpense() {
            return idExpense;
        }

        public void setIdExpense(int idExpense) {
            this.idExpense = idExpense;
        }

        public String getDescriptionExpense() {
            return descriptionExpense;
        }

        public void setDescriptionExpense(String descriptionExpense) {
            this.descriptionExpense = descriptionExpense;
        }

        public String getAmountExpense() {
            return amountExpense;
        }

        public void setAmountExpense(String amountExpense) {
            this.amountExpense = amountExpense;
        }
    }
}
