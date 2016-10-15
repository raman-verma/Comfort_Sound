package sound.myapp.com.comfortsound;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TestViewPagerAdapter extends FragmentStatePagerAdapter {

    String[] title = {"One", "Two", "Three"};

    public TestViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TestOneFragment();
            case 1:
                return new TestOneFragment();
            case 2:
                return new TestOneFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
