package manhntph29583.baithi.dam_manhntph29583.mFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import manhntph29583.baithi.dam_manhntph29583.R;
import manhntph29583.baithi.dam_manhntph29583.mAdapter.LoaiSachAdapter;
import manhntph29583.baithi.dam_manhntph29583.mAdapter.ThuThuAdapter;
import manhntph29583.baithi.dam_manhntph29583.mDAO.LoaiSachDAO;
import manhntph29583.baithi.dam_manhntph29583.mDAO.ThuThuDAO;
import manhntph29583.baithi.dam_manhntph29583.mDTO.LoaiSach;
import manhntph29583.baithi.dam_manhntph29583.mDTO.Sach;
import manhntph29583.baithi.dam_manhntph29583.mDTO.ThuThu;

public class FragAdmin extends Fragment {
    RecyclerView rcvAdmin;
    ThuThuDAO dao;
    ArrayList<ThuThu> list =new ArrayList<>();
    ThuThuAdapter adapter;
    Button btnAddAdm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvAdmin = view.findViewById(R.id.rcv_admin);
        btnAddAdm = view.findViewById(R.id.btn_addAD);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvAdmin.setLayoutManager(layoutManager);
        dao = new ThuThuDAO(getContext());
        list = dao.getAdmin();
        adapter = new ThuThuAdapter(getContext(), list);
        rcvAdmin.setAdapter(adapter);
        btnAddAdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.thuthu_add, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                TextInputLayout inputTenAd = view.findViewById(R.id.input_ten_Ad);
                TextInputLayout inputUserAd = view.findViewById(R.id.input_user_ad);
                TextInputLayout inputPassAd = view.findViewById(R.id.input_pass_ad);
                TextInputLayout inputRePass = view.findViewById(R.id.input_rePass_ad);
                Button btnHuyAdd = view.findViewById(R.id.btn_exit_add_TT);
                Button btnAdd = view.findViewById(R.id.btn_add_TT);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String quyen = "Admin";
                        String tenAd = inputTenAd.getEditText().getText().toString();
                        String userAd = inputUserAd.getEditText().getText().toString();
                        String Pass = inputPassAd.getEditText().getText().toString();
                        String rePass = inputRePass.getEditText().getText().toString();
                        ThuThu tt = new ThuThu(quyen, userAd, tenAd, Pass);
                        if(tenAd.isEmpty()||userAd.isEmpty()||Pass.isEmpty()||rePass.isEmpty()){
                            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                        }else if(!Pass.equals(rePass)) {
                            Toast.makeText(getContext(), "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
                        }else{
                            if (dao.insert(tt)) {
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                list.clear();
                                list.addAll(dao.getAdmin());
                                adapter.notifyDataSetChanged();
                            }else{
                                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
                btnHuyAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });
    }
}
