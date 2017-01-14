package com.frosquivel.magicalcamera.Functionallities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.SparseArray;
import com.frosquivel.magicalcamera.Objects.FaceRecognitionObject;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import static android.graphics.Color.RED;

/**
 * Created by Fabian Rosales Esquivel (Frosquivel Developer)
 * Created Date 06/12/2016.
 * Made in Costa Rica
 * This is the class for use face recognition
 */

public class FaceRecognition {

    //================================================================================
    // Properties and constructor
    //================================================================================
    //region Properties
    private FaceRecognitionObject faceRecognitionInformation;
    private FaceDetector detector;

    //Getter and setter methods
    public FaceRecognitionObject getFaceRecognitionInformation() {
        return faceRecognitionInformation;
    }
    //endregion

    //region Constructor
    public FaceRecognition(){
        super();
        faceRecognitionInformation = new FaceRecognitionObject();
    }
    //endregion

    //================================================================================
    // Face detector methods
    //================================================================================
    //region FaceDetector
    public Bitmap faceDetector(int stroke, int color, Activity activity, Bitmap photo){
        return faceDetection(stroke, color, activity, photo);
    }

    public Bitmap faceDetector(Activity activity, Bitmap photo){
        return faceDetection(5, RED, activity, photo);
    }

    /***
     * This method realize the face detection, and this call in another methods
     * for automatice the process
     * @param stroke the bold of line to show around the face
     * @param color the color of rectangle to recognizer the face
     * @param activity the currect activity
     * @param photo your photo
     * @return
     */
    private Bitmap faceDetection(int stroke, int color, Activity activity, Bitmap photo){
         this.detector = new FaceDetector.Builder(activity)
                .setMode(FaceDetector.ACCURATE_MODE)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setTrackingEnabled(false)
                .build();
            try {
                if (false == this.detector.isOperational()) {
                    return null;
                }

                //Add the image on a Frame object
                Frame frame = new Frame.Builder()
                        .setBitmap(photo)
                        .build();

                //Detect all faces from Frame object
                SparseArray<Face> faceArray = detector.detect(frame);

                //Do some drawing on faces
                Bitmap outBitmap = drawOnFace(faceArray, photo, stroke, color);

                //Releasing the detector object
                this.detector.release();
                return (outBitmap != null) ? outBitmap : photo;
            }catch(Exception ev){
                return null;
            }
    }


    /**
     * Method to drawing on faces
     */
    private Bitmap drawOnFace(SparseArray<Face> faceArray, Bitmap photo, int stroke, int color){
        Bitmap outBitmap = Bitmap.createBitmap(photo.getWidth(), photo.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(outBitmap);
        canvas.drawBitmap(photo, 0, 0, null);

        for(int i=0; i < faceArray.size(); i++){
            int key = faceArray.keyAt(i);
            // get the object by the key.
            Face face = faceArray.get(key);
            //Drawing rectangle on each face
            drawRectangle(canvas, face.getPosition(), face.getWidth(), face.getHeight(),stroke,color);
            this.faceRecognitionInformation.setListLandMarkPhoto(face.getLandmarks());
        }
        return outBitmap;
    }

    //draw a rectangle in your bitmap
    private void drawRectangle(Canvas canvas, PointF point, float width, float height, int stroke, int color){
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(stroke);
        paint.setStyle(Paint.Style.STROKE);

        float x1 = point.x;
        float y1 = point.y;
        float x2 = x1 + width;
        float y2 = y1 + height;

        RectF rect = new RectF(x1, y1, x2, y2);
        canvas.drawRect(rect, paint);
    }
    //endregion
}
