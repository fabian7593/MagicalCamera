package cl.cutiko.magicalpermissions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.support.v4.app.Fragment;

/**
 * Created by cutiko on 25-02-17.
 */

public class MagicalPermissions {

    private static final int RC_PERMISSIONS_ACTIVITY = 878;
    private static final int RC_PERMISSIONS_FRAGMENT = 879;
    private Activity activity;
    private Fragment fragment;
    private String[] permissions;

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

    public boolean askPermissions() {
        boolean validation = permissionsNeeded();
        if (permissionsNeeded()) {
            requestPermissions();
        }
        return validation;
    }

    @SuppressLint("NewApi")
    private void requestPermissions() {
        if (activity != null) {
            activity.requestPermissions(permissions, RC_PERMISSIONS_ACTIVITY);
        } else {
            fragment.requestPermissions(permissions, RC_PERMISSIONS_FRAGMENT);
        }
    }



}
