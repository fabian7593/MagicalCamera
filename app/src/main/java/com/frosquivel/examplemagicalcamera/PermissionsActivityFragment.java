package com.frosquivel.examplemagicalcamera;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        magicalPermissions = new MagicalPermissions(this, new String[]{Manifest.permission.CAMERA});
        Button button = (Button) view.findViewById(R.id.permissionsBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magicalPermissions.askPermissions();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("PERMISSION_RESULT", "fragment");
    }
}
