package manhntph29583.baithi.dam_manhntph29583.mDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "PN_Lib";
    static final int DB_VERSION = 2;
    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE tb_thuthu(maTT INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, quyen TEXT NOT NULL, username NOT NULL UNIQUE, hoTen TEXT NOT NULL, matKhau TEXT NOT NULL)";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO tb_thuthu VALUES(null, 'Admin', 'Manhnt', 'Mạnh', '11111111')";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO tb_thuthu VALUES(null, 'Thủ thư', 'Gamnt', 'Gấm', '22222222')";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE tb_thanhvien(maTV TEXT PRIMARY KEY UNIQUE, hoTen TEXT NOT NULL, namSinh TEXT NOT NULL)";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO tb_thanhvien VALUES('PH29583', 'Mạnh Nguyễn', '2003')";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO tb_thanhvien VALUES('PH37862', 'Gấm Nguyễn', '2004')";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE tb_loaisach(maLoai INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, tenLoai TEXT NOT NULL UNIQUE)";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO tb_loaisach VALUES(null , 'Tiểu thuyết')";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO tb_loaisach VALUES(null , 'Lập trình')";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE tb_sach(maSach INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, tenSach TEXT NOT NULL UNIQUE, giaThue INTEGER NOT NULL," +
                " maLoai INTEGER REFERENCES tb_loaisach(maLoai))";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO tb_sach VALUES(null, 'nhà giả kim', '10000', 1)";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO tb_sach VALUES(null, 'Android', '20000', 2)";
        sqLiteDatabase.execSQL(sql);
        sql = "CREATE TABLE tb_phieumuon(maPM INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, maTV TEXT REFERENCES tb_thanhvien(maTV)," +
                "maSach INTEGER REFERENCES tb_sach(maSach), tienThue INTEGER NOT NULL, ngay DATE NOT NULL, traSach INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO tb_phieumuon VALUES(null, 'PH37862', 1, '10000', '2023/01/02', 0)";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO tb_phieumuon VALUES(null, 'PH29583', 2, '20000', '2023/01/05', 1)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tb_thuthu");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tb_thanhvien");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tb_loaisach");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tb_sach");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tb_phieumuon");
        onCreate(sqLiteDatabase);
    }
}
