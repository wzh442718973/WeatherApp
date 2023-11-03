package ua.dp.michaellang.weather.presentation.ui.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.presentation.ui.adapter.SectionsPagerAdapter;
import ua.dp.michaellang.weather.presentation.ui.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector {
    @Inject SectionsPagerAdapter mSectionsPagerAdapter;
    @Inject DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;

TabLayout mTabLayout;
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tool add
        this.mTabLayout = this.findViewById(R.id.tabs);
        this.mViewPager = this.findViewById(R.id.container);
        //tool end

        setToolbar(R.id.toolbar, null);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }
}
