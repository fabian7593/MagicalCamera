package com.frosquivel.magicalcamera;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cutiko on 25-02-17.
 */

public class MagicalPermissions {

    private static final int RC_PERMISSIONS_ACTIVITY = 878;
    private static final int RC_PERMISSIONS_FRAGMENT = 879;
    private Activity activity;
    private Fragment fragment;
    private android.support.v4.app.Fragment fragmentV4;
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

    public MagicalPermissions(android.support.v4.app.Fragment fragmentV4, String[] permissions) {
        this.fragmentV4 = fragmentV4;
        this.permissions = permissions;
    }


    public boolean permissionsNeeded() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    public void askPermissions(final Runnable task) {
        //In case the developer want to do something after the permissions are granted
        this.task = task;
        if (permissionsNeeded()) {
            requestPermissions();
        } else {
            runPendingTask();
        }
    }

    private void requestPermissions() {
        Context context = null;
        if (activity != null) {
            context = activity;
        } else {
            context = (fragment != null) ? fragment.getActivity() : fragmentV4.getContext();
        }

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
            //Request permissions is being lint, but that is not a problem in the askPermissions method there is a validation to prevent it in lowe API
            //Adding a suppress warning annotation is worst because since android studio 2.3 annotated methods can be marked as dangerous
            if (activity != null) {
                activity.requestPermissions(permissions, RC_PERMISSIONS_ACTIVITY);
            } else  if (fragment != null) {
                fragment.requestPermissions(permissions, RC_PERMISSIONS_FRAGMENT);
            } else if (fragmentV4 != null){
                fragmentV4.requestPermissions(permissions, RC_PERMISSIONS_FRAGMENT);
            }
        } else {
            //But if every permission is granted then go a head and do what you want
            runPendingTask();
        }
    }

    public Map<String, Boolean> permissionResult(int requestCode, String[] permissions, int[] grantResults) {
        //Returning a map is better to let developer know the permissions and what happened
        Map<String, Boolean> map = new HashMap<>();
        if (RC_PERMISSIONS_ACTIVITY == requestCode || RC_PERMISSIONS_FRAGMENT == requestCode) {
            boolean validation = true;
            for (int i = 0; i < permissions.length; i++) {
                int result = grantResults[i];
                map.put(permissions[i], (result == PackageManager.PERMISSION_GRANTED));
                if (PackageManager.PERMISSION_GRANTED != result) {
                    validation = false;
                }
            }
            if (validation) {
                runPendingTask();
            }
        }
        return map;
    }

    private void runPendingTask(){
        if (task != null) {
            task.run();
        }
    }
}
