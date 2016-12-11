package com.frosquivel.magicalcamera.Functionallities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.Objects.MagicalCameraObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Fabian Rosales Esquivel (Frosquivel Developer)
 * Created Date 07/12/2016.
 * Made in Costa Rica
 * This class save the photo bitmap like a real photo in device
 */

public class SaveEasyPhoto {

    //================================================================================
    // Save Photo in device
    //================================================================================
    //region Save Photo in device

    /**
     * This library write the file in the device storage or sdcard
     *
     * @param bitmap                  the bitmap that you need to write in device
     * @param photoName               the photo name
     * @param directoryName           the directory that you need to create the picture
     * @param format                  the format of the photo, maybe png or jpeg
     * @param autoIncrementNameByDate is this variable is active the system create
     *                                the photo with a number of the date, hour, and second to diferenciate this
     * @return return true if the photo is writen
     */
    public String writePhotoFile(Bitmap bitmap, String photoName, String directoryName,
                                  Bitmap.CompressFormat format, boolean autoIncrementNameByDate, Activity activity) {

        if (bitmap == null) {
            return null;
        } else {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(format, 100, bytes);

            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = df.format(Calendar.getInstance().getTime());

            if (format == MagicalCameraObject.PNG) {
                photoName = (autoIncrementNameByDate) ? photoName + "_" + date + ".png" : photoName + ".png";
            } else if (format == MagicalCameraObject.JPEG) {
                photoName = (autoIncrementNameByDate) ? photoName + "_" + date + ".jpeg" : photoName + ".jpeg";
            } else if (format == MagicalCameraObject.WEBP) {
                photoName = (autoIncrementNameByDate) ? photoName + "_" + date + ".webp" : photoName + ".webp";
            }

            File wallpaperDirectory = null;

            try {
                wallpaperDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + directoryName + "/");
            } catch (Exception ev) {
                try {
                    wallpaperDirectory = Environment.getExternalStorageDirectory();
                } catch (Exception ex) {
                    try {
                        wallpaperDirectory = Environment.getDataDirectory();
                    } catch (Exception e) {
                        wallpaperDirectory = Environment.getRootDirectory();
                    }
                }
            }

            if (wallpaperDirectory != null) {
                if (!wallpaperDirectory.exists()) {
                    wallpaperDirectory.exists();
                    wallpaperDirectory.mkdirs();
                }

                File f = new File(wallpaperDirectory, photoName);
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.parse("file://" + f.getAbsolutePath())));

                    try {
                        //Update the System
                        Uri u = Uri.parse(f.getAbsolutePath());
                        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, u));
                    } catch (Exception ex) {
                    }

                    return f.getAbsolutePath();
                } catch (Exception ev) {
                    return null;
                }

            } else {
                return null;
            }
        }
    }
    //endregion
}
