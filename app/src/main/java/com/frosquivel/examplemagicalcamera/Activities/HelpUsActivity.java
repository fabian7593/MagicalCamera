package com.frosquivel.examplemagicalcamera.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.frosquivel.examplemagicalcamera.R;
import com.frosquivel.examplemagicalcamera.Utils.Utils;

/**
 * Created by Fabian on 30/03/2017.
 */

public class HelpUsActivity extends AppCompatActivity {

    private ImageView imgEmail;
    private ImageView imgGithub;
    private ImageView imgYouTube;
    private ImageView imgShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_us);

        imgEmail = (ImageView) findViewById(R.id.imgEmail);
        imgGithub = (ImageView) findViewById(R.id.imgGithub);
        imgYouTube = (ImageView) findViewById(R.id.imgYouTube);
        imgShare = (ImageView) findViewById(R.id.imgShare);


        imgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.sendMeAnEmail(HelpUsActivity.this);
            }
        });

        imgGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.goToWebView(getString(R.string.link_documentation), HelpUsActivity.this);
            }
        });

        imgYouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.openScheme(HelpUsActivity.this, "youtube://channel/", "UCJnvvHb_vwMwbnZWplkHIfw",
                        "https://www.youtube.com/channel/UCJnvvHb_vwMwbnZWplkHIfw", getString(R.string.error_not_open_youtube));
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.sharedApp(HelpUsActivity.this);
            }
        });
    }
}
