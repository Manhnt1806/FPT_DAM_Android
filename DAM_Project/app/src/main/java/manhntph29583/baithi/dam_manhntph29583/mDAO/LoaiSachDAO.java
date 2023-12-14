package manhntph29583.baithi.dam_manhntph29583.mDAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import manhntph29583.baithi.dam_manhntph29583.mDTO.LoaiSach;
import manhntph29583.baithi.dam_manhntph29583.mDTO.ThanhVien;
import manhntph29583.baithi.dam_manhntph29583.mDatabase.DbHelper;

public class LoaiSachDAO {
    SQLiteDatabase db;
    DbHelper dbHelper;

    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public ArrayList<LoaiSach> getAll(){
        db = dbHelper.getReadableDatabase();
        ArrayList<LoaiSach> list = new ArrayList<>();
        Cursor cs = db.rawQuery("SELECT maLoai, tenLoai FROM tb_loaisach ORDER BY maLoai ASC", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()){
            int _maLoai = cs.getInt(0);
            String _tenLoai = cs.getString(1);
            LoaiSach ls = new LoaiSach(_maLoai, _tenLoai);
            list.add(ls);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return list;
    }
    public boolean Insert(String obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", obj);
        long row = db.insert("tb_loaisach", null, values);
        return row>0;
    }
    public boolean Update(LoaiSach obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", obj.getTenLoai());
        long row = db.update("tb_loaisach", values, "maLoai=?", new String[]{String.valueOf(obj.getMaLoai())});
        return row>0;
    }
    public boolean Delete(int id){
        db = dbHelper.getWritableDatabase();
        long row = db.delete("tb_loaisach", "maLoai=?", new String[]{id+""});
        return row>0;
    }

}
