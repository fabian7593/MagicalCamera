package com.frosquivel.magicalcamera.Functionallities;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.Objects.ActionPictureObject;
import com.frosquivel.magicalcamera.Objects.MagicalCameraObject;
import com.frosquivel.magicalcamera.Objects.PermissionGrantedObject;
import com.frosquivel.magicalcamera.Utilities.PictureUtils;

/**
 * Created by Fabian Rosales Esquivel (Frosquivel Developer)
 * Created Date 07/12/2016.
 * Made in Costa Rica
 * This class call the intent of take picture or select picture in device
 */

    public class ActionPicture {

    //================================================================================
    // Properties and constructor
    //================================================================================
    //region Properties
    private ActionPictureObject actionPictureObject;
    private PermissionGrantedObject permissionGrantedObject;
    private URIPaths uriPaths;

    //Getter and Setter methods
    public void setMagicalCameraPermissionGranted(PermissionGrantedObject permissionGrantedObject) {
        this.permissionGrantedObject = permissionGrantedObject;
    }

    public ActionPictureObject getActionPictureObject() {
        return actionPictureObject;
    }
    //endregion

    //region Constructor
    public ActionPicture(Activity activity, int resizePicture,
                         PermissionGrantedObject permissionGrantedObject, URIPaths uriPaths){
        this.actionPictureObject = new ActionPictureObject();
        this.permissionGrantedObject = permissionGrantedObject;
        this.uriPaths = uriPaths;

        this.actionPictureObject.setActivity(activity);
        this.actionPictureObject.setResizePhoto(resizePicture);
    }

    public ActionPicture(Activity activity,int resizePicture, URIPaths uriPaths){
        this.actionPictureObject = new ActionPictureObject();
        this.permissionGrantedObject = null;
        this.uriPaths = uriPaths;
        this.actionPictureObject.setActivity(activity);
        this.actionPictureObject.setResizePhoto(resizePicture);
    }
    //endregion


    //================================================================================
    // Take and Select photos
    //================================================================================
    //region Photo Methods

    /**
     * This method call the intent for take the picture in activity screen
     * Too validate the permissions in android 6.0
     * @return return true if the picture was taken
     */
    public boolean takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri FileUri = this.uriPaths.getPhotoFileUri(ActionPictureObject.photoNameAuxiliar,
                ActionPictureObject.photoNameAuxiliar, this.actionPictureObject.getActivity());

        if (FileUri != null) {

            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUri);
            if (intent.resolveActivity(this.actionPictureObject.getActivity().getPackageManager()) != null) {
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    if(this.permissionGrantedObject.isCameraPermission()){
                        this.actionPictureObject.getActivity().startActivityForResult(intent, MagicalCamera.TAKE_PHOTO);
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    this.actionPictureObject.getActivity().startActivityForResult(intent, MagicalCamera.TAKE_PHOTO);
                    return true;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method call the intent for take the picture in fragment screen
     * Too validate the permissions in android 6.0
     * @return return true if the picture was taken
     */
    public boolean takeFragmentPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri FileUri = this.uriPaths.getPhotoFileUri(ActionPictureObject.photoNameAuxiliar,
                ActionPictureObject.photoNameAuxiliar, this.actionPictureObject.getActivity());

        if (FileUri != null) {

            intent.putExtra(MediaStore.EXTRA_OUTPUT, this.uriPaths.getPhotoFileUri(ActionPictureObject.photoNameAuxiliar,
                    ActionPictureObject.photoNameAuxiliar, this.actionPictureObject.getActivity()));

            if (intent.resolveActivity(this.actionPictureObject.getActivity().getPackageManager()) != null) {

                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    if(this.permissionGrantedObject.isCameraPermission()){
                        this.actionPictureObject.setIntentFragment(intent);
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    this.actionPictureObject.setIntentFragment(intent);
                    return true;
                }

            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    /**
     * This call the intent to selected the picture for activity screen
     * @param headerName the header name of popUp that you need to shown
     * @return return true if the photo was taken or false if it was not.
     */
    public boolean selectedPicture(String headerName) {

        try {
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            this.actionPictureObject.getActivity().startActivityForResult(
                    Intent.createChooser(intent, (!headerName.equals("") ? headerName : "Magical Camera")),
                    MagicalCamera.SELECT_PHOTO);

            return true;
        }catch (Exception ev){
            return false;
        }
    }

    /**
     * This call the intent to selected the picture for activity screen
     * @return return true if the photo was taken or false if it was not.
     */
    public boolean selectedFragmentPicture() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (intent.resolveActivity(this.actionPictureObject.getActivity().getPackageManager()) != null) {
            this.actionPictureObject.setIntentFragment(intent);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method obtain the path of the picture selected, and convert this in the
     * phsysical path of the image, and decode the file with the respective options,
     * resize the file and change the quality of photos selected.
     *
     * @param data the intent data for take the photo path
     * @return return a bitmap of the photo selected
     */
    @SuppressWarnings("deprecation")
    private Bitmap onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = this.actionPictureObject.getActivity().managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String selectedImagePath = cursor.getString(column_index);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(selectedImagePath, options);
        bm = PictureUtils.resizePhoto(bm, this.actionPictureObject.getResizePhoto(), true);

        this.uriPaths.getPhotoFileUri(selectedImagePath);
        if (bm != null)
            return bm;
        else
            return null;
    }

    /**
     * Save the photo in memory bitmap, resize and return the photo
     * @return the bitmap of the respective photo
     */
    private Bitmap onTakePhotoResult() {
        Uri takenPhotoUri = this.uriPaths.getPhotoFileUri(ActionPictureObject.photoNameAuxiliar,
                ActionPictureObject.photoNameAuxiliar, actionPictureObject.getActivity());
        // by this point we have the camera photo on disk
        if (takenPhotoUri != null) {
            Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
            takenImage = PictureUtils.resizePhoto(takenImage, actionPictureObject.getResizePhoto(), true);
            return takenImage;
        } else {
            return null;
        }
    }
    //endregion

    //region Method to call in Override
    /**
     * This methods is called in the override method onActivityResult
     * for the respective activation, and this validate which of the intentn result be,
     * for example: if is selected file or if is take picture
     */
    public void resultPhoto(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MagicalCamera.SELECT_PHOTO) {
                this.actionPictureObject.setMyPhoto(onSelectFromGalleryResult(data));
            } else if (requestCode == MagicalCamera.TAKE_PHOTO) {
                this.actionPictureObject.setMyPhoto(onTakePhotoResult());
            }
        }
    }

    /**
     * This methods is called in the override method onActivityResult
     * for the respective activation, and this validate which of the intentn result be,
     * for example: if is selected file or if is take picture
     *
     * doLandScape
     * BUT you have the posibillity of rotate the picture "manually", with the parameter doLandScape
     */
    public void resultPhoto(int requestCode, int resultCode, Intent data, int rotatePicture) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MagicalCamera.SELECT_PHOTO) {
                this.actionPictureObject.setMyPhoto(onSelectFromGalleryResult(data));
            } else if (requestCode == MagicalCamera.TAKE_PHOTO) {
                this.actionPictureObject.setMyPhoto(onTakePhotoResult());
            }

            if (this.actionPictureObject.getMyPhoto() != null) {
                this.actionPictureObject.setMyPhoto(PictureUtils.rotateImage(this.actionPictureObject.getMyPhoto(), rotatePicture));
            }
        }
    }
    //endregion
}
