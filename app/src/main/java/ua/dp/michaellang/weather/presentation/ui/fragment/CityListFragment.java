package ua.dp.michaellang.weather.presentation.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.SearchView;
import android.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.data.entity.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.data.entity.Location.City;
import ua.dp.michaellang.weather.presentation.presenter.CityListPresenter;
import ua.dp.michaellang.weather.presentation.ui.adapter.CityListAdapter;
import ua.dp.michaellang.weather.presentation.ui.base.BaseFragment;
import ua.dp.michaellang.weather.presentation.view.CityListView;

import javax.inject.Inject;
import java.util.List;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class CityListFragment extends BaseFragment
        implements CityListView, SearchView.OnQueryTextListener {
    public static final String ARG_COUNTRY_KEY = "ARG_COUNTRY_KEY";

    @Inject CityListAdapter mAdapter;
    @Inject CityListPresenter mPresenter;

SearchView mSearchView;RecyclerView mRecyclerView;ProgressBar mProgressBar;
    private String mCountryId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.onStart();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void loadArgs() {
        if (getArguments() != null) {
            mCountryId = getArguments().getString(ARG_COUNTRY_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);
        //tool add
        this.mSearchView =  view.findViewById(R.id.fragment_city_list_search_view);
        this.mRecyclerView =  view.findViewById(R.id.fragment_city_list_recycler_view);
        this.mProgressBar =  view.findViewById(R.id.fragment_city_list_progress_bar);
        this.mSearchView.setOnClickListener(v ->{
            onSearchViewClick();
        });
        //tool end
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mSearchView.setOnQueryTextListener(this);
        mPresenter.loadCityList(mCountryId);
    }

    void onSearchViewClick() {
        mSearchView.setIconified(false);
    }

    private void showProgress(boolean flag) {
        if (flag) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCityListLoaded(List<City> data) {
        if (data == null) {
            return;
        }
        Timber.d("%d cities loaded.", data.size());
        //загружаем погоду по городам
        mPresenter.loadCitiesWeather();

        mSearchView.setQuery("", false);
        showProgress(false);
        mAdapter.setData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCityWeatherLoaded(Pair<String, HourlyForecast> data) {
        mAdapter.updateWeather(data);
    }

    @Override
    public void onCityListFiltered(List<City> cities) {
        mAdapter.setData(cities);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(@StringRes int stringResId) {
        Snackbar.make(mRecyclerView, stringResId, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.action_retry, v -> {
                    mPresenter.onStart();
                    mPresenter.loadCityList(mCountryId);
                })
                .show();
        showProgress(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mPresenter.filterData(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mPresenter.filterData(newText);
        return true;
    }

    public static CityListFragment newInstance() {
        return new CityListFragment();
    }

    public static CityListFragment newInstance(String countryId) {
        Bundle args = new Bundle();
        args.putString(ARG_COUNTRY_KEY, countryId);

        CityListFragment fragment = new CityListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
