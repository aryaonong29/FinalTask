package com.arianasp.finaltask.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arianasp.finaltask.R;
import com.arianasp.finaltask.model.TransactionIncomeData;

import java.util.ArrayList;

/**
 * Created by Ariana on 10/1/2016.
 */

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder>{
    ArrayList<TransactionIncomeData> dataI = new ArrayList<>();

    public IncomeAdapter(ArrayList<TransactionIncomeData> dataI){
        this.dataI = dataI;
    }

    @Override
    public IncomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_income, parent, false);
        ViewHolder vhdr = new ViewHolder(view);
        return vhdr;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final TransactionIncomeData tid = dataI.get(position);
        holder.tvIncomeDesc.setText(tid.getDescriptionIncome());
        holder.tvIncomeAmount.setText(tid.getAmountIncome());
    }

    @Override
    public int getItemCount() {
        return dataI.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cvIncome;
        public LinearLayout linearIncome;
        public TextView tvIncomeDesc, tvIncomeAmount;

        public ViewHolder(View v){
            super(v);
            cvIncome = (CardView) v.findViewById(R.id.cvIncome);
            tvIncomeDesc = (TextView) v.findViewById(R.id.tvIncomeDesc);
            tvIncomeAmount = (TextView) v.findViewById(R.id.tvIncomeAmount);
        }
    }
}
