package manhntph29583.baithi.dam_manhntph29583.mDAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import manhntph29583.baithi.dam_manhntph29583.mDTO.PhieuMuon;
import manhntph29583.baithi.dam_manhntph29583.mDTO.Sach;
import manhntph29583.baithi.dam_manhntph29583.mDTO.Top10;
import manhntph29583.baithi.dam_manhntph29583.mDatabase.DbHelper;

public class PhieuMuonDAO {
    private SQLiteDatabase db;
    private Context context;
    DbHelper dbHelper;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public PhieuMuonDAO(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public ArrayList<PhieuMuon> getAll(){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String sql = "SELECT k.maPM, k.maTV, k.maSach, k.tienThue, k.ngay, k.traSach, l.tenSach FROM tb_phieumuon k, tb_sach l WHERE k.maSach = l.maSach";
        Cursor cs = db.rawQuery(sql, null);
        if (cs.getCount()>0) {
            cs.moveToFirst();
            do{
                int _maPM = cs.getInt(0);
                String _maTV = cs.getString(1);
                int _maSach = cs.getInt(2);
                int _tienThue = cs.getInt(3);
                Date _ngay = new Date(System.currentTimeMillis());
                try {
                    _ngay = sdf.parse(cs.getString(4));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int _traSach = cs.getInt(5);
                String _tenSach = cs.getString(6);
                list.add(new PhieuMuon(_maPM, _maTV, _maSach, _ngay, _tienThue, _traSach, _tenSach));
            }while (cs.moveToNext());
        }
        cs.close();
        db.close();
        return list;
    }
    public boolean Insert(PhieuMuon obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTV", obj.getMaTV());
        values.put("maSach", obj.getMaSach());
        values.put("ngay", sdf.format(obj.getNgay()));
        values.put("tienThue", obj.getTienThue());
        values.put("traSach", obj.getTraSach());
        long row = db.insert("tb_phieumuon", null, values);
        return row>0;
    }
    public boolean Update(PhieuMuon obj){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maPM", obj.getMaPM());
        values.put("maTV", obj.getMaTV());
        values.put("maSach", obj.getMaSach());
        values.put("ngay", sdf.format(obj.getNgay()));
        values.put("tienThue", obj.getTienThue());
        values.put("traSach", obj.getTraSach());
        long row = db.update("tb_phieumuon", values, "maPM=?", new String[]{String.valueOf(obj.getMaPM())});
        return row>0;
    }
    public boolean Delete(int id){
        db = dbHelper.getWritableDatabase();
        long row = db.delete("tb_phieumuon", "maPM=?", new String[]{id+""});
        return row>0;
    }

    @SuppressLint("Range")
    public ArrayList<Top10> getTop10(){
        ArrayList<Top10> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String sqlTop10 = "SELECT maSach, count(maSach) as soLuong FROM tb_phieumuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        SachDAO sachDAO = new SachDAO(context);
        Cursor cs = db.rawQuery(sqlTop10, null);
        if (cs.getCount()>0) {
            cs.moveToFirst();
            do{
                Top10 top10 = new Top10();
                @SuppressLint("Range")
                Sach sach = sachDAO.getID(cs.getString(cs.getColumnIndex("maSach")));
                top10.setTenSach(sach.getTenSach());
                top10.setSoLuong(Integer.parseInt(cs.getString(cs.getColumnIndex("soLuong"))));
                list.add(top10);
            }while (cs.moveToNext());
        }
        cs.close();
        db.close();
        return list;
    }
    public int DoanhThu(String tuNgay, String denNgay){
        db = dbHelper.getWritableDatabase();
        int dt = 0;
        String sqlDT = "SELECT SUM(tienThue) as doanhThu FROM tb_phieumuon WHERE ngay BETWEEN ? AND ?";
        Cursor cs = db.rawQuery(sqlDT, new String[]{tuNgay, denNgay});
        if (cs.getCount() != 0) {
            cs.moveToFirst();
            dt = cs.getInt(0);
        }
        return dt;
    }
}
