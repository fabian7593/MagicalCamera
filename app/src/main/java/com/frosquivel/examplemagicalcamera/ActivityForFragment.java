package com.frosquivel.examplemagicalcamera;
import android.app.Activity;
import android.os.Bundle;
import com.frosquivel.magicalcamera.Functionallities.PermissionGranted;

/**
 * Created by          Fabián Rosales Esquivel
 * Visit my web page   http://www.frosquivel.com
 * Visit my blog       http://www.frosquivel.com/blog
 * Created Date        on 5/19/16
 * This is an android library to take easy picture
 */
public class ActivityForFragment extends Activity {

    //You need to declare permission granted in activity parent fragment
    //in static context for use in fragment
    public static PermissionGranted permissionGranted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialized the permission in onCreate event
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
        setContentView(R.layout.activity_activity_for_fragment);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //callthe permission grant
        permissionGranted.permissionGrant(requestCode, permissions, grantResults);
    }
}
