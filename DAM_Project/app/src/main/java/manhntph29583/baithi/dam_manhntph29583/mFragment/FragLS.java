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
import manhntph29583.baithi.dam_manhntph29583.mAdapter.LoaiSachAdapter;
import manhntph29583.baithi.dam_manhntph29583.mAdapter.PhieuMuonAdapter;
import manhntph29583.baithi.dam_manhntph29583.mDAO.LoaiSachDAO;
import manhntph29583.baithi.dam_manhntph29583.mDAO.PhieuMuonDAO;
import manhntph29583.baithi.dam_manhntph29583.mDTO.LoaiSach;
import manhntph29583.baithi.dam_manhntph29583.mDTO.PhieuMuon;

public class FragLS extends Fragment {
    RecyclerView rcvLoaiSach;
    LoaiSachDAO dao;
    ArrayList<LoaiSach> list =new ArrayList<>();
    LoaiSachAdapter adapter;
    Button btnAddLS;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_loaisach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvLoaiSach = view.findViewById(R.id.rcv_loaisach);
        btnAddLS = view.findViewById(R.id.btn_addLS);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvLoaiSach.setLayoutManager(layoutManager);
        dao = new LoaiSachDAO(getContext());
        list = dao.getAll();
        adapter = new LoaiSachAdapter(getContext(), list);
        rcvLoaiSach.setAdapter(adapter);

        btnAddLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.loaisach_add_edit, null);
                builder.setView(view);
                TextView tvLSaddEdit = view.findViewById(R.id.tv_LS_add_edit);
                TextInputLayout inputTenLS = view.findViewById(R.id.input_TenLoaiSach);
                Button btnHuyAddLS = view.findViewById(R.id.btn_exit_add_edit_LS);
                Button btnAddLS = view.findViewById(R.id.btn_add_edit_LS);
                tvLSaddEdit.setText("Thêm loại sách mới");

                Dialog dialog = builder.create();
                dialog.show();

                btnAddLS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tenLS = inputTenLS.getEditText().getText().toString();
                        if(tenLS.isEmpty()){
                            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                        }else {
                            if (dao.Insert(tenLS)) {
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
                btnHuyAddLS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
    }
}
