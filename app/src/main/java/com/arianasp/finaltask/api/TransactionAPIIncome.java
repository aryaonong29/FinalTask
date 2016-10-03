package com.arianasp.finaltask.api;

import com.arianasp.finaltask.model.TransactionIncomeData;

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

public interface TransactionAPIIncome {

    //UNTUK INCOME API

    @GET("/income_Trans")
    Call<TransactionSerializedIncome> getIncomeItem();


    @GET("/income_Trans{idIncome}")

    Call<TransactionIncomeData> getIncomeItem(@Path("idIncome") String incomeItem_idIncome);


    @PUT("/income_Trans/{idIncome}")

    Call<TransactionIncomeData> updateIncomeItem(@Path("idIncome") int incomeItem_idIncome, @Body TransactionIncomeData incomeItem);


    @POST("/income_Trans")

    Call<TransactionIncomeData> saveIncomeItem(@Body TransactionIncomeData incomeItem);


    @DELETE("/income_Trans/{idIncome}")

    Call<TransactionIncomeData> deleteIncomeItem(@Path("idIncome") String incomeItem_idIncome);





}

