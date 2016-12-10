package com.frosquivel.magicalcamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import com.frosquivel.magicalcamera.Functionallities.PermissionGranted;
import com.frosquivel.magicalcamera.Objects.ActionPictureObject;
import com.frosquivel.magicalcamera.Objects.FaceRecognitionObject;
import com.frosquivel.magicalcamera.Objects.MagicalCameraObject;
import com.frosquivel.magicalcamera.Objects.PermissionGrantedObject;
import com.frosquivel.magicalcamera.Objects.PrivateInformationObject;

import static android.graphics.Color.*;

/**
 * Created by          Fabián Rosales Esquivel
 * Visit my web page   http://www.frosquivel.com
 * Created Date        on 5/15/16
 * This is an android library to take easy picture
 */
public class MagicalCamera {
    private MagicalCameraObject magicalCameraObject;
    private PermissionGrantedObject permissionGrantedObject;
    private PermissionGranted permissionGranted;

    //================================================================================
    // Constructs
    //================================================================================
    //region Construct
    public MagicalCamera(Activity activity, int resizePhoto, PermissionGranted permissionGranted) {
        this.permissionGrantedObject =  permissionGranted.getPermissionGrantedObject();
        this.permissionGranted = permissionGranted;

        magicalCameraObject = new MagicalCameraObject(activity, resizePhoto <= 0 ?
                ActionPictureObject.BEST_QUALITY_PHOTO : resizePhoto, permissionGrantedObject);
    }

    public MagicalCamera(Activity activity, PermissionGranted permissionGranted) {
        this.permissionGrantedObject =  permissionGranted.getPermissionGrantedObject();
        this.permissionGranted = permissionGranted;

        magicalCameraObject = new MagicalCameraObject(activity, permissionGrantedObject);
    }
    //endregion


    public PermissionGrantedObject getPermissionGrantedObject() {
        return permissionGrantedObject;
    }

    public void setPermissionGrantedObject(PermissionGrantedObject permissionGrantedObject) {
        this.permissionGrantedObject = permissionGrantedObject;
    }

    public PermissionGranted getPermissionGranted() {
        return permissionGranted;
    }

    public void setPermissionGranted(PermissionGranted permissionGranted) {
        this.permissionGranted = permissionGranted;
    }

    //Principal Methods
    public void takePhoto(){
        magicalCameraObject.getActionPicture().takePhoto();
    }

    public void selectedPicture(String headerPopUpName){
        magicalCameraObject.getActionPicture().selectedPicture(headerPopUpName);
    }

    public boolean takeFragmentPhoto(){
        return magicalCameraObject.getActionPicture().takeFragmentPhoto();
    }

    public boolean selectedFragmentPicture(){
        return magicalCameraObject.getActionPicture().selectedFragmentPicture();
    }

    //Face detector methods
    public Bitmap faceDetector(int stroke, int color){
        return  magicalCameraObject.getFaceRecognition().faceDetector(stroke, color,
                magicalCameraObject.getActivity(), magicalCameraObject.getActionPicture().getActionPictureObject().getMyPhoto());
    }

    public Bitmap faceDetector(){
        return magicalCameraObject.getFaceRecognition().faceDetector(5, RED,
                magicalCameraObject.getActivity(), magicalCameraObject.getActionPicture().getActionPictureObject().getMyPhoto());
    }

    public FaceRecognitionObject getFaceRecognitionInformation(){
        return magicalCameraObject.getFaceRecognition().getFaceRecognitionInformation();
    }


    //Image information methods
    public boolean hasImageInformation() {
        return magicalCameraObject.getPrivateInformation().getImageInformation(magicalCameraObject.getUriPaths().getUriPathsObject().getRealPath());
    }

    public PrivateInformationObject getPrivateInformation() {
        return magicalCameraObject.getPrivateInformation().getPrivateInformationObject();
    }


    /**
     * ***********************************************
     * This methods save the photo in memory device
     * with diferents params
     * **********************************************
     */
    public String savePhotoInMemoryDevice(Bitmap bitmap, String photoName, boolean autoIncrementNameByDate) {
        return magicalCameraObject.getSaveEasyPhoto().writePhotoFile(bitmap,
                photoName, "MAGICAL CAMERA", MagicalCameraObject.PNG, autoIncrementNameByDate, magicalCameraObject.getActivity());
    }

    public String savePhotoInMemoryDevice(Bitmap bitmap, String photoName, Bitmap.CompressFormat format, boolean autoIncrementNameByDate) {
        return magicalCameraObject.getSaveEasyPhoto().writePhotoFile(bitmap,
                photoName, "MAGICAL CAMERA", format, autoIncrementNameByDate, magicalCameraObject.getActivity());
    }

    public String savePhotoInMemoryDevice(Bitmap bitmap, String photoName, String directoryName, boolean autoIncrementNameByDate) {
        return magicalCameraObject.getSaveEasyPhoto().writePhotoFile(bitmap,
                photoName, directoryName, MagicalCameraObject.PNG, autoIncrementNameByDate, magicalCameraObject.getActivity());
    }

    public String savePhotoInMemoryDevice(Bitmap bitmap, String photoName, String directoryName,
                                           Bitmap.CompressFormat format, boolean autoIncrementNameByDate) {
        return magicalCameraObject.getSaveEasyPhoto().writePhotoFile(bitmap,
                photoName, directoryName, format, autoIncrementNameByDate, magicalCameraObject.getActivity());
    }



    //get variables
    public Bitmap getPhoto(){
        return magicalCameraObject.getActionPicture().getActionPictureObject().getMyPhoto();
    }

    public void resultPhoto(int requestCode, int resultCode, Intent data){
        magicalCameraObject.getActionPicture().resultPhoto(requestCode, resultCode, data);
    }

    public void resultPhoto(int requestCode, int resultCode, Intent data, boolean doLandScape){
        magicalCameraObject.getActionPicture().resultPhoto(requestCode, resultCode, data, doLandScape);
    }

    public void permissionGrant(int requestCode, String[] permissions, int[] grantResults){
        this.permissionGranted.permissionGrant(requestCode, permissions, grantResults);
    }

    public Intent getIntentFragment(){
        return magicalCameraObject.getActionPicture().getActionPictureObject().getIntentFragment();
    }
    //endregion
}