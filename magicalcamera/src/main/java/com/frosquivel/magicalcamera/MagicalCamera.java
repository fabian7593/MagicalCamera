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
import com.frosquivel.magicalcamera.Utilities.PictureUtils;

import static android.graphics.Color.*;

/**
 * Created by          Fabián Rosales Esquivel
 * Created Date        on 5/15/16
 * This is an android library to take easy picture,
 * This have the objects or anothers classes to toast the best form to use this library
 * I recommended use only this class in your code for take a best experience of this third party library
 * You have the possibility of write me in my personal email fabian7593@gmail.com
 * v2
 */
public class MagicalCamera {

    //compress format public static variables
    public static final Bitmap.CompressFormat JPEG = Bitmap.CompressFormat.JPEG;
    public static final Bitmap.CompressFormat PNG = Bitmap.CompressFormat.PNG;
    public static final Bitmap.CompressFormat WEBP = Bitmap.CompressFormat.WEBP;

    //the orientation rotates
    public static final int ORIENTATION_ROTATE_NORMAL = 0;
    public static final int ORIENTATION_ROTATE_90 = 90;
    public static final int ORIENTATION_ROTATE_180 = 180;
    public static final int ORIENTATION_ROTATE_270 = 270;

    //other constants for realized tasks
    public static final int TAKE_PHOTO = 0;
    public static final int SELECT_PHOTO = 1;
    public static final int LANDSCAPE_CAMERA = 2;
    public static final int NORMAL_CAMERA = 3;

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
    public boolean initImageInformation() {
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
                photoName, "MAGICAL CAMERA", PNG, autoIncrementNameByDate, magicalCameraObject.getActivity());
    }

    public String savePhotoInMemoryDevice(Bitmap bitmap, String photoName, Bitmap.CompressFormat format, boolean autoIncrementNameByDate) {
        return magicalCameraObject.getSaveEasyPhoto().writePhotoFile(bitmap,
                photoName, "MAGICAL CAMERA", format, autoIncrementNameByDate, magicalCameraObject.getActivity());
    }

    public String savePhotoInMemoryDevice(Bitmap bitmap, String photoName, String directoryName, boolean autoIncrementNameByDate) {
        return magicalCameraObject.getSaveEasyPhoto().writePhotoFile(bitmap,
                photoName, directoryName, PNG, autoIncrementNameByDate, magicalCameraObject.getActivity());
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

    public void setPhoto(Bitmap bitmap){
        magicalCameraObject.getActionPicture().getActionPictureObject().setMyPhoto(bitmap);
    }

    public void resultPhoto(int requestCode, int resultCode, Intent data){
        magicalCameraObject.getActionPicture().resultPhoto(requestCode, resultCode, data);
    }

    public void resultPhoto(int requestCode, int resultCode, Intent data, int rotateType){
        magicalCameraObject.getActionPicture().resultPhoto(requestCode, resultCode, data, rotateType);
    }

    public void permissionGrant(int requestCode, String[] permissions, int[] grantResults){
        this.permissionGranted.permissionGrant(requestCode, permissions, grantResults);
    }

    public Intent getIntentFragment(){
        return magicalCameraObject.getActionPicture().getActionPictureObject().getIntentFragment();
    }

    //methods to rotate picture
    public Bitmap rotatePicture(int rotateType){
        if(getPhoto() != null)
            return PictureUtils.rotateImage(getPhoto(), rotateType);
        else
            return null;
    }

    public Bitmap rotatePicture(Bitmap picture,int rotateType){
        if(picture != null)
            return PictureUtils.rotateImage(picture, rotateType);
        else
            return null;
    }
    //endregion
}