package com.frosquivel.examplemagicalcamera.Activities;
/*
Copyright 2013 Square, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.frosquivel.examplemagicalcamera.R;
import com.frosquivel.examplemagicalcamera.Utils.Utils;
import com.squareup.picasso.Picasso;

/**
 * Created by Fabian on 05/03/2017.
 * This class only shown the about us of the developers of magical camera
 */

public class AboutUsActivity extends AppCompatActivity{

    private ImageView imageCreator;
    private ImageView imageContributor1;
    private ImageView imageContributor2;

    private ImageButton btnGitContributor1;
    private ImageButton btnGitContributor2;
    private ImageButton btnGitCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        imageCreator = (ImageView) findViewById(R.id.imageCreator);
        imageContributor1 = (ImageView) findViewById(R.id.imageContributor1);
        imageContributor2 = (ImageView) findViewById(R.id.imageContributor2);

        btnGitContributor1 = (ImageButton) findViewById(R.id.btnGitContributor1);
        btnGitContributor2 = (ImageButton) findViewById(R.id.btnGitContributor2);
        btnGitCreator = (ImageButton) findViewById(R.id.btnGitCreator);

        Picasso.with(this)
                .load(getString(R.string.dev_image_creator))
                .into(imageCreator);

        Picasso.with(this)
                .load(getString(R.string.dev_image_contributor_1))
                .into(imageContributor1);

        Picasso.with(this)
                .load(getString(R.string.dev_image_contributor_2))
                .into(imageContributor2);

        btnGitContributor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.goToWebView(getString(R.string.dev_link_git_contributor_1), AboutUsActivity.this);
            }
        });

        btnGitContributor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.goToWebView(getString(R.string.dev_link_git_contributor_2), AboutUsActivity.this);
            }
        });

        btnGitCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.goToWebView(getString(R.string.dev_link_git_creator), AboutUsActivity.this);
            }
        });
    }
}
