package com.arianasp.finaltask.api;

import com.arianasp.finaltask.model.TransactionExpensesData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
/**
 * Created by Ariana on 10/1/2016.
 */

public interface TransactionAPIExpenses {
    //untuk EXPENSES API

    @GET("/expenses_Trans")
    Call<TransactionSerializedExpenses> getExpensesItem();


    @GET("/expenses_Trans{idExpense}")

    Call<TransactionExpensesData> getExpensesItem(@Path("idExpense") String expensesItem_idExpenses);


    @PUT("/expenses_Trans/{idExpense}")

    Call<TransactionExpensesData> updateExpensesItem(@Path("idExpense") int expensesItem_idExpenses, @Body TransactionExpensesData expensesItem);


    @POST("/expenses_Trans")

    Call<TransactionExpensesData> saveExpensesItem(@Body TransactionExpensesData expensesItem);


    @DELETE("/expenses_Trans/{id}")

    Call<TransactionExpensesData> deleteExpensesItem(@Path("idExpense") String expensesItem_idExpenses);
}

