package manhntph29583.baithi.dam_manhntph29583.mAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import manhntph29583.baithi.dam_manhntph29583.mFragment.FragAdmin;
import manhntph29583.baithi.dam_manhntph29583.mFragment.FragThuThu;

public class vpTaiKhoanAdapter extends FragmentStateAdapter {
    int slPage = 2;
    public vpTaiKhoanAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new FragAdmin();
            case 1: return new FragThuThu();
            default: return new FragAdmin();
        }
    }

    @Override
    public int getItemCount() {
        return slPage;
    }
}
