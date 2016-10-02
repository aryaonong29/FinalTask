package com.arianasp.finaltask.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.arianasp.finaltask.R;
import com.arianasp.finaltask.adapter.ExpensesAdapter;
import com.arianasp.finaltask.adapter.IncomeAdapter;
import com.arianasp.finaltask.database.DataBaseSQLite;
import com.arianasp.finaltask.model.TransactionExpensesData;
import com.arianasp.finaltask.model.TransactionIncomeData;

import java.util.ArrayList;


public class DashboardActivity extends BaseActivity {
    RecyclerView recyclerViewIncome;
    RecyclerView recyclerViewExpenses;
    RecyclerView.Adapter rvAdapterIncome, rvAdapterExpenses;
    RecyclerView.LayoutManager rvLmIncome, rvLmExpenses;
    Cursor cIncome, cExpenses,cIn,cExp;
    int subTotalInc, subTotalExp;
    TextView tvTotalInc,tvTotalExp,tvTotalBlc;
    private String rowID = null;
    DataBaseSQLite db;
    ArrayList<TransactionIncomeData> tid = new ArrayList<>();
    ArrayList<TransactionExpensesData> ted = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //interface recyclerview income
        recyclerViewIncome = (RecyclerView) findViewById(R.id.recyclerViewIncome);
        rvLmIncome = new LinearLayoutManager(this);
        recyclerViewIncome.setLayoutManager(rvLmIncome);
        recyclerViewIncome.setHasFixedSize(true);

        //interface recyclerview income
        recyclerViewExpenses = (RecyclerView) findViewById(R.id.recyclerViewExpenses);
        rvLmExpenses = new LinearLayoutManager(this);
        recyclerViewExpenses.setLayoutManager(rvLmExpenses);
        recyclerViewExpenses.setHasFixedSize(true);

        db = new DataBaseSQLite(DashboardActivity.this);
        cIn = db.getDataIncome();
        cExp = db.getDataExpenses();


        //adapter income
        rvAdapterIncome = new IncomeAdapter(putCursorIncome());
        recyclerViewIncome.setAdapter(rvAdapterIncome);


        //adapter expenses
        rvAdapterExpenses = new ExpensesAdapter(putCursorExpenses());
        recyclerViewExpenses.setAdapter(rvAdapterExpenses);

        int amountInc = 0;
        tvTotalInc = (TextView) findViewById(R.id.tvTotalIncome);
        while (cIn.moveToNext()) {
            amountInc += cIn.getInt(cIn.getColumnIndex("amountinc"));
        }
        tvTotalInc.setText("Rp. " + String.valueOf(amountInc));

        int amountExp = 0;
        tvTotalExp = (TextView) findViewById(R.id.tvTotalExpenses);
        while (cExp.moveToNext()) {
            amountExp += cExp.getInt(cExp.getColumnIndex("amountexp"));
        }
        tvTotalExp.setText("Rp. " + String.valueOf(amountExp));

        tvTotalBlc = (TextView) findViewById(R.id.tv_balancetotal);
        tvTotalBlc.setText("Rp. " + String.valueOf(amountInc-amountExp));
    }

    private ArrayList<TransactionIncomeData> putCursorIncome(){
        ArrayList<TransactionIncomeData> tid = new ArrayList<>();
        String [] exp = new String[cExpenses.getCount()];
        while(cIncome.moveToNext()){
            exp[cIncome.getPosition()] = cExpenses.getString(1)+" "+cExpenses.getString(2);
        }
        return tid;
    }

    private ArrayList<TransactionExpensesData> putCursorExpenses(){
        ArrayList<TransactionExpensesData> ted = new ArrayList<>();
        String [] inc = new String[cIncome.getCount()];
        while(cIncome.moveToNext()){
            inc[cIncome.getPosition()] = cIncome.getString(1)+" "+cIncome.getString(2);
        }
        return ted;
    }

}
