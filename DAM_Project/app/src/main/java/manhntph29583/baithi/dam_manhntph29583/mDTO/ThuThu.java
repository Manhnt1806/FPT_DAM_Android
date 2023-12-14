package manhntph29583.baithi.dam_manhntph29583.mDTO;

import java.io.Serializable;

public class ThuThu implements Serializable {
    int maTT;
    String quyen;
    String username;
    String hoTen;
    String matKhau;

    public ThuThu() {
    }

    public ThuThu(int maTT, String quyen, String username, String hoTen, String matKhau) {
        this.maTT = maTT;
        this.quyen = quyen;
        this.username = username;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
    }

    public ThuThu(String quyen, String username, String hoTen, String matKhau) {
        this.quyen = quyen;
        this.username = username;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
    }

    public int getMaTT() {
        return maTT;
    }

    public void setMaTT(int maTT) {
        this.maTT = maTT;
    }

    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
