package com.example.quanlyphonggym.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyphonggym.database.DbHelper;

public class DoanhThuDAO {
    DbHelper dbHelper;

    public DoanhThuDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public int getDoanhThu(String ngaybatdau, String ngayketthuc){
        ngaybatdau = ngaybatdau.replace("/", "");
        ngayketthuc = ngayketthuc.replace("/", "");
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(tienthue) FROM THEHOIVIEN WHERE substr(ngay, 7) || substr(ngay, 4, 2) || substr(ngay, 1, 2) BETWEEN ? AND ?", new String[]{ngaybatdau, ngayketthuc});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
}
