package manhntph29583.baithi.dam_manhntph29583.mFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import manhntph29583.baithi.dam_manhntph29583.R;
import manhntph29583.baithi.dam_manhntph29583.mAdapter.PhieuMuonAdapter;
import manhntph29583.baithi.dam_manhntph29583.mDAO.PhieuMuonDAO;
import manhntph29583.baithi.dam_manhntph29583.mDAO.SachDAO;
import manhntph29583.baithi.dam_manhntph29583.mDAO.ThanhVienDAO;
import manhntph29583.baithi.dam_manhntph29583.mDTO.PhieuMuon;
import manhntph29583.baithi.dam_manhntph29583.mDTO.Sach;
import manhntph29583.baithi.dam_manhntph29583.mDTO.ThanhVien;

public class FragPM extends Fragment {
    RecyclerView rcvPhieuMuon;
    PhieuMuonDAO dao;
    ArrayList<PhieuMuon> list =new ArrayList<>();
    PhieuMuonAdapter adapter;
    Button btnAddPM;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_phieumuon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvPhieuMuon = view.findViewById(R.id.rcv_phieumuon);
        btnAddPM = view.findViewById(R.id.btn_addPM);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvPhieuMuon.setLayoutManager(layoutManager);
        dao = new PhieuMuonDAO(getContext());
        list = dao.getAll();
        adapter = new PhieuMuonAdapter(getContext(), list);
        rcvPhieuMuon.setAdapter(adapter);
        btnAddPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.phieumuon_add, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                Spinner spnAddTenTV = view.findViewById(R.id.spn_add_tentv);
                Spinner spnAddTenSach = view.findViewById(R.id.spn_add_tenSach);
                TextInputLayout inputNgay = view.findViewById(R.id.intput_add_ngay);
                TextView tvNgayPM = view.findViewById(R.id.dialog_add_ngay);
                CheckBox chk = view.findViewById(R.id.chk_pm);
                EditText tienThuePM = view.findViewById(R.id.ed_tien_thue);
                Button btnHuyAdd_PM = view.findViewById(R.id.btn_exit_add_pm);
                Button btnAdd_PM = view.findViewById(R.id.btn_add_pm);

                ThanhVienDAO tvDAO = new ThanhVienDAO(getContext());
                ArrayList<ThanhVien> listTV = tvDAO.getAll();
                ArrayAdapter adapterTV = new ArrayAdapter(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, listTV);
                spnAddTenTV.setAdapter(adapterTV);

                SachDAO sDAO = new SachDAO(getContext());
                ArrayList<Sach> listS = sDAO.getAll();
                ArrayAdapter adapterS = new ArrayAdapter(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, listS);
                spnAddTenSach.setAdapter(adapterS);
                spnAddTenSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tienThuePM.setText(String.valueOf(listS.get(position).getGiaThue()));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                Date objDate = new Date(System.currentTimeMillis());
                DateFormat dateFormat = new DateFormat();
                String chuoi_ngay_thang_nam = (String) dateFormat.format("yyyy/MM/dd", objDate);
                inputNgay.getEditText().setText(chuoi_ngay_thang_nam);
                tvNgayPM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        DatePickerDialog dialog = new DatePickerDialog(getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                        int nam = i;
                                        int thang = i1;
                                        int ngay = i2;
                                        inputNgay.getEditText().setText(nam +"/"+ (thang + 1) +"/"+ ngay);
                                    }
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DATE)
                        );
                        dialog.show();
                    }
                });
                btnAdd_PM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int tienThue = Integer.parseInt(tienThuePM.getText().toString());
                        Date ngayThue = null;
                        try {
                            ngayThue = sdf.parse(inputNgay.getEditText().getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Sach s = (Sach) spnAddTenSach.getSelectedItem();
                        int idSach = s.getMaSach();
                        ThanhVien tv = (ThanhVien) spnAddTenTV.getSelectedItem();
                        String idThanhVien = tv.getMaTV();

                        PhieuMuon kt = new PhieuMuon(idThanhVien, idSach,ngayThue ,tienThue);
                        if(chk.isChecked()){
                            kt.setTraSach(1);
                        }else{
                            kt.setTraSach(0);
                        }
                        if(dao.Insert(kt)){
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(dao.getAll());
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
                btnHuyAdd_PM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
    }
}
