package com.example.dn;

public class Contact {
    String ngay;
    String mua;

    public Contact(String ngay, String mua) {
        this.ngay = ngay;
        this.mua = mua;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getMua() {
        return mua;
    }

    public void setMua(String mua) {
        this.mua = mua;
    }
}
