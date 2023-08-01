package com.example.quanlyphonggym.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context){
        super(context, "QLPHONGGYM", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String dbNhanVien = "CREATE TABLE NHANVIEN(manv text primary key, hoten text, matkhau text, loaitaikhoan text)";
        db.execSQL(dbNhanVien);

        String dbHoiVien = "CREATE TABLE HOIVIEN(mahv integer primary key autoincrement, hoten text, namsinh text)";
        db.execSQL(dbHoiVien);

        String dbLoai = "CREATE TABLE LOAIGOI(maloai integer primary key autoincrement, tenloai text)";
        db.execSQL(dbLoai);

        String dbGOI = "CREATE TABLE GOI(magoi integer primary key autoincrement, tengoi text, giagoi integer, maloai integer references LOAIGOI(maloai))";
        db.execSQL(dbGOI);

        String dbTheHoiVien = "CREATE TABLE THEHOIVIEN(mathv integer primary key autoincrement," +
                " mahv integer references HOIVIEN(mahv), manv text references NHANVIEN(manv), " +
                "magoi integer references GOI(magoi), ngay text, tragoi integer, tienthue integer)";
        db.execSQL(dbTheHoiVien);

        //data
        db.execSQL("INSERT INTO NHANVIEN VALUES('nguyenduydat', 'Nguyễn Duy Đạt', '123', 'Admin'), ('phamhuudinh', 'Phạm Hữu Định', '1234', 'Nhân Viên')");
        db.execSQL("INSERT INTO LOAIGOI VALUES(1, 'Rèn luyện thân thủ'), (2, 'Yoga'), (3, 'Chế độ')");
        db.execSQL("INSERT INTO GOI VALUES(1, 'Giảm căng thẳng', 3000, 2), (2, 'Boxing', 8000, 1), (3, 'Giảm cân', 7000, 3)");
        db.execSQL("INSERT INTO HOIVIEN VALUES(1, 'Lê Nguyên Sáng', 2005), (2, 'Nguyễn Thanh Hoàng', 2004)");
//        trả gói: 1: đã ĐK - 0:chưa ĐK
        db.execSQL("INSERT INTO THEHOIVIEN VALUES(1, 1, 'nguyenduydat', 1, '19/11/2022', 1, 3000), (2, 1, 'nguyenduydat', 3, '20/12/2022', 0, 7000), (3, 2, 'phamhuudinh', 2, '20/11/2022', 0, 8000)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i != i1){
            db.execSQL("DROP TABLE IF EXISTS NHANVIEN");
            db.execSQL("DROP TABLE IF EXISTS HOIVIEN");
            db.execSQL("DROP TABLE IF EXISTS LOAIGOI");
            db.execSQL("DROP TABLE IF EXISTS GOI");
            db.execSQL("DROP TABLE IF EXISTS THEHOIVIEN");

            onCreate(db);
        }
    }
}
