package com.frosquivel.magicalcamera.Objects;

/**
 * Created by Fabian on 07/12/2016.
 */

public class PrivateInformationObject {
    //Properties of face recognition
    float latitude;
    String latitudeReference;
    float longitude;
    String longitudeReference;
    String dateTimeTakePhoto;
    String imageLength;
    String imageWidth;
    String modelDevice;
    String makeCompany;
    String orientation;
    String iso;
    String dateStamp;

    public void setLongitudeReference(String longitudeReference) {
        this.longitudeReference = longitudeReference;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public void setDateTimeTakePhoto(String dateTimeTakePhoto) {
        this.dateTimeTakePhoto = dateTimeTakePhoto;
    }

    public void setImageLength(String imageLength) {
        this.imageLength = imageLength;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLatitudeReference(String latitudeReference) {
        this.latitudeReference = latitudeReference;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setMakeCompany(String makeCompany) {
        this.makeCompany = makeCompany;
    }

    public void setModelDevice(String modelDevice) {
        this.modelDevice = modelDevice;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getLatitudeReference() {
        return latitudeReference;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getLongitudeReference() {
        return longitudeReference;
    }

    public String getMakeCompany() {
        return makeCompany;
    }

    public String getModelDevice() {
        return modelDevice;
    }

    public String getDateTimeTakePhoto() {
        return dateTimeTakePhoto;
    }

    public String getImageLength() {
        return imageLength;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public String getOrientation() {
        return orientation;
    }

    public String getIso() {
        return iso;
    }

    public String getDateStamp() {
        return dateStamp;
    }
}
