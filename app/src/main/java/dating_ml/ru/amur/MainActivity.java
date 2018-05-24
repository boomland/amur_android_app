package dating_ml.ru.amur;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import dating_ml.ru.amur.adapter.TabsFragmentAdapter;
import dating_ml.ru.amur.dto.MainUserDTO;


public class MainActivity extends AppCompatActivity {
    private static final int LAYOUT = R.layout.activity_main;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;

    public MainUserDTO mainUser;

    public String base_url;
    public String auth_response;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        mainUser = new MainUserDTO();
        receiveData();

//        initToolbar();

        drawerLayout = findViewById(R.id.drawer_layout);

        initTabs();
    }


    private void initTabs() {
        viewPager = findViewById(R.id.view_pager);

        TabsFragmentAdapter adapter = new TabsFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(Constants.MAIN_TAB_INDEX).select();
    }

    private void receiveData() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            mainUser = b.getParcelable(Constants.MAIN_USER);
            base_url = b.getString("base_url");
            auth_response = b.getString("auth_response");
        }
    }

    /*
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }
    */
}
