package ua.dp.michaellang.weather.presenter;

/**
 * Date: 15.09.2017
 *
 * @author Michael Lang
 */
public interface CityListPresenter extends BasePresenter {
    void loadCityList();
    void loadCitiesWeather();
}
