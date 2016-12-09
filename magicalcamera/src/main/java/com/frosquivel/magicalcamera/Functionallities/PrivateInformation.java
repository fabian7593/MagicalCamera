package com.frosquivel.magicalcamera.Functionallities;

import android.media.ExifInterface;

import com.frosquivel.magicalcamera.Objects.PrivateInformationObject;
import com.frosquivel.magicalcamera.Utilities.Utils;

import java.io.IOException;

/**
 * Created by Fabian on 07/12/2016.
 */

public class PrivateInformation {
    
    //object
    private PrivateInformationObject privateInformationObject;
    public PrivateInformationObject getPrivateInformationObject() {
        return privateInformationObject;
    }

    //constructor
    public PrivateInformation(){
        super();
        privateInformationObject = new PrivateInformationObject();
    }

    //================================================================================
    // Exif interface methods
    //================================================================================
    private ExifInterface getAllFeatures(String realPath) {
        if (!realPath.equals("")) {
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(realPath.toString());
                return exif;
            } catch (IOException e) {
                return exif;
            }
        } else {
            return null;
        }
    }

    public boolean getImageInformation(String realPath) {
        try {
            ExifInterface exif = getAllFeatures(realPath);
            if (exif != null) {

                float[] latLong = new float[2];
                try{
                    exif.getLatLong(latLong);
                    privateInformationObject.setLatitude(latLong[0]);
                    privateInformationObject.setLongitude(latLong[1]);
                }catch(Exception ex){}


                if (Utils.notNullNotFill(exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF))) {
                    privateInformationObject.setLatitudeReference(exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));
                }

                if (Utils.notNullNotFill(exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF))) {
                    privateInformationObject.setLongitudeReference(exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));
                }

                if (Utils.notNullNotFill(exif.getAttribute(ExifInterface.TAG_DATETIME))) {
                    privateInformationObject.setDateTimeTakePhoto(exif.getAttribute(ExifInterface.TAG_DATETIME));
                }

                if (Utils.notNullNotFill(exif.getAttribute(ExifInterface.TAG_ORIENTATION))) {
                    privateInformationObject.setOrientation(exif.getAttribute(ExifInterface.TAG_ORIENTATION));
                }

                if (Utils.notNullNotFill(exif.getAttribute(ExifInterface.TAG_ISO))) {
                    privateInformationObject.setIso(exif.getAttribute(ExifInterface.TAG_ISO));
                }

                if (Utils.notNullNotFill(exif.getAttribute(ExifInterface.TAG_GPS_DATESTAMP))) {
                    privateInformationObject.setDateStamp(exif.getAttribute(ExifInterface.TAG_GPS_DATESTAMP));
                }

                if (Utils.notNullNotFill(exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH))) {
                    privateInformationObject.setImageLength(exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH));
                }

                if (Utils.notNullNotFill(exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH))) {
                    privateInformationObject.setImageWidth(exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));
                }

                if (Utils.notNullNotFill(exif.getAttribute(ExifInterface.TAG_MODEL))) {
                    privateInformationObject.setModelDevice(exif.getAttribute(ExifInterface.TAG_MODEL));
                }

                if (Utils.notNullNotFill(exif.getAttribute(ExifInterface.TAG_MAKE))) {
                    privateInformationObject.setMakeCompany(exif.getAttribute(ExifInterface.TAG_MAKE));
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}
