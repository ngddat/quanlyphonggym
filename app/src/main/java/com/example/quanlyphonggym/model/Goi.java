package com.example.quanlyphonggym.model;

public class Goi {
    private int magoi;
    private String tengoi;
    private int giagoi;
    private int maloai;

    private int soluongdaumuon;
    private String tenloai;

    public Goi(int magoi, String tengoi, int giagoi, int maloai) {
        this.magoi = magoi;
        this.tengoi = tengoi;
        this.giagoi = giagoi;
        this.maloai = maloai;
    }

    public Goi(int magoi, String tengoi, int soluongdaumuon) {
        this.magoi = magoi;
        this.tengoi = tengoi;
        this.soluongdaumuon = soluongdaumuon;
    }

    public Goi(int magoi, String tengoi, int giagoi, int maloai, String tenloai) {
        this.magoi = magoi;
        this.tengoi = tengoi;
        this.giagoi = giagoi;
        this.maloai = maloai;
        this.tenloai = tenloai;
    }

    public int getMagoi() {
        return magoi;
    }

    public void setMagoi(int magoi) {
        this.magoi = magoi;
    }

    public String getTengoi() {
        return tengoi;
    }

    public void setTengoi(String tengoi) {
        this.tengoi = tengoi;
    }

    public int getGiagoi() {
        return giagoi;
    }

    public void setGiagoi(int giagoi) {
        this.giagoi = giagoi;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public int getSoluongdaumuon() {
        return soluongdaumuon;
    }

    public void setSoluongdaumuon(int soluongdaumuon) {
        this.soluongdaumuon = soluongdaumuon;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }
}
