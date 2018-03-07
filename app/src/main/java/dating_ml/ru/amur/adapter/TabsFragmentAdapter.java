package dating_ml.ru.amur.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import dating_ml.ru.amur.fragment.AbstractTabFragment;
import dating_ml.ru.amur.fragment.MainFragment;
import dating_ml.ru.amur.fragment.MatchesFragment;
import dating_ml.ru.amur.fragment.ProfileFragment;

public class TabsFragmentAdapter extends FragmentPagerAdapter{
    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    public TabsFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;

        initTabsMap(context);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap(Context context) {
        tabs = new HashMap<>();
        tabs.put(0, ProfileFragment.getInstance(context));
        tabs.put(1, MainFragment.getInstance(context));
        tabs.put(2, MatchesFragment.getInstance(context));
    }
}
