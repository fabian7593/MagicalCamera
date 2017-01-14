package com.frosquivel.magicalcamera.Objects;

import android.app.Activity;

/**
 * Created by Fabian on 08/12/2016.
 */

public class PermissionGrantedObject {

    public static final int REQUEST_CAMERA_PERMISSION = 1;
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 2;
    public static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 3;
    public static final int REQUEST_LOCATION_PERMISSION = 4;
    public static final int REQUEST_ALL_PERMISSION = 5;

    private boolean cameraPermission;
    private boolean writeExternalStoragePermission;
    private boolean readExternalStoragePermission;
    private boolean locationPermission;

    private Activity activity;

    public boolean isReadExternalStoragePermission() {
        return readExternalStoragePermission;
    }

    public void setReadExternalStoragePermission(boolean readExternalStoragePermission) {
        this.readExternalStoragePermission = readExternalStoragePermission;
    }

    public boolean isCameraPermission() {
        return cameraPermission;
    }

    public void setCameraPermission(boolean cameraPermission) {
        this.cameraPermission = cameraPermission;
    }

    public boolean isWriteExternalStoragePermission() {
        return writeExternalStoragePermission;
    }

    public void setWriteExternalStoragePermission(boolean writeExternalStoragePermission) {
        this.writeExternalStoragePermission = writeExternalStoragePermission;
    }

    public boolean isLocationPermission() {
        return locationPermission;
    }

    public void setLocationPermission(boolean locationPermission) {
        this.locationPermission = locationPermission;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
