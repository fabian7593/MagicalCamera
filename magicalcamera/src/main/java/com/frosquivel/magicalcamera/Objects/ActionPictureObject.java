package com.frosquivel.magicalcamera.Objects;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by Fabian on 08/12/2016.
 */

public class ActionPictureObject {

    //the max of quality photo
    public static final int BEST_QUALITY_PHOTO = 4000;

    //The photo name for uxiliar save picture
    public static final String photoNameAuxiliar = "MagicalCamera";

    //properties
    //my intent curret fragment (only use for fragments)
    private Intent intentFragment;

    //Your own resize picture
    private int resizePhoto;

    //my activity variable
    private Activity activity;

    //bitmap to set and get
    private Bitmap myPhoto;


    //getters and setters
    public Intent getIntentFragment() {
        return intentFragment;
    }

    public void setIntentFragment(Intent intentFragment) {
        this.intentFragment = intentFragment;
    }

    public Bitmap getMyPhoto() {
        return myPhoto;
    }

    public void setMyPhoto(Bitmap myPhoto) {
        this.myPhoto = myPhoto;
    }

    public int getResizePhoto() {
        return resizePhoto;
    }

    public void setResizePhoto(int resizePhoto) {
        if (resizePhoto < BEST_QUALITY_PHOTO)
            this.resizePhoto = resizePhoto;
        else
            this.resizePhoto = BEST_QUALITY_PHOTO;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
