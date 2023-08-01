package com.example.quanlyphonggym.model;

public class TheHoiVien {
    private int mathv;
    private int mahv;
    private String manv;
    private int magoi;
    private String ngay;
    private int tragoi;
    private int tienthue;

    private String tenhv;
    private String tennv;
    private String tengoi;

    //thv.mathv, thv.mahv, hv.hoten, thv.manv, nv.hoten, thv.magoi, gt.tengoi, thv.ngay, thv.tragoi, thv.tienthue
    public TheHoiVien(int mathv, int mahv, String tenhv, String manv, String tennv, int magoi, String tengoi, String ngay, int tragoi, int tienthue) {
        this.mathv = mathv;
        this.mahv = mahv;
        this.manv = manv;
        this.magoi = magoi;
        this.ngay = ngay;
        this.tragoi = tragoi;
        this.tienthue = tienthue;
        this.tenhv = tenhv;
        this.tennv = tennv;
        this.tengoi = tengoi;
    }

    public TheHoiVien(int mahv, String manv, int magoi, String ngay, int tragoi, int tienthue) {
        this.mahv = mahv;
        this.manv = manv;
        this.magoi = magoi;
        this.ngay = ngay;
        this.tragoi = tragoi;
        this.tienthue = tienthue;
    }

    public int getMathv() {
        return mathv;
    }

    public void setMathv(int mathv) {
        this.mathv = mathv;
    }

    public int getMahv() {
        return mahv;
    }

    public void setMahv(int mahv) {
        this.mahv = mahv;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public int getMagoi() {
        return magoi;
    }

    public void setMagoi(int magoi) {
        this.magoi = magoi;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTragoi() {
        return tragoi;
    }

    public void setTragoi(int tragoi) {
        this.tragoi = tragoi;
    }

    public int getTienthue() {
        return tienthue;
    }

    public void setTienthue(int tienthue) {
        this.tienthue = tienthue;
    }

    public String getTenhv() {
        return tenhv;
    }

    public void setTenhv(String tenhv) {
        this.tenhv = tenhv;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getTengoi() {
        return tengoi;
    }

    public void setTengoi(String tengoi) {
        this.tengoi = tengoi;
    }

    @Override
    public String toString() {
        return "TheHoiVien{" +
                "mathv=" + mathv +
                ", mahv=" + mahv +
                ", manv='" + manv + '\'' +
                ", magoi=" + magoi +
                ", ngay='" + ngay + '\'' +
                ", tragoi=" + tragoi +
                ", tienthue=" + tienthue +
                ", tenhv='" + tenhv + '\'' +
                ", tennv='" + tennv + '\'' +
                ", tengoi='" + tengoi + '\'' +
                '}';
    }
}
