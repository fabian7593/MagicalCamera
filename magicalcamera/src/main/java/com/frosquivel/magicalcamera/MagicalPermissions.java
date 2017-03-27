package com.frosquivel.magicalcamera;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by cutiko on 25-02-17.
 * This library helps us for request the necessary permissions for use magical camera
 */

public class MagicalPermissions {

    private static final int RC_PERMISSIONS_ACTIVITY = 878;
    private static final int RC_PERMISSIONS_FRAGMENT = 879;
    private Activity activity;
    private Fragment fragment;
    private android.support.v4.app.Fragment fragmentV4;
    private String[] permissions;
    private String[] allPermissions;
    private Runnable task;
    //Constants for permissions
    public static final String CAMERA = "android.permission.CAMERA";
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    public static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    public static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";

    private static Map<String, Boolean> mapForResponsePermissions = new HashMap<>();

    public MagicalPermissions(Activity activity, String[] permissions) {
        this.activity = activity;
        this.permissions = permissions;
        this.allPermissions = permissions;
    }

    public MagicalPermissions(Fragment fragment, String[] permissions) {
        this.fragment = fragment;
        this.permissions = permissions;
        this.allPermissions = permissions;
    }

    public MagicalPermissions(android.support.v4.app.Fragment fragmentV4, String[] permissions) {
        this.fragmentV4 = fragmentV4;
        this.permissions = permissions;
        this.allPermissions = permissions;
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

    public void askPermissions(final Runnable task, String operationType) {
        //In case the developer want to do something after the permissions are granted
        this.task = task;
        if (permissionsNeeded()) {
            requestPermissions(operationType);
        } else {
            runPendingTask();
        }
    }

    private void requestPermissions() {
        executeRequestPermission(null);
    }

    private void requestPermissions(String operationType) {
        executeRequestPermission(operationType);
    }

    private void executeRequestPermission(String operationType) {
        Context context = null;
        if (activity != null) {
            context = activity;
        } else {
            context = (fragment != null) ? fragment.getActivity() : fragmentV4.getContext();
        }

        List<String> permissionListFault = new ArrayList<>();
        //obtain the list of permissions faults
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionListFault.add(permission);
            }
        }

        boolean runPendingTaskFlag = true;
        //Then only if there are permissions to be requested, only request those
        if (permissionListFault.size() > 0) {
            //If there are permissions to be requested then we can redefine the original array with the ones we have to take care
            permissions = permissionListFault.toArray(new String[permissionListFault.size()]);
            runPendingTaskFlag = false;
        } else {
            runPendingTaskFlag = true;
            //But if every permission is granted then go a head and do what you want
            runPendingTask();
        }

        //if you have the list of permissions
        if (!runPendingTaskFlag && this.mapForResponsePermissions.size() > 0) {

            List<String> hasPermissions = new ArrayList<>();

            //all of permissions that you need (include the permissions fault and success)
            for (int x = 0; this.allPermissions.length > x; x++) {
                Iterator it = this.mapForResponsePermissions.entrySet().iterator();

                boolean isCurrentPermissionRequired = false;
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();

                    if (this.allPermissions[x].equals(pair.getKey())) {
                        isCurrentPermissionRequired = true;
                        boolean permissionGranted = mapForResponsePermissions.get(pair.getKey().toString());
                        if (permissionGranted) {
                            if (operationType.equals(CAMERA)) {
                                isCurrentPermissionRequired = false;
                            } else if (operationType.equals(READ_EXTERNAL_STORAGE)) {
                                isCurrentPermissionRequired = false;
                            } else if (operationType.equals(WRITE_EXTERNAL_STORAGE)) {
                                isCurrentPermissionRequired = false;
                            } else if (operationType.equals(ACCESS_COARSE_LOCATION)) {
                                isCurrentPermissionRequired = false;
                            } else if (operationType.equals(ACCESS_FINE_LOCATION)) {
                                isCurrentPermissionRequired = false;
                            }
                        }
                    }
                }

                //if the operation selected have a permission granted true
                //then add in list of haspermissions
                if (!isCurrentPermissionRequired) {
                    hasPermissions.add(this.allPermissions[x]);
                }
            }

            //if the permissions is equeals to the selected permission
            //run the task...
            for (int x = 0; hasPermissions.size() > x; x++) {
                if (hasPermissions.get(x).equals(operationType)) {
                    runPendingTask();
                }
            }

            //if not have all permissions, request then.
        }else if(!runPendingTaskFlag){
            //Request permissions is being lint, but that is not a problem in the askPermissions method there is a validation to prevent it in lowe API
            //Adding a suppress warning annotation is worst because since android studio 2.3 annotated methods can be marked as dangerous
            if (activity != null) {
                activity.requestPermissions(permissions, RC_PERMISSIONS_ACTIVITY);
            } else if (fragment != null) {
                fragment.requestPermissions(permissions, RC_PERMISSIONS_FRAGMENT);
            } else if (fragmentV4 != null) {
                fragmentV4.requestPermissions(permissions, RC_PERMISSIONS_FRAGMENT);
            }
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
        mapForResponsePermissions = map;
        return map;
    }

    private void runPendingTask() {
        if (task != null) {
            task.run();
        }
    }
}
