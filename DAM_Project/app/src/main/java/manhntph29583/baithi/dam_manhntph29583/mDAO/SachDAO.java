package manhntph29583.baithi.dam_manhntph29583.mDAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import manhntph29583.baithi.dam_manhntph29583.mDTO.LoaiSach;
import manhntph29583.baithi.dam_manhntph29583.mDTO.Sach;
import manhntph29583.baithi.dam_manhntph29583.mDatabase.DbHelper;

public class SachDAO {
    SQLiteDatabase db;
    DbHelper dbHelper;

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public ArrayList<Sach> getAll(){
        db = dbHelper.getReadableDatabase();
        ArrayList<Sach> list = new ArrayList<>();
        Cursor cs = db.rawQuery("SELECT k.maSach, k.tenSach, k.giaThue, k.maLoai, l.tenLoai FROM tb_sach k, tb_loaisach l WHERE k.maLoai = l.maLoai", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()){
            int _maSach = cs.getInt(0);
            String _tenSach = cs.getString(1);
            int _giaThue = cs.getInt(2);
            int _maLoai = cs.getInt(3);
            String _tenLoai = cs.getString(4);
            Sach ls = new Sach(_maSach, _tenSach, _giaThue, _maLoai, _tenLoai);
            list.add(ls);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return list;
    }
    public boolean Insert(Sach obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSach", obj.getTenSach());
        values.put("giaThue", obj.getGiaThue());
        values.put("maLoai", obj.getMaLoai());
        long row = db.insert("tb_sach", null, values);
        return row>0;
    }
    public boolean Update(Sach obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSach", obj.getTenSach());
        values.put("giaThue", obj.getGiaThue());
        values.put("maLoai", obj.getMaLoai());
        int row = db.update("tb_sach", values, "maSach=?", new String[]{String.valueOf(obj.getMaSach())});
        return row>0;
    }
    public boolean Delete(int id){
        db = dbHelper.getWritableDatabase();
        int row = db.delete("tb_sach", "maSach=?", new String[]{id+""});
        return row>0;
    }
    public Sach getID(String id){
        db = dbHelper.getWritableDatabase();
        String sql="SELECT * FROM tb_sach WHERE maSach=?";
        List<Sach> list = getData(sql, id);
        return list.get(0);
    }
    //getData
    @SuppressLint("Range")
    private List<Sach>getData(String sql, String...SelectArgs){
        db = dbHelper.getReadableDatabase();
        List<Sach> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,SelectArgs);
        while (c.moveToNext()){
            Sach object = new Sach();
            object.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex("maSach"))));
            object.setTenSach(c.getString(c.getColumnIndex("tenSach")));
            object.setGiaThue(Integer.parseInt(c.getString(c.getColumnIndex("giaThue"))));
            object.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maLoai"))));
            list.add(object);
        }
        c.close();
        db.close();
        return list;
    }
}
