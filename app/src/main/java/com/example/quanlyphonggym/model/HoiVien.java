package com.example.quanlyphonggym.model;

public class HoiVien {
    private int mahv;
    private String hoten;
    private String namsinh;

    public HoiVien(int mahv, String hoten, String namsinh) {
        this.mahv = mahv;
        this.hoten = hoten;
        this.namsinh = namsinh;
    }

    public int getMahv() {
        return mahv;
    }

    public void setMahv(int mahv) {
        this.mahv = mahv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(String namsinh) {
        this.namsinh = namsinh;
    }
}
