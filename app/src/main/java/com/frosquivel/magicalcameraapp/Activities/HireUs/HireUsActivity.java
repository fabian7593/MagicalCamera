package com.frosquivel.magicalcameraapp.Activities.HireUs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.frosquivel.magicalcameraapp.R;
import com.github.paolorotolo.appintro.AppIntro2;

/**
 * Created by Fabian on 03/04/2017.
 */
public class HireUsActivity extends AppIntro2 {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFlowAnimation();
        addSlide(FirstPageHireUsFragment.newInstance(R.layout.fragment_hire_us_first_page));
        addSlide(SecondPageHireUsFragment.newInstance(R.layout.fragment_hire_us_second_page));
        addSlide(ThirdPageHireUsFragment.newInstance(R.layout.fragment_hire_us_third_page));
        addSlide(FourthPageHireUsFragment.newInstance(R.layout.fragment_hire_us_fourth_page));
        addSlide(FifthPageHireUsFragment.newInstance(R.layout.fragment_hire_us_fifth_page));
        addSlide(SixthPageHireUsFragment.newInstance(R.layout.fragment_hire_us_sixth_page));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

}
