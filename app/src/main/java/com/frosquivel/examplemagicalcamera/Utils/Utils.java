package com.frosquivel.examplemagicalcamera.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.frosquivel.examplemagicalcamera.R;
import com.frosquivel.examplemagicalcamera.Activities.WebViewActivity;
import com.frosquivel.magicalcamera.MagicalCamera;

/**
 * Created by Fabian on 01/03/2017.
 * This class have the general method for avoid code duplicate
 */

public class Utils {

    public static final String C_PREFERENCE_MAGICAL_CAMERA = "MCPreference";
    public static final String C_PREFERENCE_MC_DIRECTORY_NAME = "MCDirectoryName";
    public static final String C_PREFERENCE_MC_PHOTO_NAME = "MCPhotoName";
    public static final String C_PREFERENCE_MC_SELECTED_PICTURE = "MCTitleSelectPicture";
    public static final String C_PREFERENCE_MC_QUALITY_PICTURE = "MCQualityPicture";
    public static final String C_PREFERENCE_MC_FORMAT = "MCFormat";
    public static final String C_PREFERENCE_MC_AUTO_IC_NAME = "MCAutoincrementName";
    public static final String C_PREFERENCE_MC_FACIAL_RECOGNITION_THICK = "MCFacialRecognitionThick";
    public static final String C_PREFERENCE_MC_FACIAL_RECOGNITION_COLOR = "MCFacialRecognitionColor";

    public static final String C_PNG = "png";
    public static final String C_JPG = "jpg";
    public static final String C_WEBP = "webp";

    // Utils methods

    /**
     * Validate if magicalcamera object is null of not have photo, and shown a message if it is null.
     * @param context
     * @param view
     * @param magicalCamera
     * @return return true or false
     */
    public static boolean validateMagicalCameraNull(Context context, View view, MagicalCamera magicalCamera){
        if(magicalCamera != null) {
            if (magicalCamera.getPhoto() != null) {
                return true;
            }else{
                viewSnackBar(context.getString(R.string.error_image_null), view);
                return false;
            }
        }else{
            viewSnackBar(context.getString(R.string.error_init_magicalcamera), view);
            return false;
        }
    }

    /**
     * View Snack bar for simple form
     * @param message the message to shown
     * @param view the principal view (layout)
     */
    public static void viewSnackBar(String message, View view){
        Snackbar snackbar =  Snackbar.make(view, message,
                Snackbar.LENGTH_LONG).setDuration(Snackbar.LENGTH_LONG);

        View snackbarView = snackbar.getView();
        TextView tv= (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setMaxLines(5);
        snackbar.show();
    }

    /**
     * validate if the variable is null or empty
     * @param validate the string to validate
     * @return true or false
     */
    public static boolean notNullNotFill(String validate){
        if(validate != null){
            if(!validate.trim().equals("")){
                return true;
            }else{

                return false;
            }
        }else{
            return false;
        }
    }


    //#Shared preference methods
    public static void setSharedPreference(Context context, String preferenceName, String preferenceValue){
        SharedPreferences.Editor editor = context.getSharedPreferences(C_PREFERENCE_MAGICAL_CAMERA, context.MODE_PRIVATE).edit();
        editor.putString(preferenceName, preferenceValue);
        editor.commit();
    }

    public static String getSharedPreference(Context context, String preferenceName){
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(C_PREFERENCE_MAGICAL_CAMERA, context.MODE_PRIVATE);
        return pref.getString(preferenceName, "");
    }

    /**
     * Get the format for save into sd or internal memory device.
     * @param context
     * @return
     */
    public static Bitmap.CompressFormat getFormat(Context context){
        String format = getSharedPreference(context, C_PREFERENCE_MC_FORMAT);
        Bitmap.CompressFormat compressFormat = null;
        switch(format){
            case C_PNG:
                compressFormat = MagicalCamera.PNG;
                break;

            case C_JPG:
                compressFormat = MagicalCamera.JPEG;
                break;

            case C_WEBP:
                compressFormat = MagicalCamera.WEBP;
                break;

            default:
                break;
        }

        return compressFormat;
    }

    //initial the shared preference
    public static void setInitialSharedPreference(Context context, boolean isDefaultValues){

        if(getSharedPreference(context, C_PREFERENCE_MC_DIRECTORY_NAME).equals("") || isDefaultValues)
            setSharedPreference(context, C_PREFERENCE_MC_DIRECTORY_NAME, context.getString(R.string.value_directory_name));

        if(getSharedPreference(context, C_PREFERENCE_MC_PHOTO_NAME).equals("") || isDefaultValues)
            setSharedPreference(context, C_PREFERENCE_MC_PHOTO_NAME, context.getString(R.string.value_photo_name));

        if(getSharedPreference(context, C_PREFERENCE_MC_QUALITY_PICTURE).equals("") || isDefaultValues)
            setSharedPreference(context, C_PREFERENCE_MC_QUALITY_PICTURE, context.getString(R.string.value_quality_picture));

        if(getSharedPreference(context, C_PREFERENCE_MC_FORMAT).equals("") || isDefaultValues)
            setSharedPreference(context, C_PREFERENCE_MC_FORMAT, C_PNG);

        if(getSharedPreference(context, C_PREFERENCE_MC_AUTO_IC_NAME).equals("") || isDefaultValues)
            setSharedPreference(context, C_PREFERENCE_MC_AUTO_IC_NAME, context.getString(R.string.value_autoincrement_picture));

        if(getSharedPreference(context, C_PREFERENCE_MC_FACIAL_RECOGNITION_THICK).equals("") || isDefaultValues)
            setSharedPreference(context, C_PREFERENCE_MC_FACIAL_RECOGNITION_THICK, context.getString(R.string.value_facial_recognition_thick));

        if(getSharedPreference(context, C_PREFERENCE_MC_FACIAL_RECOGNITION_COLOR).equals("") || isDefaultValues)
            setSharedPreference(context, C_PREFERENCE_MC_FACIAL_RECOGNITION_COLOR, context.getString(R.string.value_facial_recognition_color));

        if(getSharedPreference(context, C_PREFERENCE_MC_SELECTED_PICTURE).equals("") || isDefaultValues)
            setSharedPreference(context, C_PREFERENCE_MC_SELECTED_PICTURE, context.getString(R.string.value_selected_picture));

    }

    /**
     * open a link into webView
     * @param url
     * @param activity
     */
    public static void goToWebView(String url, Activity activity){
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra("link",url);
        activity.startActivity(intent);
    }
}
