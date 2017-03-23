package com.frosquivel.examplemagicalcamera.Activities;

import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import com.frosquivel.examplemagicalcamera.ConnectionBaaS.BackendlessImp;
import com.frosquivel.examplemagicalcamera.R;
import com.frosquivel.examplemagicalcamera.Utils.Utils;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/**
 * Created by Fabian on 28/02/2017.
 * This class shown the splash screen of the application
 * init the backendless and the shared preferences
 */

public class SplashActivity extends AwesomeSplash {
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    public void initSplash(ConfigSplash configSplash) {

        //Fabric.with(this, new Crashlytics());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Utils.setInitialSharedPreference(this, false);
        BackendlessImp.backendlessInit(this);

        Intent i = new Intent(this, MainActivity.class);
        startService(i);

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorWhite); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.ic_magical_vertical_div_2); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)

        //Customize Title
        configSplash.setTitleSplash(getString(R.string.version));
        configSplash.setTitleTextColor(R.color.colorPrimary);
        configSplash.setTitleTextSize(25f); //float value
        configSplash.setAnimTitleDuration(2000);
        configSplash.setAnimTitleTechnique(Techniques.DropOut);
        //configSplash.setTitleFont(ConstantsUtils.FONT_PATH); //provide string to your font located in assets/fonts/
    }

    @Override
    public void animationsFinished() {
        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
