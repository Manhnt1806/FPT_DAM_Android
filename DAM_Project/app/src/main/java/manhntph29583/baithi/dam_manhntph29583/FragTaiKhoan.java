package manhntph29583.baithi.dam_manhntph29583;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import manhntph29583.baithi.dam_manhntph29583.mAdapter.vpTaiKhoanAdapter;

public class FragTaiKhoan extends Fragment {
    TabLayout mTabLayout;
    ViewPager2 mViewPager;
    vpTaiKhoanAdapter viewpagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.taikhoan_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout =view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.viewpager);
        viewpagerAdapter = new vpTaiKhoanAdapter(this);
        mViewPager.setAdapter(viewpagerAdapter);

        TabLayoutMediator mediator =new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
            switch (position){
                case 0: tab.setText("Admin");
                    break;
                case 1: tab.setText("Thủ thư");
                    break;
            }
        });
        mediator.attach();
    }
}
