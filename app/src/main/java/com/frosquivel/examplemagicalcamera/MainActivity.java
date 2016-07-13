package com.frosquivel.examplemagicalcamera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.frosquivel.magicalcamera.MagicalCamera;

/**
 * Created by          Fabián Rosales Esquivel
 * Visit my web page   http://www.frosquivel.com
 * Visit my blog       http://www.frosquivel.com/blog
 * Created Date        on 5/19/16
 * This is an android library to take easy picture
 */
public class MainActivity extends AppCompatActivity {
//http://stackoverflow.com/questions/19042511/android-camera-failure-delivering-result-resultinfowho-null-request-0-resul
    private ImageView imageView;
    private Button btntakephoto;
    private Button btnselectedphoto;
    private Button btnGoTo;
    private Button btnSeeData;
    private TextView texttitle;

    private MagicalCamera magicalCamera;
    private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        magicalCamera = new MagicalCamera(this,RESIZE_PHOTO_PIXELS_PERCENTAGE);

        imageView =  (ImageView) findViewById(R.id.imageView);
        btntakephoto =  (Button) findViewById(R.id.btntakephoto);
        btnselectedphoto =  (Button) findViewById(R.id.btnselectedphoto);
        btnGoTo =  (Button) findViewById(R.id.btnGoTo);
        texttitle =  (TextView) findViewById(R.id.texttitle);
        texttitle.setText("Activity Example");
        btnGoTo.setText("Go to Fragment");
        btnSeeData = (Button) findViewById(R.id.btnSeeData);

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

        btnSeeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(magicalCamera.getMyPhoto()!=null) {
                    if(magicalCamera.getImageInformation()) {

                        StringBuilder builderInformation = new StringBuilder();

                        if (notNullNotFill(magicalCamera.getLatitude() + ""))
                            builderInformation.append("Latitude: " + magicalCamera.getLatitude() + "\n");

                        if (notNullNotFill(magicalCamera.getLatitudeReference()))
                            builderInformation.append("Latitude Reference: " + magicalCamera.getLatitudeReference() + "\n");

                        if (notNullNotFill(magicalCamera.getLongitude() + ""))
                            builderInformation.append("Longitude: " + magicalCamera.getLongitude() + "\n");

                        if (notNullNotFill(magicalCamera.getLongitudeReference()))
                            builderInformation.append("Longitude Reference: " + magicalCamera.getLongitudeReference() + "\n");

                        if (notNullNotFill(magicalCamera.getDateTimeTakePhoto()))
                            builderInformation.append("Date time to photo: " + magicalCamera.getDateTimeTakePhoto() + "\n");

                        if (notNullNotFill(magicalCamera.getDateStamp()))
                            builderInformation.append("Date stamp to photo: " + magicalCamera.getDateStamp() + "\n");

                        if (notNullNotFill(magicalCamera.getIso()))
                            builderInformation.append("ISO: " + magicalCamera.getIso() + "\n");

                        if (notNullNotFill(magicalCamera.getOrientation()))
                            builderInformation.append("Orientation photo: " + magicalCamera.getOrientation() + "\n");

                        if (notNullNotFill(magicalCamera.getImageLength()))
                            builderInformation.append("Image lenght: " + magicalCamera.getImageLength() + "\n");

                        if (notNullNotFill(magicalCamera.getImageWidth()))
                            builderInformation.append("Image Width: " + magicalCamera.getImageWidth() + "\n");

                        if (notNullNotFill(magicalCamera.getModelDevice()))
                            builderInformation.append("Model Device: " + magicalCamera.getModelDevice() + "\n");

                        if (notNullNotFill(magicalCamera.getMakeCompany()))
                            builderInformation.append("Make company: " + magicalCamera.getMakeCompany() + "\n");

                        new MaterialDialog.Builder(MainActivity.this)
                                .title("See photo information")
                                .content(builderInformation.toString())
                                .positiveText("ok")
                                .show();
                    }else{
                        Toast.makeText(MainActivity.this, "You dont have data to show because the real path photo is wrong contact with fabian7593@gmail.com", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "You dont have data to show because the photo is null (your photo isn't in memory device), contact with fabian7593@gmail.com", Toast.LENGTH_SHORT).show();
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

        if(magicalCamera.getMyPhoto()!=null) {
            imageView.setImageBitmap(magicalCamera.getMyPhoto());

            if (magicalCamera.savePhotoInMemoryDevice(magicalCamera.getMyPhoto(), "myTestPhoto", MagicalCamera.JPEG, true)) {
                Toast.makeText(MainActivity.this, "The photo is save in device, please check this", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Sorry your photo dont write in devide, please contact with fabian7593@gmail and say this error", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(MainActivity.this, "Your image is null, please debug, or test with another device, or maybe contact with fabian7593@gmail.com for try to fix the bug, thanks and sorry", Toast.LENGTH_SHORT).show();
        }
    }
}
