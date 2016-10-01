package com.arianasp.finaltask.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arianasp.finaltask.model.TransactionExpensesData;
import com.arianasp.finaltask.model.TransactionIncomeData;

import java.util.ArrayList;

/**
 * Created by Ariana on 9/30/2016.
 */

public class DataBaseSQLite extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";

    public DataBaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String TABLE_NAME_INC = "income";
    public static final String COLUMN_IN_ID = "idinc";
    public static final String COLUMN_IN_DESC = "descriptioninc";
    public static final String COLUMN_IN_AM = "amountinc";
    public static final String COLUMN_IN_TIMESTAMP = "timeStampinc";

    public static final String TABLE_NAME_EXP = "expenses";
    public static final String COLUMN_EXP_ID = "idexp";
    public static final String COLUMN_EXP_DESC = "descriptionexp";
    public static final String COLUMN_EXP_AM = "amountexp";
    public static final String COLUMN_EXP_TIMESTAMP = "timeStampexp";

    public static final String TABLE_NAME_TMP = "temporary";
    public static final String COLUMN_TMP_ID = "idtemp";
    public static final String COLUMN_TMP_DATA = "datatemp";
    public static final String COLUMN_TMP_NT = "tablenametemp";
    public static final String COLUMN_TMP_TIMESTAMP = "timeStampexp";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME_INC + " ( " + COLUMN_IN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_IN_DESC + " TEXT, " + COLUMN_IN_AM + "TEXT, " + COLUMN_IN_TIMESTAMP + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
        db.execSQL("create table " + TABLE_NAME_EXP + " ( " + COLUMN_EXP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_EXP_DESC + " TEXT, " + COLUMN_EXP_AM + "TEXT, "+ COLUMN_IN_TIMESTAMP + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
        db.execSQL("create table " + TABLE_NAME_TMP + " ( " + COLUMN_TMP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TMP_DATA + " TEXT, " + COLUMN_TMP_NT + " TEXT, " + COLUMN_IN_TIMESTAMP + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST" + TABLE_NAME_INC);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_NAME_EXP);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_NAME_TMP);
        onCreate(db);
    }

    public boolean saveIncomeData(String descinc,String amountinc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVal = new ContentValues();
        cVal.put(COLUMN_IN_ID, "idinc");
        cVal.put(COLUMN_IN_DESC,"descinc");
        cVal.put(COLUMN_IN_AM,"amountinc");
        cVal.put(COLUMN_IN_TIMESTAMP," time('now') " );
        long temp = db.insert(TABLE_NAME_INC, null, cVal);
        return temp != -1;
    }

    public boolean saveExpensesData(String descexp,String amountexp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVal = new ContentValues();
        cVal.put(COLUMN_EXP_ID, "idexp");
        cVal.put(COLUMN_EXP_DESC,"descexp");
        cVal.put(COLUMN_EXP_AM,"amountexp");
        cVal.put(COLUMN_EXP_TIMESTAMP," time('now') " );
        long temp = db.insert(TABLE_NAME_EXP, null, cVal);
        return temp != -1;
    }

    public boolean saveTempData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVal = new ContentValues();
        cVal.put(COLUMN_TMP_ID, "idtemp");
        cVal.put(COLUMN_TMP_DATA, "datatemp");
        cVal.put(COLUMN_TMP_NT, "tablenametemp");
        cVal.put(COLUMN_TMP_TIMESTAMP, " time('now') ");
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

    public ArrayList<TransactionIncomeData> getAllDataIncome(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_INC, null);
        ArrayList<TransactionIncomeData> dataInc = new ArrayList<TransactionIncomeData>();
        TransactionIncomeData incData;
        if(cursor.getCount() >0 ){
            for (int i=0;i<cursor.getCount();i++){
                cursor.moveToNext();
                incData = new TransactionIncomeData();
                incData.setIdIncome(cursor.getString(0));
                incData.setDescriptionIncome(cursor.getString(1));
                incData.setAmountIncome(cursor.getString(2));
                dataInc.add(incData);

            }
        }
        cursor.close();
        db.close();
        return dataInc;
    }

    public ArrayList<TransactionExpensesData> getAllDataExpenses(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_EXP, null);
        ArrayList<TransactionExpensesData> dataExp = new ArrayList<TransactionExpensesData>();
        TransactionExpensesData expData;
        if(cursor.getCount() >0 ){
            for (int i=0;i<cursor.getCount();i++){
                cursor.moveToNext();
                expData = new TransactionExpensesData();
                expData.setIdExpenses(cursor.getString(0));
                expData.setDescriptionExpenses(cursor.getString(1));
                expData.setAmountExpenses(cursor.getString(2));
                dataExp.add(expData);

            }
        }
        cursor.close();
        db.close();
        return dataExp;
    }
}
