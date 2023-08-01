package com.example.quanlyphonggym.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyphonggym.database.DbHelper;
import com.example.quanlyphonggym.model.TheHoiVien;

import java.util.ArrayList;

public class TheHoiVienDAO {
    DbHelper dbHelper;

    public TheHoiVienDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<TheHoiVien> getDSTheHoiVien(){
        ArrayList<TheHoiVien> list = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor=  sqLiteDatabase.rawQuery("SELECT thv.mathv, thv.mahv, hv.hoten, thv.manv, nv.hoten, thv.magoi, gt.tengoi, thv.ngay, thv.tragoi, thv.tienthue FROM THEHOIVIEN thv, HOIVIEN hv, NHANVIEN nv, GOI gt WHERE thv.mahv = hv.mahv and thv.manv = nv.manv AND thv.magoi = gt.magoi ORDER BY thv.mathv DESC", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new TheHoiVien(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean thaydoiTrangThai(int mathv){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tragoi", 1);
        long check = sqLiteDatabase.update("THEHOIVIEN", contentValues, "mathv = ?", new String[]{String.valueOf(mathv)});
        if(check == -1){
            return false;
        }
        return true;
    }

    public boolean themTheHoiVien(TheHoiVien theHoiVien){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mahv", theHoiVien.getMahv());
        contentValues.put("manv", theHoiVien.getManv());
        contentValues.put("magoi", theHoiVien.getMagoi());
        contentValues.put("ngay", theHoiVien.getNgay());
        contentValues.put("tragoi", theHoiVien.getTragoi());
        contentValues.put("tienthue", theHoiVien.getTienthue());

        long check = sqLiteDatabase.insert("THEHOIVIEN", null, contentValues);
        if(check == -1){
            return false;
        }
        return true;
    }
}
