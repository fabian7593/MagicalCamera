package com.frosquivel.examplemagicalcamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.frosquivel.magicalcamera.Functionallities.PermissionGranted;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.google.android.gms.vision.face.Landmark;

import java.util.List;

/**
 * Created by          Fabián Rosales Esquivel
 * Created Date        on 5/19/16
 * This is an android library to take easy picture
 */
public class MainActivity extends AppCompatActivity {
    //this is the image view for show your picture taken
    private ImageView imageView;
    //button to take picture
    private Button btntakephoto;
    //button to select picture of your device
    private Button btnselectedphoto;
    //see the information data of the picture
    private Button btnSeeData;
    //button for realized the facial recognition of your picture
    private Button btnFacialRecognition;
    //button for rotate the image in bitmap and in image view
    private Button imgRotate;
    //button for save the photo in device
    private Button saveImage;
    //button for go to fragment
    private Button btnGoTo;
    private TextView texttitle;

    //Ever you need to call magical camera and permissionGranted
    private MagicalCamera magicalCamera;
    private PermissionGranted permissionGranted;
    private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionGranted = new PermissionGranted(this);
        //call the permission for all that you need to use in this library
        //This has nothing to do with the library API, I only put it as an example
        //but if you needed call this like another versions
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            permissionGranted.checkAllMagicalCameraPermission();
        }else{
            //call one on one permission
            //permission for take photo, it is false if the user check deny
            permissionGranted.checkCameraPermission();
            //for search and write photoss in device internal memory
            //normal or SD memory
            permissionGranted.checkReadExternalPermission();
            permissionGranted.checkWriteExternalPermission();
            //permission for location for use the `photo information device.
            permissionGranted.checkLocationPermission();
        }

        //realized the instance of magical camera, this need the context, this need the context,
        //the percentage of quality photo and the permission granted
        magicalCamera = new MagicalCamera(this, RESIZE_PHOTO_PIXELS_PERCENTAGE, permissionGranted);

        imageView =  (ImageView) findViewById(R.id.imageView);
        imgRotate =  (Button) findViewById(R.id.imgRotate);
        btntakephoto =  (Button) findViewById(R.id.btntakephoto);
        btnselectedphoto =  (Button) findViewById(R.id.btnselectedphoto);
        btnGoTo =  (Button) findViewById(R.id.btnGoTo);
        texttitle =  (TextView) findViewById(R.id.texttitle);
        texttitle.setText("Activity Example");
        btnGoTo.setText("Go to Fragment");
        saveImage = (Button) findViewById(R.id.saveImage);
        btnSeeData = (Button) findViewById(R.id.btnSeeData);
        btnFacialRecognition = (Button) findViewById(R.id.btnFacialRecognition);


        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(magicalCamera != null) {
                    if (magicalCamera.getPhoto() != null) {
                        //save the photo in your memory external or internal of your device
                        String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "myTestPhoto", MagicalCamera.JPEG, true);
                        if (path != null) {
                            Toast.makeText(MainActivity.this,
                                    "The photo is save manual in device, please check this path: " + path,
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Sorry your photo dont write in devide, please contact with fabian7593@gmail and say this error",
                                    Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this,
                                "Your image is null, please select or take one",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,
                            "Please initialized magical camera, maybe in static context for use in all activity",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        imgRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(magicalCamera != null) {
                    if (magicalCamera.getPhoto() != null) {
                        //for once click rotate the picture in 90ª, and set in the image view.
                        magicalCamera.setPhoto(magicalCamera.rotatePicture(magicalCamera.getPhoto(), MagicalCamera.ORIENTATION_ROTATE_90));
                        imageView.setImageBitmap(magicalCamera.getPhoto());
                    }else{
                        Toast.makeText(MainActivity.this,
                                "Your image is null, please select or take one",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,
                            "Please initialized magical camera, maybe in static context for use in all activity",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        
        btntakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the method of take the picture
                magicalCamera.takePhoto();
            }
        });

        btnselectedphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the method of selected picture
                magicalCamera.selectedPicture("my_header_name");
            }
        });

        btnGoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityForFragment.class));
            }
        });

        btnFacialRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(magicalCamera != null){
                    if(magicalCamera.getPhoto() != null){
                        //obtain the bitmap with facial recognition and the landmark of this points
                        Bitmap faceDetectorBitmap = magicalCamera.faceDetector(50, Color.GREEN);
                        if(faceDetectorBitmap != null){
                            imageView.setImageBitmap(faceDetectorBitmap);
                            List<Landmark> listMark = magicalCamera.getFaceRecognitionInformation().getListLandMarkPhoto();
                        }

                    }else{
                        Toast.makeText(MainActivity.this,
                                "Your image is null, please select or take one",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,
                            "Please initialized magical camera, maybe in static context for use in all activity",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSeeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(magicalCamera.getPhoto()!=null) {
                    //for see the data you need to call this method ever
                    //if the function return true you have the posibility of see the data
                    if(magicalCamera.initImageInformation()) {

                        StringBuilder builderInformation = new StringBuilder();
                        //question in have data
                        if (notNullNotFill(magicalCamera.getPrivateInformation().getLatitude() + ""))
                            builderInformation.append("Latitude: " + magicalCamera.getPrivateInformation().getLatitude() + "\n");

                        if (notNullNotFill(magicalCamera.getPrivateInformation().getLatitudeReference()))
                            builderInformation.append("Latitude Reference: " + magicalCamera.getPrivateInformation().getLatitudeReference() + "\n");

                        if (notNullNotFill(magicalCamera.getPrivateInformation().getLongitude() + ""))
                            builderInformation.append("Longitude: " + magicalCamera.getPrivateInformation().getLongitude() + "\n");

                        if (notNullNotFill(magicalCamera.getPrivateInformation().getLongitudeReference()))
                            builderInformation.append("Longitude Reference: " + magicalCamera.getPrivateInformation().getLongitudeReference() + "\n");

                        if (notNullNotFill(magicalCamera.getPrivateInformation().getDateTimeTakePhoto()))
                            builderInformation.append("Date time to photo: " + magicalCamera.getPrivateInformation().getDateTimeTakePhoto() + "\n");

                        if (notNullNotFill(magicalCamera.getPrivateInformation().getDateStamp()))
                            builderInformation.append("Date stamp to photo: " + magicalCamera.getPrivateInformation().getDateStamp() + "\n");

                        if (notNullNotFill(magicalCamera.getPrivateInformation().getIso()))
                            builderInformation.append("ISO: " + magicalCamera.getPrivateInformation().getIso() + "\n");

                        if (notNullNotFill(magicalCamera.getPrivateInformation().getOrientation()))
                            builderInformation.append("Orientation photo: " + magicalCamera.getPrivateInformation().getOrientation() + "\n");

                        if (notNullNotFill(magicalCamera.getPrivateInformation().getImageLength()))
                            builderInformation.append("Image lenght: " + magicalCamera.getPrivateInformation().getImageLength() + "\n");

                        if (notNullNotFill(magicalCamera.getPrivateInformation().getImageWidth()))
                            builderInformation.append("Image Width: " + magicalCamera.getPrivateInformation().getImageWidth() + "\n");

                        if (notNullNotFill(magicalCamera.getPrivateInformation().getModelDevice()))
                            builderInformation.append("Model Device: " + magicalCamera.getPrivateInformation().getModelDevice() + "\n");

                        if (notNullNotFill(magicalCamera.getPrivateInformation().getMakeCompany()))
                            builderInformation.append("Make company: " + magicalCamera.getPrivateInformation().getMakeCompany() + "\n");

                        new MaterialDialog.Builder(MainActivity.this)
                                .title("See photo information")
                                .content(builderInformation.toString())
                                .positiveText("ok")
                                .show();
                    }else{
                        Toast.makeText(MainActivity.this,
                                "You dont have data to show because the real path photo is wrong contact with fabian7593@gmail.com",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,
                            "You dont have data to show because the photo is null (your photo isn't in memory device)",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private boolean notNullNotFill(String validate){
        if(validate != null){
            if(!validate.trim().equals("")){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //this is for rotate picture in this method
        //magicalCamera.resultPhoto(requestCode, resultCode, data, MagicalCamera.ORIENTATION_ROTATE_180);

        //you should to call the method ever, for obtain the bitmap photo (= magicalCamera.getPhoto())
        magicalCamera.resultPhoto(requestCode, resultCode, data);

        if(magicalCamera.getPhoto()!=null) {
            //another form to rotate image
            magicalCamera.setPhoto(magicalCamera.rotatePicture(magicalCamera.getPhoto(), MagicalCamera.ORIENTATION_ROTATE_NORMAL));

            //set the photo in image view
            imageView.setImageBitmap(magicalCamera.getPhoto());

            //save the picture inmemory device, and return the physical path of this photo
            String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "myTestPhoto", MagicalCamera.JPEG, true);

            //CONVERT BITMAP EXAMPLE COMMENT
            //convert the bitmap to bytes
            /*byte[] bytesArray =  ConvertSimpleImage.bitmapToBytes(magicalCamera.getPhoto(), MagicalCamera.PNG);
            //convert the bytes to string 64, with this form is easly to send by web service or store data in DB
            String imageBase64 = ConvertSimpleImage.bytesToStringBase64(bytesArray);

            //if you need to revert the process
            byte[] anotherArrayBytes = ConvertSimpleImage.stringBase64ToBytes(imageBase64);

            //again deserialize the image
            Bitmap myImageAgain = ConvertSimpleImage.bytesToBitmap(anotherArrayBytes);
            */

            if (path != null) {
                Toast.makeText(MainActivity.this,
                        "The photo is save in device, please check this path: " + path,
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Sorry your photo dont write in devide, please contact with fabian7593@gmail and say this error",
                        Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(MainActivity.this,
                    "Your image is null, please debug, or test with another device, or maybe contact with fabian7593@gmail.com for try to fix the bug, thanks and sorry",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //call the event of onRequestPermissionsResult for android 6.0 or more
        permissionGranted.permissionGrant(requestCode, permissions, grantResults);
    }
}
