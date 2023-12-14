package manhntph29583.baithi.dam_manhntph29583.mFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import manhntph29583.baithi.dam_manhntph29583.mAdapter.SachAdapter;
import manhntph29583.baithi.dam_manhntph29583.mAdapter.ThanhVienAdapter;
import manhntph29583.baithi.dam_manhntph29583.mDAO.SachDAO;
import manhntph29583.baithi.dam_manhntph29583.mDAO.ThanhVienDAO;
import manhntph29583.baithi.dam_manhntph29583.mDTO.Sach;
import manhntph29583.baithi.dam_manhntph29583.mDTO.ThanhVien;

public class FragTV extends Fragment {
    RecyclerView rcvTV;
    ThanhVienDAO dao;
    ArrayList<ThanhVien> list =new ArrayList<>();
    ThanhVienAdapter adapter;
    Button btnAddTV;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_thanhvien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvTV = view.findViewById(R.id.rcv_thanhvien);
        btnAddTV = view.findViewById(R.id.btn_add_TV);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvTV.setLayoutManager(layoutManager);
        dao = new ThanhVienDAO(getContext());
        list = dao.getAll();
        adapter = new ThanhVienAdapter(getContext(), list);
        rcvTV.setAdapter(adapter);

        btnAddTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.thanhvien_add_edit, null);
                builder.setView(view);
                TextView tvTVaddEdit = view.findViewById(R.id.tv_TV_add_edit);
                TextInputLayout inputMaTV = view.findViewById(R.id.input_ma_tv);
                TextInputLayout inputTenTV = view.findViewById(R.id.input_Ten_TV);
                TextInputLayout inputNamSinh = view.findViewById(R.id.input_nam_sinh);
                Button btnHuyAddTV = view.findViewById(R.id.btn_exit_add_edit_TV);
                Button btnAddTV = view.findViewById(R.id.btn_add_edit_TV);
                tvTVaddEdit.setText("Thêm thành viên mới");

                Dialog dialog = builder.create();
                dialog.show();
                btnAddTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String maTV = inputMaTV.getEditText().getText().toString();
                        String tenTV = inputTenTV.getEditText().getText().toString();
                        String namSinh = inputNamSinh.getEditText().getText().toString();
                        ThanhVien tv = new ThanhVien(maTV, tenTV, namSinh);
                        if(maTV.isEmpty()||tenTV.isEmpty()||namSinh.isEmpty()){
                            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                        }else {
                            if (dao.Insert(tv)) {
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                list.clear();
                                list.addAll(dao.getAll());
                                adapter.notifyDataSetChanged();
                            }else{
                                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
                btnHuyAddTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
    }
}
