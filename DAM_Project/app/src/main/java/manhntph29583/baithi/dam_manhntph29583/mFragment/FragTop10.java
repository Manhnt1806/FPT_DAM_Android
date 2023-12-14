package manhntph29583.baithi.dam_manhntph29583.mFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import manhntph29583.baithi.dam_manhntph29583.R;
import manhntph29583.baithi.dam_manhntph29583.mAdapter.LoaiSachAdapter;
import manhntph29583.baithi.dam_manhntph29583.mAdapter.Top10Adapter;
import manhntph29583.baithi.dam_manhntph29583.mDAO.LoaiSachDAO;
import manhntph29583.baithi.dam_manhntph29583.mDAO.PhieuMuonDAO;
import manhntph29583.baithi.dam_manhntph29583.mDTO.Top10;

public class FragTop10 extends Fragment {
    RecyclerView rcvTop10;
    PhieuMuonDAO phieuMuonDAO;
    ArrayList<Top10> list = new ArrayList<>();
    Top10Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_top10, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvTop10 = view.findViewById(R.id.rcv_top10);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvTop10.setLayoutManager(layoutManager);
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        list = phieuMuonDAO.getTop10();
        adapter = new Top10Adapter(getContext(), list);
        rcvTop10.setAdapter(adapter);
    }
}
