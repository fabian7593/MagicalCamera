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
import com.frosquivel.magicalcamera.Objects.MagicalCameraObject;
import com.google.android.gms.vision.face.Landmark;

import java.util.List;

/**
 * Created by          Fabián Rosales Esquivel
 * Created Date        on 5/19/16
 * This is an android library to take easy picture
 */
public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button btntakephoto;
    private Button btnselectedphoto;
    private Button btnGoTo;
    private Button btnSeeData;
    private Button btnFacialRecognition;
    private TextView texttitle;

    private MagicalCamera magicalCamera;
    private PermissionGranted permissionGranted;
    private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionGranted = new PermissionGranted(this);
        //permission for take photo, it is false if the user check deny
        permissionGranted.checkCameraPermission();
        //for search and write photoss in device internal memory
        //normal or SD memory
        permissionGranted.checkReadExternalPermission();
        permissionGranted.checkWriteExternalPermission();
        //permission for location for use the `photo information device.
        permissionGranted.checkLocationPermission();

        magicalCamera = new MagicalCamera(this, RESIZE_PHOTO_PIXELS_PERCENTAGE, permissionGranted);

        imageView =  (ImageView) findViewById(R.id.imageView);
        btntakephoto =  (Button) findViewById(R.id.btntakephoto);
        btnselectedphoto =  (Button) findViewById(R.id.btnselectedphoto);
        btnGoTo =  (Button) findViewById(R.id.btnGoTo);
        texttitle =  (TextView) findViewById(R.id.texttitle);
        texttitle.setText("Activity Example");
        btnGoTo.setText("Go to Fragment");
        btnSeeData = (Button) findViewById(R.id.btnSeeData);
        btnFacialRecognition = (Button) findViewById(R.id.btnFacialRecognition);

        btntakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magicalCamera.takePhoto();
            }
        });

        btnselectedphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        Bitmap faceDetectorBitmap = magicalCamera.faceDetector(50, Color.GREEN);
                        if(faceDetectorBitmap != null){
                            imageView.setImageBitmap(faceDetectorBitmap);
                            List<Landmark> listMark = magicalCamera.getFaceRecognitionInformation().getListLandMarkPhoto();
                        }

                    }else{
                        Toast.makeText(MainActivity.this,
                                "Your image is null, please select or take one",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,
                            "Please initialized magical camera, maybe in static context for use in all activity",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSeeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(magicalCamera.getPhoto()!=null) {
                    if(magicalCamera.hasImageInformation()) {

                        StringBuilder builderInformation = new StringBuilder();

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
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,
                            "You dont have data to show because the photo is null (your photo isn't in memory device)",
                            Toast.LENGTH_SHORT).show();
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
        magicalCamera.resultPhoto(requestCode, resultCode, data);

        if(magicalCamera.getPhoto()!=null) {
            imageView.setImageBitmap(magicalCamera.getPhoto());

            String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "myTestPhoto", MagicalCameraObject.JPEG, true);

            if (path != null) {
                Toast.makeText(MainActivity.this,
                        "The photo is save in device, please check this path: " + path,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Sorry your photo dont write in devide, please contact with fabian7593@gmail and say this error",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(MainActivity.this,
                    "Your image is null, please debug, or test with another device, or maybe contact with fabian7593@gmail.com for try to fix the bug, thanks and sorry",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        magicalCamera.permissionGrant(requestCode, permissions, grantResults);
    }
}
