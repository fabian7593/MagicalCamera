package com.frosquivel.magicalcamera.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Fabian on 07/12/2016.
 */

public class ConvertSimpleImage {

    //================================================================================
    // Conversion Methods
    //================================================================================

    public static Bitmap resizeImageRunTime(byte[] byteArray, int width, int height,boolean isFilter){
        Bitmap b = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return Bitmap.createScaledBitmap(b, width, height, isFilter);
    }

    //region Conversion Methods
    public static byte[] bitmapToBytes(Bitmap bitmap, Bitmap.CompressFormat format) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(format, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap bytesToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static Bitmap bytesToBitmap(byte[] byteArray, Bitmap.CompressFormat format) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(format, 100, stream);
        return bitmap;
    }

    public static String bytesToStringBase64(byte[] byteArray) {
        StringBuilder base64 = new StringBuilder(Base64.encodeToString(byteArray, Base64.DEFAULT));
        return base64.toString();
    }

    public static byte[] stringBase64ToBytes(String stringBase64) {
        byte[] byteArray = Base64.decode(stringBase64, Base64.DEFAULT);
        return byteArray;
    }
    //endregion
}
