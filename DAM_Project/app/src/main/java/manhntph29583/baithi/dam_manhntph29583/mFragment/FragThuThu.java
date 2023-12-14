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
import manhntph29583.baithi.dam_manhntph29583.mAdapter.ThuThuAdapter;
import manhntph29583.baithi.dam_manhntph29583.mDAO.ThuThuDAO;
import manhntph29583.baithi.dam_manhntph29583.mDTO.ThuThu;

public class FragThuThu extends Fragment {
    RecyclerView rcvThuthu;
    ThuThuDAO dao;
    ArrayList<ThuThu> list =new ArrayList<>();
    ThuThuAdapter adapter;
    Button btnAddTT;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_thuthu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvThuthu = view.findViewById(R.id.rcv_thuthu);
        btnAddTT = view.findViewById(R.id.btn_addTT);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvThuthu.setLayoutManager(layoutManager);
        dao = new ThuThuDAO(getContext());
        list = dao.getThuThu();
        adapter = new ThuThuAdapter(getContext(), list);
        rcvThuthu.setAdapter(adapter);
        btnAddTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.thuthu_add, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                TextInputLayout inputTenTT = view.findViewById(R.id.input_ten_Ad);
                TextInputLayout inputUserTT = view.findViewById(R.id.input_user_ad);
                TextInputLayout inputPassTT = view.findViewById(R.id.input_pass_ad);
                TextInputLayout inputRePassTT = view.findViewById(R.id.input_rePass_ad);
                Button btnHuyAdd = view.findViewById(R.id.btn_exit_add_TT);
                Button btnAdd = view.findViewById(R.id.btn_add_TT);

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String quyen = "Thủ thư";
                        String tenAd = inputTenTT.getEditText().getText().toString();
                        String userAd = inputUserTT.getEditText().getText().toString();
                        String Pass = inputPassTT.getEditText().getText().toString();
                        String rePass = inputRePassTT.getEditText().getText().toString();
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
                                list.addAll(dao.getThuThu());
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
