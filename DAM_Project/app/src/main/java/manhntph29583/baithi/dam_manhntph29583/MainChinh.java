package manhntph29583.baithi.dam_manhntph29583;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import manhntph29583.baithi.dam_manhntph29583.mDAO.ThuThuDAO;
import manhntph29583.baithi.dam_manhntph29583.mDTO.ThuThu;
import manhntph29583.baithi.dam_manhntph29583.mFragment.FragAdmin;
import manhntph29583.baithi.dam_manhntph29583.mFragment.FragDT;
import manhntph29583.baithi.dam_manhntph29583.mFragment.FragDoiMk;
import manhntph29583.baithi.dam_manhntph29583.mFragment.FragLS;
import manhntph29583.baithi.dam_manhntph29583.mFragment.FragPM;
import manhntph29583.baithi.dam_manhntph29583.mFragment.FragS;
import manhntph29583.baithi.dam_manhntph29583.mFragment.FragTV;
import manhntph29583.baithi.dam_manhntph29583.mFragment.FragTop10;

public class MainChinh extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawerLayout;
    TextView tvToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chinh);

        Toolbar toolbar = findViewById(R.id.toolbar);
        tvToolBar = findViewById(R.id.tv_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.nav_open, R.string.nav_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new FragPM());
        navigationView.getMenu().findItem(R.id.nav_phieumuon).setChecked(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        ThuThu thuThu = (ThuThu) getIntent().getExtras().get("quyen");
        int itemId = item.getItemId();
        Fragment fragment = new Fragment();
        switch (itemId) {
            case R.id.nav_phieumuon:
                fragment = new FragPM();
                tvToolBar.setText("Quản lý phiếu mượn");
                break;
            case R.id.nav_loaisach:
                fragment = new FragLS();
                tvToolBar.setText("Quản lý loại sách");
                break;
            case R.id.nav_sach:
                fragment = new FragS();
                tvToolBar.setText("Quản lý sách");
                break;
            case R.id.nav_thanhvien:
                fragment = new FragTV();
                tvToolBar.setText("Quản lý thành viên");
                break;
            case R.id.nav_top10:
                fragment = new FragTop10();
                tvToolBar.setText("10 sách mượn nhiều nhất");
                break;
            case R.id.nav_doanhthu:
                fragment = new FragDT();
                tvToolBar.setText("Doanh thu");
                break;
            case R.id.nav_doimk:
                    tvToolBar.setText("Đổi mật khẩu");
                    fragment = new FragDoiMk();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("doiPass", thuThu);
                    fragment.setArguments(bundle);
                break;
            case R.id.nav_taikoan:
                String quyen = thuThu.getQuyen();
                if (quyen.equals("Admin")){
                    fragment = new FragTaiKhoan();
                    tvToolBar.setText("Quản lý tài khoản");
                }
                else{
                    Toast.makeText(this, "Thủ thư không thể mở", Toast.LENGTH_SHORT).show();
                    fragment = new FragPM();
                    tvToolBar.setText("Quản lý phiếu mượn");
        }
                break;
            case R.id.nav_logtout:
                AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
                mDialog.setTitle("Question");
                mDialog.setMessage("Are you sure You want to Log out?");
                mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), MainLogin.class);
                        startActivity(intent);

                        finish();
                    }
                });
                mDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                mDialog.create().show();
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment).addToBackStack(null).commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}
