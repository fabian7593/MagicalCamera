package com.frosquivel.magicalcamera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.ExifInterface;

import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.SparseArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import static android.graphics.Color.*;

/**
 * Created by          Fabián Rosales Esquivel
 * Visit my web page   http://www.frosquivel.com
 * Visit my blog       http://www.frosquivel.com/blog
 * Created Date        on 5/15/16
 * This is an android library to take easy picture
 */
public class MagicalCamera {



    //================================================================================
    // Properties
    //================================================================================
    //region Properties
    //The constants for take or selected photo validate
    public static int TAKE_PHOTO = 0;
    public static int SELECT_PHOTO = 1;
    public static final int LANDSCAPE_CAMERA = 1;
    private static final int NORMAL_CAMERA = 3;

    //compress format public static variables
    public static Bitmap.CompressFormat JPEG = Bitmap.CompressFormat.JPEG;
    public static Bitmap.CompressFormat PNG = Bitmap.CompressFormat.PNG;
    public static Bitmap.CompressFormat WEBP = Bitmap.CompressFormat.WEBP;

    //the max of quality photo
    int BEST_QUALITY_PHOTO = 4000;

    //Your own resize picture
    int resizePhoto;

    //the names of our photo
    String thePhotoName;
    String anotherPhotoName;

    //my activity variable
    Activity activity;

    //bitmap to set and get
    Bitmap myPhoto;

    //my intent curret fragment (only use for fragments)
    Intent intentFragment;

    //THE CURRENT IMAGE PATH
    private static String imgPath;

    //the references properties photo
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

    private String realPath;
    private List<Landmark> listLandMarkPhoto;
    //endregion





    //================================================================================
    // Accessors
    //================================================================================
    //region Getter and Setters
    public Intent getIntentFragment() {
        return intentFragment;
    }

    public void setMyPhoto(Bitmap myPhoto) {
        this.myPhoto = myPhoto;
    }

    public Bitmap getMyPhoto() {
        return myPhoto;
    }

    public int getResizePhoto() {
        return resizePhoto;
    }

    public void setResizePhoto(int resizePhoto) {
        if (resizePhoto < BEST_QUALITY_PHOTO)
            this.resizePhoto = resizePhoto;
        else
            this.resizePhoto = BEST_QUALITY_PHOTO;
    }

    private void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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

    public String getRealPath() {
        return realPath;
    }

    public List<Landmark> getListLandMarkPhoto() {
        return listLandMarkPhoto;
    }
    //endregion






    //================================================================================
    // Constructs
    //================================================================================
    //region Construct
    public MagicalCamera(Activity activity, int resizePhoto) {
        if (resizePhoto < BEST_QUALITY_PHOTO)
            this.resizePhoto = resizePhoto;
        else
            this.resizePhoto = BEST_QUALITY_PHOTO;

        if (resizePhoto == 0) {
            this.resizePhoto = 1;
        }
        this.activity = activity;
    }

    public MagicalCamera(Activity activity) {
        this.activity = activity;
        this.resizePhoto = BEST_QUALITY_PHOTO;
    }
    //endregion





    //================================================================================
    // Principal Methods
    //================================================================================
    //region Take and Select photos

    /**
     * This method call the intent to take photo
     */
    public boolean takePhoto() {
        this.thePhotoName = "MagicalCamera";
        this.anotherPhotoName = "MagicalCamera";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri FileUri = getPhotoFileUri(this.thePhotoName, this.anotherPhotoName, this.activity);

        if (FileUri != null) {

            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUri);
            if (intent.resolveActivity(this.activity.getPackageManager()) != null) {
                this.activity.startActivityForResult(intent, TAKE_PHOTO);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * This library call the intent to take photo
     */
    public boolean takeFragmentPhoto() {
        this.thePhotoName = "MagicalCamera";
        this.anotherPhotoName = "MagicalCamera";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri FileUri = getPhotoFileUri(this.thePhotoName, this.anotherPhotoName, this.activity);

        if (FileUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(this.thePhotoName, this.anotherPhotoName, this.activity));
            if (intent.resolveActivity(this.activity.getPackageManager()) != null) {
                this.intentFragment = intent;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * This call the intent to selected the picture
     *
     * @param headerName the header name of popUp
     */
    public boolean selectedPicture(String headerName) {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        this.activity.startActivityForResult(
                Intent.createChooser(intent, (!headerName.equals("") ? headerName : "Magical Camera")),
                SELECT_PHOTO);

        return true;
    }

    /**
     * This call the intent to selected the picture
     */
    public boolean selectedFragmentPicture() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (intent.resolveActivity(this.activity.getPackageManager()) != null) {
            this.intentFragment = intent;
            return true;
        } else {
            return false;
        }
    }

    /**
     * This methods is called in the override method onActivityResult
     * for the respective activation, and this validate which of the intentn result be,
     * for example: if is selected file or if is take picture
     */
    public void resultPhoto(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PHOTO) {
                this.myPhoto = onSelectFromGalleryResult(data);
            } else if (requestCode == TAKE_PHOTO) {
                this.myPhoto = onTakePhotoResult();
            }

            if (this.myPhoto != null) {
                if (ifCameraLandScape(true) == LANDSCAPE_CAMERA) {
                    this.myPhoto = rotateImage(getMyPhoto(), 270);
                }
            }
        }
    }


    public void resultPhoto(int requestCode, int resultCode, Intent data, boolean doLandScape) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PHOTO) {
                this.myPhoto = onSelectFromGalleryResult(data);
            } else if (requestCode == TAKE_PHOTO) {
                this.myPhoto = onTakePhotoResult();
            }

            if (this.myPhoto != null) {
                if (ifCameraLandScape(doLandScape) == LANDSCAPE_CAMERA) {
                    this.myPhoto = rotateImage(getMyPhoto(), 270);
                }
            }
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
        Cursor cursor = this.activity.managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String selectedImagePath = cursor.getString(column_index);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(selectedImagePath, options);
        bm = resizePhoto(bm, this.resizePhoto, true);
        getPhotoFileUri(selectedImagePath);
        if (bm != null)
            return bm;
        else
            return null;
    }

    /**
     * Save the photo in memory bitmap, resize and return the photo
     *
     * @return the bitmap of the respective photo
     */
    public Bitmap onTakePhotoResult() {
        Uri takenPhotoUri = getPhotoFileUri(this.thePhotoName, this.anotherPhotoName, this.activity);
        // by this point we have the camera photo on disk
        if (takenPhotoUri != null) {
            Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
            takenImage = resizePhoto(takenImage, this.resizePhoto, true);
            return takenImage;
        } else {
            return null;
        }
    }
    //endregion






    //================================================================================
    // Save Photo in device
    //================================================================================
    //region Save Photo in device

    /**
     * This library write the file in the device storage or sdcard
     *
     * @param bitmap                  the bitmap that you need to write in device
     * @param photoName               the photo name
     * @param directoryName           the directory that you need to create the picture
     * @param format                  the format of the photo, maybe png or jpeg
     * @param autoIncrementNameByDate is this variable is active the system create
     *                                the photo with a number of the date, hour, and second to diferenciate this
     * @return return true if the photo is writen
     */
    private boolean writePhotoFile(Bitmap bitmap, String photoName, String directoryName,
                                   Bitmap.CompressFormat format, boolean autoIncrementNameByDate) {

        if (bitmap == null) {
            return false;
        } else {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(format, 100, bytes);

            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = df.format(Calendar.getInstance().getTime());

            if (format == PNG) {
                photoName = (autoIncrementNameByDate) ? photoName + "_" + date + ".png" : photoName + ".png";
            } else if (format == JPEG) {
                photoName = (autoIncrementNameByDate) ? photoName + "_" + date + ".jpeg" : photoName + ".jpeg";
            } else if (format == WEBP) {
                photoName = (autoIncrementNameByDate) ? photoName + "_" + date + ".webp" : photoName + ".webp";
            }

            File wallpaperDirectory = null;

            try {
                wallpaperDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + directoryName + "/");
            } catch (Exception ev) {
                try {
                    wallpaperDirectory = Environment.getExternalStorageDirectory();
                } catch (Exception ex) {
                    try {
                        wallpaperDirectory = Environment.getDataDirectory();
                    } catch (Exception e) {
                        wallpaperDirectory = Environment.getRootDirectory();
                    }
                }
            }

            if (wallpaperDirectory != null) {
                if (!wallpaperDirectory.exists()) {
                    wallpaperDirectory.exists();
                    wallpaperDirectory.mkdirs();
                }

                File f = new File(wallpaperDirectory, photoName);
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    this.activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.parse("file://" + f.getAbsolutePath())));

                    try {
                        //Update the System
                        Uri u = Uri.parse(f.getAbsolutePath());
                        this.activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, u));
                    } catch (Exception ex) {
                    }

                    return true;
                } catch (Exception ev) {
                    return false;
                }

            } else {
                return false;
            }
        }
    }

    /**
     * ***********************************************
     * This methods save the photo in memory device
     * with diferents params
     * **********************************************
     */
    public boolean savePhotoInMemoryDevice(Bitmap bitmap, String photoName, boolean autoIncrementNameByDate) {
        return writePhotoFile(bitmap, photoName, "MAGICAL CAMERA", PNG, autoIncrementNameByDate);
    }

    public boolean savePhotoInMemoryDevice(Bitmap bitmap, String photoName, Bitmap.CompressFormat format, boolean autoIncrementNameByDate) {
        return writePhotoFile(bitmap, photoName, "MAGICAL CAMERA", format, autoIncrementNameByDate);
    }

    public boolean savePhotoInMemoryDevice(Bitmap bitmap, String photoName, String directoryName, boolean autoIncrementNameByDate) {

        return writePhotoFile(bitmap, photoName, directoryName, PNG, autoIncrementNameByDate);
    }

    public boolean savePhotoInMemoryDevice(Bitmap bitmap, String photoName, String directoryName,
                                           Bitmap.CompressFormat format, boolean autoIncrementNameByDate) {
        return writePhotoFile(bitmap, photoName, directoryName, format, autoIncrementNameByDate);
    }
    //endregion





    //===============================================================================
    // Utils methods, resize and get Photo Uri and others
    //================================================================================
    //region Utils
    public static int ifCameraLandScape(boolean doRotate) {
        if(doRotate) {
            if (rotateIfLandScapeCamera()) {
                return LANDSCAPE_CAMERA;
            } else {
                return NORMAL_CAMERA;
            }
        }else{
            return 0;
        }
    }

    /**
     * Rotate the image if the device camera is land scape
     * @return
     */
    private static boolean rotateIfLandScapeCamera() {
        if (Build.BRAND.toLowerCase().equals("samsung")) {
            return true;
        } else if(Build.BRAND.toLowerCase().equals("sony")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Rotate the bitmap if the image is in landscape camera
     * @param source
     * @param angle
     * @return
     */
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return retVal;
    }

    /**
     * This method resize the photo
     *
     * @param realImage    the bitmap of image
     * @param maxImageSize the max image size percentage
     * @param filter       the filter
     * @return a bitmap of the photo rezise
     */
    private static Bitmap resizePhoto(Bitmap realImage, float maxImageSize,
                                      boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    //validate if the string isnull or empty
    private boolean notNullNotFill(String validate) {
        if (validate != null) {
            if (!validate.trim().equals("")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Returns the Uri for a photo stored on memory device
    // the real URI for show the information of the photo
    // select photos
    private Uri getPhotoFileUri(String fileDir) {
        File mediaStorageDir = null;
        mediaStorageDir = new File("", fileDir);

        Uri imgUri = Uri.fromFile(mediaStorageDir);
        setImgPath(mediaStorageDir.getAbsolutePath());

        this.realPath = mediaStorageDir.getPath();
        try {
            getImageInformation();
        } catch (Exception ex) {
        }
        return imgUri;
    }





    //================================================================================
    // Get URI photo for selected photos of device
    //================================================================================
    // Returns the Uri for a photo stored on memory device
    private Uri getPhotoFileUri(String fileName, String fileDir, Context context) {
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
     * @param direction
     * @param nameFile
     * @return
     */
    private Uri getUriAuxiliar(String direction, String nameFile) {
        try {
            File file = new File(direction, nameFile);
            Uri imgUri = Uri.fromFile(file);
            setImgPath(file.getAbsolutePath());

            this.realPath = imgUri.getPath();
            try {
                getImageInformation();
            } catch (Exception ev) {
            }

            return imgUri;
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
            Uri imgUri = Uri.fromFile(file);
            setImgPath(file.getAbsolutePath());
            this.realPath = imgUri.getPath();
            return imgUri;
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





    //================================================================================
    // Exif interface methods
    //================================================================================
    private ExifInterface getAllFeatures() {
        if (!this.realPath.equals("")) {
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(this.realPath.toString());
                return exif;
            } catch (IOException e) {
                return exif;
            }
        } else {
            return null;
        }
    }

    public boolean getImageInformation() {
        try {
            ExifInterface exif = getAllFeatures();
            if (exif != null) {

                float[] latLong = new float[2];
                try{
                    exif.getLatLong(latLong);
                    latitude = latLong[0];
                    longitude = latLong[1];
                }catch(Exception ex){}


                if (notNullNotFill(exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF))) {
                    latitudeReference = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
                }

                if (notNullNotFill(exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF))) {
                    longitudeReference = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
                }

                if (notNullNotFill(exif.getAttribute(ExifInterface.TAG_DATETIME))) {
                    dateTimeTakePhoto = exif.getAttribute(ExifInterface.TAG_DATETIME);
                }

                if (notNullNotFill(exif.getAttribute(ExifInterface.TAG_ORIENTATION))) {
                    orientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                }

                if (notNullNotFill(exif.getAttribute(ExifInterface.TAG_ISO))) {
                    iso = exif.getAttribute(ExifInterface.TAG_ISO);
                }

                if (notNullNotFill(exif.getAttribute(ExifInterface.TAG_GPS_DATESTAMP))) {
                    dateStamp = exif.getAttribute(ExifInterface.TAG_GPS_DATESTAMP);
                }

                if (notNullNotFill(exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH))) {
                    imageLength = exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
                }

                if (notNullNotFill(exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH))) {
                    imageWidth = exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
                }

                if (notNullNotFill(exif.getAttribute(ExifInterface.TAG_MODEL))) {
                    modelDevice = exif.getAttribute(ExifInterface.TAG_MODEL);
                }

                if (notNullNotFill(exif.getAttribute(ExifInterface.TAG_MAKE))) {
                    makeCompany = exif.getAttribute(ExifInterface.TAG_MAKE);
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }


    //================================================================================
    // Face detector methods
    //================================================================================
    public Bitmap faceDetector(int stroke, int color){

        if(stroke<1){
            stroke =1;
        }else if(stroke > 80){
            stroke = 80;
        }

        FaceDetector detector = new FaceDetector.Builder(this.activity)
                .setMode(FaceDetector.ACCURATE_MODE)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setTrackingEnabled(false)
                .build();

        if(false == detector.isOperational()){
            return null;
        }

        //Add the image on a Frame object
        Frame frame = new Frame.Builder()
                .setBitmap(getMyPhoto())
                .build();

        //Detect all faces from Frame object
        SparseArray<Face> faceArray = detector.detect(frame);

        //Do some drawing on faces
        Bitmap outBitmap = drawOnFace(faceArray,stroke,color);

        //Releasing the detector object
        detector.release();
        return outBitmap;
    }

    public Bitmap faceDetector(){
        FaceDetector detector = new FaceDetector.Builder(this.activity)
                .setMode(FaceDetector.ACCURATE_MODE)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setTrackingEnabled(false)
                .build();

        if(false == detector.isOperational()){
            return null;
        }

        //Add the image on a Frame object
        Frame frame = new Frame.Builder()
                .setBitmap(getMyPhoto())
                .build();

        //Detect all faces from Frame object
        SparseArray<Face> faceArray = detector.detect(frame);

        //Do some drawing on faces
        Bitmap outBitmap = drawOnFace(faceArray,5,RED);

        //Releasing the detector object
        detector.release();
        return outBitmap;
    }

    /**
     * Method to do some drawing on faces
     */
    private Bitmap drawOnFace(SparseArray<Face> faceArray, int stroke, int color){
        Bitmap outBitmap = Bitmap.createBitmap(getMyPhoto().getWidth(), getMyPhoto().getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(outBitmap);
        canvas.drawBitmap(getMyPhoto(), 0, 0, null);

        for(int i=0; i < faceArray.size(); i++){
            int key = faceArray.keyAt(i);
            // get the object by the key.
            Face face = faceArray.get(key);
            //Drawing rectangle on each face
            drawRectangle(canvas, face.getPosition(), face.getWidth(), face.getHeight(),stroke,color);
            this.listLandMarkPhoto = face.getLandmarks();
        }
        return outBitmap;
    }

    //draw a rectangle in your butmap
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

    //================================================================================
    // Conversion Methods
    //================================================================================
    //region Conversion Methods
    public static byte[] bitmapToBytes(Bitmap bitmap, Bitmap.CompressFormat format) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(format, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap bytesToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static Bitmap bytesToBitmap(byte[] byteArray, Bitmap.CompressFormat format) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(format, 100, stream);
        return bitmap;
    }

    public static String bytesToStringBase64(byte[] byteArray) {
        StringBuilder base64 = new StringBuilder(Base64.encodeToString(byteArray, Base64.DEFAULT));
        return base64.toString();
    }

    public static byte[] stringBase64ToBytes(String stringBase64) {
        byte[] byteArray = Base64.decode(stringBase64, Base64.DEFAULT);
        return byteArray;
    }
    //endregion
}