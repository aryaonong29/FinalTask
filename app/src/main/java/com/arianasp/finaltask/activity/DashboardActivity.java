package com.arianasp.finaltask.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arianasp.finaltask.R;
import com.arianasp.finaltask.model.TransactionIncomeData;

import java.util.ArrayList;


public class DashboardActivity extends BaseActivity {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerview;
    private static ArrayList<TransactionIncomeData> dataIncome;
    static View.OnLongClickListener myOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //interface recyclerview income
        recyclerview = (RecyclerView) findViewById(R.id.recyclerViewIncome);
        recyclerview.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        dataIncome = new ArrayList<TransactionIncomeData>();
        for(int i =0;i<dataIncome.size();i++){
            dataIncome.add(new TransactionIncomeData());
        }


    }


}
