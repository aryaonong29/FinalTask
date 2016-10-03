package com.arianasp.finaltask.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arianasp.finaltask.model.TransactionIncomeData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ariana on 9/30/2016.
 */

public class DataBaseSQLite extends SQLiteOpenHelper {

    public static final int database_version = 1;
    public static final String database_name = "SQLiteDatabase.db";

    public static final String table_income = "income";
    public static final String inc_id = "id";
    public static final String inc_desc = "desc";
    public static final String inc_amt = "amount";

    public DataBaseSQLite(Context context){
        super(context, database_name,null,database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_inc_table = "create table " + table_income + "("
                + inc_id + " integer primary key," + inc_desc + " text,"
                + inc_amt + " text" + ")";
        db.execSQL(create_inc_table);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + table_income);
        onCreate(db);
    }

    //adding data
    void addIncomeData(TransactionIncomeData tid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conval = new ContentValues();
        conval.put(inc_desc,tid.getDescriptionIncome());
        conval.put(inc_amt, tid.getAmountIncome());

        //insert baris
        db.insert(table_income,null,conval);
        db.close();
    }


    //baca baris pada tabel, get single data
    public TransactionIncomeData getDataInc(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(table_income, new String[]{inc_id,inc_desc,inc_amt},
                inc_id + "=?",new String[]{String.valueOf(id)}, null,null,null,null);
        if(cursor !=null){
            cursor.moveToFirst();
        }
        TransactionIncomeData tid = new TransactionIncomeData(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2));
        return tid;
    }

    //ambil semua data income
    public List<TransactionIncomeData> getAllIncome(){
        List<TransactionIncomeData> tidList = new ArrayList<TransactionIncomeData>();
        //SELECT QUERY
        String selectQuery= "select * from " + table_income;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        //looping ke semua baris dan menambahkan pada list
        if(cursor.moveToFirst()){
            do{
                TransactionIncomeData tid = new TransactionIncomeData();
                tid.setIdIncome(Integer.parseInt(cursor.getString(0)));
                tid.setDescriptionIncome(cursor.getString(1));
                tid.setAmountIncome(cursor.getString(2));
                tidList.add(tid);
            }while(cursor.moveToNext());
        }
        return tidList;
    }

    //ambil total data income
    public int getIncomeCount(){
        String countQuery = "select * from " + table_income;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        return cursor.getCount();

    }
    //update ke id= xxx
    public int updateIncome(TransactionIncomeData tid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conval = new ContentValues();
        conval.put(inc_desc, tid.getDescriptionIncome());
        conval.put(inc_amt, tid.getAmountIncome());

        return db.update(table_income,conval, inc_id + "= ?",
                new String[]{String.valueOf(tid.getIdIncome())});
    }

    //delete data
    public void deleteIncome(TransactionIncomeData tid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_income, inc_id + "= ?",
                new String[]{String.valueOf(tid.getIdIncome())});
    }

}
