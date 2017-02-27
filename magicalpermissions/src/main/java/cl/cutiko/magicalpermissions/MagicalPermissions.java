package cl.cutiko.magicalpermissions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cutiko on 25-02-17.
 */

public class MagicalPermissions {

    private static final int RC_PERMISSIONS_ACTIVITY = 878;
    private static final int RC_PERMISSIONS_FRAGMENT = 879;
    private Activity activity;
    private Fragment fragment;
    private String[] permissions;
    private Runnable task;

    public MagicalPermissions(Activity activity, String[] permissions) {
        this.activity = activity;
        this.permissions = permissions;
    }

    public MagicalPermissions(Fragment fragment, String[] permissions) {
        this.fragment = fragment;
        this.permissions = permissions;
    }

    private boolean permissionsNeeded() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    public boolean askPermissions(final Runnable task) {
        //In case the developer want to do something after the permissions are granted
        this.task = task;
        boolean validation = permissionsNeeded();
        if (permissionsNeeded()) {
            requestPermissions();
        }
        return validation;
    }

    @SuppressLint("NewApi")
    private void requestPermissions() {
        final Context context = (activity != null) ? activity : fragment.getContext();

        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        //Then only if there are permissions to be requested, only request those
        if (permissionList.size() > 0) {
            //If there are permissions to be requested then we can redefine the original array with the ones we have to take care
            permissions = permissionList.toArray(new String[permissionList.size()]);
            if (activity != null) {
                activity.requestPermissions(permissions, RC_PERMISSIONS_ACTIVITY);
            } else {
                fragment.requestPermissions(permissions, RC_PERMISSIONS_FRAGMENT);
            }
        }
    }

    public void permissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (RC_PERMISSIONS_ACTIVITY == requestCode || RC_PERMISSIONS_FRAGMENT == requestCode) {
            boolean validation = true;
            for (int i = 0; i < permissions.length; i++) {
                if (PackageManager.PERMISSION_GRANTED != grantResults[i]) {
                    validation = false;
                }
            }
            if (validation && task != null) {
                task.run();
            }
        }
    }
}
