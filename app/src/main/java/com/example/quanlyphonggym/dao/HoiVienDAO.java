package com.example.quanlyphonggym.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyphonggym.database.DbHelper;
import com.example.quanlyphonggym.model.HoiVien;

import java.util.ArrayList;

public class HoiVienDAO {
    DbHelper dbHelper;

    public HoiVienDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<HoiVien> getDSHoiVien(){
        ArrayList<HoiVien> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM HOIVIEN", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new HoiVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themHoiVien(String hoten, String namsinh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoten", hoten);
        contentValues.put("namsinh", namsinh);
        long check = sqLiteDatabase.insert("HOIVIEN", null, contentValues);
        if(check == -1)
            return false;
        return true;
    }

    public boolean capNhatThongTinHV(int mahv, String hoten, String namsinh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoten", hoten);
        contentValues.put("namsinh", namsinh);

        long check = sqLiteDatabase.update("HOIVIEN", contentValues, "mahv = ?", new String[]{String.valueOf(mahv)});
        if(check == -1)
            return false;
        return true;
    }

    //int 1:xóa thành công, 0: thất bại, -1:tìm thấy hội viên đang có phiếu mượn
    public int xoaThongTinHV(int mahv){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THEHOIVIEN WHERE mahv = ?", new String[]{String.valueOf(mahv)});
        if(cursor.getCount() != 0){
            return -1;
        }

        long check = sqLiteDatabase.delete("HOIVIEN", "mahv = ?", new String[]{String.valueOf(mahv)});
        if(check == -1)
            return 0;
        return 1;
    }
}
