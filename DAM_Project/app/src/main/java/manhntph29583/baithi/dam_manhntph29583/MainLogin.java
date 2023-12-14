package manhntph29583.baithi.dam_manhntph29583;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import manhntph29583.baithi.dam_manhntph29583.mDAO.ThuThuDAO;
import manhntph29583.baithi.dam_manhntph29583.mDTO.ThuThu;
import manhntph29583.baithi.dam_manhntph29583.mFragment.FragDoiMk;

public class MainLogin extends AppCompatActivity {
    TextInputLayout edUsename, edPassword;
    Button btnLogin, btnExit;
    CheckBox luuMK;
    ThuThuDAO dao;
    String strUser, strPass, strQuyen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        edUsename = findViewById(R.id.input_edt_username);
        edPassword = findViewById(R.id.input_edit_password);
        btnLogin = findViewById(R.id.btn_Login_main);
        btnExit = findViewById(R.id.btn_Huy_login);
        luuMK = findViewById(R.id.luu_mk);
        dao = new ThuThuDAO(this);

        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME","");
        String pass = pref.getString("PASSWORD","");
        Boolean luuMk = pref.getBoolean("REMEMBER", false);

        edUsename.getEditText().setText(user);
        edPassword.getEditText().setText(pass);
        luuMK.setChecked(luuMk);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mDialog = new AlertDialog.Builder(view.getContext());
                mDialog.setTitle("Question");
                mDialog.setMessage("Are you sure You want to Exit?");
                mDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startActivity(startMain);
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
            }
        });
    }
    public void rememberUser(String u, String p, boolean status){
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if(!status){
            edit.clear();
        }else{
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
        }
        edit.commit();
    }
    public void checkLogin(){
        strUser = edUsename.getEditText().getText().toString();
        strPass = edPassword.getEditText().getText().toString();

        if(strUser.isEmpty()||strPass.isEmpty()){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }else {
            if(dao.checkLogin(strUser,strPass)>0){
                ThuThu tt = dao.getID(strUser);
                Bundle bundle = new Bundle();
                bundle.putSerializable("quyen", tt);
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser, strPass, luuMK.isChecked());
                Intent intent = new Intent(getApplicationContext(), MainChinh.class);
                intent.putExtra("user", strUser);
                intent.putExtras(bundle);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }
}