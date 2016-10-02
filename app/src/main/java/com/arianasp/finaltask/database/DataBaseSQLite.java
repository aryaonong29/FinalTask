package com.arianasp.finaltask.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arianasp.finaltask.model.TransactionExpensesData;
import com.arianasp.finaltask.model.TransactionIncomeData;

/**
 * Created by Ariana on 9/30/2016.
 */

public class DataBaseSQLite extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";

    public static final String TABLE_NAME_INC = "income";
    public static final String COLUMN_IN_ID = "idinc";
    public static final String COLUMN_IN_DESC = "descriptioninc";
    public static final String COLUMN_IN_AM = "amountinc";
//    public static final String COLUMN_IN_TIMESTAMP = "timestampinc";

    public static final String TABLE_NAME_EXP = "expenses";
    public static final String COLUMN_EXP_ID = "idexp";
    public static final String COLUMN_EXP_DESC = "descriptionexp";
    public static final String COLUMN_EXP_AM = "amountexp";
//    public static final String COLUMN_EXP_TIMESTAMP = "timestampexp";

    public static final String TABLE_NAME_TMP = "temporary";
    public static final String COLUMN_TMP_ID = "idtemp";
    public static final String COLUMN_TMP_DATA = "datatemp";
    public static final String COLUMN_TMP_NT = "tablenametemp";
//    public static final String COLUMN_TMP_TIMESTAMP = "timestamptemp";

    public DataBaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table income " +
        "(idinc integer primary key,descriptioninc text,amountinc text)" );
        db.execSQL("create table expenses " +
        "(idexp integer primary key,descriptionexp text, amouuntexp text)" );
        db.execSQL("create table teporary" +
        "(idtemp integer primary key,descriptiontemp text,amounttemp text)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exist income");
        db.execSQL("drop table if exist expenses");
        db.execSQL("drop table if exist temporary");
        onCreate(db);
    }

    public Cursor getDataIncome(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from income ",null);
        return res;
    }

    public Cursor getDataExpenses(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from expenses",null);
        return res;
    }

    public boolean insertIncomeData(String descinc,String amountinc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVal = new ContentValues();
        cVal.put("descriptioninc",descinc);
        cVal.put("amountinc",amountinc);
//        cVal.put(COLUMN_IN_TIMESTAMP," timestampinc " );
        db.insert(TABLE_NAME_INC, null, cVal);
        return true;
    }

    public boolean insertExpensesData(String descexp,String amountexp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVal = new ContentValues();
        cVal.put("descriptionexp",descexp);
        cVal.put("amountexp",amountexp);
//        cVal.put(COLUMN_EXP_TIMESTAMP," timestampexp " );
        long temp = db.insert(TABLE_NAME_EXP, null, cVal);
        return temp != -1;
    }

    public boolean insertTempData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVal = new ContentValues();
        cVal.put(COLUMN_TMP_ID, "idtemp");
        cVal.put(COLUMN_TMP_DATA, "datatemp");
        cVal.put(COLUMN_TMP_NT, "tablenametemp");
//        cVal.put(COLUMN_TMP_TIMESTAMP, " timestamptemp ");
        long temp = db.insert(TABLE_NAME_TMP, null, cVal);
        return temp != -1;
    }

    public void updateIncomeData(TransactionIncomeData tid){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cVal = new ContentValues();
        cVal.put(COLUMN_IN_DESC, tid.getDescriptionIncome());
        cVal.put(COLUMN_IN_AM, tid.getAmountIncome());
        db.update(TABLE_NAME_INC, cVal, COLUMN_IN_ID + " = ?", new String[]{tid.getIdIncome()});
        db.close();

    }

    public void updateExpensesData(TransactionExpensesData ted){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cVal = new ContentValues();
        cVal.put(COLUMN_EXP_DESC, ted.getDescriptionExpenses());
        cVal.put(COLUMN_EXP_AM, ted.getAmountExpenses());
        db.update(TABLE_NAME_EXP, cVal, COLUMN_EXP_ID + " = ?", new String[]{ted.getIdExpenses()});
        db.close();
    }

    public void deleteIncomeData(TransactionIncomeData tid){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME_INC, COLUMN_IN_ID + " = ?", new String[]{tid.getIdIncome()});
        db.close();
    }

    public void deleteExpensesData(TransactionExpensesData tid){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME_EXP, COLUMN_EXP_ID + " = ?", new String[]{tid.getIdExpenses()});
        db.close();
    }

//    public ArrayList<TransactionIncomeData> getAllDataIncome(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_INC, null);
//        ArrayList<TransactionIncomeData> dataInc = new ArrayList<TransactionIncomeData>();
//        TransactionIncomeData incData;
//        if(cursor.getCount() >0 ){
//            for (int i=0;i<cursor.getCount();i++){
//                cursor.moveToNext();
//                incData = new TransactionIncomeData();
//                incData.setIdIncome(cursor.getString(0));
//                incData.setDescriptionIncome(cursor.getString(1));
//                incData.setAmountIncome(cursor.getString(2));
//                dataInc.add(incData);
//
//            }
//        }
//        cursor.close();
//        db.close();
//        return dataInc;
//    }
//
//    public ArrayList<TransactionExpensesData> getAllDataExpenses(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_EXP, null);
//        ArrayList<TransactionExpensesData> dataExp = new ArrayList<TransactionExpensesData>();
//        TransactionExpensesData expData;
//        if(cursor.getCount() >0 ){
//            for (int i=0;i<cursor.getCount();i++){
//                cursor.moveToNext();
//                expData = new TransactionExpensesData();
//                expData.setIdExpenses(cursor.getString(0));
//                expData.setDescriptionExpenses(cursor.getString(1));
//                expData.setAmountExpenses(cursor.getString(2));
//                dataExp.add(expData);
//
//            }
//        }
//        cursor.close();
//        db.close();
//        return dataExp;
//    }
}
