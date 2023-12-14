package manhntph29583.baithi.dam_manhntph29583.mFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import manhntph29583.baithi.dam_manhntph29583.R;
import manhntph29583.baithi.dam_manhntph29583.mDAO.ThuThuDAO;
import manhntph29583.baithi.dam_manhntph29583.mDTO.ThuThu;

public class FragDoiMk extends Fragment {
    TextInputLayout edPassCu, edPassMoi, edPass2;
    Button btnSave, btnHuy;
    ThuThuDAO dao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_doimk, container, false);
        edPassCu = v.findViewById(R.id.input_edt_pass_cu);
        edPassMoi = v.findViewById(R.id.input_edit_pass_moi);
        edPass2 = v.findViewById(R.id.input_edit_pass_moi2);
        btnSave = v.findViewById(R.id.btn_luu_doiMK);
        btnHuy = v.findViewById(R.id.btn_Huy_doiMK);
        dao = new ThuThuDAO(getActivity());
        ThuThu thuThu = (ThuThu) getArguments().get("doiPass");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = thuThu.getUsername();
                if(validate()>0){
                    ThuThu thuThu = dao.getID(user);
                    thuThu.setMatKhau(edPassMoi.getEditText().getText().toString());
                    if(dao.UpdatePass(thuThu)){
                        Toast.makeText(getContext(), "Thay đổi hoàn tất!", Toast.LENGTH_SHORT).show();
                        edPassCu.getEditText().setText("");
                        edPassMoi.getEditText().setText("");
                        edPass2.getEditText().setText("");
                    }else {
                        Toast.makeText(getContext(), "Thay đổi thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPassCu.getEditText().setText("");
                edPassMoi.getEditText().setText("");
                edPass2.getEditText().setText("");
            }
        });
        return v;
    }
    public int validate(){
        int check = 1;
        if(edPassCu.getEditText().getText().length()==0 || edPassMoi.getEditText().getText().length()==0 || edPass2.getEditText().getText().length()==0){
            Toast.makeText(getContext(), "Vui lòng nhập nhập đủ!", Toast.LENGTH_SHORT).show();
            check = -1;
        }else{
            ThuThu thuThu = (ThuThu) getArguments().get("doiPass");
            String passCu = thuThu.getMatKhau();
            String passMoi = edPassMoi.getEditText().getText().toString();
            String passMoi2 = edPass2.getEditText().getText().toString();
            if(!passCu.equals(edPassCu.getEditText().getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu cũ chưa đúng!", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if(!passMoi.equals(passMoi2)){
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if(passCu.equals(passMoi)){
                Toast.makeText(getContext(), "Mật khẩu mới trùng khớp mật khẩu cũ", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}
