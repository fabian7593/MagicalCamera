package com.frosquivel.magicalcamera.Functionallities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.frosquivel.magicalcamera.Objects.PermissionGrantedObject;

/**
 * Created by Fabian Rosales Esquivel (Frosquivel Developer)
 * Created Date 08/12/2016.
 * Made in Costa Rica
 * This class validate the permission in android 6.0 or more
 */

public class PermissionGranted {
    //region Properties
    private PermissionGrantedObject permissionGrantedObject;

    //Getter and setter method
    public void setPermissionGrantedObject(PermissionGrantedObject permissionGrantedObject) {
        this.permissionGrantedObject = permissionGrantedObject;
    }
    public PermissionGrantedObject getPermissionGrantedObject() {
        return permissionGrantedObject;
    }
    //endregion

    //region Constructor
    public PermissionGranted(Activity activity){
        this.permissionGrantedObject = new PermissionGrantedObject();
        this.permissionGrantedObject.setCameraPermission(false);
        this.permissionGrantedObject.setReadExternalStoragePermission(false);
        this.permissionGrantedObject.setWriteExternalStoragePermission(false);
        this.permissionGrantedObject.setActivity(activity);
    }
    //endregion

    //only if the user check all the permissions the variables are in true
    public void checkAllMagicalCameraPermission(){
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this.permissionGrantedObject.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.permissionGrantedObject.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.permissionGrantedObject.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.permissionGrantedObject.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.permissionGrantedObject.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this.permissionGrantedObject.getActivity(),
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        },
                        PermissionGrantedObject.REQUEST_ALL_PERMISSION);
            }else{
                this.permissionGrantedObject.setCameraPermission(true);
                this.permissionGrantedObject.setReadExternalStoragePermission(true);
                this.permissionGrantedObject.setWriteExternalStoragePermission(true);
                this.permissionGrantedObject.setLocationPermission(true);
            }
        }
    }

    //region Check permission for use camera, write and read of external storage and location for android 6.0
    public void checkCameraPermission(){
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this.permissionGrantedObject.getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this.permissionGrantedObject.getActivity(),
                        new String[]{
                                Manifest.permission.CAMERA
                        },
                        PermissionGrantedObject.REQUEST_CAMERA_PERMISSION);
            }else{
                this.permissionGrantedObject.setCameraPermission(true);
            }
        }
    }

    public void checkReadExternalPermission(){
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this.permissionGrantedObject.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this.permissionGrantedObject.getActivity(),
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        },
                        PermissionGrantedObject.REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
            }else{
                this.permissionGrantedObject.setReadExternalStoragePermission(true);
            }
        }
    }

    public void checkWriteExternalPermission(){
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this.permissionGrantedObject.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this.permissionGrantedObject
                        .getActivity(),
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        PermissionGrantedObject.REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
            }else{
                this.permissionGrantedObject.setWriteExternalStoragePermission(true);
            }
        }
    }

    public void checkLocationPermission(){
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this.permissionGrantedObject.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.permissionGrantedObject.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
             {
                ActivityCompat.requestPermissions(this.permissionGrantedObject
                                .getActivity(),
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        },
                        PermissionGrantedObject.REQUEST_LOCATION_PERMISSION);
            }else{
                this.permissionGrantedObject.setLocationPermission(true);
            }
        }
    }
    //endregion

    //region Method to call in override
    /***
     * This method is call in the grant permission override
     * For verify if the user acept the camera permission or not
     * And activate a flag of permissions
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void permissionGrant(int requestCode, String[] permissions, int[] grantResults){
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if(requestCode == PermissionGrantedObject.REQUEST_CAMERA_PERMISSION){
                this.permissionGrantedObject.setCameraPermission(true);
            }else if(requestCode == PermissionGrantedObject.REQUEST_READ_EXTERNAL_STORAGE_PERMISSION){
                this.permissionGrantedObject.setReadExternalStoragePermission(true);
            }else if(requestCode == PermissionGrantedObject.REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION){
                this.permissionGrantedObject.setWriteExternalStoragePermission(true);
            }else if(requestCode == PermissionGrantedObject.REQUEST_LOCATION_PERMISSION){
                this.permissionGrantedObject.setLocationPermission(true);

            }else if(requestCode == PermissionGrantedObject.REQUEST_ALL_PERMISSION){
                this.permissionGrantedObject.setWriteExternalStoragePermission(true);
                this.permissionGrantedObject.setCameraPermission(true);
                this.permissionGrantedObject.setReadExternalStoragePermission(true);
                this.permissionGrantedObject.setLocationPermission(true);
            }
        }
    }
    //endregion
}
