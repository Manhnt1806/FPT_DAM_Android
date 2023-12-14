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

import java.util.ArrayList;

import manhntph29583.baithi.dam_manhntph29583.R;
import manhntph29583.baithi.dam_manhntph29583.mDAO.LoaiSachDAO;
import manhntph29583.baithi.dam_manhntph29583.mDAO.ThanhVienDAO;
import manhntph29583.baithi.dam_manhntph29583.mDTO.Sach;
import manhntph29583.baithi.dam_manhntph29583.mDTO.ThanhVien;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {
    Context context;
    ArrayList<ThanhVien> list;
    ThanhVienDAO sDao;
    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
        sDao = new ThanhVienDAO(context);
    }

    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.cv_thanhvien, parent, false);
        ThanhVienAdapter.ThanhVienViewHolder viewHolder = new ThanhVienAdapter.ThanhVienViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienAdapter.ThanhVienViewHolder holder, int position) {
        ThanhVien pm = list.get(position);
        holder.tvMaTV.setText(pm.getMaTV()+"");
        holder.tvTenTV.setText(pm.getHoTen());
        holder.tvNamSinh.setText(pm.getNamSinh());
        holder.imgDelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setTitle("Bạn có chắc muốn xóa?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(sDao.Delete(pm.getMaTV())){
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
        holder.imgEditTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenUpdateTV(pm);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ThanhVienViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaTV, tvTenTV, tvNamSinh;
        ImageView imgTV, imgDelTV, imgEditTV;
        CardView cvTV;
        public ThanhVienViewHolder(@NonNull View view) {
            super(view);
            tvMaTV = view.findViewById(R.id.tv_ma_thanhvien_TV);
            tvTenTV = view.findViewById(R.id.tv_ten_thanhvien);
            tvNamSinh = view.findViewById(R.id.tv_nam_sinh);
            imgTV = view.findViewById(R.id.img_tv);
            imgDelTV = view.findViewById(R.id.img_delete_tv);
            cvTV = view.findViewById(R.id.cardview_thanhvien);
            imgEditTV = view.findViewById(R.id.img_edit_tv);
        }
    }
    private void OpenUpdateTV(ThanhVien tv) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.thanhvien_add_edit, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView tvTVaddEdit = view.findViewById(R.id.tv_TV_add_edit);
        TextInputLayout inputEditMaTV = view.findViewById(R.id.input_ma_tv);
        TextInputLayout inputEditTenTV = view.findViewById(R.id.input_Ten_TV);
        TextInputLayout inputEditNamSinh = view.findViewById(R.id.input_nam_sinh);
        Button btnHuyEditTV = view.findViewById(R.id.btn_exit_add_edit_TV);
        Button btnEditTV = view.findViewById(R.id.btn_add_edit_TV);
        tvTVaddEdit.setText("Sửa thông tin thành viên");

        inputEditMaTV.getEditText().setText(tv.getMaTV());
        inputEditTenTV.getEditText().setText(tv.getHoTen());
        inputEditNamSinh.getEditText().setText(tv.getNamSinh());
        btnEditTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputEditMaTV.getEditText().getText().toString().isEmpty()||
                        inputEditTenTV.getEditText().getText().toString().isEmpty()||
                            inputEditNamSinh.getEditText().getText().toString().isEmpty()){
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    tv.setMaTV(inputEditMaTV.getEditText().getText().toString());
                    tv.setHoTen(inputEditTenTV.getEditText().getText().toString());
                    tv.setNamSinh(inputEditNamSinh.getEditText().getText().toString());
                    if (sDao.Update(tv)) {
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

        btnHuyEditTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
}
