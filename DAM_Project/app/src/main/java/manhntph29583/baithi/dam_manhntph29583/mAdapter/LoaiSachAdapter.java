package manhntph29583.baithi.dam_manhntph29583.mAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import manhntph29583.baithi.dam_manhntph29583.R;
import manhntph29583.baithi.dam_manhntph29583.mDAO.LoaiSachDAO;
import manhntph29583.baithi.dam_manhntph29583.mDAO.PhieuMuonDAO;
import manhntph29583.baithi.dam_manhntph29583.mDTO.LoaiSach;
import manhntph29583.baithi.dam_manhntph29583.mDTO.PhieuMuon;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachViewHolder> {
    Context context;
    ArrayList<LoaiSach> list;
    LoaiSachDAO lsDao;
    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list) {
        this.context = context;
            this.list = list;
        lsDao = new LoaiSachDAO(context);
    }


    @NonNull
    @Override
    public LoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.cv_loaisach, parent, false);
        LoaiSachAdapter.LoaiSachViewHolder viewHolder = new LoaiSachAdapter.LoaiSachViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachAdapter.LoaiSachViewHolder holder, int position) {
        LoaiSach pm = list.get(position);
        holder.tvTenLoaiSach.setText(pm.getTenLoai());
        holder.imgDelLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setTitle("Bạn có chắc muốn xóa?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(lsDao.Delete(pm.getMaLoai())){
                            Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(lsDao.getAll());
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
        holder.imgEditLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenUdateLS(pm);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LoaiSachViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenLoaiSach;
        ImageView imgLS, imgDelLS, imgEditLS;
        CardView cvLoaiSach;
        public LoaiSachViewHolder(@NonNull View view) {
            super(view);
            tvTenLoaiSach = view.findViewById(R.id.tv_ten_loai_sach);
            imgLS = view.findViewById(R.id.img_loaisach);
            imgDelLS = view.findViewById(R.id.img_delete_ls);
            imgEditLS = view.findViewById(R.id.img_edit_ls);
            cvLoaiSach = view.findViewById(R.id.cardview_loai_sach);
        }
    }

    private void OpenUdateLS(LoaiSach pm) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.loaisach_add_edit, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView tvLSaddEdit = view.findViewById(R.id.tv_LS_add_edit);
        TextInputLayout inputEditTenLS = view.findViewById(R.id.input_TenLoaiSach);
        Button btnHuyEditTV = view.findViewById(R.id.btn_exit_add_edit_LS);
        Button btnEditTV = view.findViewById(R.id.btn_add_edit_LS);
        tvLSaddEdit.setText("Sửa thông tin loại sách");

        inputEditTenLS.getEditText().setText(pm.getTenLoai());
        btnEditTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputEditTenLS.getEditText().getText().toString().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    pm.setTenLoai(inputEditTenLS.getEditText().getText().toString());
                    if (lsDao.Update(pm)) {
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        list.clear();
                        list.addAll(lsDao.getAll());
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        btnHuyEditTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

}
