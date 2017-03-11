package com.frosquivel.examplemagicalcamera;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.frosquivel.magicalcamera.MagicalPermissions;

public class PermissionsActivity extends AppCompatActivity {

    private MagicalPermissions magicalPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        magicalPermissions = new MagicalPermissions(PermissionsActivity.this, new String[]{Manifest.permission.CAMERA});
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PermissionsActivity.this, "Oli", Toast.LENGTH_SHORT).show();
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
