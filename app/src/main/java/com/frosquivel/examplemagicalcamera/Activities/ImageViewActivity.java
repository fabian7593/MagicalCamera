package com.frosquivel.examplemagicalcamera.Activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.frosquivel.examplemagicalcamera.R;
import com.frosquivel.examplemagicalcamera.Utils.Utils;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.Utilities.ConvertSimpleImage;

/**
 * Created by Fabian on 02/03/2017.
 * This activity show the bitmap image and have the posibility of resie the bitmap
 */

public class ImageViewActivity extends Activity {
    //this is the image view for show your picture taken in big size
    private ImageView bigImageView;
    private Button btnChangeSize;

    private LinearLayout progressLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        bigImageView = (ImageView) findViewById(R.id.bigImageView);
        btnChangeSize = (Button) findViewById(R.id.btnChangeSize);
        progressLoadingIndicator = (LinearLayout) findViewById(R.id.progressLoadingIndicator);
        bigImageView.setImageBitmap(Utils.magicalCameraBitmap);

        btnChangeSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, String>() {
                    protected void onPreExecute() {
                        progressLoadingIndicator.setVisibility(View.VISIBLE);
                    }

                    protected String doInBackground(Void... params) {
                        try {
                            byte[] arrayBytesFromBitmap = ConvertSimpleImage.bitmapToBytes(Utils.magicalCameraBitmap,
                                    MagicalCamera.PNG);

                            Utils.magicalCameraBitmap = ConvertSimpleImage.resizeImageRunTime(arrayBytesFromBitmap,
                                    Utils.magicalCameraBitmap.getWidth() - 150,
                                    Utils.magicalCameraBitmap.getHeight() - 150, false);
                        }catch(Exception ev){}
                        return null;
                    }

                    protected void onPostExecute(String msg) {
                        bigImageView.setImageBitmap(Utils.magicalCameraBitmap);
                        progressLoadingIndicator.setVisibility(View.GONE);
                    }
                }.execute();
            }
        });
    }
}
