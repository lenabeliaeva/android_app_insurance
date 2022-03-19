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
                    return new PoliciesCategoryFragment(user);
                case 1:
                    return new MyPoliciesFragment(user);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.get_police);
                case 1:
                    return getResources().getText(R.string.my_policies);
            }
            return null;
        }
    }
}
