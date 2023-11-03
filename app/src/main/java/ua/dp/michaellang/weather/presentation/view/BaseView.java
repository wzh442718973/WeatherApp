package ua.dp.michaellang.weather.presentation.view;


import androidx.annotation.StringRes;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public interface BaseView {
    void onError(@StringRes int stringResId);
}
