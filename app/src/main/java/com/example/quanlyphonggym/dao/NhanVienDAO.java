package com.example.quanlyphonggym.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlyphonggym.database.DbHelper;

public class NhanVienDAO {
    DbHelper dbHelper;
    SharedPreferences sharedPreferences;

    public NhanVienDAO(Context context){
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("THONGTIN", context.MODE_PRIVATE);
    }

    public boolean checkDangNhap(String manv, String matkhau){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NHANVIEN WHERE manv = ? AND matkhau = ?", new String[]{manv, matkhau});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("manv", cursor.getString(0));
            editor.putString("loaitaikhoan", cursor.getColumnName(3));
            editor.commit();
            return true;
        }else {
            return false;
        }
    }

    public int capNhatMatKhau(String username, String oldPass, String newPass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NHANVIEN WHERE manv = ? AND matkhau = ?", new String[]{username, oldPass});
        if(cursor.getCount() > 0 ){
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", newPass);
            long check = sqLiteDatabase.update("NHANVIEN", contentValues, "manv = ?", new String[]{username});
            if(check == -1)
                return -1;
            return 1;
        }
        return 0;
    }
}
