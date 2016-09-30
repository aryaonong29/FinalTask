package com.arianasp.finaltask.activity;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arianasp.finaltask.R;

import com.arianasp.finaltask.database.DataBaseSQLite;
import com.arianasp.finaltask.model.*;


public class DashboardActivity extends BaseActivity {
    RecyclerView recyclerViewIncome;
    RecyclerView recyclerViewExpenses;
    RecyclerView.Adapter rvAdapterIncome, rvAdapterExpenses;
    RecyclerView.LayoutManager rvLmIncome, rvLmExpenses;
    Cursor cIncome, cExpenses,cIn,cExp;
    int subTotalInc, subTotalExp;
    TextView tvTotalInc,tvTotalExp,tvTotalBlc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final DataBaseSQLite db = new DataBaseSQLite(this);

        recyclerViewIncome = (RecyclerView) findViewById(R.id.recyclerViewIncome);
        recyclerViewIncome.setHasFixedSize(true);

        recyclerViewExpenses = (RecyclerView) findViewById(R.id.recyclerViewExpenses);
        recyclerViewExpenses.setHasFixedSize(true);//RecyclerView can perform several optimizations
        // if it can know in advance that RecyclerView's size is not affected by the adapter contents.

        //LinearLayoutManager
        //Hanya mendukung satu kolom jika itu orientasinya vertical dan satu baris jika orientasinya horizontal.
        rvLmIncome = new LinearLayoutManager(this);
        rvLmExpenses = new LinearLayoutManager(this);
        recyclerViewIncome.setLayoutManager(rvLmIncome);
        recyclerViewExpenses.setLayoutManager(rvLmExpenses);

        rvAdapterIncome = new IncomeAdapter(putIncome());
        recyclerViewIncome.setAdapter(rvAdapterIncome);

        rvAdapterExpenses = new ExpenseAdapter(putExpenses());
        recyclerViewExpenses.setAdapter(rvAdapterExpenses);

        int amountInc = 0;
        tvTotalInc = (TextView) findViewById(R.id.tvTotalIncome);
        while (cIn.moveToNext()) {
            amountInc += cIn.getInt(cIn.getColumnIndex("AMOUNT"));
        }
        tvTotalInc.setText("Rp. " + String.valueOf(amountInc));

        int amountExp = 0;
        tvTotalExp = (TextView) findViewById(R.id.tvTotalExpenses);
        while (cExp.moveToNext()) {
            amountExp += cExp.getInt(cExp.getColumnIndex("AMOUNT"));
        }
        tvTotalExp.setText("Rp. " + String.valueOf(amountExp));

        tvTotalBlc = (TextView) findViewById(R.id.tv_balancetotal);
        tvTotalBlc.setText("Rp. " + String.valueOf(amountInc-amountExp));
    }

    //mthod Cursor interface, that provides random read-write access to the result set returned by a database query.
    private String [] putIncome(){
        String [] incomeA = new String[cIncome.getCount()];
        while(cIncome.moveToNext()){
            incomeA[cIncome.getPosition()] = cIncome.getString(1)+" "+cIncome.getString(2);
        }
        return incomeA;
    }

    //method untuk atur dan menampilkan transactionData untuk expenses
    private String[] putExpenses(){
        String[] expensesA = new String[cExpenses.getCount()];
        while(cExpenses.moveToNext()){
            expensesA[cExpenses.getPosition()] = cExpenses.getString(1)+" "+cExpenses.getString(2);
        }
        return expensesA;
    }

    public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder>{
        private String [] dataI;

        public class ViewHolder extends RecyclerView.ViewHolder{
            public CardView cvIncome;
            public TextView tvIncome;

            public ViewHolder(View v){
                super(v);
                cvIncome = (CardView) v.findViewById(R.id.cvIncome);
                tvIncome = (TextView) v.findViewById(R.id.tvIncome);
            }
        }

        public IncomeAdapter(String [] data){
            dataI = data;
        }

        @Override
        public IncomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_income, parent, false);
            ViewHolder vhdr = new ViewHolder(view);
            return vhdr;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.tvIncome.setText(dataI[position]);

            holder.tvIncome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(DashboardActivity.this);
                    dialog.setContentView(R.layout.dialog_edit);
                    cIncome.moveToPosition(position);

                    final int idxIncome =cIncome.getInt(cIncome.getColumnIndexOrThrow("ID"));

                    final EditText descIncome = (EditText) dialog.findViewById(R.id.edDesc);
                    String getDescIncome = cIncome.getString(cIncome.getColumnIndex("DESCRIPTION"));
                    descIncome.setText(getDescIncome);

                    final EditText amoIncome = (EditText) dialog.findViewById(R.id. edAmount);
                    String getAmoIncome = cIncome.getString(cIncome.getColumnIndex("AMOUNT"));
                    amoIncome.setText(getAmoIncome);

                    Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);
                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBaseSQLite myDB1 = new DataBaseSQLite(DashboardActivity.this);
                            myDB1.updateIncomeData();
                            Toast.makeText(DashboardActivity.this, "UPDATED", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                            dialog.dismiss();
                        }
                    });

                    Button btnDelete = (Button) dialog.findViewById(R.id.btnDelete);
                    btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBaseSQLite myDB1 = new DataBaseSQLite(DashboardActivity.this);
                            myDB1.deleteIncomeData();
                            Toast.makeText(DashboardActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                            dialog.dismiss();
                        }
                    });

                    Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });

            //code utk put dialog
        }

        @Override
        public int getItemCount() {
            return dataI.length;
        }
    }




    public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder>{
        private String[] dataE;

        public class ViewHolder extends RecyclerView.ViewHolder{
            public  CardView cvExpenses;
            public TextView tvExpenses;

            public ViewHolder(View v){
                super(v);
                cvExpenses = (CardView) v.findViewById(R.id.cvExpenses);
                tvExpenses = (TextView) v.findViewById(R.id.tvExpenses);
            }
        }

        public ExpenseAdapter(String[] data){
            dataE = data;
        }

        @Override
        public ExpenseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_expenses, parent, false);
            ViewHolder vhdr = new ViewHolder(v);
            return vhdr;
        }

        @Override
        public void onBindViewHolder(ExpenseAdapter.ViewHolder holder, final int position) {
            holder.tvExpenses.setText(dataE[position]);

            holder.tvExpenses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(DashboardActivity.this);
                    dialog.setContentView(R.layout.dialog_edit);
                    cExpenses.moveToPosition(position);

                    final int idxExpenses =cExpenses.getInt(cExpenses.getColumnIndexOrThrow("ID"));

                    final EditText descExpense= (EditText) dialog.findViewById(R.id.edDesc);
                    String getDescExpenses = cExpenses.getString(cExpenses.getColumnIndex("DESCRIPTION"));
                    descExpense.setText(getDescExpenses);

                    final EditText amoExpenses = (EditText) dialog.findViewById(R.id.edAmount);
                    String getAmoExpenses = cExpenses.getString(cExpenses.getColumnIndex("AMOUNT"));
                    amoExpenses.setText(getAmoExpenses);

                    Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);
                    btnUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBaseSQLite myDB1 = new DataBaseSQLite(DashboardActivity.this);
                            myDB1.updateExpensesData();
                            Toast.makeText(DashboardActivity.this, "UPDATED", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                            dialog.dismiss();
                        }
                    });

                    Button btnDelete = (Button) dialog.findViewById(R.id.btnDelete);
                    btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBaseSQLite myDB1 = new DataBaseSQLite(DashboardActivity.this);
                            myDB1.deleteExpensesData();
                            Toast.makeText(DashboardActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                            dialog.dismiss();
                        }
                    });

                    Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });

            //code utk put dialog
        }

        @Override
        public int getItemCount() {
            return dataE.length;
        }
    }
}
