package com.example.insurance.TopLevelScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.insurance.R;
import com.example.insurance.pojo.User;
import com.google.android.material.tabs.TabLayout;

public class TopLevelActivity extends AppCompatActivity {
    private User user;
    public static final String EXTRA_USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = (User) bundle.get(EXTRA_USER);
        } else {
            user = new User();
        }
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new BestProductsFragment(user);
                case 2:
                    return new MyPoliciesFragment(user);
                default:
                    return new PoliciesCategoryFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.best_products_avg);
                case 2:
                    return getResources().getText(R.string.my_policies);
                default:
                    return getResources().getText(R.string.get_police);
            }
        }
    }
}
