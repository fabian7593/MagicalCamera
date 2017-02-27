package com.frosquivel.examplemagicalcamera;

import android.Manifest;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import cl.cutiko.magicalpermissions.MagicalPermissions;

/**
 * A placeholder fragment containing a simple view.
 */
public class PermissionsActivityFragment extends Fragment {

    private MagicalPermissions magicalPermissions;

    public PermissionsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_permissions, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        magicalPermissions = new MagicalPermissions(this, permissions);
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "something", Toast.LENGTH_SHORT).show();
            }
        };
        Button button = (Button) view.findViewById(R.id.permissionsBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                magicalPermissions.askPermissions(runnable);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        magicalPermissions.permissionResult(requestCode, permissions, grantResults);
    }
}
