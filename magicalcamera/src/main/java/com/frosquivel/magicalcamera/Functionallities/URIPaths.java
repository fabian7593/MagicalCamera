package com.frosquivel.magicalcamera.Functionallities;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.frosquivel.magicalcamera.Objects.URIPathsObject;
import java.io.File;

/**
 * Created by Fabian Rosales Esquivel (Frosquivel Developer)
 * Created Date 07/12/2016.
 * Made in Costa Rica
 * This class return the uri photo like real uri or fake (for a some jobs)
 */

public class URIPaths {
    private URIPathsObject uriPathsObject;
    private PrivateInformation privateInformation;
    private Context context;

    public URIPaths(PrivateInformation privateInformation, Context context){
        this.privateInformation = privateInformation;
        this.uriPathsObject = new URIPathsObject();
        this.context = context;
    }

    public URIPathsObject getUriPathsObject() {
        return uriPathsObject;
    }

    public void setUriPathsObject(URIPathsObject uriPathsObject) {
        this.uriPathsObject = uriPathsObject;
    }

    //================================================================================
    // Get URI photo for selected photos of device
    //================================================================================
    // Returns the Uri for a photo stored on memory device
    // the real URI for show the information of the photo
    // select photos
    public Uri getPhotoFileUri(String fileDir) {
        File mediaStorageDir = null;
        mediaStorageDir = new File("", fileDir);

        Uri photoURI = null;
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            photoURI = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".provider", mediaStorageDir);
        }else{
            photoURI = Uri.fromFile(mediaStorageDir);
        }

        this.uriPathsObject.setRealPath(mediaStorageDir.getPath());
        try {
            this.privateInformation.getImageInformation(uriPathsObject.getRealPath());
        } catch (Exception ex) {}
        return photoURI;
    }



    // Returns the Uri for a photo stored on memory device
    public Uri getPhotoFileUri(String fileName, String fileDir, Context context) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            File mediaStorageDir = null;
            mediaStorageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileDir);

            if (mediaStorageDir != null) {
                return getUriFiles(mediaStorageDir, fileName);
            } else {
                mediaStorageDir = new File(context.getFilesDir(), fileDir);
                return getUriFiles(mediaStorageDir, fileName);
            }
        } else {
            File mediaStorageDir = new File(
                    context.getFilesDir(), fileDir);
            return getUriFiles(mediaStorageDir, fileName);
        }
    }

    // return the real URI from files
    private Uri getUriFiles(File mediaStorageDir, String fileName) {

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            mediaStorageDir.exists();
            mediaStorageDir.mkdirs();
        }

        try {
            return getUriAuxiliar(mediaStorageDir.getPath() + File.separator + fileName);
        } catch (Exception ev) {
            try {
                return getUriAuxiliar(Environment.getExternalStorageDirectory() + "/DCIM/", fileName);
            } catch (Exception ex) {
                try {
                    return getUriAuxiliar(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "");
                } catch (Exception e) {
                    try {
                        return getUriAuxiliar(Environment.getDataDirectory() + "");
                    } catch (Exception ef) {
                        return null;
                    }
                }
            }
        }
    }

    /**
     * Obtain the Uri from file (like an auxiliar method)
     * @param direction the direction string
     * @param nameFile the name string
     * @return
     */
    private Uri getUriAuxiliar(String direction, String nameFile) {
        try {
            File file = new File(direction, nameFile);

            Uri photoURI = null;
            if (android.os.Build.VERSION.SDK_INT >= 24) {
                photoURI = FileProvider.getUriForFile(context,
                        context.getApplicationContext().getPackageName() + ".provider", file);
            }else{
                photoURI = Uri.fromFile(file);
            }

            this.uriPathsObject.setRealPath(photoURI.getPath());
            try {
                this.privateInformation.getImageInformation(this.uriPathsObject.getRealPath());
            } catch (Exception ev) {}

            return photoURI;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Obtain the Uri from file (like an auxiliar method)
     * @param direction
     * @return
     */
    private Uri getUriAuxiliar(String direction) {
        try {
            File file = new File(direction);
            Uri photoURI;
            if (android.os.Build.VERSION.SDK_INT >= 24) {
                 photoURI = FileProvider.getUriForFile(context,
                         context.getApplicationContext().getPackageName() + ".provider", file);
            }else{
                photoURI = Uri.fromFile(file);
            }

            this.uriPathsObject.setRealPath(photoURI.getPath());
            return photoURI;
        } catch (Exception ex) {
            return null;
        }
    }

    // Returns true if external storage for photos is available
    private static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
    //endregion

}
