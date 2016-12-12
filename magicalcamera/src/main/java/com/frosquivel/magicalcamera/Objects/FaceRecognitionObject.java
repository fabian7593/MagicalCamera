package com.frosquivel.magicalcamera.Objects;

import com.google.android.gms.vision.face.Landmark;
import java.util.List;

/**
 * Created by Fabian on 06/12/2016.
 */

public class FaceRecognitionObject {
    //Getters and Setters method
    public List<Landmark> listLandMarkPhoto;

    public List<Landmark> getListLandMarkPhoto() {
        return listLandMarkPhoto;
    }

    public void setListLandMarkPhoto(List<Landmark> listLandMarkPhoto) {
        this.listLandMarkPhoto = listLandMarkPhoto;
    }
}
