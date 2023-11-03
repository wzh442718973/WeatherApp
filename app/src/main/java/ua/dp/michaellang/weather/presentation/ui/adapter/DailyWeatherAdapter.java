package ua.dp.michaellang.weather.presentation.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.data.entity.Forecast.DailyForecast;
import ua.dp.michaellang.weather.data.entity.Forecast.DayNight;
import ua.dp.michaellang.weather.data.entity.Forecast.MinMax;
import ua.dp.michaellang.weather.presentation.ui.base.BaseAdapter;
import ua.dp.michaellang.weather.presentation.ui.base.BaseViewHolder;
import ua.dp.michaellang.weather.presentation.utils.AssetsUtils;

import javax.inject.Inject;
import java.util.Date;

/**
 * Date: 18.09.2017
 *
 * @author Michael Lang
 */
public class DailyWeatherAdapter extends BaseAdapter<DailyForecast, DailyWeatherAdapter.DailyWeatherViewHolder> {
    private AssetsUtils mAssetsUtils;

    @Inject
    public DailyWeatherAdapter(Context context, AssetsUtils assetsUtils) {
        super(context);
        mAssetsUtils = assetsUtils;
    }

    @Override
    public DailyWeatherViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_daily_weather, parent, false);
        return new DailyWeatherViewHolder(view, mAssetsUtils);
    }

    static class DailyWeatherViewHolder extends BaseViewHolder<DailyForecast> {
TextView mDayTextView;TextView mDateTextView;ImageView mImageView;TextView mTemperatureMaxTextView;TextView mTemperatureMinTextView;TextView mWindTextView;
        private AssetsUtils mAssetsUtils;

        public DailyWeatherViewHolder(View itemView, AssetsUtils assetsUtils) {
            super(itemView);
            //tool add
            this.mDayTextView =  itemView.findViewById(R.id.item_daily_weather_day);
            this.mDateTextView =  itemView.findViewById(R.id.item_daily_weather_date);
            this.mImageView =  itemView.findViewById(R.id.item_daily_weather_image);
            this.mTemperatureMaxTextView =  itemView.findViewById(R.id.item_daily_weather_temperature_max);
            this.mTemperatureMinTextView =  itemView.findViewById(R.id.item_daily_weather_temperature_min);
            this.mWindTextView =  itemView.findViewById(R.id.item_daily_weather_wind);
            //tool end

            mAssetsUtils = assetsUtils;
        }

        @Override
        public void onBindView(Context context, DailyForecast data) {
            long mils = data.getEpochDate() * 1000;
            Date date = new Date(mils);
            DayNight day = data.getDay();

            MinMax temperature = data.getTemperature();
            double maxValue = temperature.getMaximum().getValue();
            double minValue = temperature.getMinimum().getValue();

            double windSpeedValue = data.getDay().getWind().getSpeed().getValue();

            String dayStr = context.getString(R.string.day_of_week, date);
            mDayTextView.setText(dayStr);

            String dateStr = context.getString(R.string.abbreviated_date, date);
            mDateTextView.setText(dateStr);

            //ImageUtils.loadWeatherIcon(context, mImageView, day.getIcon());
            Drawable drawable = mAssetsUtils.getWeatherIcon(context, day.getIcon());
            mImageView.setImageDrawable(drawable);

            String maxTempStr = context.getString(R.string.temperature, maxValue);
            String minTempStr = context.getString(R.string.temperature, minValue);

            mTemperatureMaxTextView.setText(maxTempStr);
            mTemperatureMinTextView.setText(minTempStr);

            String windSpeedStr = context.getString(R.string.wind, windSpeedValue);
            mWindTextView.setText(windSpeedStr);
        }
    }

}
