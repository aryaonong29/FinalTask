package com.arianasp.finaltask.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arianasp.finaltask.R;
import com.arianasp.finaltask.model.TransactionIncomeData;


public class CardOverviewFragment extends Fragment implements View.OnClickListener  {

    EditText tvDesription,tvAmount,tvStuff,tvPrice;
    Button buttonAddInc,buttonAddExp;
    String descIncome, descExpenses, amountIncome, amountExpenses;
    ProgressDialog dialogReg;
    TransactionIncomeData tid;
    String tempDescInc, tempAmountInc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_card, container, false);
        tvDesription = (EditText)view.findViewById(R.id.tvDescription);
        tvAmount = (EditText)view.findViewById(R.id.tvAmount);
        buttonAddInc = (Button)view.findViewById(R.id.buttonAddIncome);
        buttonAddInc.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonAddIncome:
                descIncome = tvDesription.getText().toString();
                amountIncome = tvAmount.getText().toString();
                if(descIncome.length() == 0){
                    Toast.makeText(getActivity(),"isian heula desc na bray", Toast.LENGTH_LONG).show();
                }
                else if(amountIncome.length()==0){
                    Toast.makeText(getActivity(),"isian heula amount na bray", Toast.LENGTH_LONG).show();
                }
                else if(descIncome.length()== 0 && amountIncome.length()==0){
                    Toast.makeText(getActivity(),"isian heula desc sareng amount na bray", Toast.LENGTH_LONG).show();
                }

        }
    }
}
