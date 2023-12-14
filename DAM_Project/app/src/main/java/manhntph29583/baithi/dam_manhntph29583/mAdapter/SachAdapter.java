package manhntph29583.baithi.dam_manhntph29583.mAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.ArrayList;

import manhntph29583.baithi.dam_manhntph29583.R;
import manhntph29583.baithi.dam_manhntph29583.mDAO.LoaiSachDAO;
import manhntph29583.baithi.dam_manhntph29583.mDAO.SachDAO;
import manhntph29583.baithi.dam_manhntph29583.mDTO.LoaiSach;
import manhntph29583.baithi.dam_manhntph29583.mDTO.Sach;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {
    Context context;
    ArrayList<Sach> list;
    SachDAO sDao;
    public SachAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
        sDao = new SachDAO(context);
    }


    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.cv_sach, parent, false);
        SachAdapter.SachViewHolder viewHolder = new SachAdapter.SachViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SachAdapter.SachViewHolder holder, int position) {
        Sach pm = list.get(position);
        holder.tvTenSach.setText(pm.getTenSach());
        holder.tvTenLoaiSach.setText(pm.getTenLoai());
        holder.tvGiaThue.setText(pm.getGiaThue()+"");
        holder.imgDelS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setTitle("Bạn có chắc muốn xóa?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(sDao.Delete(pm.getMaSach())){
                            Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(sDao.getAll());
                            notifyDataSetChanged();
                            dialogInterface.dismiss();
                        }else{
                            Toast.makeText(context, "Xóa Thất bại", Toast.LENGTH_SHORT).show();
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
        holder.imgEditS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDiaLogEditS(pm);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SachViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenLoaiSach, tvTenSach, tvGiaThue;
        ImageView imgS, imgDelS, imgEditS;
        CardView cvSach;
        public SachViewHolder(@NonNull View view) {
            super(view);
            tvTenSach = view.findViewById(R.id.tv_ten_sach_S);
            tvTenLoaiSach = view.findViewById(R.id.tv_loai_sach_S);
            imgS = view.findViewById(R.id.img_S);
            imgDelS = view.findViewById(R.id.img_delete_s);
            imgEditS = view.findViewById(R.id.img_edit_sach);
            cvSach = view.findViewById(R.id.cardview_sach);
            tvGiaThue = view.findViewById(R.id.tv_gia_thue);
        }
    }
    private void OpenDiaLogEditS(Sach pm) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.sach_add, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView tvEditSach = view.findViewById(R.id.tv_sach_add);
        EditText edtGiaThue = view.findViewById(R.id.ed_gia_thue);
        Spinner spnLoaiSach = view.findViewById(R.id.spn_add_loai_sach);
        TextInputLayout inputEditTenSach = view.findViewById(R.id.intput_add_sach);
        Button btnHuyEditS = view.findViewById(R.id.btn_exit_add_sach);
        Button btnEditS = view.findViewById(R.id.btn_add_sach);

        tvEditSach.setText("Sửa sách");
        LoaiSachDAO lsDAO = new LoaiSachDAO(context);
        ArrayList<LoaiSach> listLS = lsDAO.getAll();
        ArrayAdapter adapterKT = new ArrayAdapter(context,
                android.R.layout.simple_spinner_dropdown_item, listLS);
        spnLoaiSach.setAdapter(adapterKT);
        for(int i=0; i< listLS.size(); i++){
            if(pm.getTenLoai().equalsIgnoreCase(String.valueOf(listLS.get(i)))){
                spnLoaiSach.setSelection(i);
            }
        }
        edtGiaThue.setText(pm.getGiaThue()+"");
        inputEditTenSach.getEditText().setText(pm.getTenSach());

        btnEditS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pm.setGiaThue(Integer.parseInt(edtGiaThue.getText().toString()));
                pm.setTenSach(inputEditTenSach.getEditText().getText().toString());
                LoaiSach ls = (LoaiSach) spnLoaiSach.getSelectedItem();
                int idLoaiSach = ls.getMaLoai();
                pm.setMaLoai(idLoaiSach);
                if(inputEditTenSach.getEditText().getText().toString().isEmpty()||Integer.parseInt(edtGiaThue.getText().toString())==0){
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    if (sDao.Update(pm)) {
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        list.clear();
                        list.addAll(sDao.getAll());
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        btnHuyEditS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

}
