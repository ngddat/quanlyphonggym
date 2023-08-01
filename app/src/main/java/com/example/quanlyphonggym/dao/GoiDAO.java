package com.example.quanlyphonggym.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyphonggym.database.DbHelper;
import com.example.quanlyphonggym.model.Goi;

import java.util.ArrayList;

public class GoiDAO {
    DbHelper dbHelper;

    public GoiDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    //lấy toàn bộ đầu gói có ở trong phòng gym
    public ArrayList<Goi> getDSDauGoi(){
        ArrayList<Goi> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT gt.magoi, gt.tengoi, gt.giagoi, gt.maloai, lg.tenloai FROM GOI gt, LOAIGOI lg WHERE gt.maloai = lg.maloai", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new Goi(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean themGoiMoi(String tengoi, int giagoi, int maloai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tengoi", tengoi);
        contentValues.put("giagoi", giagoi);
        contentValues.put("maloai", maloai);

        long check = sqLiteDatabase.insert("GOI", null, contentValues);
        if(check == -1)
            return false;
        return true;
    }

    public boolean capNhatThongTinGoi(int magoi, String tengoi, int giagoi, int maloai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tengoi", tengoi);
        contentValues.put("giagoi", giagoi);
        contentValues.put("maloai", maloai);

        long check = sqLiteDatabase.update("GOI", contentValues, "magoi = ?", new String[]{String.valueOf(magoi)});
        if(check == -1)
            return false;
        return true;
    }

    public int xoaGoi(int magoi){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THEHOIVIEN WHERE magoi = ?", new String[]{String.valueOf(magoi)});
        if(cursor.getCount() != 0){
            return -1;
        }

        long check = sqLiteDatabase.delete("GOI", "magoi = ?", new String[]{String.valueOf(magoi)});
        if(check == -1)
            return 0;
        return 1;
    }
}
