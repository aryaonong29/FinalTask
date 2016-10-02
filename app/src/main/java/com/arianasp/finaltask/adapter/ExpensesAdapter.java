package com.arianasp.finaltask.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arianasp.finaltask.R;
import com.arianasp.finaltask.model.TransactionExpensesData;

import java.util.ArrayList;

/**
 * Created by Ariana on 10/1/2016.
 */
public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ViewHolder>{
    private ArrayList<TransactionExpensesData> dataE = new ArrayList<>();

    public ExpensesAdapter(ArrayList<TransactionExpensesData> data){
        dataE = data;
    }

    @Override
    public ExpensesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expenses, parent, false);
        ViewHolder vhdr = new ViewHolder(v);
        return vhdr;
    }

    @Override
    public void onBindViewHolder(ExpensesAdapter.ViewHolder holder, final int position) {
        final TransactionExpensesData ted = dataE.get(position);
        holder.tvExpensesDesc.setText(ted.getDescriptionExpenses());
        holder.tvExpensesAmount.setText(ted.getAmountExpenses());
    }

    @Override
    public int getItemCount() {
        return dataE.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cvExpenses;
        public LinearLayout linearExpenses;
        public TextView tvExpensesDesc, tvExpensesAmount;

        public ViewHolder(View v){
            super(v);
            cvExpenses = (CardView) v.findViewById(R.id.cvExpenses);
            tvExpensesDesc = (TextView) v.findViewById(R.id.tvExpenseDesc);
            tvExpensesAmount = (TextView) v.findViewById(R.id.tvExpensesAmount);
        }
    }
}
