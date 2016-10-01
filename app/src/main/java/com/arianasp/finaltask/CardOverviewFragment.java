package com.arianasp.finaltask;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arianasp.finaltask.database.DataBaseSQLite;


public class CardOverviewFragment extends Fragment implements View.OnClickListener  {
    DataBaseSQLite db;
    EditText tvDesription,tvAmount,tvStuff,tvPrice;
    Button buttonAddInc,buttonAddExp;
    String descIncome, descExpenses, amountIncome, amountExpenses;
    ProgressDialog dialogReg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_card, container, false);
        db = new DataBaseSQLite(getActivity());
        tvDesription = (EditText)view.findViewById(R.id.tvDescription);
        tvAmount = (EditText)view.findViewById(R.id.tvAmount);
        tvStuff = (EditText) view.findViewById(R.id.tvstuff);
        tvPrice = (EditText) view.findViewById(R.id.tvprice);
        buttonAddInc = (Button)view.findViewById(R.id.buttonAddIncome);
        buttonAddInc.setOnClickListener(this);
        buttonAddExp = (Button)view.findViewById(R.id.buttonAddExpenses);
        buttonAddExp.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAddIncome:
                descIncome = tvDesription.getText().toString();
                amountIncome = tvAmount.getText().toString();
                if(descIncome.equals("") || amountIncome.equals("")){
                    Toast.makeText(getActivity(), "Isi heula sadayana bray", Toast.LENGTH_LONG).show();
                }
                else{
                    boolean resultInc = db.saveIncomeData(descIncome,amountIncome);
                    if (resultInc) {
                        Toast.makeText(getActivity(), "Inc Sukses Bro", Toast.LENGTH_SHORT).show();
                        tvDesription.setText("");
                        tvAmount.setText("");
                    }else {
                        Toast.makeText(getActivity(), "Hapunten Inc Gagal Bro", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.buttonAddExpenses:
                descExpenses = tvStuff.getText().toString();
                amountExpenses = tvPrice.getText().toString();
                if(descExpenses.equals("") || amountExpenses.equals("")){
                    Toast.makeText(getActivity(), "Isi heula sadayana bray", Toast.LENGTH_LONG).show();
                }
                boolean resultExp = db.saveExpensesData(descExpenses, amountExpenses);
                if (resultExp) {
                    Toast.makeText(getActivity(), "Exp Sukses Bro", Toast.LENGTH_SHORT).show();
                    tvStuff.setText("");
                    tvPrice.setText("");
                } else {
                    Toast.makeText(getActivity(), "Hapunten Exp Gagal Bro", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}
