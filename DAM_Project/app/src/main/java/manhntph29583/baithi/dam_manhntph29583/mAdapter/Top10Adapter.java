package manhntph29583.baithi.dam_manhntph29583.mAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import manhntph29583.baithi.dam_manhntph29583.R;
import manhntph29583.baithi.dam_manhntph29583.mDAO.PhieuMuonDAO;
import manhntph29583.baithi.dam_manhntph29583.mDAO.SachDAO;
import manhntph29583.baithi.dam_manhntph29583.mDTO.PhieuMuon;
import manhntph29583.baithi.dam_manhntph29583.mDTO.Sach;
import manhntph29583.baithi.dam_manhntph29583.mDTO.Top10;
import manhntph29583.baithi.dam_manhntph29583.mFragment.FragTop10;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.Top10ViewHolder> {
    Context context;
    ArrayList<Top10> list;
    PhieuMuonDAO pmDao;

    public Top10Adapter(@NonNull Context context, ArrayList<Top10> list) {
        this.context = context;
        this.list = list;
        pmDao = new PhieuMuonDAO(context);
    }

    @NonNull
    @Override
    public Top10Adapter.Top10ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.cv_top10, parent, false);
        Top10Adapter.Top10ViewHolder viewHolder = new Top10Adapter.Top10ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Top10Adapter.Top10ViewHolder holder, int position) {
        Top10 pm = list.get(position);
        holder.tvTenSach.setText(pm.getTenSach());
        holder.tvSoLuong.setText(pm.getSoLuong()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Top10ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSach, tvSoLuong;
        public Top10ViewHolder(@NonNull View view) {
            super(view);
            tvTenSach = view.findViewById(R.id.tv_ten_sach_Top);
            tvSoLuong = view.findViewById(R.id.tv_soLuong);
        }
    }
}
