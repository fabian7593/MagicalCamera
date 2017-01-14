package com.frosquivel.magicalcamera.Objects;

import android.app.Activity;
import android.graphics.Bitmap;

import com.frosquivel.magicalcamera.Functionallities.ActionPicture;
import com.frosquivel.magicalcamera.Functionallities.FaceRecognition;
import com.frosquivel.magicalcamera.Functionallities.PrivateInformation;
import com.frosquivel.magicalcamera.Functionallities.SaveEasyPhoto;
import com.frosquivel.magicalcamera.Functionallities.URIPaths;

/**
 * Created by Fabian on 07/12/2016.
 */

public class MagicalCameraObject {
    //================================================================================
    // Properties
    //================================================================================

    //my activity variable
    private Activity activity;

    //the face recognition class for instance
    private FaceRecognition faceRecognition;

    //the private information class for instance
    private PrivateInformation privateInformation;

    //the private variable of saveEasyPhoto
    private SaveEasyPhoto saveEasyPhoto;

    //the actions to take pictures or selected
    private ActionPicture actionPicture;

    //the uri of paths class
    private URIPaths uriPaths;
    //endregion


    //Constructor
    public MagicalCameraObject(Activity activity, PermissionGrantedObject permissionGranted){
        this.activity = activity;
        this.faceRecognition = new FaceRecognition();
        this.privateInformation = new PrivateInformation();
        this.uriPaths = new URIPaths(this.privateInformation, activity);
        this.saveEasyPhoto = new SaveEasyPhoto();
        this.actionPicture = new ActionPicture(activity, ActionPictureObject.BEST_QUALITY_PHOTO, permissionGranted, this.uriPaths);
    }

    public MagicalCameraObject(Activity activity, int qualityPhoto, PermissionGrantedObject permissionGranted){
        this.activity = activity;
        this.faceRecognition = new FaceRecognition();
        this.privateInformation = new PrivateInformation();
        this.uriPaths = new URIPaths(this.privateInformation, activity);
        this.saveEasyPhoto = new SaveEasyPhoto();
        this.actionPicture = new ActionPicture(activity, qualityPhoto, permissionGranted, this.uriPaths);
    }


    //================================================================================
    // Accessors
    //================================================================================

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public FaceRecognition getFaceRecognition() {
        return faceRecognition;
    }

    public void setFaceRecognition(FaceRecognition faceRecognition) {
        this.faceRecognition = faceRecognition;
    }

    public PrivateInformation getPrivateInformation() {
        return privateInformation;
    }

    public void setPrivateInformation(PrivateInformation privateInformation) {
        this.privateInformation = privateInformation;
    }

    public SaveEasyPhoto getSaveEasyPhoto() {
        return saveEasyPhoto;
    }

    public void setSaveEasyPhoto(SaveEasyPhoto saveEasyPhoto) {
        this.saveEasyPhoto = saveEasyPhoto;
    }

    public ActionPicture getActionPicture() {
        return actionPicture;
    }

    public void setActionPicture(ActionPicture actionPicture) {
        this.actionPicture = actionPicture;
    }
    public URIPaths getUriPaths() {
        return uriPaths;
    }
    //endregion
}
