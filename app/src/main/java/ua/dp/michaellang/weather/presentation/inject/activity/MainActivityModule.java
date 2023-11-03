package ua.dp.michaellang.weather.presentation.inject.activity;

import android.content.Context;
//import androidx.fragment.app.FragmentManager;
//import androidx.appcompat.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import ua.dp.michaellang.weather.presentation.inject.fragment.CityListFragmentSubcomponent;
import ua.dp.michaellang.weather.presentation.inject.fragment.CountryListFragmentSubcomponent;
import ua.dp.michaellang.weather.presentation.inject.fragment.SearchByCountryFragmentSubcomponent;
import ua.dp.michaellang.weather.presentation.ui.activity.MainActivity;
import ua.dp.michaellang.weather.presentation.ui.adapter.SectionsPagerAdapter;

import javax.inject.Named;

import static ua.dp.michaellang.weather.Contants.ACTIVITY_CONTEXT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

/**
 * Date: 24.09.2017
 *
 * @author Michael Lang
 */
@Module(subcomponents = {
        CityListFragmentSubcomponent.class,
        CountryListFragmentSubcomponent.class,
        SearchByCountryFragmentSubcomponent.class
})
public class MainActivityModule {
    @Provides
    SectionsPagerAdapter provideSectionsPagerAdapter(@Named(ACTIVITY_CONTEXT) Context context, FragmentManager fm) {
        return new SectionsPagerAdapter(context, fm);
    }

    @Provides
    AppCompatActivity provideAppCompatActivity(MainActivity activity){
        return activity;
    }
}
