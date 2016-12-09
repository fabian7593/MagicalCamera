package com.frosquivel.magicalcamera.Utilities;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;

import com.frosquivel.magicalcamera.Objects.MagicalCameraObject;

/**
 * Created by Fabian on 08/12/2016.
 */

public class PictureUtils {
    //===============================================================================
    // Utils methods, resize and get Photo Uri and others
    //================================================================================
    //region Utils
    public static int ifCameraLandScape(boolean doRotate) {
        if(doRotate) {
            if (rotateIfLandScapeCamera()) {
                return MagicalCameraObject.LANDSCAPE_CAMERA;
            } else {
                return MagicalCameraObject.NORMAL_CAMERA;
            }
        }else{
            return 0;
        }
    }

    /**
     * Rotate the image if the device camera is land scape
     * @return
     */
    private static boolean rotateIfLandScapeCamera() {
        if (Build.BRAND.toLowerCase().equals("samsung")) {
            return true;
        } else if(Build.BRAND.toLowerCase().equals("sony")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Rotate the bitmap if the image is in landscape camera
     * @param source
     * @param angle
     * @return
     */
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return retVal;
    }

    /**
     * This method resize the photo
     *
     * @param realImage    the bitmap of image
     * @param maxImageSize the max image size percentage
     * @param filter       the filter
     * @return a bitmap of the photo rezise
     */
    public static Bitmap resizePhoto(Bitmap realImage, float maxImageSize,
                                      boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }
}
