<<<<<<< HEAD:app/src/main/java/com/frosquivel/examplemagicalcamera/Fragments/ActivityForFragment.java
package com.frosquivel.examplemagicalcamera.Fragments;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import com.frosquivel.examplemagicalcamera.R;
import com.frosquivel.magicalcamera.Functionallities.PermissionGranted;
=======
package com.frosquivel.examplemagicalcamera;

import android.app.Activity;
import android.os.Bundle;
>>>>>>> 51004cf7c22cc88f8e0ee6ed99d4b66109cfb6e7:app/src/main/java/com/frosquivel/examplemagicalcamera/ActivityForFragment.java

/**
 * Created by          Fabián Rosales Esquivel
 * Visit my web page   http://www.frosquivel.com
 * Visit my blog       http://www.frosquivel.com/blog
 * Created Date        on 5/19/16
 * This is an android library to take easy picture
 */
public class ActivityForFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD:app/src/main/java/com/frosquivel/examplemagicalcamera/Fragments/ActivityForFragment.java

        permissionGranted = new PermissionGranted(this);
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            permissionGranted.checkAllMagicalCameraPermission();
        }

        setContentView(R.layout.activity_activity_for_fragment);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //callthe permission grant
        permissionGranted.permissionGrant(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
=======
        setContentView(R.layout.activity_activity_for_fragment);
    }
>>>>>>> 51004cf7c22cc88f8e0ee6ed99d4b66109cfb6e7:app/src/main/java/com/frosquivel/examplemagicalcamera/ActivityForFragment.java
}
