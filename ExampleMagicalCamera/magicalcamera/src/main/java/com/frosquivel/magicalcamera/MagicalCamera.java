package com.frosquivel.magicalcamera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        if(resizePhoto < BEST_QUALITY_PHOTO)
            this.resizePhoto = resizePhoto;
        else
            this.resizePhoto = BEST_QUALITY_PHOTO;
    }
    //endregion

    //================================================================================
    // Constructs
    //================================================================================
    //region Construct
    public MagicalCamera(Activity activity, int resizePhoto) {
        if(resizePhoto<BEST_QUALITY_PHOTO)
            this.resizePhoto = resizePhoto;
        else
            this.resizePhoto = BEST_QUALITY_PHOTO;

        if(resizePhoto == 0){
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
    public void takePhoto(){
        this.thePhotoName = "MagicalCamera";
        this.anotherPhotoName = "MagicalCamera";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(this.thePhotoName ,this.anotherPhotoName, this.activity));

        if (intent.resolveActivity(this.activity.getPackageManager()) != null) {
            this.activity.startActivityForResult(intent, TAKE_PHOTO);
        }
    }

    /**
     * This library call the intent to take photo
     */
    public boolean takeFragmentPhoto(){
        this.thePhotoName = "MagicalCamera";
        this.anotherPhotoName = "MagicalCamera";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(this.thePhotoName ,this.anotherPhotoName, this.activity));

        if (intent.resolveActivity(this.activity.getPackageManager()) != null) {
            this.intentFragment = intent;
            return true;
        }else{
            return false;
        }
    }


    /**
     * This call the intent to selected the picture
     * @param headerName the header name of popUp
     */
    public void selectedPicture(String headerName){
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        this.activity.startActivityForResult(
                Intent.createChooser(intent, (!headerName.equals("") ? headerName : "Magical Camera")),
                SELECT_PHOTO);
    }

    /**
     * This call the intent to selected the picture
     */
    public boolean selectedFragmentPicture(){
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        if (intent.resolveActivity(this.activity.getPackageManager()) != null) {
            this.intentFragment = intent;
            return true;
        }else{
            return false;
        }
    }

    /**
     * This methods is called in the override method onActivityResult
     * for the respective activation, and this validate which of the intentn result be,
     * for example: if is selected file or if is take picture
     */
    public void resultPhoto(int requestCode, int resultCode, Intent data){
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PHOTO){
                this.myPhoto = onSelectFromGalleryResult(data);
            }

            else if (requestCode == TAKE_PHOTO){
                this.myPhoto =  onTakePhotoResult();
            }
        }
    }

    /**
     * This method obtain the path of the picture selected, and convert this in the
     * phsysical path of the image, and decode the file with the respective options,
     * resize the file and change the quality of photos selected.
     * @param data the intent data for take the photo path
     * @return return a bitmap of the photo selected
     */
    @SuppressWarnings("deprecation")
    private Bitmap onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor =  this.activity.managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String selectedImagePath = cursor.getString(column_index);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(selectedImagePath, options);
        bm =  resizePhoto(bm, this.resizePhoto, true);

        return bm;
    }

    /**
     * Save the photo in memory bitmap, resize and return the photo
     * @return the bitmap of the respective photo
     */
    public Bitmap onTakePhotoResult() {
        Uri takenPhotoUri = getPhotoFileUri(this.thePhotoName, this.anotherPhotoName, this.activity);
        // by this point we have the camera photo on disk
        Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
        takenImage =  resizePhoto(takenImage, this.resizePhoto, true);

        return takenImage;
    }
    //endregion

    //================================================================================
    // Save Photo in device
    //================================================================================
    //region Save Photo in device
    /**
     * This library write the file in the device storage or sdcard
     * @param bitmap the bitmap that you need to write in device
     * @param photoName the photo name
     * @param directoryName the directory that you need to create the picture
     * @param format the format of the photo, maybe png or jpeg
     * @param autoIncrementNameByDate is this variable is active the system create
     *                                the photo with a number of the date, hour, and second to diferenciate this
     * @return return true if the photo is writen
     */
    private boolean writePhotoFile(Bitmap bitmap, String photoName, String directoryName,
                                   Bitmap.CompressFormat format, boolean autoIncrementNameByDate){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(format, 100, bytes);

        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = df.format(Calendar.getInstance().getTime());

        if(format == PNG){
            photoName = (autoIncrementNameByDate) ?  photoName + "_" + date + ".png" : photoName + ".png";
        }else if (format == JPEG){
            photoName = (autoIncrementNameByDate) ?  photoName + "_" + date + ".jpeg" : photoName + ".jpeg";
        }else if (format == WEBP){
            photoName = (autoIncrementNameByDate) ?  photoName + "_" + date + ".webp" : photoName + ".webp";
        }

        File wallpaperDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + directoryName + "/");
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        File f = new File(wallpaperDirectory,photoName);

        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
            this.activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse("file://"+f.getAbsolutePath())));

            return true;
        }catch (Exception ev){
            return false;
        }
    }

    /**
     * ***********************************************
     * This methods save the photo in memory device
     * with diferents params
     * **********************************************
     */
    public boolean savePhotoInMemoryDevice(Bitmap bitmap, String photoName, boolean autoIncrementNameByDate){
        return writePhotoFile(bitmap,photoName,"MAGICAL CAMERA",PNG, autoIncrementNameByDate);
    }

    public boolean savePhotoInMemoryDevice(Bitmap bitmap, String photoName, Bitmap.CompressFormat format, boolean autoIncrementNameByDate){
        return writePhotoFile(bitmap,photoName,"MAGICAL CAMERA",format, autoIncrementNameByDate);
    }

    public boolean savePhotoInMemoryDevice(Bitmap bitmap, String photoName, String directoryName, boolean autoIncrementNameByDate){

        return writePhotoFile(bitmap,photoName,directoryName,PNG, autoIncrementNameByDate);
    }

    public boolean savePhotoInMemoryDevice(Bitmap bitmap, String photoName, String directoryName,
                                           Bitmap.CompressFormat format, boolean autoIncrementNameByDate){
        return writePhotoFile(bitmap,photoName,directoryName,format, autoIncrementNameByDate);
    }
    //endregion

    //================================================================================
    // Utils methods, resize and get Photo Uri
    //================================================================================
    //region Utils
    /**
     * This method resize the photo
     * @param realImage the bitmap of image
     * @param maxImageSize the max image size percentage
     * @param filter the filter
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

    // Returns the Uri for a photo stored on disk given the fileName
    private static Uri getPhotoFileUri(String fileName,String fileDir,Context context) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            File mediaStorageDir = new File(
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileDir);

            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            }

            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }else{
            File mediaStorageDir = new File(
                    context.getFilesDir(), fileDir);

            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            }

            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
    }
    // Returns true if external storage for photos is available
    private static boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
    //endregion

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

    public static String bytesToStringBase64(byte[] byteArray){
        StringBuilder base64 = new StringBuilder(Base64.encodeToString(byteArray, Base64.DEFAULT));
        return base64.toString();
    }

    public static byte[] stringBase64ToBytes(String stringBase64){
        byte[] byteArray = Base64.decode(stringBase64, Base64.DEFAULT);
        return byteArray;
    }
    //endregion
}