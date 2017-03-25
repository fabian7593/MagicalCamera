package com.frosquivel.examplemagicalcamera.Fragments;
import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.frosquivel.examplemagicalcamera.Activities.ImageViewActivity;
import com.frosquivel.examplemagicalcamera.R;
import com.frosquivel.examplemagicalcamera.Utils.Utils;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.frosquivel.magicalcamera.Utilities.ConvertSimpleImage;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.vision.face.Landmark;

import java.util.List;
import java.util.Map;

/**
 * Created by          Fabián Rosales Esquivel
 * Visit my web page   http://www.frosquivel.com
 * Visit my blog       http://www.frosquivel.com/blog
 * Created Date        on 5/19/16
 * This is an android library to take easy picture
 */
public class FragmentSample extends Fragment{

    //this is the image view for show your picture taken,
    // if you click in this show the picture in all screen
    private ImageView imageView;
    //button to take picture
    private ImageButton btntakephoto;
    //button to select picture of your device
    private ImageButton btnselectedphoto;
    //button for save the photo in device
    private ImageButton saveImage;
    //button for go to fragment
    private Button btnGoTo;
    private TextView texttitle;

    private FloatingActionMenu floatingBtnMenu;

    private FrameLayout frame;
    private View principalLayout;

    //button for realized the facial recognition of your picture
    private FloatingActionButton floatingBtnFacialRecognition;
    //see the information data of the picture
    private FloatingActionButton floatingBtnPhotoInformation;
    //button for rotate the image in bitmap and in image view
    private FloatingActionButton floatingBtnRotate;
    //button for view the string base 64 trasnform the bitmap
    private FloatingActionButton floatingBtnSeeString64;

    //Ever you need to call magical camera and permissionGranted
    private MagicalCamera magicalCamera;
    private MagicalPermissions magicalPermissions;
    private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 80;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        //obtain the activity of the parent
        activity = getActivity();
        setUIComponents(rootView);

        //instance magical camera
        String[] permissions = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        magicalPermissions = new MagicalPermissions(this, permissions);
        magicalCamera = new MagicalCamera(activity, RESIZE_PHOTO_PIXELS_PERCENTAGE, magicalPermissions);

        btntakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the method of take the picture
                magicalCamera.takeFragmentPhoto(FragmentSample.this);
            }
        });

        btnselectedphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is the form to select picture of device
                magicalCamera.selectFragmentPicture(FragmentSample.this, "My Header Example");
            }
        });

        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.validateMagicalCameraNull(activity, principalLayout, magicalCamera)) {
                    //save the photo in your memory external or internal of your device
                    String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "myTestPhoto", MagicalCamera.JPEG, true);

                    if (path != null) {
                        Utils.viewSnackBar(getString(R.string.message_save_manual) + path, principalLayout);
                    } else {
                        Utils.viewSnackBar(getString(R.string.error_dont_save_photo), principalLayout);
                    }
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.validateMagicalCameraNull(activity, principalLayout, magicalCamera)) {

                    Utils.magicalCameraBitmap = magicalCamera.getPhoto();
                    Intent intent = new Intent(activity, ImageViewActivity.class);
                    startActivity(intent);
                }
            }
        });

        floatingBtnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.validateMagicalCameraNull(activity, principalLayout, magicalCamera)) {
                    //for once click rotate the picture in 90ª, and set in the image view.
                    magicalCamera.setPhoto(magicalCamera.rotatePicture(magicalCamera.getPhoto(), MagicalCamera.ORIENTATION_ROTATE_90));
                    imageView.setImageBitmap(magicalCamera.getPhoto());
                }
            }
        });

        floatingBtnFacialRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.validateMagicalCameraNull(activity, principalLayout, magicalCamera)) {
                    Bitmap faceDetectorBitmap = magicalCamera.faceDetector(50, Color.GREEN);
                    if (faceDetectorBitmap != null) {
                        imageView.setImageBitmap(faceDetectorBitmap);
                        List<Landmark> listMark = magicalCamera.getFaceRecognitionInformation().getListLandMarkPhoto();
                    }else{
                        Utils.viewSnackBar(getString(R.string.error_not_detect_face), principalLayout);
                    }
                }
            }
        });

        floatingBtnSeeString64.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.validateMagicalCameraNull(activity, principalLayout, magicalCamera)) {

                    try {
                        //convert the bitmap to bytes array
                        byte[] bytesArray = ConvertSimpleImage.bitmapToBytes(magicalCamera.getPhoto(), Utils.getFormat(activity));
                        //convert the bytes to string 64, with this form is easly to send by web service or store data in DB
                        String imageBase64 = ConvertSimpleImage.bytesToStringBase64(bytesArray);

                        /*****************************************************************
                         *                    Revert the process
                         *****************************************************************/
                        //if you need to revert the process
                        //byte[] anotherArrayBytes = ConvertSimpleImage.stringBase64ToBytes(imageBase64);
                        //again deserialize the image
                        //Bitmap myImageAgain = ConvertSimpleImage.bytesToBitmap(anotherArrayBytes);

                        String base64 = imageBase64.substring(0,300);

                        new MaterialDialog.Builder(activity)
                                .title(getString(R.string.convert_to_string_base_64_title))
                                .content(base64)
                                .positiveText(getString(R.string.message_ok))
                                .show();
                    } catch (Exception e) {
                        Utils.viewSnackBar(getString(R.string.error_string_base_64), principalLayout);
                    }
                }
            }
        });

        floatingBtnPhotoInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.validateMagicalCameraNull(activity, principalLayout, magicalCamera)) {
                    //for see the data you need to call this method ever
                    //if the function return true you have the posibility of see the data
                    if (magicalCamera.initImageInformation()) {

                        StringBuilder builderInformation = new StringBuilder();

                        //question in have data
                        if (Utils.notNullNotFill(magicalCamera.getPrivateInformation().getLatitude() + ""))
                            builderInformation.append(getString(R.string.info_data_latitude) + magicalCamera.getPrivateInformation().getLatitude() + "\n");

                        if (Utils.notNullNotFill(magicalCamera.getPrivateInformation().getLatitudeReference()))
                            builderInformation.append(getString(R.string.info_data_latitude_referene) + magicalCamera.getPrivateInformation().getLatitudeReference() + "\n");

                        if (Utils.notNullNotFill(magicalCamera.getPrivateInformation().getLongitude() + ""))
                            builderInformation.append(getString(R.string.info_data_longitude)  + magicalCamera.getPrivateInformation().getLongitude() + "\n");

                        if (Utils.notNullNotFill(magicalCamera.getPrivateInformation().getLongitudeReference()))
                            builderInformation.append(getString(R.string.info_data_longitude_reference) + magicalCamera.getPrivateInformation().getLongitudeReference() + "\n");

                        if (Utils.notNullNotFill(magicalCamera.getPrivateInformation().getDateTimeTakePhoto()))
                            builderInformation.append(getString(R.string.info_data_date_time_photo) + magicalCamera.getPrivateInformation().getDateTimeTakePhoto() + "\n");

                        if (Utils.notNullNotFill(magicalCamera.getPrivateInformation().getDateStamp()))
                            builderInformation.append(getString(R.string.info_data_date_stamp_photo) + magicalCamera.getPrivateInformation().getDateStamp() + "\n");

                        if (Utils.notNullNotFill(magicalCamera.getPrivateInformation().getIso()))
                            builderInformation.append(getString(R.string.info_data_ISO) + magicalCamera.getPrivateInformation().getIso() + "\n");

                        if (Utils.notNullNotFill(magicalCamera.getPrivateInformation().getOrientation()))
                            builderInformation.append(getString(R.string.info_data_orientation_photo) + magicalCamera.getPrivateInformation().getOrientation() + "\n");

                        if (Utils.notNullNotFill(magicalCamera.getPrivateInformation().getImageLength()))
                            builderInformation.append(getString(R.string.info_data_image_lenght) + magicalCamera.getPrivateInformation().getImageLength() + "\n");

                        if (Utils.notNullNotFill(magicalCamera.getPrivateInformation().getImageWidth()))
                            builderInformation.append(getString(R.string.info_data_image_width) + magicalCamera.getPrivateInformation().getImageWidth() + "\n");

                        if (Utils.notNullNotFill(magicalCamera.getPrivateInformation().getModelDevice()))
                            builderInformation.append(getString(R.string.info_data_model_device) + magicalCamera.getPrivateInformation().getModelDevice() + "\n");

                        if (Utils.notNullNotFill(magicalCamera.getPrivateInformation().getMakeCompany()))
                            builderInformation.append(getString(R.string.info_data_make_company) + magicalCamera.getPrivateInformation().getMakeCompany() + "\n");

                        new MaterialDialog.Builder(activity)
                                .title(getString(R.string.message_see_photo_information))
                                .content(builderInformation.toString())
                                .positiveText(getString(R.string.message_ok))
                                .show();
                    } else {
                        Utils.viewSnackBar(getString(R.string.error_not_have_data), principalLayout);
                    }
                }
            }
        });

        btnGoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
                activity.onBackPressed();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //this is for rotate picture in this method
        //magicalCamera.resultPhoto(requestCode, resultCode, data, MagicalCamera.ORIENTATION_ROTATE_180);

        if (resultCode == Activity.RESULT_OK) {
            //you should to call the method ever, for obtain the bitmap photo (= magicalCamera.getPhoto())
            magicalCamera.resultPhoto(requestCode, resultCode, data);

            if (Utils.validateMagicalCameraNull(activity, principalLayout, magicalCamera)) {
                floatingBtnMenu.setVisibility(View.VISIBLE);
                saveImage.setVisibility(View.VISIBLE);
                //set the photo in image view
                imageView.setImageBitmap(magicalCamera.getPhoto());

                //save the picture inmemory device, and return the physical path of this photo
                String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "myTestPhoto", MagicalCamera.JPEG, true);

                if (path != null) {
                    Utils.viewSnackBar(getString(R.string.message_save_manual) + path, principalLayout);
                } else {
                    Utils.viewSnackBar(getString(R.string.error_dont_save_photo), principalLayout);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Map<String, Boolean> map = magicalPermissions.permissionResult(requestCode, permissions, grantResults);
        for (String permission : map.keySet()) {
            Log.d("PERMISSIONS", permission + " was: " + map.get(permission));
        }
    }

    private void setUIComponents(View rootView) {
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        btntakephoto = (ImageButton) rootView.findViewById(R.id.btntakephoto);
        btnselectedphoto = (ImageButton) rootView.findViewById(R.id.btnselectedphoto);
        btnGoTo = (Button) rootView.findViewById(R.id.btnGoTo);
        texttitle = (TextView) rootView.findViewById(R.id.texttitle);
        saveImage = (ImageButton) rootView.findViewById(R.id.saveImage);
        floatingBtnRotate = (FloatingActionButton) rootView.findViewById(R.id.floatingBtnRotate);
        floatingBtnFacialRecognition = (FloatingActionButton) rootView.findViewById(R.id.floatingBtnFacialRecognition);
        floatingBtnPhotoInformation = (FloatingActionButton) rootView.findViewById(R.id.floatingBtnPhotoInformation);
        floatingBtnSeeString64 = (FloatingActionButton) rootView.findViewById(R.id.floatingBtnSeeString64);
        frame = (FrameLayout) rootView.findViewById(R.id.frame);
        principalLayout = rootView.findViewById(R.id.principalLayout);
        floatingBtnMenu = (FloatingActionMenu) rootView.findViewById(R.id.floatingBtnMenu);


        floatingBtnMenu.setVisibility(View.GONE);
        saveImage.setVisibility(View.GONE);
        btnGoTo.setText(getString(R.string.go_to_activity));
        texttitle.setText(getString(R.string.title_fragment));
    }
}
