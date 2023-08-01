package com.example.quanlyphonggym.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyphonggym.database.DbHelper;
import com.example.quanlyphonggym.model.LoaiGoi;

import java.util.ArrayList;

public class LoaiGoiDAO {
    DbHelper dbHelper;

    public LoaiGoiDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    //lấy danh sách loại sách
    public ArrayList<LoaiGoi> getDSLoaiGoi(){
        ArrayList<LoaiGoi> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAIGOI", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new LoaiGoi(cursor.getInt(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    //thêm loại gói
    public boolean themLoaiGoi(String tengoi){
        SQLiteDatabase sqLiteDatabase =dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tengoi);
        long check = sqLiteDatabase.insert("LOAIGOI", null, contentValues);
        if(check == -1)
            return false;
        return true;
    }

    //xóa loại sách
    //1: xóa thành công - 0:Xóa thất bại - -1:có sách tồn tại k thể xóa
    public int xoaLoaiGoi(int id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM GOI WHERE maloai = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }

        long check = sqLiteDatabase.delete("LOAIGOI", "maloai = ?", new String[]{String.valueOf(id)});
        if (check == -1)
            return 0;
        return 1;
    }

    public boolean thayDoiLoaiGoi(LoaiGoi loaiGoi){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", loaiGoi.getTenloai());
        long check = sqLiteDatabase.update("LOAIGOI", contentValues, "maloai = ?", new String[]{String.valueOf(loaiGoi.getId())});
        if(check == -1)
            return false;
        return true;
    }
}
