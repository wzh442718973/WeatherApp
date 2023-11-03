package ua.dp.michaellang.weather;

import android.app.Activity;
import android.app.Application;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.realm.Realm;
import timber.log.Timber;
import ua.dp.michaellang.weather.presentation.inject.app.DaggerAppComponent;

import javax.inject.Inject;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class WeatherApplication extends Application implements HasActivityInjector {

    @Inject DispatchingAndroidInjector<Activity> mActivityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        initializeInjector();
        initializeLeakDetection();
        initializeTimber();
    }

    private void initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initializeInjector() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    private void initializeLeakDetection() {

    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return mActivityDispatchingAndroidInjector;
    }
}
