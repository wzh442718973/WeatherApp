package ua.dp.michaellang.weather.presentation.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.data.entity.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.presentation.ui.base.BaseAdapter;
import ua.dp.michaellang.weather.presentation.ui.base.BaseViewHolder;

import java.util.Date;

/**
 * Date: 18.09.2017
 *
 * @author Michael Lang
 */
public class HourlyWeatherAdapter extends BaseAdapter<HourlyForecast, HourlyWeatherAdapter.HourlyWeatherViewHolder> {
    public HourlyWeatherAdapter(Context context) {
        super(context);
    }

    @Override
    public HourlyWeatherViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_hourly_weather, parent, false);
        return new HourlyWeatherViewHolder(view);
    }

    static class HourlyWeatherViewHolder extends BaseViewHolder<HourlyForecast> {
TextView mClockTextView;TextView mHumidityTextView;TextView mTemperatureTextView;TextView mWindTextView;
        public HourlyWeatherViewHolder(View itemView) {
            super(itemView);
            //tool add
            this.mClockTextView =  itemView.findViewById(R.id.item_hourly_weather_clock);
            this.mHumidityTextView =  itemView.findViewById(R.id.item_hourly_weather_humidity);
            this.mTemperatureTextView =  itemView.findViewById(R.id.item_hourly_weather_temperature);
            this.mWindTextView =  itemView.findViewById(R.id.item_hourly_weather_wind);
            //tool end
        }

        @Override
        public void onBindView(Context context, HourlyForecast data) {
            long epochDateTime = data.getEpochDateTime();
            Date date = new Date(epochDateTime * 1000);
            String timeStr = context.getString(R.string.time, date);
            mClockTextView.setText(timeStr);

            String humidityStr = context.getString(R.string.humidity, data.getRelativeHumidity());
            mHumidityTextView.setText(humidityStr);

            double windSpeed = data.getWind().getSpeed().getValue();
            String windStr = context.getString(R.string.wind, windSpeed);
            mWindTextView.setText(windStr);

            double temperature = data.getTemperature().getValue();
            String tempStr = context.getString(R.string.temperature, temperature);
            mTemperatureTextView.setText(tempStr);
        }
    }
}
