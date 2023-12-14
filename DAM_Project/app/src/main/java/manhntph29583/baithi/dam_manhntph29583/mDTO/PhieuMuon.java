package manhntph29583.baithi.dam_manhntph29583.mDTO;

import java.util.Date;

public class PhieuMuon {
    int maPM;
    String maTV;
    int maSach;
    Date ngay;
    int tienThue;
    int traSach;
    String tenSach;

    public PhieuMuon() {
    }

    public PhieuMuon(int maPM, String maTV, int maSach, Date ngay, int tienThue, int traSach, String tenSach) {
        this.maPM = maPM;
        this.maTV = maTV;
        this.maSach = maSach;
        this.ngay = ngay;
        this.tienThue = tienThue;
        this.traSach = traSach;
        this.tenSach = tenSach;
    }

    public PhieuMuon(String maTV, int maSach, Date ngay, int tienThue) {
        this.maTV = maTV;
        this.maSach = maSach;
        this.ngay = ngay;
        this.tienThue = tienThue;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public String getMaTV() {
        return maTV;
    }

    public void setMaTV(String maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
}
