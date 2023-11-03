package ua.dp.michaellang.weather.data.repository;

import android.util.Pair;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import ua.dp.michaellang.weather.data.entity.CityWeather;
import ua.dp.michaellang.weather.data.entity.Forecast.HourlyForecast;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
public interface WeatherRepository {
    Observable<Pair<String, HourlyForecast>> getCurrentCitiesWeather(final Iterable<String> locationKeys,
                                                                     @Nullable String language, @Nullable Boolean details);

    Observable<CityWeather> getCityWeather(String locationKey,
            @Nullable String language, @Nullable Boolean details);
}
