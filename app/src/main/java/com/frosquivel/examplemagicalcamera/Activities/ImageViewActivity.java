package com.frosquivel.examplemagicalcamera.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.frosquivel.examplemagicalcamera.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        bigImageView = (ImageView) findViewById(R.id.bigImageView);
        btnChangeSize = (Button) findViewById(R.id.btnChangeSize);
        bigImageView.setImageBitmap(MainActivity.magicalCameraBitmap);

        btnChangeSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                byte[] arrayBytesFromBitmap = ConvertSimpleImage.bitmapToBytes(MainActivity.magicalCameraBitmap,
                        MagicalCamera.PNG);

                MainActivity.magicalCameraBitmap =  ConvertSimpleImage.resizeImageRunTime(arrayBytesFromBitmap,
                        300,
                        500, false);

                bigImageView.setImageBitmap(MainActivity.magicalCameraBitmap);
            }
        });
    }
}
