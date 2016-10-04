package com.arianasp.finaltask.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arianasp.finaltask.R;
import com.arianasp.finaltask.api.TransactionAPIIncome;
import com.arianasp.finaltask.database.DataBaseSQLite;
import com.arianasp.finaltask.model.TransactionIncomeData;
import com.arianasp.finaltask.serialization.TransactionSerializedIncome;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SynchronizeActivity extends BaseActivity {
    TextView tvStatus, tvResponses;
    EditText tvDesription,tvAmount,tvStuff,tvPrice;
    DataBaseSQLite db;
    Button btnSync;
    ProgressDialog dialogReg;
    Cursor curInc, curExp;
    String descIncome, descExpenses, amountIncome, amountExpenses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronize);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvResponses = (TextView) findViewById(R.id.tvLastData);
        tvDesription = (EditText)findViewById(R.id.tvDescription);
        tvAmount = (EditText)findViewById(R.id.tvAmount);
        tvStuff = (EditText)findViewById(R.id.tvstuff);
        tvPrice = (EditText)findViewById(R.id.tvprice);
        db = new DataBaseSQLite(this);
//        curInc = db.addIncome();
//        curExp = db.addExpenses();
        btnSync = (Button) findViewById(R.id.btnSync);
        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getApiIncome();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getApiIncome ()throws JSONException{
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-b22195-advanceapp1.apiary-mock.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TransactionAPIIncome user_apiIncome = retrofit.create(TransactionAPIIncome.class);

        // // implement interface for get all user
        Call<TransactionSerializedIncome> call = user_apiIncome.getIncomeItem();
        call.enqueue(new Callback<TransactionSerializedIncome>() {

            @Override
            public void onResponse(Call<TransactionSerializedIncome> call, Response<TransactionSerializedIncome> response) {
                int status = response.code();
                tvStatus.setText(String.valueOf(status));
                for(TransactionSerializedIncome.IncomeItem incomeItem : response.body().getIncomeItem()){
                    tvResponses.append(
                            "Id = " + String.valueOf(incomeItem.getIdIncome()) +
                                    System.getProperty("line.separator") +
                                    "Description Income = " + incomeItem.getDescriptionIncome() +
                                    System.getProperty("line.separator") +
                                    "Amount Income = " + incomeItem.getAmountIncome() +
                                    System.getProperty("line.separator")
                    );
                    break;
                }
            }

            @Override
            public void onFailure(Call<TransactionSerializedIncome> call, Throwable t) {
                dialogReg.dismiss();
                tvStatus.setText(String.valueOf(t));
            }
        });
    }

    private void putApiIncome() throws JSONException{
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-b22195-advanceapp1.apiary-mock.com/expenseTrans")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final TransactionAPIIncome postApiIncome = retrofit.create(TransactionAPIIncome.class);
    }

    private void postApiIncome() throws JSONException{
        int count = 0;

        dialogReg = new ProgressDialog(SynchronizeActivity.this);
        dialogReg.setTitle("Syncronize dulu bro");
        dialogReg.setMessage("Loading ...");
        dialogReg.setProgress(0);

        dialogReg.show();
        count++;

        Integer current_status = (int) ((count / (float) curInc.getColumnCount()) * 100);
        dialogReg.setProgress(current_status);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://private-b22195-advanceapp1.apiary-mock.com/expenseTrans")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final TransactionAPIIncome postApiIncome = retrofit.create(TransactionAPIIncome.class);
        // // implement interface for get all user
        TransactionIncomeData dataPostIncome = new TransactionIncomeData(descIncome,amountIncome);
        Gson gsonPAI = new Gson();
        String json = gsonPAI.toJson(dataPostIncome);
        Log.e("CEKIDOT", json);

        TransactionIncomeData curData = new TransactionIncomeData(curInc.getString(1), curInc.getString(2));

        Call<TransactionIncomeData> callPI = postApiIncome.saveIncomeItem(curData);
        callPI.enqueue(new Callback<TransactionIncomeData>() {
            @Override
            public void onResponse(Call<TransactionIncomeData> call, Response<TransactionIncomeData> response) {
                int status = response.code();
                for(curInc.moveToFirst(); ! curInc.isAfterLast(); curInc.moveToNext()) {
                    tvResponses.setText(String.valueOf(curInc.getPosition()));
                    DataBaseSQLite myDb = new DataBaseSQLite(SynchronizeActivity.this);
                    //myDb.updateIncome(String.valueOf(curInc.getInt(0)), tvResponses.getText().toString());
                    Toast.makeText(SynchronizeActivity.this, String.valueOf(curInc.getPosition()), Toast.LENGTH_SHORT).show();
                }
//                tvResponses.setText(String.valueOf(status)+ " : last income sync : " + String.valueOf(curInc.getPosition()));
                if (status==201) {
                    Toast.makeText(SynchronizeActivity.this, "Sync Success", Toast.LENGTH_SHORT).show();
                } else if (status==400) {
                    Toast.makeText(SynchronizeActivity.this, "Sync Failed", Toast.LENGTH_SHORT).show();
                }
                if (dialogReg.isShowing()) dialogReg.dismiss();

            }

            @Override
            public void onFailure(Call<TransactionIncomeData> call, Throwable t) {
                if(dialogReg.isShowing()){
                    dialogReg.dismiss();
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(SynchronizeActivity.this);

                alert.setCancelable(false).setTitle("Synchronize").setMessage("fails synchronize")
                        .setPositiveButton("Skip", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(SynchronizeActivity.this, "Skip.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    postApiIncome();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                                Toast.makeText(SynchronizeActivity.this, "Internet Mati Coy", Toast.LENGTH_SHORT).show();
                            }
                        });
                alert.show();

            }
        });
    }
}


//public class SyncronizeActivity extends BaseActivity {
//
//    TextView tv_respond;
//    Cursor incomes;
//    ProgressDialog progressDialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_syncronize);
//        this.setTitle("Syncronize");
//
//        DatabaseHelper myDB = new DatabaseHelper(this);
//        incomes = myDB.listIncome();
//
//        tv_respond = (TextView) findViewById(R.id.tv_respond);
//
//        Button btn_sync = (Button) findViewById(R.id.btn_Sync);
//        btn_sync.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    getApi();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    private void getApi() throws JSONException {
//
//        progresStart();
//
//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://private-fc7f8-cateduit.apiary-mock.com/")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//
//        final IncomeTransactionApi income_api = retrofit.create(IncomeTransactionApi.class);
//
//        if (incomes.getCount() > 0) {
//            incomes.moveToFirst();
//            do {
//                final Integer id = new Integer(incomes.getInt(0));
//                Call<IncomeTransaction> call = income_api.getIncomeTransaction(id);
//                call.enqueue(new Callback<IncomeTransaction>() {
//                    @Override
//                    public void onResponse(Call<IncomeTransaction> call, Response<IncomeTransaction> response) {
//                        int status = response.code();
//                        int ids = response.body().getId(id); // api dummy can't dynamic
//                        int pos = incomes.getPosition();
//
//                        Log.e("cek id local", String.valueOf(id));
//                        Log.e("cek status respons", String.valueOf(status));
//                        Log.e("cek id server", String.valueOf(ids));
//
//                        if (id != ids) {
//                            postApi(pos);
//                        } else if (id == ids) {
//                            putApi(pos);
//                        }
//                        progresStop();
//                    }
//
//                    @Override
//                    public void onFailure(Call<IncomeTransaction> call, Throwable t) {
//                        Log.e("cek5", String.valueOf(t));
//                        tv_respond.setText(String.valueOf(t));
//                    }
//                });
//                incomes.moveToNext();
//            } while (!incomes.isLast());
//        } else {
//            Toast.makeText(SyncronizeActivity.this, "nothing to sync", Toast.LENGTH_SHORT).show();
//            progresStop();
//        }
//    }
//
//    private void postApi(int pos) {
//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://private-fc7f8-cateduit.apiary-mock.com/")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//
//        final IncomeTransactionApi income_api = retrofit.create(IncomeTransactionApi.class);
//        // POST
//        incomes.moveToPosition(pos);
//        IncomeTransaction incometransaction = new IncomeTransaction(incomes.getInt(0), incomes.getString(1), incomes.getString(2));
//        Call<IncomeTransaction> call = income_api.saveIncomeTransaction(incometransaction);
//        call.enqueue(new Callback<IncomeTransaction>() {
//            @Override
//            public void onResponse(Call<IncomeTransaction> call, Response<IncomeTransaction> response) {
//                int status = response.code();
//                tv_respond.setText(String.valueOf(status));
//                if (String.valueOf(status).equals("201")) {
//                    Toast.makeText(SyncronizeActivity.this, "Add success", Toast.LENGTH_SHORT).show();
//                } else if (String.valueOf(status).equals("400")) {
//                    Toast.makeText(SyncronizeActivity.this, "Add failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<IncomeTransaction> call, Throwable t) {
//                tv_respond.setText(String.valueOf(t));
//            }
//
//        });
//    }
//
//
//    private void putApi(int pos) {
//        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                .create();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://private-fc7f8-cateduit.apiary-mock.com/")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//
//        final IncomeTransactionApi income_api = retrofit.create(IncomeTransactionApi.class);
//        // PUT
//        incomes.moveToPosition(pos);
//        Call<IncomeTransaction> call = income_api.updateIncomeTransaction(incomes.getInt(0), new IncomeTransaction(incomes.getInt(0), incomes.getString(1), incomes.getString(2)));
//        call.enqueue(new Callback<IncomeTransaction>() {
//            @Override
//            public void onResponse(Call<IncomeTransaction> call, Response<IncomeTransaction> response) {
//                int status = response.code();
//                tv_respond.setText(String.valueOf(status));
//                if (String.valueOf(status).equals("201")) {
//                    Toast.makeText(SyncronizeActivity.this, "Update success", Toast.LENGTH_SHORT).show();
//                } else if (String.valueOf(status).equals("400")) {
//                    Toast.makeText(SyncronizeActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<IncomeTransaction> call, Throwable t) {
//                tv_respond.setText(String.valueOf(t));
//            }
//        });
//    }
//
//    private void progresStart() {
//        progressDialog = new ProgressDialog(SyncronizeActivity.this);
//        progressDialog.setTitle("Syncronize on Process");
//        progressDialog.setCancelable(false);
//        progressDialog.setProgress(0);
//        progressDialog.show();
//    }
//
//    private void progresStop() {
//        if (progressDialog.isShowing()){
//            progressDialog.dismiss();
//        }
//    }
//
//}
