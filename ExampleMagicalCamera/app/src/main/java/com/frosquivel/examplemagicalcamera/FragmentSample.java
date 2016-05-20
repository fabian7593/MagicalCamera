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

import com.frosquivel.magicalcamera.MagicalCamera;

/**
 * Created by          Fabián Rosales Esquivel
 * Visit my web page   http://www.frosquivel.com
 * Visit my blog       http://www.frosquivel.com/blog
 * Created Date        on 5/19/16
 * This is an android library to take easy picture
 */
public class FragmentSample extends Fragment {

    private Activity activity;

    private ImageView imageView;
    private Button btntakephoto;
    private Button btnselectedphoto;
    private Button btnGoTo;
    private TextView texttitle;

    private MagicalCamera magicalCamera;
    private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 3000;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        activity = getActivity();
        magicalCamera = new MagicalCamera(activity ,RESIZE_PHOTO_PIXELS_PERCENTAGE);

        imageView =  (ImageView) rootView.findViewById(R.id.imageView);
        btntakephoto =  (Button) rootView.findViewById(R.id.btntakephoto);
        btnselectedphoto =  (Button) rootView.findViewById(R.id.btnselectedphoto);
        btnGoTo =  (Button) rootView.findViewById(R.id.btnGoTo);
        texttitle =  (TextView) rootView.findViewById(R.id.texttitle);
        texttitle.setText("Fragment Example");
        btnGoTo.setText("Go to Activity");

        btntakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(magicalCamera.takeFragmentPhoto()){
                    startActivityForResult(magicalCamera.getIntentFragment(),
                            MagicalCamera.TAKE_PHOTO);
                }

            }
        });

        btnselectedphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(magicalCamera.selectedFragmentPicture()){
                    startActivityForResult(
                            Intent.createChooser(magicalCamera.getIntentFragment(),  "My Header Example"),
                            MagicalCamera.SELECT_PHOTO);
                }
            }
        });

        btnGoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });


        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        magicalCamera.resultPhoto(requestCode, resultCode, data);
        imageView.setImageBitmap(magicalCamera.getMyPhoto());

        if(magicalCamera.savePhotoInMemoryDevice(magicalCamera.getMyPhoto(),"myTestPhoto", MagicalCamera.JPEG, true)){
            Toast.makeText(activity, "The photo is save in device, please check this", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(activity, "Sorry your photo dont write in devide, please contact with fabian7593@gmail and say this error", Toast.LENGTH_SHORT).show();
        }
    }

}
