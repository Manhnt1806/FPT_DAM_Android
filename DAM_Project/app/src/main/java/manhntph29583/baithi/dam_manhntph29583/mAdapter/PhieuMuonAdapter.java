package manhntph29583.baithi.dam_manhntph29583.mAdapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import manhntph29583.baithi.dam_manhntph29583.R;
import manhntph29583.baithi.dam_manhntph29583.mDAO.PhieuMuonDAO;
import manhntph29583.baithi.dam_manhntph29583.mDAO.SachDAO;
import manhntph29583.baithi.dam_manhntph29583.mDAO.ThanhVienDAO;
import manhntph29583.baithi.dam_manhntph29583.mDTO.PhieuMuon;
import manhntph29583.baithi.dam_manhntph29583.mDTO.Sach;
import manhntph29583.baithi.dam_manhntph29583.mDTO.ThanhVien;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {
    Context context;
    ArrayList<PhieuMuon> list;
    PhieuMuonDAO pmDao;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        pmDao = new PhieuMuonDAO(context);
    }


    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.cv_phieumuon, parent, false);
        PhieuMuonAdapter.PhieuMuonViewHolder viewHolder = new PhieuMuonAdapter.PhieuMuonViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonAdapter.PhieuMuonViewHolder holder, int position) {
        PhieuMuon pm = list.get(position);
        if(pm.getTraSach()==0){
            holder.tvTraSach.setTextColor(Color.RED);
            holder.tvTraSach.setText("Chưa trả sách");
        }else{
            holder.tvTraSach.setTextColor(Color.BLUE);
            holder.tvTraSach.setText("Đã trả sách");
        }
        holder.tvTienThue.setText(pm.getTienThue()+"");
        holder.tvTenSach.setText(pm.getTenSach());
        holder.tvNgay.setText(simpleDateFormat.format(pm.getNgay()));
        holder.tvMaTV.setText(pm.getMaTV());
        holder.cvPhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                view = inflater.inflate(R.layout.dialog_chi_tiet, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                TextView tvMaPM = view.findViewById(R.id.tv_chi_tiet);
                TextView tvTenSachPM = view.findViewById(R.id.tv_tenloai_ct);
                TextView tvTienThue = view.findViewById(R.id.tv_tien_ct);
                TextView tvTenThanhVien = view.findViewById(R.id.tv_ten_TV_ct);
                TextView tvNgayCT = view.findViewById(R.id.tv_ngay_ct);
                TextView tvNoteCT = view.findViewById(R.id.tv_note_ct);
                ImageView btnBack = view.findViewById(R.id.btn_back_ct);
                ImageView btnDel = view.findViewById(R.id.btn_del_ct);
                Button btnEdit = view.findViewById(R.id.btn_edit_ct);
                tvTenSachPM.setText(pm.getTenSach());
                tvTienThue.setText(pm.getTienThue()+"");
                tvTenThanhVien.setText(pm.getMaTV()+"");
                tvMaPM.setText("Phiếu mượn số: "+pm.getMaPM());
                tvNgayCT.setText(simpleDateFormat.format(pm.getNgay()));
                if(pm.getTraSach()==1){
                    tvNoteCT.setText("Đã trả sách");
                }else{
                    tvNoteCT.setText("Chưa trả sách");
                }
                btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                        builder.setTitle("Bạn có chắc muốn xóa?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(pmDao.Delete(pm.getMaPM())){
                                    Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                    list.clear();
                                    list.addAll(pmDao.getAll());
                                    notifyDataSetChanged();
                                    dialogInterface.dismiss();
                                    dialog.dismiss();
                                }else{
                                    Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                });
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                        view = inflater.inflate(R.layout.phieumuon_add, null);
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

                        ThanhVienDAO tvDAO = new ThanhVienDAO(context);
                        ArrayList<ThanhVien> listTV = tvDAO.getAll();
                        ArrayAdapter adapterTV = new ArrayAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, listTV);
                        spnAddTenTV.setAdapter(adapterTV);
                        for(int i=0; i< listTV.size(); i++){
                            if(listTV.get(i).getHoTen().equalsIgnoreCase(String.valueOf(listTV.get(i)))){
                                spnAddTenTV.setSelection(i);
                            }
                        }
                        SachDAO sDAO = new SachDAO(context);
                        ArrayList<Sach> listS = sDAO.getAll();
                        ArrayAdapter adapterS = new ArrayAdapter(context,
                                android.R.layout.simple_spinner_dropdown_item, listS);
                        spnAddTenSach.setAdapter(adapterS);
                        for(int i=0; i< listS.size(); i++){
                            if(pm.getTenSach().equalsIgnoreCase(String.valueOf(listS.get(i)))){
                                spnAddTenSach.setSelection(i);
                            }
                        }
                        spnAddTenSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                tienThuePM.setText(String.valueOf(listS.get(position).getGiaThue()));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        if(tvNoteCT.getText()=="Đã trả sách"){
                            chk.setChecked(true);
                        }else{
                            chk.setChecked(false);
                        }

                        tienThuePM.setText(pm.getTienThue()+"");
                        inputNgay.getEditText().setText(simpleDateFormat.format(pm.getNgay()));
                        tvNgayPM.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                DatePickerDialog dialog = new DatePickerDialog(context,
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
                            public void onClick(View v) {
                                Sach s = (Sach) spnAddTenSach.getSelectedItem();
                                int idSach = s.getMaSach();
                                pm.setMaSach(idSach);
                                ThanhVien tv = (ThanhVien) spnAddTenTV.getSelectedItem();
                                String idThanhVien = tv.getMaTV();
                                pm.setMaTV(idThanhVien);
                                pm.setTienThue(Integer.parseInt(tienThuePM.getText().toString()));
                                try {
                                    pm.setNgay(simpleDateFormat.parse(inputNgay.getEditText().getText().toString()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (chk.isChecked()) {
                                    pm.setTraSach(1);
                                    tvNoteCT.setText("Đã trả sách");
                                } else {
                                    pm.setTraSach(0);
                                    tvNoteCT.setText("Chưa trả sách");
                                }

                                if (pmDao.Update(pm)) {
                                    Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    list.clear();
                                    list.addAll(pmDao.getAll());
                                    notifyDataSetChanged();

                                    s = (Sach) spnAddTenSach.getSelectedItem();
                                    tvTenSachPM.setText(s.getTenSach());
                                    tv = (ThanhVien) spnAddTenTV.getSelectedItem();
                                    tvTenThanhVien.setText(tv.getHoTen());
                                    tvTienThue.setText(pm.getTienThue()+"");
                                    tvNgayCT.setText(simpleDateFormat.format(pm.getNgay()));

                                }else{
                                    Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();

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
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder {
        TextView tvNgay, tvTraSach, tvTenSach, tvMaTV, tvTienThue;
        ImageView imgPM;
        CardView cvPhieuMuon;
        public PhieuMuonViewHolder(@NonNull View view) {
            super(view);
            tvNgay = view.findViewById(R.id.tv_ngay);
            tvTraSach = view.findViewById(R.id.tv_tra_sach);
            tvTenSach = view.findViewById(R.id.tv_ten_sach_PM);
            tvMaTV = view.findViewById(R.id.tv_ma_thanh_vien_PM);
            tvTienThue = view.findViewById(R.id.tv_tien_thue);
            imgPM = view.findViewById(R.id.img_PM);
            cvPhieuMuon = view.findViewById(R.id.cardview_phieu_muon);
        }
    }
}
