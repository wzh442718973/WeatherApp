package ua.dp.michaellang.weather.presentation.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;
//import android.support.annotation.StringRes;
//import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.data.entity.Location.Region;
import ua.dp.michaellang.weather.presentation.ui.base.BaseFragment;
import ua.dp.michaellang.weather.presentation.utils.AssetsUtils;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

import com.google.android.material.snackbar.Snackbar;

/**
 * Date: 16.09.2017
 *
 * @author Michael Lang
 */
public class SearchByCountryFragment extends BaseFragment {
    private static final int REQUEST_COUNTRY = 0;
    private static final String DIALOG_COUNTRY_LIST = "DIALOG_COUNTRY_LIST";

    private static final String KEY_COUNTRY = "KEY_COUNTRY";

View mContentView;Button mButton;ImageView mImageView;
    @Inject AssetsUtils mAssetsUtils;

    private CountryListDialog mCountryListDialog;
    private Region mCountry;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCountry = savedInstanceState.getParcelable(KEY_COUNTRY);
        }
    }

    @Override
    protected void loadArgs() {
        //nothing
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_by_country, container, false);
        //tool add
        this.mContentView =  view.findViewById(R.id.fragment_search_by_country_content);
        this.mButton =  view.findViewById(R.id.fragment_search_by_country_button);
        this.mImageView =  view.findViewById(R.id.fragment_search_by_country_image);
        this.mButton.setOnClickListener(v ->{
            onSearchByCountryButtonClick();
        });
        //tool end
        updateUI();
        return view;
    }

    public void onSearchByCountryButtonClick() {
        if (mCountryListDialog != null && mCountryListDialog.isVisible()) {
            return;
        }

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        mCountryListDialog = CountryListDialog.newInstance();
        mCountryListDialog.setTargetFragment(SearchByCountryFragment.this, REQUEST_COUNTRY);
        mCountryListDialog.show(fragmentManager, DIALOG_COUNTRY_LIST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_COUNTRY) {
            if (data != null) {
                if (resultCode != RESULT_OK) {
                    int errorMessage = CountryListDialog.getErrorMessage(data);
                    onError(errorMessage);
                } else {
                    Region country = CountryListDialog.getCountry(data);
                    onDialogItemSelected(country);
                }
            }
        }
    }

    private void onDialogItemSelected(Region country) {
        mCountry = country;
        updateUI();
        showFragment();
    }

    private void updateUI() {
        if (mCountry != null) {
            mButton.setText(mCountry.getLocalizedName());
            Drawable drawable = mAssetsUtils.getFlag(getContext(), mCountry.getId());
            mImageView.setImageDrawable(drawable);
        }
    }

    private void showFragment() {
        FragmentManager sfm = getActivity().getSupportFragmentManager();

        CityListFragment fragment =
                CityListFragment.newInstance(mCountry.getId());

        sfm.beginTransaction()
                .replace(R.id.fragment_search_by_country_content, fragment)
                .commit();
    }

    public void onError(@StringRes int stringResId) {
        // TODO: 24.09.2017
        Timber.e(getString(stringResId));
        Snackbar.make(mContentView, stringResId, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_COUNTRY, mCountry);
    }

    public static SearchByCountryFragment newInstance() {
        return new SearchByCountryFragment();
    }
}
