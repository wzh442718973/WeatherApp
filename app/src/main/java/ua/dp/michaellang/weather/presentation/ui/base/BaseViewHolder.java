package ua.dp.michaellang.weather.presentation.ui.base;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindView(Context context, T data);
}
