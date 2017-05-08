package com.frosquivel.magicalcameraapp.Activities.HireUs;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.frosquivel.magicalcameraapp.R;
import com.frosquivel.magicalcameraapp.Utils.Utils;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

/**
 * Created by Fabian on 04/05/2017.
 */
//https://github.com/apl-devs/AppIntro/blob/master/example/src/main/java/com/amqtech/opensource/appintroexample/ui/mainTabs/CustomBackgroundIntro.java
public class FirstPageHireUsFragment extends Fragment implements ISlideBackgroundColorHolder {


    private static final String ARG_LAYOUT_RES_ID = "fragment_hire_us_first_page";
    private int layoutResId;
    private View rootView;

    private ImageView imgEmail;
    private ImageView imgLinkedIn;
    private ImageView imgMessenger;

    private Activity activity;

    public static FirstPageHireUsFragment newInstance(int layoutResId) {
        FirstPageHireUsFragment sampleSlide = new FirstPageHireUsFragment();
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

        activity= getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(layoutResId, container, false);
        rootView.setBackgroundColor(getResources().getColor(R.color.colorFirstFragment));

        imgEmail = (ImageView) rootView.findViewById(R.id.imgEmail);
        imgLinkedIn = (ImageView) rootView.findViewById(R.id.imgLinkedIn);
        imgMessenger = (ImageView) rootView.findViewById(R.id.imgMessenger);

        imgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Utils.sendMeAnEmail(activity);
            }
        });

        imgLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.openScheme(activity, "linkedin://", "fabian-rosales-esquivel-698893106",
                        "https://www.linkedin.com/in/fabian-rosales-esquivel-698893106/", activity.getString(R.string.error_not_open_linkedin));
            }
        });

        imgMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.openScheme(activity, "fb-messenger://user/", "100003819211529",
                        "https://www.messenger.com/t/fabian.rosales.509", activity.getString(R.string.error_not_open_messenger));
            }
        });

        return rootView;
    }

    @Override
    public int getDefaultBackgroundColor() {
        // Return the default background color of the slide.
        return getResources().getColor(R.color.colorFirstFragment);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        // Set the background color of the view within your slide to which the transition should be applied.
        if (rootView != null) {
            rootView.setBackgroundColor(backgroundColor);
        }
    }
}
