package com.frosquivel.examplemagicalcamera;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class FragmentSample extends Fragment{

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
    private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 100;

    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        //obtain the activity of the parent
        activity = getActivity();

        //instance magical camera
        magicalCamera = new MagicalCamera(activity ,RESIZE_PHOTO_PIXELS_PERCENTAGE, ActivityForFragment.permissionGranted);

        imageView =  (ImageView) rootView.findViewById(R.id.imageView);
        imgRotate =  (Button) rootView.findViewById(R.id.imgRotate);
        btntakephoto =  (Button) rootView.findViewById(R.id.btntakephoto);
        btnselectedphoto =  (Button) rootView.findViewById(R.id.btnselectedphoto);
        btnGoTo =  (Button) rootView.findViewById(R.id.btnGoTo);
        texttitle =  (TextView) rootView.findViewById(R.id.texttitle);
        texttitle.setText("Fragment Example");
        btnGoTo.setText("Go to Activity");
        btnSeeData = (Button) rootView.findViewById(R.id.btnSeeData);
        btnFacialRecognition = (Button) rootView.findViewById(R.id.btnFacialRecognition);
        saveImage = (Button) rootView.findViewById(R.id.saveImage);


        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(magicalCamera != null) {
                    if (magicalCamera.getPhoto() != null) {
                        //save the photo in your memory external or internal of your device
                        String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "myTestPhoto", MagicalCamera.JPEG, true);
                        if (path != null) {
                            Toast.makeText(activity,
                                    "The photo is save manual in device, please check this path: " + path,
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(activity,
                                    "Sorry your photo dont write in devide, please contact with fabian7593@gmail and say this error",
                                    Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(activity,
                                "Your image is null, please select or take one",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(activity,
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
                        Toast.makeText(activity,
                                "Your image is null, please select or take one",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(activity,
                            "Please initialized magical camera, maybe in static context for use in all activity",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        
        btntakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is the form to take picture in fragment
                if (magicalCamera.takeFragmentPhoto()) {
                    startActivityForResult(magicalCamera.getIntentFragment(),
                            MagicalCamera.TAKE_PHOTO);
                }

            }
        });

        btnselectedphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is the form to select picture of device
                if (magicalCamera.selectedFragmentPicture()) {
                    startActivityForResult(
                            Intent.createChooser(magicalCamera.getIntentFragment(), "My Header Example"),
                            MagicalCamera.SELECT_PHOTO);
                }
            }
        });

        btnGoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to acivity
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });


        btnFacialRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(magicalCamera != null){
                    if(magicalCamera.getPhoto() != null){
                        //call the face detector
                        imageView.setImageBitmap(magicalCamera.faceDetector());
                        //imageView.setImageBitmap(magicalCamera.faceDetector(10, Color.GREEN));
                    }else{
                        Toast.makeText(activity,
                                "Your image is null, please select or take one",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(activity,
                            "Please initialized magical camera, maybe in static context for use in all activity",
                            Toast.LENGTH_SHORT).show();
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
                        //question in have data
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

                        new MaterialDialog.Builder(activity)
                                .title("See photo information")
                                .content(builderInformation.toString())
                                .positiveText("ok")
                                .show();
                    }else{
                        Toast.makeText(activity,
                                "You dont have data to show because the real path photo is wrong contact with fabian7593@gmail.com",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(activity,
                            "You dont have data to show because the photo is null (your photo isn't in memory device, plase save your photo in gallery first), contact with fabian7593@gmail.com",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
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
        magicalCamera.resultPhoto(requestCode, resultCode, data, MagicalCamera.ORIENTATION_ROTATE_NORMAL);

        if(magicalCamera.getPhoto()!=null) {
            imageView.setImageBitmap(magicalCamera.getPhoto());

            String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "myTestPhoto", MagicalCamera.JPEG, true);

            if (path != null) {
                Toast.makeText(activity, "The photo is save in device, please check this path: " + path, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Sorry your photo dont write in devide, please contact with fabian7593@gmail and say this error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        magicalCamera.permissionGrant(requestCode, permissions, grantResults);
    }

}
