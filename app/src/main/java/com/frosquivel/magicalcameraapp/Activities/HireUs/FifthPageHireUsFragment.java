package com.frosquivel.magicalcameraapp.Activities.HireUs;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frosquivel.magicalcameraapp.R;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

/**
 * Created by Fabian on 05/05/2017.
 */

public class FifthPageHireUsFragment extends Fragment implements ISlideBackgroundColorHolder {

    private static final String ARG_LAYOUT_RES_ID = "fragment_hire_us_fifth_page";
    private int layoutResId;
    private View rootView;

    public static FifthPageHireUsFragment newInstance(int layoutResId) {
        FifthPageHireUsFragment sampleSlide = new FifthPageHireUsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);
        return sampleSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(layoutResId, container, false);
        rootView.setBackgroundColor(getResources().getColor(R.color.colorFifthFragment));
        return rootView;
    }

    @Override
    public int getDefaultBackgroundColor() {
        // Return the default background color of the slide.
        return getResources().getColor(R.color.colorFifthFragment);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        // Set the background color of the view within your slide to which the transition should be applied.
        if (rootView != null) {
            rootView.setBackgroundColor(backgroundColor);
        }
    }

}
