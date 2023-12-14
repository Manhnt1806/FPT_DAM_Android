package manhntph29583.baithi.dam_manhntph29583.mDAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import manhntph29583.baithi.dam_manhntph29583.mDTO.Sach;
import manhntph29583.baithi.dam_manhntph29583.mDTO.ThuThu;
import manhntph29583.baithi.dam_manhntph29583.mDatabase.DbHelper;

public class ThuThuDAO {
    SQLiteDatabase db;
    DbHelper dbHelper;
    public ThuThuDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public ArrayList<ThuThu> getAll(String...agrs){
        db = dbHelper.getReadableDatabase();
        ArrayList<ThuThu> list = new ArrayList<>();
        Cursor cs = db.rawQuery("SELECT maTT, quyen, username, hoTen, matKhau FROM tb_thuthu ORDER BY maTT ASC", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()){
            int _maTT = cs.getInt(0);
            String _quyen = cs.getString(1);
            String _username = cs.getString(2);
            String _hoTen = cs.getString(3);
            String _matKhau = cs.getString(4);
            ThuThu tt = new ThuThu(_maTT, _quyen, _username, _hoTen, _matKhau);
            list.add(tt);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return list;
    }
    public ArrayList<ThuThu> getAdmin(){
        db = dbHelper.getReadableDatabase();
        ArrayList<ThuThu> list = new ArrayList<>();
        Cursor cs = db.rawQuery("SELECT * FROM tb_thuthu WHERE quyen = 'Admin'", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()){
            int _maTT = cs.getInt(0);
            String _quyen = cs.getString(1);
            String _username = cs.getString(2);
            String _hoTen = cs.getString(3);
            String _matKhau = cs.getString(4);
            ThuThu tt = new ThuThu(_maTT, _quyen, _username, _hoTen, _matKhau);
            list.add(tt);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return list;
    }
    public ArrayList<ThuThu> getThuThu(){
        db = dbHelper.getReadableDatabase();
        ArrayList<ThuThu> list = new ArrayList<>();
        Cursor cs = db.rawQuery("SELECT * FROM tb_thuthu WHERE quyen = 'Thủ thư'", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()){
            int _maTT = cs.getInt(0);
            String _quyen = cs.getString(1);
            String _username = cs.getString(2);
            String _hoTen = cs.getString(3);
            String _matKhau = cs.getString(4);
            ThuThu tt = new ThuThu(_maTT, _quyen, _username, _hoTen, _matKhau);
            list.add(tt);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return list;
    }
    public ThuThu getID(String id){
        db = dbHelper.getWritableDatabase();
        String sql="SELECT * FROM tb_thuthu WHERE username=?";
        List<ThuThu> list = getData(sql, id);
        return list.get(0);
    }
    @SuppressLint("Range")
    private List<ThuThu>getData(String sql, String...SelectArgs){
        db = dbHelper.getWritableDatabase();
        List<ThuThu> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,SelectArgs);
        while (c.moveToNext()){
            ThuThu object = new ThuThu();
            object.setMaTT(Integer.parseInt(c.getString(c.getColumnIndex("maTT"))));
            object.setQuyen(c.getString(c.getColumnIndex("quyen")));
            object.setUsername(c.getString(c.getColumnIndex("username")));
            object.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            object.setMatKhau(c.getString(c.getColumnIndex("matKhau")));
            list.add(object);
        }
        return list;
    }
    public boolean insert(ThuThu obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quyen", obj.getQuyen());
        values.put("username", obj.getUsername());
        values.put("hoTen", obj.getHoTen());
        values.put("matKhau", obj.getMatKhau());
        long row = db.insert("tb_thuthu", null, values);
        return (row>0);
    }
    public boolean UpdatePass(ThuThu obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTT", obj.getMaTT());
        values.put("quyen", obj.getQuyen());
        values.put("username", obj.getUsername());
        values.put("hoTen", obj.getHoTen());
        values.put("matKhau", obj.getMatKhau());
        String [] tham_so = new String[]{obj.getMaTT()+ ""};
        int row = db.update("tb_thuthu", values, "maTT=?", tham_so);
        return (row>0);
    }
    public boolean Delete(int obj){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String [] tham_so = new String[]{obj + ""};
        int row = db.delete("tb_thuthu", "maTT=?", tham_so);
        return (row>0);
    }
    public int checkLogin(String username, String password){
        db = dbHelper.getWritableDatabase();
        String sql = "SELECT * FROM tb_thuthu WHERE username = ? AND matKhau = ?";
        Cursor cursor = db.rawQuery(sql, new String[] {username, password});
        if(cursor.getCount()==0){
            return -1;
        }
        return 1;
    }
}
