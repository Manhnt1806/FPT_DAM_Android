package manhntph29583.baithi.dam_manhntph29583.mDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import manhntph29583.baithi.dam_manhntph29583.mDTO.ThanhVien;
import manhntph29583.baithi.dam_manhntph29583.mDatabase.DbHelper;

public class ThanhVienDAO {
    SQLiteDatabase db;
    DbHelper dbHelper;
    public ThanhVienDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public ArrayList<ThanhVien> getAll(){
        db = dbHelper.getReadableDatabase();
        ArrayList<ThanhVien> list = new ArrayList<>();
        Cursor cs = db.rawQuery("SELECT maTV, hoTen, namSinh FROM tb_thanhvien", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()){
            String _maTV = cs.getString(0);
            String _hoTen = cs.getString(1);
            String _namSinh = cs.getString(2);
            ThanhVien ls = new ThanhVien(_maTV, _hoTen, _namSinh);
            list.add(ls);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return list;
    }
    public boolean Insert(ThanhVien obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTV", obj.getMaTV());
        values.put("hoTen", obj.getHoTen());
        values.put("namSinh", obj.getNamSinh());
        long row = db.insert("tb_thanhvien", null, values);
        return (row>0);
    }
    public boolean Update(ThanhVien obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTV", obj.getMaTV());
        values.put("hoTen", obj.getHoTen());
        values.put("namSinh", obj.getNamSinh());
        long row = db.update("tb_thanhvien", values, "maTV=?", new String[]{String.valueOf(obj.getMaTV())});
        return row>0;
    }
    public boolean Delete(String id){
        db = dbHelper.getWritableDatabase();
        long row = db.delete("tb_thanhvien", "maTV=?", new String[]{id});
        return row>0;
    }

}
