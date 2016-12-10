package com.frosquivel.magicalcamera.Functionallities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.frosquivel.magicalcamera.Objects.PermissionGrantedObject;

/**
 * Created by Fabian on 08/12/2016.
 */

public class PermissionGranted {
    //properties
    private PermissionGrantedObject permissionGrantedObject;

    //Getters and setters
    public void setPermissionGrantedObject(PermissionGrantedObject permissionGrantedObject) {
        this.permissionGrantedObject = permissionGrantedObject;
    }
    public PermissionGrantedObject getPermissionGrantedObject() {
        return permissionGrantedObject;
    }

    //constructor
    public PermissionGranted(Activity activity){
        this.permissionGrantedObject = new PermissionGrantedObject();
        this.permissionGrantedObject.setCameraPermission(false);
        this.permissionGrantedObject.setReadExternalStoragePermission(false);
        this.permissionGrantedObject.setWriteExternalStoragePermission(false);

        this.permissionGrantedObject.setActivity(activity);
    }

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
            }
        }
    }

}
