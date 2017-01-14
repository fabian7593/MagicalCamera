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
    public static PermissionGranted permissionGranted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionGranted = new PermissionGranted(this);
        permissionGranted.checkCameraPermission();
        permissionGranted.checkReadExternalPermission();
        permissionGranted.checkWriteExternalPermission();
        permissionGranted.checkLocationPermission();
        setContentView(R.layout.activity_activity_for_fragment);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        permissionGranted.permissionGrant(requestCode, permissions, grantResults);
    }

}
